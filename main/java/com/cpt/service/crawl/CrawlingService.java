package com.cpt.service.crawl;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpt.cmm.CrawlUtil;
import com.cpt.cmm.TelegramUtil;
import com.cpt.controller.WebCrawlingController;
import com.cpt.service.coin.CoinService;

@Service
public class CrawlingService {
	
	private static final Logger log = LoggerFactory.getLogger(CrawlingService.class);
	
	@Autowired
	private CoinService coinService;
	
	public void coinpanCrawling (String ticker, String targetUrl) {
		
		// 해당 티커의 코인 관련 정보를 가져온다.
		Map<String, Object> coinInfo = coinService.selectCoinInfo(ticker);

		String isCollect = (String) coinInfo.get("isCollect"); // 크롤링대상여부
		String isAlarm = (String) coinInfo.get("isAlarm"); // 텔레그램 알림 여부
		String channelChatId = (String) coinInfo.get("telChId"); // 텔레그램 채널 아이디
		String myChatId = (String) coinInfo.get("myChatId"); // 텔레그램 채널 아이디
		String botToken = (String) coinInfo.get("telBotToken"); // 텔레그램 봇토큰
		
		String keyword = (String) coinInfo.get("keywordArr"); // 필터링할 검색어
		String keywordArr[] = (keyword != null) ? keyword.split("/") : null;

		String except = (String) coinInfo.get("exceptArr"); // 제외할 검색어
		String exceptArr[] = (except != null) ? except.split("/") : null;
		
//		log.debug("코인정보 결과 : " + coinInfo);
		
//		업비트 API로 해당 코인의 전일종가, 전일대비, 현재가, 가격변동추세를 가져온다.
		Map<String, Object> paramMap = coinService.getCurrentPrice(ticker);

		try {

			// 크롤링 대상인 경우만 실행
			if("Y".equals(isCollect)) {
				
				// 해당 커뮤니티의 화면 웹리소스를 가져온다
				Document doc = Jsoup.connect(targetUrl).get();
				
				// 웹리소스 내, 태그로 특정부분을 지정
				Elements elem = doc.select("#board_list > table > tbody > tr");
				
				// 가져온 요소 출력
				for (int i = elem.size() - 1; i > -1; i--) {
	//				
					// 공지사항 글 여부
					boolean isNotice = elem.get(i).hasClass("notice");
					
					if(isNotice == false) {
						// 해당 tr의 td들을 다시 담음
						Elements tdContents = elem.get(i).select("td"); 
						
						String contTit = tdContents.get(1).text(); // 게시물 제목
						
						boolean isCheck = CrawlUtil.filterKeyword(contTit, keywordArr, exceptArr);
						
						if(isCheck) {
							
							// 해당관련 태그의 이전 게시물 번호
							int prevNum = coinService.selectLastContentNo(paramMap);
	//						System.out.println("이전 게시물 " + prevNum);
							
							int contNum = Integer.parseInt(tdContents.get(0).text()); // 게시물 번호
	//						System.out.println("지금 게시물 " + contNum);
							
							// 이전 게시물보다 이후에 올라온 글이면 글을 저장
							if(prevNum < contNum) {
								paramMap.put("contNo", contNum); // 현재 게시물번호
								paramMap.put("contTitle", contTit); // 현재 게시물 제목
								coinService.insertCheckContent(paramMap);
	
								// 텔레그램에 일러바침
								TelegramUtil.telegramMsgSend(false, isAlarm, botToken, channelChatId, contTit, paramMap);
							}
						}
					} // end if(isNotice == false)
					
				} //end foreach
			} // end if("Y".equals(isCollect)) 
			
		} catch (Exception e) {
			e.printStackTrace();
			
			// 에러는 봇채널로 보낸다.
			TelegramUtil.telegramMsgSend(true, isAlarm, botToken, myChatId, "", null);
		}
		
	}

}

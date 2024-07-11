package com.cpt.service.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cpt.cmm.ComUtil;
import com.cpt.controller.TestController;
import com.cpt.dto.TelegramMessageContent;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Service
public class TestService {
	
	private static final Logger log = LoggerFactory.getLogger(TestService.class);
	
//	@Autowired(required=true)
	@Autowired
    private TestMapper testMapper;
	
	@Value("${cpt.telegrambot.token}")
	private String token;

	@Value("${cpt.telegrambot.chat-id}")
	private String chatId;

	@Value("${cpt.telegrambot.channel.chat-id}")
	private String channelChatId;

	// 챗봇 실행여부
	@Value("${cpt.telegrambot.onoff}")
	private String onOff;
	
	@Value("${cpt.target.url}")
    private String targetUrl;
	
	/**
	 * 샘플데이터 목록 조회
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> selectSampleList(Map<String, Object> paramMap) {
		
		System.out.println("onOff = " + onOff);

		Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        
        // 페이지 카운터 저장
        Map<String, Object> pageMap = ComUtil.setPagenation(paramMap);
        paramMap.putAll(pageMap);
        
        // 조회 결과
        List<Map<String, Object>> gridData = testMapper.selectSampleList(paramMap);
        int gridDataCnt = gridData.size();
        
        paramMap.put("resultCnt", gridDataCnt);
        
        // 조회된 데이터 갯수가 1개이상일 경우에만 결과를 반환 
        if (gridDataCnt > 0) {
            dataMap.put("resultCd", true);
            dataMap.put("resultMsg", "조회완료");
            dataMap.put("gridData", gridData);
        } else {
            dataMap.put("resultCd", false);
            dataMap.put("resultMsg", "조회 내역이 없습니다.");
        }

        resultMap.put("data", dataMap);
        
		return resultMap;
	}
	
	
	/**
	 * 전송 실행
	 * @throws RestClientException 
	 */
//	public void telegramMsgSend(boolean isError) {
//		
//		try {
//			String sendUrl = "https://api.telegram.org/bot" + token + "/sendMessage";
//
//			log.debug("111");
//			
//			if("on".equals(onOff)) {
//				String sendMessage = sendMessage(isError);
//				
//				log.debug("112");
//				TelegramMessageContent msgCont = new TelegramMessageContent(channelChatId, sendMessage);
//		        String param = new Gson().toJson(msgCont);
//		        
//		        RestTemplate restTemplate = new RestTemplate();
//		        HttpHeaders headers = new HttpHeaders();
//		        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
//		        log.debug("113");
//	
//		        // send the post request
//		        HttpEntity<String> entity = new HttpEntity<>(param, headers);
//		        restTemplate.postForEntity(sendUrl, entity, String.class);
//			}
//			
//			log.debug("114");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			// TODO: handle exception
//		}
//	}
//	
//	/**
//	 * 보낼 메세지
//	 * @return
//	 */
//	private static String sendMessage(boolean isError) {
//	   StringBuilder sb = new StringBuilder();
//
//	   if (isError) {
//		   sb.append("[Notification] 크롤링 실패");
//		} else {
//			sb.append("[Notification]").append(System.getProperty("line.separator"))
//			  .append("[Name] : ").append("Tester").append(System.getProperty("line.separator"))
//			  .append("[Message] : ").append("테스트 메시지 !!");
//		}
//
//	   return sb.toString();
//	}

}

package com.cpt.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.cmm.ComUtil;
import com.cpt.cmm.CrawlUtil;
import com.cpt.cmm.TelegramUtil;
import com.cpt.service.coin.CoinService;
import com.cpt.service.crawl.CrawlingService;

@RestController
@RequestMapping("/coinpan")
public class WebCrawlingController {
	
	private static final Logger log = LoggerFactory.getLogger(WebCrawlingController.class);
	
	@Value("${cpt.target.url}")
    private String targetUrl;
	
	@Autowired
	CoinService coinService;
	
	@Autowired
	CrawlingService crawlingService;
	
	//	5초 주기 0/5 * * * * *
	//	1분 주기 0 * * * * *
	//	5분 주기 0 */5 * * * *
	//	매일 자정 0 0 0 * * *

	/**
	 * 비트코인 BTC 관련 크롤링
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@Scheduled(cron="0 */1 * * * *")	
	public void crawingBTCScheduling() throws Exception {
		String ticker = "BTC";
		crawlingService.coinpanCrawling(ticker, targetUrl);
	}

	/**
	 * 카르다노 에이다  
	 * @throws Exception
	 */
//	@Scheduled(cron="0 */1 * * * *")	
	public void crawingAdaScheduling() throws Exception {
		String ticker = "ADA";
		crawlingService.coinpanCrawling(ticker, targetUrl);
	}
	
	
	/**
	 * 테스트용
	 * @throws Exception
	 */
	@PostMapping("/crawling-test")
	public ResponseEntity<Map<String, Object>> crawingTest(@RequestParam Map<String, Object> paramMap) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		log.debug("넘겨받은 파라미터 : " + paramMap);
		
		/* -------------- 파라미터 체크 시작 -------------- */
        Map<String, Object> validMap = new HashMap<>();
        validMap.put("티커", paramMap.get("ticker"));
        String checkedParam = ComUtil.checkParamObj(validMap);

        if(!"".equals(checkedParam)) {
            resultMap.put("data", checkedParam);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
        /* -------------- 파라미터 체크 끝 -------------- */
		
		crawlingService.coinpanCrawling((String) paramMap.get("ticker"), targetUrl);
		
		resultMap.put("크롤링 성공", "텔레그램 알림은 발견된 건수가 있으면 울림.");

		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	

}

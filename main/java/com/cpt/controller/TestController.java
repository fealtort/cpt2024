package com.cpt.controller;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.cmm.ComUtil;
import com.cpt.service.test.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/test")
public class TestController {
	
	private static final Logger log = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
    private TestService testService;
	
	@Value("${cpt.target.url}")
    private String targetUrl;
	
	/**
	 * 목록 데이터를 불러오는 샘플
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/sample")
	public ResponseEntity<Map<String, Object>> testStr(@RequestParam Map<String, Object> paramMap
			) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<>();

		log.debug("넘겨받은 파라미터 : " + paramMap);
		
		/* -------------- 파라미터 체크 시작 -------------- */
        Map<String, Object> validMap = new HashMap<>();
        validMap.put("파라미터첫번째", paramMap.get("param1"));
        String checkedParam = ComUtil.checkParamObj(validMap);

        if(!"".equals(checkedParam)) {
            resultMap.put("data", checkedParam);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
        /* -------------- 파라미터 체크 끝 -------------- */
		
		// 코드는 여기서부터 작성
        resultMap = testService.selectSampleList(paramMap);
        
		log.debug("resultMap : " + resultMap);
		
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	

}

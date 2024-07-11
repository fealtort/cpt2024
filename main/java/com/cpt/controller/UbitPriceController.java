package com.cpt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.cmm.CoinUtil;
import com.cpt.cmm.ComUtil;
import com.cpt.service.coin.CoinService;

@RestController
@RequestMapping("/upbit")
public class UbitPriceController {
	
	private static final Logger log = LoggerFactory.getLogger(UbitPriceController.class);
	
	@Autowired
	private CoinService coinService;
	
	/**
	 * 업비트 전체 상장 코인중 한화거래 지원되는 것만 추출
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/market-krw-all")
	public ResponseEntity<Map<String, Object>> testStr(@RequestParam Map<String, Object> paramMap
			) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<>();

		log.debug("넘겨받은 파라미터 : " + paramMap);
		
		/* -------------- 파라미터 체크 시작 -------------- */
        Map<String, Object> validMap = new HashMap<>();
        validMap.put("코인 코드", paramMap.get("coinCd"));
        String checkedParam = ComUtil.checkParamObj(validMap);

        if(!"".equals(checkedParam)) {
            resultMap.put("data", checkedParam);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
        /* -------------- 파라미터 체크 끝 -------------- */
		
        String url =  "https://api.upbit.com/v1/ticker";
        String param = "?markets=KRW-"+paramMap.get("coinCd");
        
        List<Map<String, Object>> upbitCoinList = CoinUtil.upbitCoinList(url, param, "GET");
       
        // 하나도 등록되어있지 않다면 등록
        int regCoinCnt = coinService.selectCoinListCnt(paramMap);
        if (regCoinCnt == 0) {
        	coinService.insertCoinList(upbitCoinList);
		}

        resultMap.put("result", upbitCoinList);
		
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	
	/**
	 * 업비트 전체 상장 코인중 한화거래 지원되는 것만 추출
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@PatchMapping("/input-filter-text")
	public ResponseEntity<Map<String, Object>> inputFilterText(@RequestParam Map<String, Object> paramMap
			) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<>();

		log.debug("넘겨받은 파라미터 : " + paramMap);
		
		/* -------------- 파라미터 체크 시작 -------------- */
        Map<String, Object> validMap = new HashMap<>();
        validMap.put("코인 코드", paramMap.get("coinCd"));
        validMap.put("필터링 검색어", paramMap.get("filterText"));
       
        String checkedParam = ComUtil.checkParamObj(validMap);

        if(!"".equals(checkedParam)) {
            resultMap.put("data", checkedParam);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
        /* -------------- 파라미터 체크 끝 -------------- */

        // 검색어 입력 
        int resCnt = coinService.updateFilterText(paramMap);
        
        if (resCnt == 0) {
        	resultMap.put("result", "검색어 입력이 반영 되지 않았습니다.");
		} else {
			resultMap.put("result", "검색어 입력이 반영되었습니다.");
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 특정코인의 현재시세 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/current-price")
	public ResponseEntity<Map<String, Object>> currentPrice(@RequestParam Map<String, Object> paramMap
			) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<>();

		log.debug("넘겨받은 파라미터 : " + paramMap);
		
		/* -------------- 파라미터 체크 시작 -------------- */
        Map<String, Object> validMap = new HashMap<>();
        validMap.put("코인 코드", paramMap.get("ticker"));
       
        String checkedParam = ComUtil.checkParamObj(validMap);

        if(!"".equals(checkedParam)) {
            resultMap.put("data", checkedParam);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
        /* -------------- 파라미터 체크 끝 -------------- */
		
        
        resultMap = coinService.getCurrentPrice((String) paramMap.get("ticker"));
        
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	

}

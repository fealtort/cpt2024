package com.cpt.service.coin;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinService {
	
	private static final Logger log = LoggerFactory.getLogger(CoinService.class);
	
//	@Autowired(required=true)
	@Autowired
    private CoinMapper coinMapper;
	
	/**
	 * 코인목록 입력
	 * @param paramList
	 */
	public void insertCoinList(List<Map<String, Object>> paramList){
//		public void insertCoinList(Map<String, Object> paramMap){
		coinMapper.insertCoinList(paramList);
	}
	
	/**
	 * 검색어 지정
	 * @param paramMap
	 */
	public int updateFilterText(Map<String, Object> paramMap) {
		int resCnt = 0;
		
		resCnt = coinMapper.updateFilterText(paramMap);
		
		return resCnt;
	}
	
//	BTC,BITCOIN,비트코인,비트,비코
	
	/**
	 * 등록된 코인갯수
	 * @param paramMap
	 * @return
	 */
	public int selectCoinListCnt(Map<String, Object> paramMap) {
		int resultInt = 0;
		
		resultInt = coinMapper.selectCoinListCnt(paramMap);
		
		return resultInt;
	}	
	
	/**
	 * 티커 1개의 코인 정보
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> selectCoinInfo(String ticker) {
		Map<String, Object> result =  coinMapper.selectCoinInfo(ticker);
				
		return result;
	}
	
	/**
	 * 게시물을 히스토리에 입력
	 * @param paramMap
	 * @return
	 */
	public int insertCheckContent(Map<String, Object> paramMap){
		return coinMapper.insertCheckContent(paramMap);
	}
	
	/**
	 * 해당 검출 게시글의 최신 게시물 번호 
	 * @param paramMap
	 * @return
	 */
	public int selectLastContentNo(Map<String, Object> paramMap) {
		int result = coinMapper.selectLastContentNo(paramMap);
		return result;
	}
	
	/**
	 * 특정 코인의 현재가격
	 * @return
	 */
	public Map<String, Object> getCurrentPrice(String ticker) {

		Map<String, Object> resultMap = new HashMap<>();
		
		String url =  "https://api.upbit.com/v1/ticker";
        String param = "?markets=KRW-" + ticker;
				
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url+param);
			request.setHeader("Content-Type", "application/json");
//	         request.addHeader("Authorization", authenticationToken);
			
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			String entityStr = EntityUtils.toString(entity, "UTF-8");
//	        System.out.println(entityStr);
			
			JSONArray jsonArray = new JSONArray(entityStr);
			List<Object> resultList = jsonArray.toList();
			Map<String, Object> checkMap = (Map<String, Object>) resultList.get(0);
			
			resultMap.put("ticker", ticker);
			resultMap.put("거래쌍",checkMap.get("market"));
			
			// 현재가
			String tradePrice = NumberFormat.getInstance().format(checkMap.get("trade_price"));
			resultMap.put("현재가", tradePrice);

			// 전일종가
			String prevClosingPrice = NumberFormat.getInstance().format(checkMap.get("prev_closing_price"));
			resultMap.put("전일종가", prevClosingPrice);
			
			Double tPrice = (Double) checkMap.get("trade_price");
			Double pPrice = (Double) checkMap.get("prev_closing_price");
			String diffPrice = NumberFormat.getInstance().format((tPrice - pPrice));
			resultMap.put("전일대비", diffPrice);
			
			// 상태 EVEN-보합, RISE-상승, FALL-하락
			String change = (String) checkMap.get("change");
			
			switch (change) {
				case "EVEN": resultMap.put("가격변동추세", "보합"); break;
				case "RISE": resultMap.put("가격변동추세", "상승"); break;
				case "FALL": resultMap.put("가격변동추세", "하락"); break;
				default: resultMap.put("가격변동추세", "-");
					break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultMap;
	}

}

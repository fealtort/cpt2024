package com.cpt.cmm;

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

public class CoinUtil {
	
	/**
	 * 전체 코인 조회
	 * @param url
	 * @param param
	 * @param reqMethod
	 * @return
	 */
	public static List<Map<String, Object>> upbitCoinList(String url, String param, String reqMethod) {

		List<Map<String, Object>> resultList = new ArrayList<>();

//		Algorithm algorithm = Algorithm.HMAC256(secretKey);
//        String jwtToken = JWT.create()
//                .withClaim("access_key", accessKey)
//                .withClaim("nonce", UUID.randomUUID().toString())
//                .sign(algorithm);
//        String authenticationToken = "Bearer " + jwtToken;

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
			List<Object> coinList = jsonArray.toList();

			for (int i = 0; i < coinList.size(); i++) {
				Map<String, Object> checkMap = (Map<String, Object>) coinList.get(i);
				String coinCd = (String) checkMap.get("market");
				String krNm = (String) checkMap.get("korean_name");
				String enNm = (String) checkMap.get("english_name");

				Map<String, Object> coinMap = new HashMap<>();

				if (coinCd.contains("KRW")) {
//        			System.out.println(i +" = "+ coinCd + "/" + krNm + "/" + enNm);
					String coinCdArr[] = coinCd.split("-");

					coinMap.put("coinCd", coinCdArr[1]);
					coinMap.put("coinNmKr", krNm);
					coinMap.put("coinNmEn", enNm);
					coinMap.put("exchange", "upbit");
					resultList.add(coinMap);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}	

	
	

}

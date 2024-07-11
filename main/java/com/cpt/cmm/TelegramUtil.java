package com.cpt.cmm;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cpt.dto.TelegramMessageContent;
import com.google.gson.Gson;

public class TelegramUtil {
	
	private static final Logger log = LoggerFactory.getLogger(TelegramUtil.class);
	
	/**
	 * 전송 실행
	 * @throws RestClientException 
	 */
	public static void telegramMsgSend(boolean isError, 
									   String isAlarm, 
									   String token, 
									   String channelChatId, 
									   String title,
									   Map<String, Object> priceMap
									   ) {
		
		try {
			String sendUrl = "https://api.telegram.org/bot" + token + "/sendMessage";
			
			if("Y".equals(isAlarm)) {
				String sendMessage = sendMessage(isError, title, priceMap);
				
				TelegramMessageContent msgCont = new TelegramMessageContent(channelChatId, sendMessage);
		        String param = new Gson().toJson(msgCont);
		        
		        RestTemplate restTemplate = new RestTemplate();
		        HttpHeaders headers = new HttpHeaders();
		        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
	
		        // send the post request
		        HttpEntity<String> entity = new HttpEntity<>(param, headers);
		        restTemplate.postForEntity(sendUrl, entity, String.class);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	
	/**
	 * 보낼 메세지
	 * @return
	 */
	private static String sendMessage(boolean isError, String title, Map<String, Object> priceMap) {
	   StringBuilder sb = new StringBuilder();

	   if (isError) {
		   sb.append("[알림] 크롤링 실패 : ").append(title);
		} else {
			sb.append("[알림]").append(System.getProperty("line.separator"))
			  .append("[제목] : ").append(title).append(System.getProperty("line.separator"))
			  .append("[현재가] : ").append(priceMap.get("현재가") + " 원").append(System.getProperty("line.separator"))
			  .append("[전일종가] : ").append(priceMap.get("전일종가") + " 원").append(System.getProperty("line.separator"))
//			  .append("[전일대비] : ").append(priceMap.get("전일대비")).append(priceMap.get("전일종가")).append(System.getProperty("line.separator"));
			  .append("[가격변동추세] : ").append(priceMap.get("가격변동추세"));
		}

	   return sb.toString();
	}

}

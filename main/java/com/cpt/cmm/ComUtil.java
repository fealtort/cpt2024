package com.cpt.cmm;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ComUtil {
	
	/**
	 * 페이징 처리 정보
	 * @param paramMap
	 * @return
	 */
	public static Map<String, Object> setPagenation(Map<String, Object> paramMap) {

        Map<String, Object> result = new HashMap<>();
        String resultCntStr = String.valueOf(paramMap.get("resultCnt")) ;
        if("".equals(resultCntStr) || paramMap.get("resultCnt") == null) resultCntStr = "0";
        int resultCnt = Integer.valueOf(resultCntStr);

        // 페이지당 보여줄 row 갯수 limit
        if(paramMap.get("rowPerPage") == null) paramMap.put("rowPerPage", "10");
        String rowPerPageStr = String.valueOf(paramMap.get("rowPerPage"));
        int rowPerPage =  Integer.valueOf(rowPerPageStr);

        // 현재 페이지
        if(paramMap.get("page") == null) paramMap.put("page", "1");
        String pageStr = String.valueOf(paramMap.get("page"));
        int page = Integer.valueOf((String) pageStr);

        // 총 페이지 갯수( resultCnt / rowperpage)
        int total = (resultCnt/rowPerPage) + 1;
        if(paramMap.get("total") == null) paramMap.put("total", total);

        // 다음 페이지 호출 셋
        paramMap.put("offSet", (rowPerPage * (page - 1)) );

        return result;
    }
	
	/**
	 * 대소문자 상관없이 캐멀케이스로 도출한다.
	 * @param underScore
	 * @return
	 */
	public static String convert2CamelCase(String underScore) {

        // '_' 가 나타나지 않으면 이미 camel case 로 가정함.
        // 단 첫째문자가 대문자이면 camel case 변환 (전체를 소문자로) 처리가
        // 필요하다고 가정함. --> 아래 로직을 수행하면 바뀜
        if (underScore.indexOf('_') < 0 && Character.isLowerCase(underScore.charAt(0))) {
            return underScore;
        }
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        int len = underScore.length();

        for (int i = 0; i < len; i++) {
            char currentChar = underScore.charAt(i);
            if (currentChar == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(currentChar));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(currentChar));
                }
            }
        }
        return result.toString();
    }
	
	
	/* Map Type 파라미터값 유무를 검증하고, 없는 경우 메세지 반환
	    * @param validMap
	    * @return
	    * @throws Exception
	    */
	    public static String checkParamObj(Map<String, Object> validMap) throws Exception {
	        String resultStr = "";
	        
	        for (Entry<String, Object> entrySet : validMap.entrySet()) {
	            if(entrySet.getValue() == null) {
	                resultStr = getPostWord(entrySet.getKey(),"이","가") + " 없습니다.";
	            }
	        }
	        
	        return resultStr; 
	    }

	    /* String Type 파라미터셋의 값 유무만 검증 
	     * @param paramStr
	     * @return
	     * @throws Exception
	     */
	    public static String checkParamStr(String paramStr, String title) throws Exception {
	        String resultStr = "";
	        
	        if( "".equals(paramStr)) {
	            resultStr = getPostWord(title,"이","가") + " 없습니다.";
	        }
	        
	        return resultStr; 
	    }
	    
	    /**
	     * 단어 뒤에 조사 이/가, 은/는, 을/를 붙이기 
	     * @param str
	     * @param firstVal
	     * @param secondVal
	     * @return
	     */
	    public static String getPostWord(String str, String firstVal, String secondVal) {

	        try {
	            char laststr = str.charAt(str.length() - 1);
	            // 한글의 제일 처음과 끝의 범위밖일 경우는 오류
	            if (laststr < 0xAC00 || laststr > 0xD7A3) {
	                return str;
	            }
	    
	            int lastCharIndex = (laststr - 0xAC00) % 28;
	    
	            // 종성인덱스가 0이상일 경우는 받침이 있는경우이며 그렇지 않은경우는 받침이 없는 경우
	            if(lastCharIndex > 0) {
	                // 받침이 있는경우
	                // 조사가 '로'인경우 'ㄹ'받침으로 끝나는 경우는 '로' 나머지 경우는 '으로'
	                if(firstVal.equals("으로") && lastCharIndex == 8) {
	                    str += secondVal;
	                } else {
	                    str += firstVal;
	                }
	            } else {
	                // 받침이 없는 경우
	                str += secondVal;
	            }
	          } catch (Exception e) {
	                //e.printStackTrace();
	          }

	        return str;
	    }
	

}

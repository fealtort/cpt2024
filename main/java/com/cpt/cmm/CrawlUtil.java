package com.cpt.cmm;

/**
 * 코인 관련 특정 단어 포함된것인지 체크
 */
public class CrawlUtil {
	
	public static boolean filterKeyword(String title, String filterkeywordArr[], String exceptKeyword[]) {
		
		boolean result = false;
		int checked = 0; // 필터링 단어 체크된 수
		int exChecked = 0; // 제외 단어 체크된 수 

		// 포함되었는지 필터링할 탄어
		if (filterkeywordArr != null) {
			for (int j = 0; j < filterkeywordArr.length; j++) {
				if (title.contains(filterkeywordArr[j])) {
					checked++;
				}
			}
		}
		
		// 제외시키고 싶은 단어
		if (exceptKeyword != null) {
			for (int i = 0; i < exceptKeyword.length; i++) {
				if (title.contains(exceptKeyword[i])) {
					exChecked++;
				} 
			}
		}

		// 제외단어에 체크되지 않았으면서 필터링에 체크되었어야 함.
		if(checked > 0 && exChecked == 0) {
			result = true;
		}
		
//		System.out.println("checked = " + checked + " / "+ "exChecked = " + exChecked);
		checked = 0;
		exChecked = 0;
		
		return result;
	}

}

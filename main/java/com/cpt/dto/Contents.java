package com.cpt.dto;

import lombok.Getter;
import lombok.ToString;
import lombok.Builder;

@ToString
@Builder
@Getter
public class Contents {

	private String num;
	private String title;
	private String regDt;
}

//Contents cont = Contents.builder()
//		 						  .num(tdContents.get(0).text())
//								  .title(tdContents.get(1).text())
//								  .regDt(tdContents.get(3).text())
//								  .build();
//				System.out.println(cont);
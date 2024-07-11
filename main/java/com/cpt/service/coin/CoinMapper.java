package com.cpt.service.coin;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Component
@MapperScan
public interface CoinMapper {
	

	/**
	 * 거래 코인 목록 입력
	 * @param paramMap
	 * @return
	 */
	int insertCoinList(List<Map<String, Object>> paramList);
	
	/**
	 * 등록된 코인목록 갯수
	 * @param paramMap
	 * @return
	 */
	int selectCoinListCnt(Map<String, Object> paramMap);

	/**
	 * 검색어 지정
	 * @param paramMap
	 * @return 
	 */
	int updateFilterText(Map<String, Object> paramMap);
	
	/**
	 * 티커 1개의 코인 정보
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> selectCoinInfo(String ticker);
	
	/**
	 * 특정코인의 현재가격
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> getCurrentPrice(Map<String, Object> paramMap);
	
	public int insertCheckContent(Map<String, Object> paramMap);
	
	public int selectLastContentNo(Map<String, Object> paramMap);
}

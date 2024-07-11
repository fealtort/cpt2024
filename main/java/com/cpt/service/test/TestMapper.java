package com.cpt.service.test;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@MapperScan
//@Repository
public interface TestMapper {

	/**
	 * 샘플 데이터 목록을 가져온다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectSampleList(Map<String, Object> paramMap);
	
	
	
//	int insertCoinList(Map<String, Object> paramMap);
	
//	/**
//     * 샘플데이터 목록의 페이지 카운트가 필요한 경우
//     * @param paramMap
//     * @return
//     */
//    int selectSampleListCnt(Map<String, Object> paramMap);
//	
//	/**
//	 * 샘플 데이터 1건을 조회한다.
//	 * @param paramMap
//	 * @return
//	 */
//	Map<String, Object> selectSampleOne(Map<String, Object> paramMap);
//	
//	/**
//	 * 샘플 데이터 1건의 입력
//	 * @param paramMap
//	 * @return
//	 */
//	int insertSampleData(Map<String, Object> paramMap);
//
//	/**
//	 * 샘플 데이터 1건의 수정
//	 * @param paramMap
//	 * @return
//	 */
//	int updateSampleData(Map<String, Object> paramMap);
//
//	/**
//	 * 샘플 데이터 1건의 삭제
//	 * @param paramMap
//	 * @return
//	 */
//	int deleteSampleData(Map<String, Object> paramMap);

}

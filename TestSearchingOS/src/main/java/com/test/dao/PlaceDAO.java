package com.test.dao;

import java.util.List;

import com.test.vo.PlaceVO;

public interface PlaceDAO {

	// 장소 작성
	public void writeList(List<PlaceVO> boardVO) throws Exception;
	
	// 장소 목록 조회
	public List<PlaceVO> list(PlaceVO placeVO) throws Exception;
	
	// 장소 단일 조회
	public String placeUnitSelect (PlaceVO placeVO) throws Exception;
	
}
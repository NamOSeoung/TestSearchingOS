package com.test.service;

import java.util.List;

import com.test.vo.PlaceVO;

public interface PlaceService {

	// 게시글 작성
	public void writeList(List<PlaceVO> placeVO) throws Exception;
	
	// 게시물 목록 조회
	public List<PlaceVO> list(PlaceVO placeVO) throws Exception;
	
}
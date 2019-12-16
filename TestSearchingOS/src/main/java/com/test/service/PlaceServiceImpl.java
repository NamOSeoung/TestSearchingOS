package com.test.service;


import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.dao.PlaceDAO;
import com.test.vo.PlaceVO;


@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {

	@Inject
	private PlaceDAO dao;
	
	// 장소 작성
	@Override
	@Transactional
	public void writeList(List<PlaceVO> placeVO) throws Exception {
		dao.writeList(placeVO);
	}

	// 장소 목록 조회
		@Override 
		public List<PlaceVO> list(PlaceVO placeVO) throws Exception {

			return dao.list(placeVO);
		}

		@Override
		public String placeUnitSelect(PlaceVO placeVO) throws Exception {
			
			return dao.placeUnitSelect(placeVO);
		}

}
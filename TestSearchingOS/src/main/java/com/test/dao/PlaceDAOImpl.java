package com.test.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.test.vo.PlaceVO;


@Repository
public class PlaceDAOImpl implements PlaceDAO {

	@Inject
	private SqlSession sqlSession;
	
	// 장소 작성
	@Override
	public void writeList(List<PlaceVO> placeVO) throws Exception {
		
		for (int i = 0; i < placeVO.size(); i ++) {
			System.out.println(i);
			sqlSession.insert("placeMapper.insert", placeVO.get(i));
		}
		
	}
	
	// 장소 목록 조회
	public List<PlaceVO> list(PlaceVO placeVO) throws Exception {
	
		return sqlSession.selectList("placeMapper.list", placeVO);

	}

}

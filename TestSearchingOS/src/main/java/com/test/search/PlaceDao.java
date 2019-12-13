package com.test.search;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;



@Repository
public class PlaceDao {

	
	private SqlSessionTemplate sqlSession;
	

	public List<PlaceDto> sel(String keyWord){

		return sqlSession.selectList("sql.sel");

	}
	
	//public List<PlaceDto> getPlaceList(PlaceVO placeVO){
	//    System.out.println(NAMESPACE + ".selectPlace");
	//	return sqlSession.selectList(NAMESPACE + ".selectPlace", placeVO);
	//}

}
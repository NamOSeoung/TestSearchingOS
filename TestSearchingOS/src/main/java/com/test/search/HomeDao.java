package com.test.search;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;



@Repository

public class HomeDao {

	@Autowired

	private SqlSessionTemplate sqlSession;

	public List<HomeDto> sel(){

		return sqlSession.selectList("sql.sel");

		

	}

}
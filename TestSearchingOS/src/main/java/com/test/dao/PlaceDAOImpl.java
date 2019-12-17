package com.test.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.test.controller.KeywordSearchController;
import com.test.log.LogInfo;
import com.test.vo.PlaceVO;


@Repository
public class PlaceDAOImpl implements PlaceDAO {

	@Inject
	private SqlSession sqlSession;

	String xmlConfigPath = "classpath:applicationContext.xml";
    GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(xmlConfigPath);  // 컨테이너가 생성될 때 initMethod가 실행
    LogInfo logInfo = ctx.getBean("logInfo", LogInfo.class); // 빈 객체 할당받음
    
	// 장소 작성
	@Override
	public void writeList(List<PlaceVO> placeVO) throws Exception {
			for (int i = 0; i < placeVO.size(); i ++) {
				int cnt = placeUnitSelect(placeVO.get(i));
				if(cnt <= 0) {
					logInfo.getSql(sqlSession,"placeMapper.insert",placeVO.get(i).getClass(),(Object)placeVO.get(i));
					sqlSession.insert("placeMapper.insert", placeVO.get(i));
				}
			}
	}
	
	@Override
	// 장소 목록 조회
	public List<PlaceVO> list(PlaceVO placeVO) throws Exception {
		logInfo.getSql(sqlSession,"placeMapper.list",placeVO.getClass(),(Object)placeVO);
		return sqlSession.selectList("placeMapper.list", placeVO);
	}
	
	
	// 장소 단일 조회
	@Override
	public int placeUnitSelect(PlaceVO placeVO) throws Exception {
		logInfo.getSql(sqlSession,"placeMapper.list",placeVO.getClass(),(Object)placeVO);
		return sqlSession.selectOne("placeMapper.placeUnitSelect", placeVO);
	}

}

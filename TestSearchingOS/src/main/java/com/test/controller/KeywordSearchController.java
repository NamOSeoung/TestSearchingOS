package com.test.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.dao.PlaceDAOImpl;
import com.test.log.LogInfo;
import com.test.service.PlaceService;
import com.test.vo.PlaceVO;

 
@Controller
public class KeywordSearchController {
	
	/**
	 * 카카오 장소 키워드 검색 컨트롤러
	 */
	
	/* 각 상황의 타입에 맞는 IoC컨테이너 안에 존재하는 Bean을 자동으로 주입해주게 된다. */
	
	//properties 에 작성 한 restfulApiKey 세팅
	@Value("#{api['api.kakaorestful']}")
	private String restfulKey;
	
	@Inject
	PlaceService placeService;
	
	String xmlConfigPath = "classpath:applicationContext.xml";
    GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(xmlConfigPath);  // 컨테이너가 생성될 때 initMethod가 실행
    LogInfo logInfo = ctx.getBean("logInfo", LogInfo.class); // 빈 객체 할당받음
    
	@RequestMapping(value = "/keyword", method = RequestMethod.GET)
    public String keyWordPlaceSearch(@RequestParam String place_name, Model model, PlaceVO placeVO) {
		Boolean placeYN = false;
		//System.out.println(LoggerFactory.getLogger(PlaceDAOImpl.class));
		ObjectMapper mapper = new ObjectMapper();
		JSONObject jsonObj = null;
		String jsonString = "";
		JSONArray array= null;
		List<PlaceVO> placeList = new ArrayList<PlaceVO>();
		try {
			placeList = placeService.list(placeVO);
			JSONParser parser = new JSONParser();
		    jsonString = mapper.writeValueAsString(placeList);
		    
		    System.out.println(jsonString);
		    Object obj = parser.parse(jsonString);
		    array = (JSONArray) obj;
		    System.out.println(array.toJSONString());
			//장소가 존재하는지 여부
			if(placeList.size() > 0) {
				placeYN = true; //장소 존재하면 true 체크 
			}
			//장소 존재하면 카카오 맵 정보api 미 호출
			if(!placeYN) {
		        String requestUrl = "https://dapi.kakao.com/v2/local/search/keyword.json"; //호출 할 HOST
		        String parameters = ""; //get으로 넘길파라메터 세팅 초기값
		        
		        try {
						parameters += "?query=" + URLEncoder.encode(place_name, "UTF-8");
						parameters += "&size=8";
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
		    	   requestUrl += parameters;

		    	   String httpMethod = "GET"; //호출방식 세팅
		    	   HttpsURLConnection conn;
		    	   OutputStreamWriter writer = null;
		    	   BufferedReader reader = null;
		    	   InputStreamReader isr = null;
		        
		    	   try {
		    		   final URL url = new URL(requestUrl);
		    		   conn = (HttpsURLConnection) url.openConnection();
		    		   conn.setRequestMethod(httpMethod); //호출 메소드 타입
		    		   conn.setRequestProperty("Authorization", "KakaoAK " + restfulKey);
		    		   conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    		   conn.setRequestProperty("charset", "utf-8");

		    		   final int responseCode = conn.getResponseCode();
		    		   
		    		   System.out.println(String.format("\nSending '%s' request to URL : %s", httpMethod, requestUrl));
		    		   System.out.println("Response Code : " + responseCode);
		    		   if (responseCode == 200) {
						    isr = new InputStreamReader(conn.getInputStream());
		    		   }else {
		    		   	    isr = new InputStreamReader(conn.getErrorStream());
		    		   }
		    		   
		    		   reader = new BufferedReader(isr);
		    		   final StringBuffer buffer = new StringBuffer();
		    		   String line;
		    		   while ((line = reader.readLine()) != null) {
						    buffer.append(line);
		    		   }
						System.out.println(buffer.toString());

					   parser = new JSONParser();
					   obj = parser.parse(buffer.toString());
					   jsonObj = (JSONObject) obj;
					   System.out.println("document data"+jsonObj.get("documents"));
					   JSONArray placeArray = (JSONArray) jsonObj.get("documents");
					   
					   for(int i = 0; i < placeArray.size(); i++) {
						   JSONObject placeObject = (JSONObject) placeArray.get(i);
						   PlaceVO p_VO = new PlaceVO();
						   p_VO.setPlace_id((String)placeObject.get("id") + "K");//장소 id 
						   p_VO.setKakao_place_id((String)placeObject.get("id")); //카카오 장소 id
						   p_VO.setPlace_name((String)placeObject.get("place_name"));//장소 이름 
						   p_VO.setCategory_detail((String)placeObject.get("category_name"));//카테고리 이름  
						   p_VO.setPlace_address((String)placeObject.get("address_name"));//주소
						   if("".equals((String)placeObject.get("category_group_code"))) {
							   p_VO.setCategory(null);//카테고리 그룹 코드 
						   }else {
							   p_VO.setCategory((String)placeObject.get("category_group_code"));//카테고리 그룹 코드 
						   }
						   p_VO.setParent_category("*"); // 부모 카테고리 코드 
						   p_VO.setTel_no((String)placeObject.get("phone"));//전화번호 
						   p_VO.setLatitude((String)placeObject.get("y"));//위도 
						   p_VO.setLongitude((String)placeObject.get("x"));//경도 
						   placeList.add(p_VO);
					   }
					   for(int i = 0 ; i < placeList.size(); i++) {
						   System.out.println("리스트확인 : " + placeList.get(i));
					   }
					   try {
						placeService.writeList(placeList);
					} catch (Exception e) {
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						e.printStackTrace();
						logInfo.setPrintErr(ExceptionUtils.getRootCauseStackTrace(e)[0]);
					}
					   model.addAttribute("result", placeArray);
					   System.out.println("json 파싱 데이터 : " + jsonObj.toString());
					   
		        } catch (IOException e) {
		            e.printStackTrace();
		            logInfo.setPrintErr(ExceptionUtils.getRootCauseStackTrace(e)[0]);
		        } catch (ParseException e){
		        	e.printStackTrace();
		        	logInfo.setPrintErr(ExceptionUtils.getRootCauseStackTrace(e)[0]);
		        }finally {
		            if (writer != null) try { writer.close(); } catch (Exception ignore) { }
		            if (reader != null) try { reader.close(); } catch (Exception ignore) { }
		            if (isr != null) try { isr.close(); } catch (Exception ignore) { }
		            logInfo.setLogWrite("[END]");
		        }
		 
			}else {
				model.addAttribute("result", array);
				ctx.close(); // 컨테이너가 소멸될 때 destroyMethod 실행
	            logInfo.setLogWrite("[END]");
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			e2.getMessage();
			logInfo.setPrintErr(ExceptionUtils.getRootCauseStackTrace(e2)[0]);
		}finally {
			  
		}
		
        return "list";
    }
}
 



package com.test.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

 
@Controller
public class KeywordSearchController {
	
	/**
	 * 카카오 장소 키워드 검색 컨트롤러
	 */
	
	
	/* 각 상황의 타입에 맞는 IoC컨테이너 안에 존재하는 Bean을 자동으로 주입해주게 된다. */
	
	//properties 에 작성 한 restfulApiKey 세팅
	@Value("#{api['api.kakaorestful']}")
	private String restfulKey;
	
	@RequestMapping(value = "/keyword", method = RequestMethod.GET)
    public String keyWordPlaceSearch(@RequestParam String keyWord, Model model) {
		
		//placeVO.setPlace_name(keyWord);
		//List<PlaceDto> list = placeService.getPlaceList(placeVO);
		
		//System.out.println(list.get(0));
		
		JSONObject jsonObj = null;
        String requestUrl = "https://dapi.kakao.com/v2/local/search/keyword.json"; //호출 할 HOST
        String parameters = ""; //get으로 넘길파라메터 세팅 초기값
        
        try {
				parameters += "?query=" + URLEncoder.encode(keyWord, "UTF-8");
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

			   JSONParser parser = new JSONParser();
			   Object obj = parser.parse(buffer.toString());
			   jsonObj = (JSONObject) obj;
			   System.out.println("document data"+jsonObj.get("documents"));
			   model.addAttribute("result", jsonObj.get("documents"));
			   System.out.println("json 파싱 데이터 : " + jsonObj.toString());
			   
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e){
        	e.printStackTrace();
        }finally {
            if (writer != null) try { writer.close(); } catch (Exception ignore) { }
            if (reader != null) try { reader.close(); } catch (Exception ignore) { }
            if (isr != null) try { isr.close(); } catch (Exception ignore) { }
        }
 
        return "list";
    }
}
 



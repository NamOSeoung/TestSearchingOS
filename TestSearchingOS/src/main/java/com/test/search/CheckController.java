package com.test.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
 
@Controller
public class CheckController {
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/aaa", method = RequestMethod.GET)
    public static JsonNode getKakaoAccessToken(String code) {
		System.out.println("dddddddd");
        final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        String apiKey = "KakaoAK b4bd7e75365a705323622c57d0b7e406";
        String query = "query=car&sort=accuracy&page=1&size=1";
        RestTemplate restTemplate = new RestTemplate(); 

        HttpHeaders headers = new HttpHeaders(); 
        headers.setContentType(MediaType.APPLICATION_JSON);//JSON 변환 
        headers.set("Authorization", apiKey); //appKey 설정 ,KakaoAK kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk 이 
        
        HttpEntity entity = new HttpEntity("parameters", headers); 


      //String 타입으로 받아오면 JSON 객체 형식으로 넘어옴
        
        JSONParser jsonParser = new JSONParser(); 
        JSONObject jsonObject;
		try {

	        URI url;
			try {
				url = URI.create("https://dapi.kakao.com/v2/search/image?query="+URLEncoder.encode("또보겠지떡볶이", "UTF-8")+"&sort=accuracy&page=1&size=1");
			      
		        ResponseEntity response= restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		        

				jsonObject = (JSONObject) jsonParser.parse(response.getBody().toString());
				//저는 Body 부분만 사용할거라 getBody 후 JSON 타입을 인자로 넘겨주었습니다
		        //헤더도 필요하면 getBody()는 사용할 필요가 없겠쥬
		        System.out.println(url.toString());
		        JSONArray docuArray = (JSONArray) jsonObject.get("documents");
		        System.out.println(docuArray.toJSONString());
		        //documents만 뽑아오고  
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	  
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        

                 
        //뽑아오기 원하는 Key 이름을 넣어주면 그 value가 반환된다.
        
	       JsonNode returnNode = null;
	 

 
        return returnNode;
    }
}
 



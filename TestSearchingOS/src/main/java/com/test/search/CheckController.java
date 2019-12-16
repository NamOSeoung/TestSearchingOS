package com.test.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
 
@Controller
public class CheckController {
	@RequestMapping(value="/apiTest", method = RequestMethod.POST)
	public String googleTestApi(HttpServletRequest request, Model model) {
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        RestTemplate restTemplate = new RestTemplate(); 

        String apiKey = "AIzaSyB25Kz59gEEBTq_H-PLkuBfTHnLfMlAFq8";
        String query = "query=car&sort=accuracy&page=1&size=1";
        String clientId = "s9hvMAPLynzwOM8jw_I5";//네이버 애플리케이션 클라이언트 아이디값";
        String clientSecret = "O8zq0mc1MG";//네이버 애플리케이션 클라이언트 시크릿값";
        
        HttpHeaders headers = new HttpHeaders(); 
        headers.setContentType(MediaType.APPLICATION_JSON);//JSON 변환 
        headers.set("Authorization", apiKey); 
        
        HttpEntity entity = new HttpEntity("parameters", headers); 
		String fields = "photos,formatted_address,name,rating,opening_hours,geometry,place_id";
		
		
		URI url;
		
		JSONParser jsonParser = new JSONParser(); 
        JSONObject jsonObject;
        JSONObject jsonData1 = new JSONObject();	// Find Place API Call
        JSONObject jsonData2 = new JSONObject();	// Place Detail API Call
        JSONObject jsonData3 = new JSONObject();	// Youtube Search API Call
        JSONObject jsonData4 = new JSONObject();	// Naver Blog Search API Call
        Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String input = URLEncoder.encode(request.getParameter("text"), "UTF-8");
			String latitude = URLEncoder.encode(request.getParameter("latitude"), "UTF-8");
			String longitude = URLEncoder.encode(request.getParameter("longitude"), "UTF-8");
			
			// Find Place API Call
			url = URI.create("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input="+input+"&inputtype=textquery&language=ko&fields="+fields+"&locationbias=point:"+latitude+","+longitude+"&key="+apiKey);
	        ResponseEntity response= restTemplate.exchange(url, HttpMethod.GET, entity, String.class);			
			
	        jsonObject = (JSONObject) jsonParser.parse(response.getBody().toString());
	        JSONArray jsonArray = (JSONArray)jsonObject.get("candidates");
	        jsonData1 = (JSONObject) jsonParser.parse(jsonArray.get(0).toString());
	        
			// Place Detail API Call
			url = URI.create("https://maps.googleapis.com/maps/api/place/details/json?place_id="+jsonData1.get("place_id").toString()+"&language=ko&fields=name,rating,formatted_phone_number,review,opening_hours&key="+apiKey);
			response= restTemplate.exchange(url, HttpMethod.GET, entity, String.class);	
			jsonObject = (JSONObject) jsonParser.parse(response.getBody().toString());
			jsonData2 = (JSONObject)jsonObject.get("result");
			
			// Youtube Search API Call
			//URL url2 = new URL("https://content.googleapis.com/youtube/v3/search?maxResults=20&part=snippet&q="+URLEncoder.encode(jsonData1.get("name").toString(), "UTF-8")+"&regionCode=KR&safeSearch=moderate&type=video&prettyPrint=false&key="+apiKey);
			URL url2 = new URL("https://content.googleapis.com/youtube/v3/search?maxResults=20&part=snippet&q="+input+"&regionCode=KR&safeSearch=moderate&type=video&prettyPrint=false&key="+apiKey);
			HttpURLConnection con = (HttpURLConnection) url2.openConnection();
			con.setRequestMethod("GET");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			jsonData3 = (JSONObject) jsonParser.parse(br.readLine());
			br.close();
			
			// Naver Blog Search API Call
			url2 = new URL("https://openapi.naver.com/v1/search/blog?query="+ URLEncoder.encode(jsonData1.get("name").toString()));
			con = (HttpURLConnection) url2.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);	
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response2 = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response2.append(inputLine);
            }
            jsonData4 = (JSONObject) jsonParser.parse(response2.toString());
            br.close();
            
			System.out.println("Find Place API Call : " + jsonData1.toJSONString());
			System.out.println("Place Detail API Call : " + jsonData2.toJSONString());
			System.out.println("Youtube Search API Call : " + jsonData3.toJSONString());
            System.out.println("Naver Blog Search API Call : " + jsonData4.toJSONString());
			
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("jsonData1", jsonData1);
		model.addAttribute("jsonData2", jsonData2);
		model.addAttribute("jsonData3", jsonData3);
		model.addAttribute("jsonData4", jsonData4);
		
		return "apiTest";
	}
}
 



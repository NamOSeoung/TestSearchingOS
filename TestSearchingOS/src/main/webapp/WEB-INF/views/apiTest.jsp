<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/search/apiTest" method="post">
		상점명 : <input type="text" name="text"><br>
		위도 : <input type="text" name="latitude"><br>
		경도 : <input type="text" name="longitude"><br>
		<input type="submit" value="조회"></button>
	</form>
	
	<h1>Google Reviews</h1>
	<p>${jsonData1['name']}</p>
	<p>
		<c:forEach items="${jsonData1['photos']}" var="photos">
			<img src="https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${photos.photo_reference}&key=AIzaSyB25Kz59gEEBTq_H-PLkuBfTHnLfMlAFq8"  width="408px" height="306px">
		</c:forEach>
	</p>
	
	<p>
		<c:forEach items="${jsonData2.opening_hours.weekday_text}" var="weekday">
			${weekday}</br>
		</c:forEach>
	</p>
	
	<p>
		<c:forEach items="${jsonData2['reviews']}" var="reviews">
			<p>${reviews.author_name} (${reviews.rating}, ${reviews.relative_time_description})</p>
			<p>${reviews.text}</p>
		</c:forEach>
	</p>
	
	<h1>GOOGLE CUSTOM SEARCH</h1>
	<table>
		<c:forEach items="${jsonData5['items']}" var="googleSearch">
			<img id = "first" src="${googleSearch.pagemap.cse_thumbnail[0]['src']}"  width="${googleSearch.pagemap.cse_thumbnail[0]['width']}" height="${googleSearch.pagemap.cse_thumbnail[0]['height']}">
			<a href="${googleSearch.link}">
				<p>TITLE : ${googleSearch.pagemap.metatags[0]['og:title']} (${googleSearch.displayLink})</p>
			</a>
			<p>작성자 : ${googleSearch.pagemap.metatags[0]['naverblog:nickname']}</p>
		</c:forEach>
	</table>
	
	<h1>NAVER BLOG</h1>
	<p>
		<c:forEach items="${jsonData4['items']}" var="naverBlog">
			<a href="${naverBlog.link}">
				<p>${naverBlog.title}(${naverBlog.postdate})</p>
			</a>
			<p>작성자 : ${naverBlog.bloggername}</p>
			<p>${naverBlog.description}</p>
		</c:forEach>
	</p>
	
	<h1>YOUTUBE</h1>
	<p>
		<c:forEach items="${jsonData3['items']}" var="youtube">
			<p>${youtube.snippet.title}</p>
			<a href="https://www.youtube.com/watch?v=${youtube.id.videoId}">
				<img src="${youtube.snippet.thumbnails.medium.url}"  width="${youtube.snippet.thumbnails.medium.width}" height="${youtube.snippet.thumbnails.medium.height}">
			</a>
			<iframe id="ytplayer" type="text/html" width="${youtube.snippet.thumbnails.medium.width}" height="${youtube.snippet.thumbnails.medium.height}"
			  src="https://www.youtube.com/embed/${youtube.id.videoId}?autoplay=0&origin=http://example.com"
			  frameborder="0"></iframe>				
		</c:forEach>
	</p>
	

</body>
</html>
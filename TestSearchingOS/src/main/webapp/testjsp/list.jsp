<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ page session="false"%>
<html>
<head>
<title>검색결과</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.5.13/vue.js"></script>
</head>
<script>
function goPlaceDetail(){
	location.href="/search/testjsp/placeDetail.jsp?latitude=22&longitude=33";
}
</script>
<body> 
	<button onclick="goPlaceDetail()">화면전환</button>
	<form action="/keyword" method="GET">
		<input type="text" id="place_name" name="place_name" placeholder="검색어를 기입 해 주세요." />
		<button>검색</button>
	</form>
</body>
</html>
 
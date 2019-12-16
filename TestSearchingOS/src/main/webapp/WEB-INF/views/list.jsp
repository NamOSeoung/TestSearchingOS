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
function goPlaceDetail(longitude,latitude){
	location.href="/testjsp/placeDetail.jsp?latitude=" + latitude + "&longitude=" + longitude;
}
</script>
<body>
	<button onclick="goPlaceDetail()">화면전환</button>
	<form action="/keyword" method="GET">
		<input type="text" id="place_name" name="place_name" placeholder="검색어를 기입 해 주세요." />
		<button>검색</button>
	</form>
<table style="width:100%; height:500px;">
	<tr>
		<c:forEach begin="0" end="3" items="${result}" var="list" >
			<td style="padding:15 15 15 15" onclick="goPlaceDetail('${list.x}','${list.y}')">
				<div style="width:100%;height:90%">
					<img style="width:100%;height:100%" src="https://t1.daumcdn.net/cfile/tistory/996D77485A2FF05018" />
				</div>
				<br>
				${list.category_name} <br>
				${list.place_name} - ${list.place_address} 
			</td>
		</c:forEach>
	</tr>
	<tr>
		<c:forEach begin="4" end="7" items="${result}" var="list" >
			<td style="padding:10 15 15 10" onclick="goPlaceDetail('${list.x}','${list.y}')"> 
				<div style="width:100%;height:90%">
					<img style="width:100%;height:100%" src="https://t1.daumcdn.net/cfile/tistory/996D77485A2FF05018" />
				</div>
				<br>
				${list.category_name} <br>
				${list.place_name} - ${list.place_address} 
			</td>
		</c:forEach>
	</tr>
</table>
</body>
</html>

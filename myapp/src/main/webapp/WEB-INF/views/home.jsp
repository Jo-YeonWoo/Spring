<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<!--
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
 -->

<h3>
	${message}
</h3>
<br><br>
	
	<a href="emp/count"><button>1. 사원의 수</button></a>
	<form action="emp/count"> 
		검색하려는 부서 번호를 입력하세요 : <input type=text name=detpId> <input type=submit value="검색">
	</form><br>
	<form action="emp/deptList"> 
		목록을 출력하려는 부서 번호를 입력하세요 : <input type=text name=detpId> <input type=submit value="검색">
	</form><br><br>
	
	<button>2. 이름 검색</button>
	<form action="emp/nameList">
		검색하려는 사원 이름을 입력하세요 : <input type=text name=name> <input type=submit value="검색">
	</form>
	
	<a href="emp/list"><button>3. 사원 목록</button></a><br><br>

</body>
</html>

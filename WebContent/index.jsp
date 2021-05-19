<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="theContext" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>首页_ch17</title>
<link href="${theContext}/css/myCSS.css" rel="stylesheet">
</head>
<body>
	<div class="mainArea">
		<div style="text-align:center; height:400px;">
			<h2>ch17 SSM整合_1_单表的添删改查</h2>
			<hr style="color:blue; width:50%;"/>
			<a href="${theContext}/manageStudents">学生管理</a> 
		</div>
		<div style="background:url(images/footer.jpg) bottom left repeat-x; height:80px;"> </div>
	</div>
</body>
</html>
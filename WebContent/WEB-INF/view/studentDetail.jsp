<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="theContext" value="${pageContext.request.contextPath}"/>
<c:set var="theStudent" value="${requestScope.student}" scope="page"/>  
<jsp:useBean id="myUtils" class="com.javaee.ch17.utils.MyUtils"/>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>显示学生信息</title>
	<link href="${theContext}/css/myCSS.css" rel="stylesheet">
</head>
<body>
	<div class="mainArea">		
<c:if test="${theStudent == null}">
		<div class="strong center">或已被删除，无此学生信息！</div>
</c:if>
<c:if test="${theStudent != null }">
		<h2 style="text-align:center;">显示学生信息</h2>
		<table class="posCenter">
			<tr>
				<th>账号</th><td>${theStudent.userName }</td>
			</tr>
			<tr>
				<th>密码</th><td>${theStudent.password }</td>
			</tr>
			<tr>
				<th>真名</th><td>${theStudent.userRealName}</td>
			</tr>	
			<tr>
				<th>性别</th>
				<td>
					${theStudent.gender eq '0' ? '男' : '女'}
				</td>
			</tr>
			<tr>
				<th>Email</th><td>${theStudent.email}</td>
			</tr>
			<tr>
				<th>电话</th><td>${theStudent.contactPhone}</td>
			</tr>
			<tr>
				<th>地址</th><td>${theStudent.address}</td>
			</tr>
			<tr>
				<th>备注</th><td><div style="width:1000px; height:500px; overflow:scroll;">${theStudent.memo}</div></td>
			</tr>
			<tr>
				<th>添加时间</th><td>${myUtils.getDateTimeString(theStudent.creationTime, 1)}</td>
			</tr>
			<tr>
				<th>修改时间</th><td>${myUtils.getDateTimeString(theStudent.lastChangeTime, 1)}</td>
			</tr>																																	
		</table>		
</c:if>
		<div class="posCenter navDiv">
			<a href="${theContext}/">首页</a> <a href="${theContext}/manageStudents">管理学生信息</a><br>
			${requestScope.theMessage}		
		</div>
	</div>
</body>
</html>
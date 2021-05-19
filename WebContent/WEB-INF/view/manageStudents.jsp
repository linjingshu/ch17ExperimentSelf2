<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="myUtils" class="com.javaee.ch17.utils.MyUtils"/>
<c:set var="theContext" value="${pageContext.request.contextPath}" scope="page"/>
<c:set var="thePageBean" value="${requestScope.pageBean}" scope="page"/>
<c:set var="theListStudents" value="${thePageBean.lists}" scope="page"/>
<c:set var="studentsCount" value="${theListStudents.size()}" scope="page"/>
<c:set var="student" value="${sessionScope.queryStudentCondition }"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理学生信息</title>
<link href="${theContext}/css/myCSS.css" rel="stylesheet">
<script src="${theContext}/js/jquery-3.6.0.min.js"></script>
<script src='${theContext}/plugins/My97DatePicker/WdatePicker.js'></script>
<script>
	function checkValid(theForm){
		//alert(theForm.typeOfQuery.value); 不能用js这么直接获取而是应通过循环，逐个判断当前元素是否被checked。
		if($("input[name='typeOfQuery']:checked").val()=="u"){ // 精确查询 提交时要保证id是>0的整型值
			var theIdO = theForm.id, theIdV = theIdO.value;
			if(theIdV=="" || isNaN(theIdV) || theIdV.indexOf(".")!=-1 || theIdV <=0 ){
				alert("精确查询时，会员的id值必须是正整数！");
				theIdO.select();
				return false;
			}
		}else{ // 模糊查询  
			theForm.id.value="0"; // 因仍要提交这个id输入元素，所以要将其value设为零(主要目的是非空)以便绑定到int型的变量或封装对象的int型属性上
			
		}
		return true;
	}
	function showHidden(theShowId, theHiddenId){
		//alert("OK"); // OK
		document.getElementById(theShowId).style.visibility="visible";
		document.getElementById(theHiddenId).style.visibility="hidden";
	}
</script>
</head>
<body>
	<div class="mainArea">
		<h2 class="pageTitle">管理学生信息</h2>
		<%--
		对于返回单个实体型的结果集：如有零行记录，则是null。但对于返回List<实体型>这样的结果集：
		当找到的结果集有零行记录时，theListCustomers不是null，是含0个元素的List，并不是不存在(null)。所以下面的判断达不到判断有无结果的效果。
		<c:if test="${theListStudents==null }"><div style="text-align:center">无学生信息</div></c:if>
		<c:if test="${theListStudents!=null }">
		而应通过如下这种方式(List类型的size()方法返回值是否为零来判断结果集的行数是否为零)：	
		<c:if test="${theListStudents.size()==0}"><div style="text-align:center">无学生信息</div></c:if>
		<c:if test="${theListStudents.size()>0}">			
		 --%>
		<form action="${theContext}/findStudentByIdOrSomeStudentsWithPage" method="post" onsubmit="return checkValid(this);">
			<div id="queryArea" style="margin:0px auto; width:800px;">
				<div id="uniqueQuery" style="border-bottom:1px solid green; margin-bottom:2px;"> <%-- precise exact accurate --%>
					<div id="selectUniqueQuery" style="width:90px; float:left;"><input type="radio" value="u" name="typeOfQuery" onclick="showHidden('uniqueQueryOperateArea', 'fuzzyQueryOperateArea');"/>精确查询</div>
					<div id="uniqueQueryOperateArea" style="visibility:hidden;">编号<input name="id" size="5"  maxlength="6"></div>
				</div>
				<div style="clear:both;"></div>
				<div id="fuzzyQuery" style="">
					<div id="selectFuzzyQuery" style="width:90px;float:left;"><input type="radio" value="f" name="typeOfQuery" checked="checked"  onclick="showHidden('fuzzyQueryOperateArea', 'uniqueQueryOperateArea');"/>模糊查询</div>
					<div id="fuzzyQueryOperateArea" style="border:1px solid green;float:left;">
						账号<input type="text" name="userName" id="userName" value="${student.userName}"> 真名<input type="text" name="userRealName" value="${student.userRealName }"> 性别
						<c:choose>
							<c:when test="${student==null or student.gender=='-1'}">
								<input type="radio" name="gender" value="-1" checked="checked">不限 <input type="radio" name="gender" value="0">男 <input type="radio" name="gender" value="1">女<br>
							</c:when>
							<c:when test="${student.gender=='0'}">
								<input type="radio" name="gender" value="-1">不限 <input type="radio" name="gender" value="0" checked="checked">男 <input type="radio" name="gender" value="1">女<br>
							</c:when>
							<c:otherwise>
								<input type="radio" name="gender" value="-1">不限 <input type="radio" name="gender" value="0">男 <input type="radio" name="gender" value="1" checked="checked">女<br>
							</c:otherwise>
						</c:choose><br>
						Email<input type="text" name="email" id="email" size="30" value="${student.email }"> 电话<input type="text" name="contactPhone" id="contactPhone" value="${student.contactPhone}"><br>
						地址<input type="text" name="address" id="address" size="50" value="${student.address }"><br>
						添加时间&nbsp;起始时间：<input type="text" name="addTimeStart" id="addTimeStart" readonly="readonly" size="40"
											   value=""
											   onclick="WdatePicker({el:this, dateFmt:'yyyy-MM-dd HH:mm:ss'})"
											   class="Wdate" style="width:90px"/>  至
									 截止时间：<input type="text" name="addTimeEnd" id="addTimeEnd" readonly="readonly" size="40"
												 value=""
												 onclick="WdatePicker({el:this, dateFmt:'yyyy-MM-dd HH:mm:ss'})"
												 class="Wdate" style="width:90px"/>
						<br>
						修改时间&nbsp;起始时间：<input type="text" name="changeTimeStart" id="changeTimeStart" readonly="readonly" size="40"
											  value=""
											  onclick="WdatePicker({el:this, dateFmt:'yyyy-MM-dd HH:mm:ss'})"
											  class="Wdate" style="width:90px"/>  至
								     截止时间：<input type="text" name="changeTimeEnd" id="changeTimeEnd" readonly="readonly" size="40"
												 value=""
												 onclick="WdatePicker({el:this, dateFmt:'yyyy-MM-dd HH:mm:ss'})"
												 class="Wdate" style="width:90px"/>
					</div>
				</div>
				<div style="clear:both;"></div>
				<div style="text-align:center;"><input type="submit" value="查询"> <input type="reset" value="重填"></div>
				<hr>
			</div>
		</form>
		<c:if test="${studentsCount==0}"><div class="posCenter">无学生信息</div></c:if>
		<c:if test="${studentsCount>0}">
			<c:set var="theResultSize" value="${studentsCount}" scope="page"/>
			<c:set var="theCurrentPage" value="${thePageBean.currPage}"/>
			<c:set var="theTotalPage" value="${thePageBean.totalPage}"/>
			<c:set var="theTotalCount" value="${thePageBean.totalCount}"/>
			<c:set var="thePageSize" value="${thePageBean.pageSize}"/>
			<c:set var="thePageIndexStart" value="${(theCurrentPage - 1)*thePageSize}"/>
			<table class="posCenter">
				<tr>
					<th>序号</th><th>编号</th><th>用户名<th>真名</th><th>性别</th><th>邮箱</th><th>电话</th><th>创建时间</th><th>详细</th><th>修改</th><th>删除</th>
				</tr>
			<c:forEach items="${theListStudents}" var="student" varStatus="theStatus" >
				<c:set var="theId" value="${student.id}" scope="page"/> <%-- 为避免重复取从而提高效率，将常用的量设置成变量theId --%>
				<c:set var="theIndex" value="${theStatus.index}" scope="page"/>
				<tr>
					<td>${thePageIndexStart+theIndex+1}</td>
					<td>${theId}</td>
					<td>${student.userName}</td>
					<td>${student.userRealName}</td>
					<td>${student.gender eq '0' ? '男' : '女'}</td>
					<td>${student.email}</td>
					<td>${student.contactPhone}</td>
					<td>${myUtils.getDateTimeString(student.creationTime, 1)}</td>
					<td class="operate"><a href="${theContext}/studentDetail?id=${theId}">详细</a></td>
					<td class="operate"><a href="${theContext}/toUpdateStudent?id=${theId}">修改</a></td>
					<td class="operate"><a href="${theContext}/deleteStudent?id=${theId}" onclick="return confirm('确定要删除？');">删除</a></td>
				</tr>
		    </c:forEach>
			</table>
			<div class="posCenter">共有<span class="strong">${studentsCount}</span>条学生记录</div>	
		<br>
		<table  border="0" cellspacing="0" cellpadding="0" class="posCenter">
			<tr>
			<td class="td2">
			   <span>第${theCurrentPage }/ ${theTotalPage}页</span>&nbsp;&nbsp;
			   <span>总记录数：${theTotalCount }&nbsp;&nbsp;每页:${thePageSize}</span>&nbsp;&nbsp;
			   <span>
			       <c:if test="${theCurrentPage > 1}">
			           <a href="${theContext}/manageStudents?currentPage=1">[首页]</a>&nbsp;&nbsp;
			           <a href="${theContext}/manageStudents?currentPage=${theCurrentPage-1}">[上一页]</a>&nbsp;&nbsp;
			       </c:if>
			
			       <c:if test="${theCurrentPage < theTotalPage}">
			           <a href="${theContext}/manageStudents?currentPage=${theCurrentPage+1}">[下一页]</a>&nbsp;&nbsp;
			           <a href="${theContext}/manageStudents?currentPage=${theTotalPage}">[尾页]</a>&nbsp;&nbsp;
			       </c:if>
			        <button onclick="gotoThePage();" id="btnGo">转到</button>第<input type="text" value="${theCurrentPage}" id="pageNo" size="5" onkeypress="enterHandler(event);">页
			   </span>
			</td>
			</tr>
		</table>					
		</c:if>
		<div class="posCenter navDiv">
			<a href="${theContext}/">首页</a> <a href="${theContext}/toInsertStudent">添加学生</a><br>
			${requestScope.theMessage }
		</div>		
	</div>
</body>
</html>
<script>
	function gotoThePage(){
		// 取出id是pageNo的输入框的值，然后让页面转向到：${pageContext.request.contextPath }/admin/manageStudents?currentPage=这个值
		//location.href="${pageContext.request.contextPath }/findCustomers?currentPage="+document.getElementById("pageNo").value.trim();
		var thePageNoO=document.getElementById("pageNo");
		var thePageNoStr=thePageNoO.value.trim();
		if(thePageNoStr=="" || isNaN(thePageNoStr) || thePageNoStr.indexOf(".")!=-1){
			alert("页号必须是非空的数值！");
			thePageNoO.select();
		} else{
			var thePageNo = parseInt(thePageNoStr);
			location.href="${theContext}/manageStudents?currentPage="+thePageNo;
		}
	}
	function enterHandler(event){
		//获取用户单击键盘的“键值”
		var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
		//如果是回车键
		if (keyCode == 13){
			document.getElementById("btnGo").click();
		}
	}
</script>
<!-- 
<c:if test="${requestScope.customer.gender=='0' }">男</c:if><c:if test="${requestScope.customer.gender=='1' }">女</c:if> ${customer.gender eq '0' ? '男' : '女'} <br>

<td class="center"><a href="${pageContext.request.contextPath }/showAProductById?id=${theId}" class="forms-btn">详细</a></td>
 -->
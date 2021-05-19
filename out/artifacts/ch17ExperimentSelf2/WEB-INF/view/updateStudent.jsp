<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="theContext" value="${pageContext.request.contextPath}"/>
<c:set var="theStudent" value="${requestScope.student}" scope="page"/>
<jsp:useBean id="myUtils" class="com.javaee.ch17.utils.MyUtils"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>更新学生信息</title>
	<link href="${theContext}/css/myCSS.css" rel="stylesheet">
	<script src="${theContext}/plugins/ckeditor/ckeditor.js"></script>
	<script src="${theContext}/js/jquery-3.6.0.min.js"></script>
	<script src='${theContext}/plugins/My97DatePicker/WdatePicker.js'></script>
<script type="text/javascript">
var existFlag=0;
function ajaxValidateStudent(theFieldType) {
    //alert("OK");
	var fieldValue, remindId, fieldName;
    /*if(theFieldType=="1"){
    	fieldValue = $("#userName").val().trim();
    	remindId="userNameRemind";
    	fieldName="账号";
    } else */if(theFieldType=="2"){
    	fieldValue = $("#email").val().trim();
    	remindId="emailRemind";
    	fieldName="Email";
    } else {
    	fieldValue = $("#contactPhone").val().trim();
    	remindId="contactPhoneRemind";
    	fieldName="联系电话";
    }
    //alert(fieldValue+"、"+theFieldType); // OK
    //if(fieldValue=="")  return; //用户在对应输入框中未留内容，此时直接返回即可   但这会导致先前产生的提示“可以/不可以注册”不同步。所以，改进如下：
    if(fieldValue=="") {
    	$("#"+remindId).html(""); 
    	return;
    }
    $.ajax({
        type:"POST",
        url:"${theContext}/isOtherExistSame",//提交地址
        data:JSON.stringify({"fieldValue":fieldValue, "fieldType":theFieldType, "id": $("#id").val()}),//提交数据  用这个 ${theStudent.id} 也行，虽有报错但可正常执行。
        //data:"fieldValue="+fieldValue,
        contentType : "application/json;charset=UTF-8",
        dataType:"json",//返回json格式        text 
        success:function(resultData){            
        	if (true == resultData) {//不能这样修改
                //alert("不能注册");
            	$("#"+remindId).html("<font color='red'>该" + fieldName + "已存在！</font>"); 
            	existFlag=1;
            } else{
            	$("#"+remindId).html("<font color='blue'>可以修改<font>");//如果没有被注册，将提示语清空。
            	existFlag=0;            	
            }
        },
        error: function () { // 当data为空时，就跳出success变成error状态了。        	

        }
    });
}

function checkValid(theForm){
	if(existFlag==1){
		alert("新会员的账号、Email、电话、证件信息都不能重复注册！");
		return false;
	}
	var theUserName=theForm.userName, theUserNameV=theUserName.value.trim();
	var thePWD=theForm.password, thePWDV=thePWD.value.trim();
	//var thePWDRe=theForm.passwordRe, thePWDReV=thePWDRe.value.trim();
	var theUserRealName=theForm.userRealName, theUserRealNameV=theUserRealName.value.trim();
	var $theCardNo=$("#cardNo"), theCardNoV=$theCardNo.val().trim();
	var theEmail=theForm.email, theEmailV=theEmail.value.trim();
	var theContactPhone=theForm.contactPhone, theContactPhoneV=theContactPhone.value.trim();	
	var theAddress=theForm.address, theAddressV=theAddress.value.trim();	
	var theCheckCode=theForm.checkCode, theCheckCodeV=theCheckCode.value.trim(), theLengthRequired=4;
	if(theUserNameV==""){
		alert("账号不能为空！");
		theUserName.select();
	} else if(thePWDV==""){
		alert("密码不能为空！");
		thePWD.select();			
	} /*else if(thePWDReV !=thePWDV){
		alert("两次输入的密码必须一致！");
		thePWDRe.select();			
	} */else if(theUserRealNameV==""){
		alert("真名不能为空！");
		theUserRealName.select();			
	} else if(theCardNoV==""){
		alert("证件号码不能为空！");
		$theCardNo.select();			
	} else if(theEmailV==""){
		alert("邮箱不能为空！");
		theEmail.select();			
	} else if(theAddressV==""){
		alert("地址不能为空！");
		theAddress.select();			
	} else if(theCheckCodeV=="" || theCheckCodeV.length < theLengthRequired){
		alert("验证码的位数必须是"+theLengthRequired);
		theCheckCode.select();			
	} else{
		return true;
	}		
	//alert("OK");
	return false;
}	
function processInputStatus(theO){
	$("#showInputStatus").html("已输入<b class='strong'>"+theO.value.length+"</b>个字符"); // theO.value.trim().length 既然是实时统计已输个数，还是不要trim了。
}
</script>		
</head>
<body>
	<div class="mainArea">
		<h2 style="text-align:center;">更新会员</h2>
		<form action="${theContext}/doUpdateStudent" method="post" onsubmit="return checkValid(this);">
			<input type="hidden" value="${theStudent.id }" name="id" id="id">
			
			<table class="posCenter">
				<c:set var="theEntryTimeOriginal" value="${myUtils.getDateTimeString(theStudent.creationTime, 1)}"/>
				<c:set var="theModifyTimeOriginal" value="${myUtils.getDateTimeString(theStudent.lastChangeTime, 1)}"/>
				<tr>
					<th>录入时间</th>
					<td>
						原：${theEntryTimeOriginal}<br>
						新：<input type="text" readonly='readonly' name='creationTime' id="theEntryTime" value="" class="Wdate" size="20" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'#F{$dp.$D(\'theModifyTime\')||\'%y-%M-%d %H:%m:%s\'}'})" /> 注：留空表示不改！
					</td>
				</tr>
				<tr>
					<th>修改时间</th>
					<td>
						原：${theModifyTimeOriginal}<br>
						新：<input type="text" readonly='readonly' name='lastChangeTime' id="theModifyTime" class="Wdate" size="20" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'theEntryTime\')||\'${theEntryTimeOriginal}\'}', maxDate:'%y-%M-%d %H:%m:%s'})" /> 注：未改过留空表示用提交时间！其余留空则表示不变。
					</td>
				</tr>
				<input type="hidden" name="theEntryTimeOriginal" value="${theEntryTimeOriginal}"/>
				<c:if test="${theModifyTimeOriginal=='无'}">
					<c:set var="theModifyTimeOriginal" value=""/>
				</c:if>
				<input type="hidden" name="theModifyTimeOriginal" value="${theModifyTimeOriginal}"/>
				<tr>
					<th>账号</th><td>${theStudent.userName }</td>
				</tr>
				<tr>
					<th>密码</th><td><input type="password" name="password" value="${theStudent.password }"></td>
				</tr>
				<tr>
					<th>真名</th><td><input type="text" name="userRealName" value="${theStudent.userRealName }"></td>
				</tr>	
				<tr>
					<th>性别</th>
					<td>
						${theStudent.gender eq '0' ? '<input type="radio" name="gender" value="0" checked="checked">男 <input type="radio" name="gender" value="1">女' : '<input type="radio" name="gender" value="0" >男 <input type="radio" name="gender" value="1" checked="checked">女'}
					</td>
				</tr>
				<tr>
					<th>Email</th><td><input type="text" name="email" id="email" value="${theStudent.email }" onblur="ajaxValidateStudent('2')"><span id="emailRemind"></span></td>
				</tr>
				<tr>
					<th>电话</th><td><input type="text" name="contactPhone" id="contactPhone" value="${theStudent.contactPhone }" onblur="ajaxValidateStudent('3')" size="30" maxlength="50"><span id="contactPhoneRemind"></span></td>
				</tr>
				<tr>
					<th>地址</th><td><input type="text" name="address" value="${theStudent.address }" size="50"></td>
				</tr>
				<tr>
					<th>备注</th><td><textarea id="editor" name="memo">${theStudent.memo }</textarea></td>
				</tr>
								
				<tr>
					<th>验证码</th>
					<td><input name="checkCode" class="checkCodeInput"  size="4" maxlength="4"><img src="${theContext}/checkCode" title="点击刷新" alt="点击刷新"  class="passCode" style="height:26px;cursor:pointer;" onclick="this.src=this.src+'?'+Math.random();"></td>
				</tr>	
				<tr>
					<td colspan="2" align="center" class="center"><input type="submit" value="确定"> <input type="reset" value="重填"></td>
				</tr>																														
			</table>
		</form>
		<div class="posCenter navDiv">
			<a href="${theContext}/">首页</a> <a href="${theContext}/manageStudents">管理学生信息</a><br>
			${requestScope.theMessage }		
		</div>

	</div>
</body>
</html>
<script>
	//initSample();
	/* 
	// 这些都是可选
	CKEDITOR.config.height = 300; // 设置高度
	CKEDITOR.config.width = 'auto';	 // 设置宽度
	var editorElement = CKEDITOR.document.getById('editor');
	editorElement.setHtml("<b>你好</b>"); //设置初始内容 
	*/
	CKEDITOR.replace('editor');
</script>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%!
	private String study = "JSP공부중";
	public String checkStr(){
		if(study == null){
			return "no";
		}else{
			return "ok";
		}
	}
 %>
 
 <%
 	Calendar c= Calendar.getInstance();
 	int hour = c.get(Calendar.HOUR_OF_DAY);
 	int minute = c.get(Calendar.MINUTE);
 	int second = c.get(Calendar.SECOND);
  %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
div#s_left{
width:200px;
height:700px;
background-color:black;
float:left;
}
#contents{
width:400px;
height:700px;
background-color:gray;
float:left;
}
#s_right{
width:200px;
height:700px;
background-color:blue;
float:left;
}
</style>
</head>
<body>

<div id="s_left" color="white">사이드바 왼쪽(검정)</div>
<div id="contents">본문(회색) <%=checkStr() + study() + study%><br>현재시간 : <%=hour %>시 <%=minute %>분 <%=second %>초</div>
<div id="s_right">사이드바 오른쪽(파랑)</div>
<%!
private String areaTest = "위치 차이";
private String study(){
	areaTest += ": 없음";
	return areaTest;
}
 %>
</body>
</html>
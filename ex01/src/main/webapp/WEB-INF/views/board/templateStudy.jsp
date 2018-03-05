<!-- JSP 프로그래민 기본 코드 구성에 대한 스터디를 위해 임의로 만든 JSP 파일
		지시자(Directives), 선언문(Declarations), 스크립틀릿(Scriptlet), 
		표현식(Expression)에 대해서 간단히 알아본다. 
		출처 : 2015.04.28 1:26 JSP 프로그래밍 기본 코드 구성 (지시자, 선언문, 스크립틀릿, 표현식, 주석 설명)
		위드 코딩, 빌노트, http://withcoding.com/41-->
<!-- 지시자(Directives)
	 	%@으로 시작하는 스크립틀릿 형식 태그 사이에 오는 코드
	 	주로 page 지시자 사용
	 	contentType, pageEncoding을 사용하여 문서 타입과 인코딩 설정
	 	contentType 인코딩은 JSP 파일을 HTML 문서로 변환 할 때 적용되는 인코딩
	 	pageEncoding은 그냥 JSP 파일에 적용되는 인코딩
	 	최근 유니코드를 주로 사용하기 때문에 UTF-8을 주로 사용
	 		(한글 문제가 있는 경우 EUC-KR을 종종 사용)
 		JSP 코드가 사용되는 부분은 HTML에서 공백으로 처리됨
 		trimDirectiveWhitespaces를 설정하면 공백을 없앨 수 있음
 		import는 자바 프로그래밍 import와 동일한 역할 수행 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="java.util.Random" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%!
	/* 선언문(Declarations)
		%!로 시작하는 스크립틀린 형식의 태그 사이에 오는 코드
		서블릿 클래스의 선언부에 오는 내용이라고 보면 됨
			Ex) Spring의 Controller 클래스 내 선언문 
		함수 언선 같은 것을 여기서 할 수 있음
		클래스(Class)를 따로 구현해서 임포트하는 구조가 깔끔하기 때문에 선언문을 쓸 일이 별로 없음*/
	public String getRandomString(Random random, int number){
		return "행운의 숫자는 "+Integer.toString(random.nextInt(number)+1)+"입니다!";
	}

 %>
 <%
	/* 스크립틀릿(Scriptlet)
		 %로 시작하는 스크립틀립 형식의 태그 사이에 오는 코드(가장 기본적인 형태)
		 자바 프로그래밍에서 할 수 있는 대부분의 일이 스크립틀릿 단에서 가능
		 	(if문, for문, while문, switch문 등등)
	 	out.print 함수를 사용하면 화면 출력도 가능*/
 	Random random = new Random();
 	random.setSeed(System.currentTimeMillis());
 	/* System.out.println()함수는 콘솔에 갑 출력 
 	out.print()함수는 Client에 값 출력
 		=> Client 단에 출력하는 값에 들어있는 개행문자 반영 되지 않음
 				(추가로 Study 필요한 부분 : 
 					html 태그 영역에서는<br>로 개행 가능)*/
 	System.out.println("스크립틀릿(Scriptlet) 영역 입니다!!"+"\r\n"+getRandomString(random, 10));
 	out.println("스크립틀릿(Scriptlet) 영역 입니다!!"+"\r\n"+getRandomString(random, 10));
  %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- 표현식(Expression)
		%= 으로 시작하는 스크립틀릿 형식 태그 안에 오는 코드
		내장 객체 (Implicit Object)라고 부르기도 함
		출력 할 내용을 여기에 입력
		out.print() 함수의 인자와 비슷
			출력할 내용 <%=출력할 것%> 과 out.print("출력할 것")의 결과가 동일함
		세미콜론(;) 사용 시 에러 발생 --%>
		<br>표현식 영역입니다!! <br>
	<%=getRandomString(random, 10) %>
</body>
</html>
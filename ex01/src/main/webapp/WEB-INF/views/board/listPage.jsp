<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>

<%@ include file="../include/header.jsp" %>

<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">Board List</h3>
				</div>
				<div class="box-body">
					<button id='newBtn'>New Board</button>
				</div>
			</div>
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">LIST PAGING</h3>
				</div>
				<div class="box-body">
					<table class="table table-bordered">
					<!-- table  태크 정리
						<th>table head</th> : <th>태그는 table head의 약자, 제목 표시에 사용, 굵은 글씨, 중앙정렬
						<tr>table row </tr> : <tr>태그는 table row의 약자, 표의 줄을 만드는데 사용, 보통글씨, 왼쪽 정렬
						<td>table data</td> : <td>태그는 table data의 약자, 표의 칸을 만드는데 사용, 보통슬씨, 왼쪽 정렬
							출처 : 지구별 안내서 표를 만드는 table 태그, tr 태그, th 태그, td 태그와 table 속성 정리 -->
						<tr>
							<th style="width: 10px">BNO</th>
							<th>TITLE</th>
							<th>WRITER</th>
							<th>REGDATE</th>
							<th style="width: 40px">VIEWCNT</th>
						</tr>
						
					<c:forEach items="${list}" var="boardVO">
						<tr>
							<td>${boardVO.bno}</td>
							<%--<td><a href='/board/read?bno=${boardVO.bno}'>${boardVO.title}</a></td>--%>
							<%-- uri 호출을 /board/read?bno=${boardVO.bno} 방식으로 처리해도 되지만 그렇게 하는 경우
									게시글의 고유 번호만을 가지고 read 페이지로 이동하여 글을 조회하기 때문에, 다시 리스트 페이징 화면으로
									돌아왔을 때 화면에 몇 개의 게시물을 보여줘야 하고, 게시물이 포함된 페이지가 몇 페이지 인지 알지 못한다.
									이러한 화면 이동간 정보 전달 문제를 해겨해가 위해서 PageMaker 클래스에서 makeQuery 메소드를
									작성하여 UniComponents 객체를 통해 페이지 Uri 를 생성하여 page, perPageNum 등 변수를
									동적으로 담는다. --%>
							<td><a href='/board/readPage${pageMaker.makeQuery(pageMaker.cri.page) }&bno=${boardVO.bno}'>${boardVO.title }</a></td>
							<td>${boardVO.writer }</td>
							<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${boardVO.regdate }" /></td>
							<td><span class="badge bg-red">${boardVO.viewcnt }</span></td>
						</tr>
					</c:forEach>
					</table>
				</div>
				<!-- /.box-body -->
				
				<div class="box-footer">
					<div class="text-center">
						<ul class="pagination">
						
						<!-- 목록을 나타내는 태그들
							<ul> : Unordered List
									순서가 없는 목록
							<ol> : Ordered List
									순서가 있는 목록
							<dl> : Definition List
									정의 목록 만드는 형식
								<dt> : Definition term
										정의하고자 하는 용어
								<dd> : Definition Description
										정의하고자 하는 용어에 대한 세부 설명
							<li> : List Item
									목록의 실질적 내용이 되는 태그
									<ul>, <ol> 태그 내부에서 사용
							출처 : [HTML 기초 강좌] 5. 목록을 나타내는 ul 태그, ol 태그, dl 태그, li 태그
								http://cofs.tistory.com/124 [CofS]
							-->
							<!-- html 주석 형식 내부에 jstl 코드를 작성하면 주석으로 인식하지 않고 코드로 인식함
							jstl 문법을 주석으로 처리하고 싶을 경우 <%-- --%> 형식으로 jsp 주석을 달아줘야함 -->
							<%-- jstl의 if문 if ~ else 문에 대한 정리, java와 사용 법이 약간 다르다
							1. if 문 <c:if></c:if>태그 사용 => else 문 없는 단순 조건문에 사용
							
							Ex)	<c:set var="name" value="홍길동" />
								<c:if test="${name eq '홍길동'}">
									홍길동이 맞습니다.
								</c:if>
								
							2. if ~else 문 <c:choose></c:choose>태그 사용 
								=> <c:choose></c:choose>, <c:when></c:when>, 
								<c:otherwise></c:otherwise> 태그 사용
							
							Ex)	<c:set var="name" value="홍길동" />
									
									<c:choose>
										<c:when test="${name eq '홍길동'}">
											홍길동이 맞습니다.
										</c:when>
										
										<c:when test="${name eq '철수'}">
											홍길동이 아닙니다.
										</c:when>
										
										<c:otherwise>
											사람이 없습니다. ㅠㅜ	
										</c:otherwise>
										
									</c:choose>
								
								
							--%>
							
							<c:if test="${pageMaker.prev}">
								<%-- Uri 에 정보를 담지 않는 방식 --%>
								<%--<li><a href="listPage?page=${pageMaker.startPage -1 }">&laquo;</a></li> --%>
								<%--서버단에서 처리하는 방식 --%>
								<%--<li><a href="listPage${pageMaker.makeQuery(pageMaker.startPage -1)} }">&laquo;</a></li> --%>
								<%-- JavaScript JQuery 를 사용하는 방식 --%>
								<li><a href = "${pageMaker.startPage -1 }">&laquo;</a></li>
							</c:if>
							
							<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
								<li
									<c:out value="${pageMaker.cri.page == idx?'class =active':''}"/>>
									<%--<a href="listPage?page=${idx }">${idx}</a> --%>
									<!-- PageMaker 클래스의 makeQuery() 메서드를 사용하는 pagination 방법
									서버단에서 현재 페이지 정보를 담고 있는 pageMaker 클래스의 Criteria 객체를 사용하여 page 변수값 받음
									받은 변수값으로 makeQuery()메서드의 UniComponents 객체로 동적 Uri 생성하여 Uri 에 반영하는 방식 -->
									<%--<a href="listPage${pageMaker.makeQuery(idx) }">${idx }</a> --%>
									<!-- javaScript를 이용한 pagination 링크 처리 방법 -->
									<a href="${idx }">${idx }</a>
								</li>
							</c:forEach>
							
							<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
								<%-- JavaScript 없는 경우 Uri 에 정보를 담지 않는 방식 --%>
								<%--<li><a href="listPage?page=${pageMaker.endPage +1}">&raquo;</a></li> --%>
								<%-- 서버단에서 처리하는 방식 --%>
								<%--<li><a href="listPage${pageMaker.makeQuery(pageMaker.endPage +1) }">&raquo;</a></li> --%>
								<%--JavaScript JQuery를 사용하는 방식 --%>
								<li><a href ="${pageMaker.endPage +1 }">&raquo;</a></li>
							</c:if>
							
						</ul>
					</div>
				</div>
				<!-- /.box-footer -->
			</div>
		</div>
		<!-- /.col (left) -->
	</div>
	<!-- /.row -->
</section>
<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<form id="jobForm">
	<input type='hidden' name="page" value=${pageMaker.cri.perPageNum}>
	<input type='hidden' name="perPageNum" value=${pageMaker.cri.perPageNum}>
</form>

<script>
	var result = '${msg}';
	var testMsge = '${testMessage}';
	var testM = 'String';
	var testMessage = 'reTry';
	
	if(result =='SUCCESS'){
		alert("처리가 완료되었습니다.");
	}
	<%--else if(testMessage =! null){
		alert("리스트 변수 확인 전 단계...\r\n\${testMessage} : "+'${testMessage}'+"\r\n"
		+"testMessage : "+testMsge+"\r\nresult : "+result+"\r\n"+"\${msg} : "+'${msg}'
		+"\r\n"+"re try testMessage : "+testMessage);
	}--%>
	
	<%-- 책의 설명
			'간단히 설명하자면 페이지 번호가 클릭되면 event.preventDefault()를 이용해서 실제 화면의 이동을 막고,
			<a> 태그에 있는 페이지 번호를 찾아서 <form> 태그를 전송하는 방식을 이용하는 것입니다.' 
		But!!!! JavaScript 나 JQuery 등의 기초에 대해 잘 모르는 사람에게는 무슨 소린가 싶다.
		이에대한 추가 Study 필요--%>
	$(".pagination li a").on("click", function(event){
	
		<%-- event.preventDefault() 함수를 사용해 실제 화면 이동 막음--%>
		event.preventDefault();
		
		<%-- <a>태그에 있는 페이지 번호를 찾음--%>
		var targetPage = $(this).attr("href");
		
		<%-- form 태그를 전송함 --%>
		var jobForm = $("#jobForm");
		jobForm.find("[name='page']").val(targetPage);
		jobForm.attr("action","/board/listPage").attr("method","get");
		jobForm.submit();
	});
</script>

<%@ include file="../include/footer.jsp" %>

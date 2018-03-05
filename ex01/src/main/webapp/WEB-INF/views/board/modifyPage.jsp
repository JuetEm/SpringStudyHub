<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>

<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">MODIFY BOARD</h3>
				</div>
				<!-- /.box-header -->
				<!-- form 태그의 action 속성은 form 태그 안에 데이터를 넘길 페이지를 지정해주는 것 -->
<form role="form" action="modifyPage" method="post">
	
	<input type='hidden' name='page' value="${cri.page}">
	<input type='hidden' name='perPageNum' value="${cri.perPageNum}">
	
	<div class="box-body">
		<div class="form-group">
			<label for="exampleInputEmail1">BNO</label>
			<input type="text" name='bno' class="form-control" value="${boardVO.bno }" readonly="readonly">
		</div>
		
		<div class="form-group">
			<label for="exampleInputEmail1">Title</label>
			<input type="text" name='title' class="form-control" value="${boardVO.title }">
		</div>
		
		<div class="form-group">
			<label for="exampleInputPassword1">Content</label>
			<textarea class="form-control" name="content" row="3">${boardVO.content }</textarea>
		</div>
		
		<div class="form-group">
			<label for="exampleInputEmail1">Writer</label>
			<input type="text" name="writer" class="form-control" value="${boardVO.writer }" readonly="readonly">
		</div>
	</div>
	<!-- /.box-body -->
</form>

<div class="box-footer">
	<button type="submit" class="btn btn-primary save">SAVE</button>
	<button type="submit" class="btn btn-warning cancel">CANCEL</button>
</div>

<script>
	$(document).ready(function(){
	
		var formObj = $("form[role='form']");
		
		console.log(formObj);
		
		$(".cancel").on("click", function(){
			self.location = "/board/listPage?page=${cri.page}&perPageNum=${cri.perPageNum}";
		});
		
		$(".save").on("click", function(){
			formObj.submit();
		});
	});
</script>
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col (left) -->
	</div>
	<!-- /.row -->
</section>
<!-- /.content -->

<%@ include file="../include/footer.jsp" %>
</body>
</html>
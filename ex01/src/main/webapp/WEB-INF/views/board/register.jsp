<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>REGISTER STS FRAMEWORK STUDY</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/header.jsp" %>
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<!-- left column -->
			<div class="col-md-12">
			 	<!-- general form elemnets -->
				<div class="box box-primary">	
					<div class="box-header">
						<h3 class="box-title">REGISTER BOARD</h3>
					</div>	 
					<!-- /.box-header -->
		<form role="form" metho="post">
			<div class="box-body">
				<div class="form-group">
					<label for="exampleInputEmail1">Title</label>
					<input type="text" name='title' class="form-control" placeholder="Enter Title">
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">Content</label>
					<textarea class="form-control" name="content" rows="3" placeholder="Enter ..."></textarea>
				</div>
				<div class="form-group">
					<label for="exampleInputEmail1">Writer</label>
					<input type="text" name="writer" class="form-control" placeholder="Enter Writer">
				</div>
			</div>
			<!-- /.box-body -->
			<div class="box-footer">
				<button type="submit" class="btn btn-primary">Submit</button>
			</div>
		</form>
					</div>
					<!-- /.box -->	
				</div>
				<!-- /.col (left) -->
			</div>
			<!-- /.row -->
		</section>
		<!-- /.content -->
		</div>
	<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
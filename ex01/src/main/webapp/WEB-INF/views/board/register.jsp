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
		<form role="form" method="post">
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
				<button type="button" class="btn btn-primary">Submit</button>
			</div>
		</form>
		<script>
			/* MVC 컨트롤러 모델에서 null값 체크를 하고 그 결과로서 메세지만 받는 script
				단순하지만 화면 변화가 생겨 client 단에서 oldfashion 느린 프로그램으로 느껴진다. */
			var msg = '${msg}';
			
			if(msg !=null && msg!=""){
				alert(msg);
			}
			
			/* script에서 null 값을 체크하는 방식, MVC 컨트롤러에서 널값을 체크하면 생기는 불필요한 화면 전환과
				alert메세지를 보여주면 배경 화면이 백색창으로 고정되어 변하지 않는 등 문제를 아직 해결하지 못했기 때문에
				script 단에서 체크하는 코드 구현,
				개인적으로 front-end 쪽 코드를 잘 모르기 때문에 더욱 알고 싶었다. 
				client 의 input 값이나 textarea의 값을 가져오는 방법을 알 수 있었다.
				구글링을 해보니 많은 초심자들이 궁금해 하는 부분이었다.
				
				출처
				http://blog.naver.com/PostView.nhn?blogId=sideni90&logNo=220179459463&parentCategoryNo=&categoryNo=11&viewDate=&isShowPopularPosts=true&from=search
				input 값을 jquery로 가져오기
			ex) <input type="text" id="test_id" class="test_class" name="test_name" vlaue="test">
					1. id로 접근하여 가져오기
						var inputValueById = $('#test_id').val();
					2. class로 접근하여 가져오기
						var inputValueByClass = $('.test_class').val();
					3. name으로 접근하여 가져오기
						var inputValueByName = $('input[name=test_name]').val();
				
				출처 
				http://blog.naver.com/PostView.nhn?blogId=sideni90&logNo=220179459463&parentCategoryNo=&categoryNo=11&viewDate=&isShowPopularPosts=true&from=search
				
				*/
				
			$(document).ready(function(){
			
				$(".btn-primary").on("click", function(){
					var tempTitle = $('input[name=title]').val();
					var tempContent = $('textarea[name=content]').val();
					var tempWriter = $('input[name=writer]').val();
					
					var result = "";
					
					/* String 클래스에서 String prototype 메서드인 replaceAll 메서드를  가져온것
						javascript의 replace 메서드는 한번 값을 바꾸면 멈춘다.
						for문을 활용해서 지우는 방법도 가능 할 듯*/
					String.prototype.replaceAll = function(org, dest){
						return this.split(org).join(dest);
					}
					
					if(null != tempTitle.replaceAll(" ","") &&"" != tempTitle.replaceAll(" ","")){
						console.log("tempTitle is not null!! : "+tempTitle.replaceAll(" ",""));
					}else {
						console.log("tempTitle is null!! : " + tempTitle);
						result += "제목, ";
					}
					if(null != tempContent.replaceAll("", "") && "" != tempContent.replaceAll(" ","")){
						console.log("tempContent is not null!! : "+tempContent);
					}else{
						console.log("tempContent is null!! : "+tempContent);
						result += "내용, ";
					}
					if(null != tempWriter.replaceAll(" ","") && "" != tempWriter.replaceAll(" ","")){
						console.log("tempWriter is not null!! : "+tempWriter);
					}else{
						console.log("tempWriter is null!! : "+tempWriter);
						result += "작성자, ";
					}
					
					if(result == ""){
						 self.location = "/board/listAll";
					}else{
						if(null == tempWriter || "" == tempWriter){
							var message = result.substring(0, result.length-2);
							alert(message+"를 입력해주세요.");
						}else{
							var message = result.substring(0, result.length-2);
							alert(message+"을 입력해주세요.");
						}
					}
				})
			
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
		</div>
	<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
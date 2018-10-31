<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="../common/head.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.txarea {
	width: 40%;
	overflow: auto;
	word-break: break-all;
}
</style>
<script type="text/javascript">
	$(function(){
		$('#btn').on('click', function() {
			var url = $('#url').val();
			if ('' != url && null != url) {
				$.ajax({
					url: url,
					type: 'GET',
					async: false,
					dataType: 'jsonp',
					jsonp: 'callback',
					jsonpCallback: 'getResult',
					success: function(res) {
						$('.txarea').val(JSON.stringify(res.result));
					},error: function() {
						$.messager.alert("错误","无法识别的URL!");
					}
				});
			} else {
				$.messager.alert('系统提示', '请输入URL');
			}
			
		})
	})
	function reset(){
		$('.txarea').val('');
		$('#url').val('');
	}
</script>
</head>
<body>
	<textarea rows="10" cols="10" class="txarea" readonly="readonly"></textarea>
	<div>
		输入接口地址：<input id='url' type="text" placeholder='please input the url'>
		<input type="button" value="获取" id="btn" />
		<button onclick="reset();">清空</button>
	</div>
	
</body>
</html>
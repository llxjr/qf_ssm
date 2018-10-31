<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../common/head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script src='js/encrypt/md5.js'></script>
<script type="text/javascript">
	function updPwd() {
		var userId = $('#userId').val();
		var oldPwd = $('#oldPwd').val();
		var newPwd = $('#newPwd').val();
		if (null == oldPwd || oldPwd.length < 5) {
			$.messager.alert("系统提示", "请输入正确长度的旧密码！");
			return;
		}
		if (null == newPwd || newPwd.length < 5) {
			$.messager.alert("系统提示", "请输入正确长度的新密码！")
			return;
		}
		$.messager.confirm("系统提示", "您确认要修改密码？", function(r) {
			if (r) {
				$.ajax({
					url: 'security/user/modifyPwd',
					method: 'POST',
					data: {
						"userId" : userId,
						"oldPwd" : hex_md5(oldPwd),
						"newPwd" : newPwd
					},
					dataType: 'json',
					//contentType: "application/json;charset=utf-8",
					beforeSend: function(XMLHttpRequest) {
						XMLHttpRequest.setRequestHeader("oper", "PassWordModify");
					},
					success: function(res) {
						if (res.resultCode == 200) {
							$.messager.alert("系统提示", "更改密码成功,正在跳转登录页面...");
							reset();
							setTimeout(function(){
								parent.location.reload();
							}, 5000);
						} else {
							$.messager.alert("系统提示", res.message);
						}
					},
					error: function(){
						$.messager.alert("系统提示", "服务器异常");
					}
				});
			}
		});
	}
	
	function reset() {
		$('#oldPwd').val("");
		$('#newPwd').val("");
	}
</script>
</head>

<body style="height:100%;width:100%;">
	<div class='main'>
	<input type="text" hidden='true' id="userId" value="${userId}" />
		<div>
			<strong>用户名</strong>&nbsp;：&nbsp;<input type="text" id='userName'
				name="userName" value="${userName}" readonly="readonly" />
		</div>
		<div>
			<strong>旧密码</strong>&nbsp;：&nbsp;<input type="password" id='oldPwd'
				name="password" />
		</div>
		<div>
			<strong>新密码</strong>&nbsp;：&nbsp;<input type="password" id='newPwd'
				name="password" />
		</div>
		<button onclick="updPwd();">修改</button>
		&nbsp;&nbsp;
		<button onclick="reset();">重置</button>
	</div>

</body>

</html>

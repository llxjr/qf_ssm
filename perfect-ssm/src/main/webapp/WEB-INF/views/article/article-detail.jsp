<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>查看详情</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>

<body>
	<div class="article" style="">
</body>
<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">	
   // function getQueryStringByName(name) {
     //   var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
      //  if (result == null || result.length < 1) {
       //     return "";
       // }
        //return result[1];
   // }
	var id = '${id}';
	alert(id);
    window.onload = function () {
        var id = '${id}';
        if (id == null) {
            alert("无数据!");
            return;
        }
        $.ajax({
            type: "GET",
            dataType: "json",
            url: '/articles/' + id,
            data: {},
            success: function (data) {
                if (data != null) {
                    $('.article').html(data.data.articleContent);
                    //console.log(data.articleContent);
                }
                else {
                    alert("无数据!");
                }
            }
        });
    }
</script>
</html>

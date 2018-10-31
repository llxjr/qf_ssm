<%@page import="com.ssm.promotion.core.common.Constants"%>
<%@page import="com.ssm.promotion.core.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd HTML 4.01 Transitional//EN">
<%@include file="../common/head.jsp"%>
<html>
<head>
<script src="js/common.js"></script>
<script src="js/home/home.js"></script>
</head>
<body class="easyui-layout">
	<div region="north" style="height: 78px; background-color: #C4E1FF">
		<table width="100%" style="margin-top:40px">
			<tr>
				<td width="50%"></td>
				<td valign="bottom"
					style="font-size: 20px; color: #8B8B8B; font-family: '楷体';"
					align="right" width="50%"><font size="3">&nbsp;&nbsp;<strong>当前管理员：</strong>
						${userName} </font>【${roleName}】 <a href="javascript:logout()" style="width: 100px;">退出登录</a>&nbsp;&nbsp;</td>
			</tr>
		</table>
	</div>
	<div region="center">
		<div class="easyui-tabs" fit="true" border="false" id="tabs">
			<div title="首页" data-options="iconCls:'icon-home'">
				<div align="center" style="padding-top: 50px">
					<font color="grey" size="10">我的后台管理系统</font>
				</div>
			</div>
		</div>
	</div>
	<!-- 左侧导航 -->
	<div region="west" style="width: 200px; height: 500px;" title="导航菜单"
		split="true">
		<div class="easyui-accordion">
			<c:forEach items="${menus}" var="m">
				<div title="${m.title}"
					data-options="selected:true,iconCls:'${m.icon}'"
					style="padding: 10px; height: 10px;">
					<c:forEach items="${m.menuChildList}" var="item">
						<a
							href="javascript:openTab('${item.title}','${item.path}','${item.icon}')"
							class="easyui-linkbutton"
							data-options="plain:true,iconCls:'${item.icon}'"
							style="width: 150px;"> ${item.name}</a>
					</c:forEach>
				</div>
			</c:forEach>
			<%--
			<div title="文章管理"
				data-options="selected:true,iconCls:'icon-wenzhangs'"
				style="padding: 10px; height: 10px;">
				<a
					href="javascript:openTab(' 文章管理-ue','admin/articles/ue_list','icon-wenzhang')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-wenzhang'"
					style="width: 150px;"> UEditor</a> <a
					href="javascript:openTab(' 文章管理-ke','admin/articles/ke_list','icon-wenzhang')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-wenzhang'"
					style="width: 150px;"> kindEditor(推荐)</a>
			</div>
			<div title="图片管理" data-options="iconCls:'icon-shouye'"
				style="padding: 10px">
				<a
					href="javascript:openTab(' 图片设置','admin/pictures/list','icon-tupians')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-tupian'"
					style="width: 150px;"> 图片设置</a>
			</div>
			<div title="系统管理" data-options="iconCls:'icon-item'"
				style="padding: 10px; border: none;">
				<a href="javascript:openTab(' 管理员列表','users/list','icon-lxr')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-lxr'" style="width: 150px;">
					管理员列表</a>
			</div>
		
			--%>
		</div>
	</div>
</body>
</html>
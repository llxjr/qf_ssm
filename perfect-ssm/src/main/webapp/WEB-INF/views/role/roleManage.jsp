<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../common/head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>角色管理</title>
<script src="js/common.js"></script>
<script src="js/role/role.js"></script>
</head>

<body style="margin:1px;">
	<!-- datagrid -->
	<table id="dg"></table>
	<!-- toolbar -->
	<div id="tb">
		<div>
			<a href="javascript:addPage()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a
				href="javascript:editPage()" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">修改</a> <a
				href="javascript:del()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a>
		</div>
		<%--<div>
			&nbsp;权限名称：&nbsp;<input type="text" id="s_title" size="20"
				onkeydown="if(event.keyCode==13) searchMenu()" /> <a
				href="javascript:searchMenu()" class="easyui-linkbutton"
				iconCls="icon-search" plain="true">搜索</a>
		</div>
	--%></div>
	<!-- dialog -->
	<div id="dlg" class="easyui-dialog" style="width: 620px;height:250px;padding: 10px 20px" closed="true" >
		<form id="add_form" method="post">
			<input type="hidden" name='id' id='id' />
			<table cellspacing="8px">
				<tr>
					<td>权限名称：</td>
					<td><input type="text" id="roleName" name="roleName"
						class="easyui-validatebox" required="true" />&nbsp;<font
						color="red">*</font></td>
					<td>状态:</td>
					<td>
						启用<input type="radio" name="status" id="startup" value="1" />
						禁用<input type="radio" name="status" id="forbid" value="0" />
					</td>
				</tr>
				<tr>
					<td>描述:</td>
					<td>
						<input type='text' name='description' id='description' class='easyui-validatebox' required='true'/>
						<%--<font color='red'>*</font>--%>
					</td>
					<%--<td>角色</td>
					<td>
						<select id='role' class="easyui-combobox" name='role'>
							<option value="9" selected="selected">普通管理员</option>
							<option value="99">系统管理员</option>
						</select>
					</td>
				--%></tr>
			</table>
		</form>
	</div>
	<!-- 授权菜单Dlg -->
	<div id="menudlg" class="easyui-dialog" style="width: 620px;height:250px;padding: 10px 20px" closed="true">
		<div id="menus"></div>
	</div>

</body>
</html>

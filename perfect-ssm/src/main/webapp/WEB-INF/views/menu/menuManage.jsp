<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../common/head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>菜单管理</title>
<script src="js/common.js"></script>
<script src="js/menu/menu.js"></script>
</head>

<body style="margin:1px;">
	<!--  dialog，全部数据显示 -->
	<table id="dg"></table>
	<!-- toolbar -->
	<div id="tb">
		<div>
			<a href="javascript:addPage()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a
				href="javascript:editPage()" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">修改</a> <a
				href="javascript:deleteMenu()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>
			&nbsp;菜单名：&nbsp;<input type="text" id="s_title" size="20"
				onkeydown="if(event.keyCode==13) searchMenu()" /> <a
				href="javascript:searchMenu()" class="easyui-linkbutton"
				iconCls="icon-search" plain="true">搜索</a>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog" style="width: 620px;height:250px;padding: 10px 20px" closed="true" >
	<!-- 添加弹框  -->	
		<form id="add_form" method="post">
			<input type="hidden" name='id' id='id' />
			<table cellspacing="8px">
				<tr>
					<td>标题：</td>
					<td><input type="text" id="title" name="title"
						class="easyui-validatebox" required="true" />&nbsp;<font
						color="red">*</font> <input type="hidden" id="id" value="0">
					</td>
					<td>类型：</td>
					<td>
						<select class='easyui-combobox' id='dirType' name='dirType'>
							<option value='0' selected="selected">父目录</option>
							<option value='1'>子目录</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>路径：</td>
					<td><input type="text" id="path" name="path"
						class="easyui-validatebox" required="true" />&nbsp;<font
						color="red">*</font></td>
					<td>父目录：</td>
					<td>
						<select class='easyui-combobox' id="parentId" name='parentId'>
							<option value="" >--请选择--</option>						
						</select>
					</td>
				</tr>
				<tr>
					<td>名称：</td>
					<td>
						<input type='text' name='name' id='name' class='easyui-validatebox' required='true'/>
						<font color='red'>*</font>
					</td>
					<td>描述：</td>
					<td>
						<input type='text' name='description' id='description' class='easyui-validatebox'/>
					</td>
				</tr>
				<tr>
					<td>图标：</td>
					<td>
						<input type='text' name='icon' id='icon' class='easyui-validatebox' required='true'/>
						<font color='red'>*</font>
					</td>
					<td>排序：</td>
					<td>
						<input type='text' name="tOrder" id="tOrder" class='easyui-validatebox' required='true' />
						<font color='red'>*</font>
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>

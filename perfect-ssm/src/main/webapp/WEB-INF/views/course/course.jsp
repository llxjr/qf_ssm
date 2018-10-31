<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程管理</title>
<script src="js/course/course.js"></script>
<script type="text/javascript">
</script>
</head>
<body style="margin:1px;">
	<!-- 显示的表格数据，数据是在course.js里面使用ajax加载的 -->
	<table id="dg"></table>
	<div id="tb">
		<div>
			<a href="javascript:addPage()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a
				href="javascript:editPage()" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">修改</a> <a
				href="javascript:deleteCategory()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>
			&nbsp;课程名：&nbsp;<input type="text" id="c_categoryName" size="20"
				onkeydown="if(event.keyCode==13) searchCategory()" /> <a
				href="javascript:searchCategory()" class="easyui-linkbutton"
				iconCls="icon-search" plain="true">搜索</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog" style="width: 620px;height:250px;padding: 10px 20px" closed="true" >
	<!-- 添加弹框  -->	
		<form id="add_form" method="post">
			<input type="hidden" name='id' id='id' />
			<table cellspacing="8px">
				<tr>
					<td>课程名：</td>
					<td><input type="text" id="courseName" name="courseName"
						class="easyui-validatebox" required="true" />&nbsp;<font
						color="red">*</font> <input type="hidden" id="id" value="0">
					</td>
				</tr>
				<tr>
					<td>标签：</td>
					<td>
						<input type='text' name='tag' id='tag' class='easyui-validatebox' required='true'/>
						<font color='red'>*</font>
					</td>
					<td>描述：</td>
					<td>
						<input type='text' name='description' id='description' class='easyui-validatebox'/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
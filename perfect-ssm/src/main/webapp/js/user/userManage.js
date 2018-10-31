$(function() {
	$('#dg')
			.datagrid(
					{
						url : 'users/datagrid',
						title : '用户管理',
						fitColumns : true,
						loadMsg : '数据加载中,请稍后...',
						rownumbers : true,
						singleSelect : true,
						checkOnSelect : true,
						columns : [ [
								{
									field : 'id',
									title : '编号',
									checkbox : true
								},
								{
									field : 'userName',
									title : '用户名',
									width : 100
								},
								{
									field : 'roleName',
									title : '权限',
									width : 100
								},
								{
									field : 'status',
									title : '用户状态',
									width : 80,
									sortable : true,
									rownumbers : true,
									formatter : function(value, row, index) {
										if (value == 0) {
											return "<font color='green'>正常</font>";
										} else if (value == 1) {
											return "<font color='red'>封停</font>";
										}
									}
								},
								{
									field : "Action",
									title : "操作",
									width : 120,
									align : 'center',
									formatter : function(value, row, index) {
										if (row.id == 1) {
											return "";
										}
										var resetPwd = "<a href='javascript:void(0);' onclick=\"resetPwd("+ row.id + ", '"+ row.userName +"')\" >重置密码 </a>";
										var setStatus;
										if (row.status == 0) {
											setStatus = "<a href='javascript:void(0);' onclick='forbid("
													+ row.id + ")' >封停</a>";
										} else if (row.status == 1) {
											setStatus = "<a href='javascript:void(0);' onclick='unforbid("
													+ row.id + ")' >解封</a>";
										}
										return resetPwd + " | " + setStatus;
									}
								} ] ],
						pagination : true,
						pageSize : 10,
						pageList : [ 10, 20, 35, 50 ],
						toolbar : '#tb'
					});
})
function forbid(id) {
	$.messager.confirm("系统提示","您确定封停该用户？", function(r) {
		if (r) {
			$.ajax({
				url : 'users/chmod/' + id + '/forbid',
				method : 'GET',
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				success : function(res) {
					if (res.resultCode == 200) {
						$.messager.alert("系统提示", "操作成功");
						$('#dg').datagrid('reload');
					} else {
						$.messager.alert("系统提示", "操作失败:" + res.message);
					}
				},
				error : function() {
					$.messager.alert("系统提示", "操作失败!");
				}
			});
		}
	});
}

function unforbid(id) {
	$.messager.confirm("系统提示","您确定解封该用户？", function(r) {
		if (r) {
			$.ajax({
				url : 'users/chmod/' + id + '/unforbid',
				method : 'GET',
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				success : function(res) {
					console.log(res);
					if (res.resultCode == 200) {
						$.messager.alert("系统提示", "操作成功");
						$('#dg').datagrid('reload');
					} else {
						$.messager.alert("系统提示", "操作失败:" + res.message);
					}
				},
				error : function() {
					$.messager.alert("系统提示", "操作失败!");
				}
			});
		}
	});
}

function resetPwd(id, name) {
	$.messager.confirm("系统提示","您确定重置用户名:<font color='red'>"+ name +"</font>的密码吗？",function(r) {
		$.ajax({
			url: 'users/resetPwd/' + id,
			method: 'GET',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			success: function(res){
				if (res.resultCode == 200) {
					$.messager.alert("系统提示", "操作成功");
					$('#dg').datagrid('reload');
				} else {
					$.messager.alert("系统提示", "操作失败:" + res.message);
				}
			},
			error : function() {
				$.messager.alert("系统提示", "操作失败!");
			}
		})
	})
}
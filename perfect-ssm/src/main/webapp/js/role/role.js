/**
 * @author chj
 */
var allChlidMenus;
$(function() {
	// datagrid
	$('#dg').datagrid({
		url : 'sys/role/datagrid',
		title : '角色管理',
		// width : '100%',
		fitColumns : true,
		loadMsg : '数据加载中,请稍后...',
		rownumbers : true,
		singleSelect : true,
		checkOnSelect : true,
		columns : [ [ {
			field : 'id',
			title : '编号',
			checkbox : true
		},{
			field: 'grade',
			title: '等级',
			hidden: true
		}, {
			field : 'roleName',
			title : '权限名称',
			width : 60
		}, {
			field : 'isUsed',
			title : '状态',
			width : 40,
			rownumbers : true,
			formatter : function(value, row, index) {
				if (value == 0) {
					return "<font color='red'>禁用</font>";
				} else if (value == 1) {
					return "<font color='green'>启用</font>";
				}
			}
		}, {
			field: "description",
			title: '描述',
			width: 100
		},{
			field : 'createTime',
			title : '创建时间',
			width : 80,
			sortable : true,
			formatter : function(date) {
				 var unixTimestamp = new Date(date.time);  
		         return unixTimestamp.toLocaleString();  
			}
		}, {
			field : 'createBy',
			title : '创建人',
			width : 60
		}, {
			field : "Action",
			title : "操作",
			width : 160,
			align : 'center',
			formatter : function(value, row, index) {
				if (row.grade == 99) {
					return "";
				} 
				var oper1;
				if (row.isUsed == 1) {//已启用
					oper1 = "<a href='javascript:void(0);' onclick=\"forbid("+ row.id +")\" >禁用 </a>&nbsp;|";
				} else if (row.isUsed == 0){//已禁用
					oper1 = "<a href='javascript:void(0);' onclick=\"startup("+ row.id +")\" >启用 </a>&nbsp;|";
				}
				var oper2 = "&nbsp;<a href='javascript:void(0);' onclick=\"authMenu("+ row.id +")\" >授权菜单 </a>&nbsp;|"
				var oper3 = "&nbsp;<a href='javascript:void(0);' onclick=\"detail("+ row.id +")\" >查看详情 </a>&nbsp;"
				return oper1 + oper2 + oper3;
			}
		} ] ],
		pagination : true,
		pageSize : 20,
		pageList : [ 10, 20, 35, 50 ],
		toolbar : '#tb'
	});
})

function addPage() {
	$('#dlg').dialog({
		title : '添加权限',
		closed : false,
		cache: false,
	    width: 600,             
	    height: 300,        
		buttons : [ {
			text : '保存',
			iconCls : 'icon-add',
			handler : function() {
				add();
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#dlg').dialog("close");
				reset();
			}
		} ]
	})
}

function editPage() {
	var selectedRows = $('#dg').datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条记录！');
		return;
	}
	var row = selectedRows[0];
	if (row.grade == 99) {
		$.messager.alert("系统提示", "您无法对超级管理员进行操作!");
		return;
	}
	$('#id').val(row.id);
	if (row.isUsed == 1) {
		$("#startup").prop("checked", "checked");
	} else {
		$("#forbid").prop("checked", "checked");
	}
	$('#roleName').val(row.roleName);
	$('#description').val(row.description);
	$('#dlg').dialog({
		title : '编辑权限',
		closed : false,
		buttons : [ {
			text : '编辑',
			iconCls : 'icon-edit',
			handler : function() {
				edit();
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#dlg').dialog('close');
				reset();
			}
		} ]
	})
}

function add(){
	var roleName = $('#roleName').val();
	var status = $("input[name = 'status']:checked").val();
	var description = $('#description').val();
	if (roleName == null || roleName == '') {
		$.messager.alert("系统提示", "请输入权限名称");
		return;
	}
	if (status == null || status == '') {
		$.messager.alert("系统提示", "请选择状态");
		return;
	}
	var data = {
		roleName: roleName,
		isUsed: status,
		description: description
	}
	$.ajax({
		url: 'sys/role/add',
		data: JSON.stringify(data),
		type: 'POST',
		dataType: 'json',
		contentType : 'application/json; charset=utf-8',
		success:function(res) {
			if (res.resultCode == 200) {
				$.messager.alert("系统提示", "添加成功!");
				reset();
				$('#dlg').dialog("close");
				$('#dg').datagrid('reload');
			} else {
				$.messager.alert("系统提示", res.message);
			}
		},error: function() {
			$.messager.alert("系统提示", "网络错误！");
		}
	})
}

function edit(){
	var id = $('#id').val();
	var roleName = $('#roleName').val();
	var status = $("input[name = 'status']:checked").val();
	var description = $('#description').val();
	if (roleName == null || roleName == '') {
		$.messager.alert("系统提示", "请输入权限名称");
		return;
	}
	if (status == null || status == '') {
		$.messager.alert("系统提示", "请选择状态");
		return;
	}
	var data = {
		id: id,
		roleName: roleName,
		isUsed: status,
		description: description
	}
	$.ajax({
		url: 'sys/role/update',
		data: JSON.stringify(data),
		type: 'POST',
		dataType: 'json',
		contentType : 'application/json; charset=utf-8',
		success:function(res) {
			if (res.resultCode == 200) {
				$.messager.alert("系统提示", "更新成功!");
				reset();
				$('#dlg').dialog("close");
				$('#dg').datagrid('reload');
			} else {
				$.messager.alert("系统提示", res.message);
			}
		},error: function() {
			$.messager.alert("系统提示", "网络错误！");
		}
	})
}

function del() {
	var selectedRows = $('#dg').datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条记录！');
		return;
	}
	var row = selectedRows[0];
	if (row.grade == 99) {
		$.messager.alert("系统提示", "您无法对超级管理员进行操作!");
		return;
	}
	$.messager.confirm("系统提示", "您确定要删除这条数据吗?", function(r) {
		if (r) {
			$.ajax({
				url : 'sys/role/delete/' + row.id,
				dataType : 'json',
				type : 'DELETE',
				data : {},
				success : function(result) {
					if (result.resultCode == 200) {
						$.messager.alert("系统提示", "删除成功!");
						$('#dg').datagrid('reload');
					} else {
						$.messager.alert("系统提示", result.message);
					}
				},error: function () {
					$.messager.alert("系统提示", "网络错误!");
				}
			})
		}
	})
}

function reset(){
	$('#id').val('');
	$('roleName').val('');
	$('description').val('');
	/*$("input[ name = 'status']").prop("checked", "checked");*/
}

function forbid(id) {
	$.messager.confirm("系统提示", "您确定禁用该权限？", function(r) {
		if (r) {
			if (null != id && id != '') {
				$.ajax({
					url: 'sys/role/chStatus/' + id + '/0x00',
					type: 'GET',
					data: {},
					dataType: 'json',
					beforeSend: function(XMLHttpRequest) {
						XMLHttpRequest.setRequestHeader("oper", "RoleOper");
					},
					success: function(r) {
						if (r.resultCode == 200) {
							$.messager.alert("系统提示", "禁用成功！");
							$("#dg").datagrid("reload");
						} else {
							$.messager.alert("系统提示", r.message);
						}
					},error: function (){
						$.messager.alert("系统提示", "操作失败，网络异常！");
					}
				})
			} else {
				$.messger.alert("系统提示", "选中数据ID异常！");
			}
		}
	})
}

function startup(id) {
	$.messager.confirm("系统提示", "您确定启用该权限？", function(r) {
		if (r) {
			if (null != id && id != '') {
				$.ajax({
					url: 'sys/role/chStatus/' + id + '/0x01',
					type: 'GET',
					data: {},
					dataType: 'json',
					beforeSend: function(XMLHttpRequest) {
						XMLHttpRequest.setRequestHeader("oper", "RoleOper");
					},
					success: function(r) {
						if (r.resultCode == 200) {
							$.messager.alert("系统提示", "启用成功！");
							$("#dg").datagrid("reload");
						} else {
							$.messager.alert("系统提示", r.message);
						}
					},error: function (){
						$.messager.alert("系统提示", "操作失败，网络异常！");
					}
				})
			} else {
				$.messger.alert("系统提示", "选中数据ID异常！");
			}
		}
	})
}

function authMenu(id) {
	if (null != id && id != '') {
		$.ajax({
			url: 'sys/role/getAuthMenus/' + id,
			type: 'GET',
			data: {},
			dataType: 'json',
			success: function(lst) {
				allChlidMenus = new Array();
				console.log(lst);
				var html = '';
				for (var i = 0; i < lst.length; ++ i) {
					var item = lst[i];
					var childList = item.menuChildList;
					if (item.selected) {
						html += "<div id='parent' style='background-color:#00FFFF;height:24px;'>" +
						"<input type='checkbox' name='m' value='"+ item.id +"' onclick='validpCb("+ item.id +")' checked />" +
								item.title + "</div>";
					} else {
						html += "<div id='parent' style='background-color:#00FFFF;height:24px;'>" +
						"<input type='checkbox' name='m' value='"+ item.id +"' onclick='validpCb("+ item.id +")' />" +
								item.title + "</div>";
					}
					for (var j = 0; j < childList.length; ++ j) {
						var child = childList[j];
						allChlidMenus.push(child);
						if (child.selected) {
							html += "<div id='child' style='height:20px;margin-left:20px;'>" +
							"<input type='checkbox' name='m' value='"+ child.id +"' onclick='validCb("+ child.id +")' checked />" +
								child.title + "</div>";
						} else {
							html += "<div id='child' style='height:20px;margin-left:20px;'>" +
							"<input type='checkbox' name='m' value='"+ child.id +"' onclick='validCb("+ child.id +")'  />" +
								child.title + "</div>";
						}
					}
				}
				$('#menus').html(html);
			}
		})
		//open Dialog
		$('#menudlg').dialog({
			height: 600,
			width: 500,
			title : '授权菜单',
			closed : false,
			buttons : [ {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					editAuthMenu(id);
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function() {
					$('#menudlg').dialog('close');
				}
			} ]
		})
	} else {
		$.messager.alert("系统提示", "选中记录ID错误！");
	}
}

function editAuthMenu(id){
	if (id == null || id == '') {
		$.messager.alert("系统提示", "权限ID异常！");
		return;
	}
	var data = '';
	$("input:checkbox[ name='m' ]:checked").each(function(){
		data += $(this).val() + ",";
	})
	if (data != '') {
		data = data.substring(0, data.length - 1);
		$.ajax({
			url: 'sys/role/auth',
			type: 'POST',
			dataType: 'json',
			data: {
				dataStr: data,
				roleId: id
			}, beforeSend: function(XMLHttpRequest){
				XMLHttpRequest.setRequestHeader("oper", "RoleAuth");
			}, success: function(r) {
				if (r.resultCode == 200) {
					$.messager.alert("系统提示", "操作成功");
					$('#menudlg').dialog('close');
				} else {
					$.messager.alert("系统提示", r.message);
				}
			}, error:function() {
				$.messager.alert("系统提示", "网络异常！");
			}
		});
	} else {
		$.messager.alert('系统提示', "操作成功！");
	}
}

function validCb(id) {
	var lst = allChlidMenus;
	var parentId = new Number();
	for (var i = 0; i < lst.length; i ++) {
		var item = lst[i];
		if (item.id == id) {//找到相同的子目录 选中父目录
			parentId = item.parentId;
			break;
		}
	}
	$("input:checkbox[ value='"+ parentId +"']").attr("checked", true);
	$("input:checkbox[ value='"+ parentId +"']").prop("checked", true);
}

function validpCb(id){
	var lst = allChlidMenus;
	for (var i = 0; i < lst.length; i ++) {
		var item = lst[i];
		if (item.parentId == id) {//找到相同的父目录 不选中子目录
			$("input:checkbox[ value='"+ item.id +"']").attr("checked", false);
			$("input:checkbox[ value='"+ item.id +"']").prop("checked", false);
		}
	}
}

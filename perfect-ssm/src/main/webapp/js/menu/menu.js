$(function() {
	// datagrid
	$('#dg').datagrid({
		url : 'sys/menu/datagrid',
		title : '菜单管理',
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
		}, {
			field : 'title',
			title : '标题',
			width : 100
		}, {
			field : 'menuName',
			title : '名称',
			width : 100
		}, {
			field : 'type',
			title : '类型',
			width : 80,
			sortable : true,
			rownumbers : true,
			formatter : function(value, row, index) {
				if (value == 0) {
					return "父菜单";
				} else if (value == 1) {
					return "子菜单";
				}
			}
		}, {
			field : 'pmName',
			title : '父菜单',
			width : 100,
//			sortable : true,
		}, {
			field : 'path',
			title : '路径',
			width : 200,
		}, {
			field : 'icon',
			title : '图标',
			width : 120,
		}, {
			field : 'description',
			title : '描述',
			width : 200,
		}, 
		{
			field: 'tOrder',
			title: '排序',
			sortable: true,
			width: 30
		},
		{
			field : 'createTime',
			title : '创建时间',
			width : 160,
			sortable : true,
			formatter : function(date){
				var time = new Date(date.time);
				return time.toLocaleString();
			}
		}, 
		{
			field : 'createBy',
			title : '创建人',
			width : 100
		} ] ],
		pagination : true,
		pageSize : 20,
		pageList : [ 10, 20, 35, 50 ],
		toolbar : '#tb'
	});

	$('#dirType').combobox({
		onChange : function(newVal, oldVal) {
			var $this = $('#dirType');
			var type = $this.combobox('getValue');
			if (type == 1) {// 若选择了子菜单
				$.ajax({// 获取父菜单列表
					url : 'sys/menu/findParentMenus',
					data : {},
					success : function(res) {
						var parentList = res.data;
						var data = new Array();
						for ( var i = 0; i < parentList.length; ++i) {
							var parent = parentList[i];
							data.push({
								"value" : parent.id,
								"text" : parent.title
							});
						}
						$('#parentId').combobox("loadData", data);
					},
					error : function(res) {
					}
				})
			} else {
				$('#parentId').combobox("loadData", "");
				$('#parentId').combobox("setValue", "");
			}
		}
	})

});

function reset() {
	$('#id').val("");
	$('#title').val("");
	$('#type').val("");
	$('#path').val("");
	$('#icon').val("");
	$('#name').val("");
	$('#description').val("");
	$('#dirType').combobox("select", "父目录");
	$('#parentId').combobox("select", "");
}

function addPage() {
	$('#dlg').dialog({
		title : '添加菜单',
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

	$('#id').val(row.id);
	$('#title').val(row.title);
	$('#path').val(row.path);
	$('#icon').val(row.icon);
	$('#description').val(row.description);
	$('#name').val(row.menuName);
	if (row.type == 0) {
		$('#dirType').combobox("select", "父目录");
		$('#dirType').combobox("setValue", 0);
	} else if (row.type == 1) {
		$('#dirType').combobox("select", "子目录");
		$('#dirType').combobox("setValue", 1);
		// $('#parentId').combobox("select", row.pmName);
	}
	$('#role').combobox("select", row.roleName);
	if (row.roleName == '系统管理员') {
		$('#role').combobox("setValue", 99);
	} else {
		$('#role').combobox("setValue", 9);
	}
	$('#tOrder').val(row.tOrder);
	$('#dlg').dialog({
		title : '编辑菜单',
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

function add() {
	var title = $('#title').val();
	var type = $('#dirType').combobox('getValue');
	var path = $('#path').val();
	var parentId = $('#parentId').combobox('getValue');
	var icon = $('#icon').val();
	var name = $('#name').val();
	var tOrder = $('#tOrder').val();
	if (type == 1) {
		if (path == null || path == '') {
			$.messager.alert('系统提示', '路径不能为空!');
			return;
		}
		if (parentId == null || parentId == '') {
			$.messager.alert('系统提示', '请选择父目录名称!');
			return;
		}
	}
	if (title == null || title == '') {
		$.messager.alert('系统提示', '标题不能为空!');
		return;
	}
	if (type == null || type == '') {
		$.messager.alert('系统提示', '类型不能为空!');
		return;
	}
	if (icon == null || icon == '') {
		$.messager.alert('系统提示', '图标不能为空!');
		return;
	}
	if (name == null || name == '') {
		$.messager.alert("系统提示", "名称不能为空");
		return;
	}
	if (tOrder == null || tOrder == '') {
		$.messager.alert("系统提示", "排序不能为空");
		return;
	}
	var menu = new Object();
	menu.type = type;
	menu.parentId = parentId;
	menu.path = path;
	menu.title = title;
	menu.icon = icon;
	menu.name = name;
	menu.description = $('#description').val();
	menu.tOrder = tOrder;
	var data = JSON.stringify(menu);
	$.ajax({
		url : 'sys/menu/add',
		type : 'POST',
		data : data,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		success : function(res) {
			if (res.resultCode == 200) {
				$.messager.alert("系统提示", "添加菜单成功!");
				reset();
				$('#dlg').dialog("close");
				$('#dg').datagrid('reload');
			} else {
				$.messager.alert("系统提示", res.message);
			}
		},
		error : function() {
			$.messager.alert("系统错误!");
		}

	})
}

function edit() {
	var title = $('#title').val();
	var type = $('#dirType').combobox('getValue');
	var path = $('#path').val();
	var parentId = $('#parentId').combobox('getValue');
	var icon = $('#icon').val();
	var name = $('#name').val();
	var tOrder = $('#tOrder').val();
	if (type == 1) {
		if (path == null || path == '') {
			$.messager.alert('系统提示', '路径不能为空!');
			return;
		}
		if (parentId == null || parentId == '') {
			$.messager.alert('系统提示', '请选择父目录名称!');
			return;
		}
	}
	if (title == null || title == '') {
		$.messager.alert('系统提示', '标题不能为空!');
		return;
	}
	if (type == null || type == '') {
		$.messager.alert('系统提示', '类型不能为空!');
		return;
	}
	if (icon == null || icon == '') {
		$.messager.alert('系统提示', '图标不能为空!');
		return;
	}
	if (name == null || name == '') {
		$.messager.alert("系统提示", "名称不能为空");
		return;
	}
	if (tOrder == null || tOrder == '') {
		$.messager.alert("系统提示", "排序不能为空");
		return;
	}
	var menu = new Object();
	menu.id = parseInt($('#id').val());
	menu.type = type;
	menu.parentId = parentId;
	menu.path = path;
	menu.title = title;
	menu.icon = icon;
	menu.name = name;
	menu.description = $('#description').val();
	menu.tOrder = tOrder;
	var data = JSON.stringify(menu);
	$.ajax({
		url : 'sys/menu/edit',
		type : 'POST',
		data : data,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		success : function(res) {
			if (res.resultCode == 200) {
				$.messager.alert("系统提示", "编辑菜单成功!");
				reset();
				$('#dlg').dialog("close");
				$('#dg').datagrid('reload');
			} else {
				$.messager.alert("系统提示", res.message);
			}
		},
		error : function() {
			$.messager.alert("系统错误!");
		}

	})
}

function deleteMenu() {
	var selectedRows = $('#dg').datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条记录！');
		return;
	}
	var row = selectedRows[0];
	$.messager.confirm("系统提示", "您确定要删除这条数据吗?", function(r) {
		if (r) {
			$.ajax({
				url : 'sys/menu/delete/' + row.id,
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
				}
			})
		}
	})
}

function searchMenu() {
	var searchText = $('#s_title').val();
	$("#dg").datagrid('load', {
		url: 'sys/menu/datagrid',
		"title" : searchText
	});
}
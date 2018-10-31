$(function() {
	$('#dg').datagrid(
					{
						url : 'qf/category/datagrid',
						title : '类目管理',
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
								}, {
									field : 'categoryName',
									title : '类目名',
									width : 100
								}, {
									field : 'description',
									title : '描述',
									width : 100
								}, {
									field : 'parentId',
									title : '类型',
									width : 50,
									sortable : true,
									rownumbers : true,
									formatter : function(value, row, index) {
										if (value == 0) {
											return "父类目";
										} else {
											return "子类目";
										}
									}
								}, {
									field : 'pcName',
									title : '父类目',
									width : 100,
//									sortable : true,
								}, {
									field : 'tag',
									title : '标签',
									width : 110
								}, {
									field : 'level',
									title : '目录等级',
									width : 50
								}, {
									field : 'createTime',
									title : '创建时间',
									width : 160,
									sortable : true,
									formatter : function(date){
										var time = new Date(date.time);
										return time.toLocaleString();
									}
								}, {
									field : 'createBy',
									title : '创建人',
									width : 80
								} ] ],
						pagination : true,
						pageSize : 10,
						pageList : [ 10, 20, 35, 50 ],
						toolbar : '#tb'
					});
	$('#dirType').combobox({
		onChange : function(newVal, oldVal) {
			var $this = $('#dirType');
			var type = $this.combobox('getValue');
			if (type == 1) {// 若选择了子菜单
				$.ajax({// 获取父菜单列表
					url : 'qf/category/findParentCategory',
					data : {},
					success : function(res) {
						var parentList = res.data;
						var data = new Array();
						for ( var i = 0; i < parentList.length; ++i) {
							var parent = parentList[i];
							data.push({
								"value" : parent.id,
								"text" : parent.categoryName
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
	$('#categoryName').val("");
	$('#description').val("");
	$('#tag').val("");
}

function addPage() {
	$('#dlg').dialog({
		title : '添加类目',
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
	$('#category').val(row.category);
	$('#description').val(row.description);
	$('#tag').val(row.tag);
	$('#dlg').dialog({
		title : '编辑类目',
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
	var categoryName = $('#categoryName').val();
	var type = $('#dirType').combobox('getValue');
	var parentId = $('#parentId').combobox('getValue');
	var description = $('#description').val();
	var tag = $('#tag').val();
	if (categoryName == null || categoryName == '') {
		$.messager.alert('系统提示', '类目名不能为空!');
		return;
	}
	if (type == 1) {
		if (parentId == null || parentId == '') {
			$.messager.alert('系统提示', '请选择父目录名称!');
			return;
		}
	}
	if (tag == null || tag == '') {
		$.messager.alert('系统提示', '标签不能为空!');
		return;
	}
	var category = new Object();
	category.categoryName = categoryName;
	category.description = description;
	category.tag = tag;
	category.parentId = parentId;
	var data = JSON.stringify(category);
	$.ajax({
		url : 'qf/category/addCategory',
		type : 'POST',
		data : data,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		success : function(res) {
			if (res.resultCode == 200) {
				$.messager.alert("系统提示", "添加类目成功!");
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
	var categoryName = $('#categoryName').val();
	var descriprion = $('#descriprion').val();
	var tag = $('#tag').val();
	if (categoryName == 1) {
		if (categoryName == null || categoryName == '') {
			$.messager.alert('系统提示', '类目名不能为空!');
			return;
		}
		if (tag == null || tag == '') {
			$.messager.alert('系统提示', '标签不能为空!');
			return;
		}
	}
	var category = new Object();
	category.id = parseInt($('#id').val());
	category.categoryName = categoryName;
	category.description = $('#description').val();;
	category.tag = tag;
	var data = JSON.stringify(category);
	$.ajax({
		url : 'qf/category/editCategory',
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

function deleteCategory() {
	var selectedRows = $('#dg').datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条记录！');
		return;
	}
	var row = selectedRows[0];
	$.messager.confirm("系统提示", "您确定要删除这条数据吗?", function(r) {
		if (r) {
			$.ajax({
				url : 'qf/category/deleteCategory/' + row.id,
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

function searchCategory() {
	var searchText = $('#c_categoryName').val();
	$("#dg").datagrid('load', {
		url: 'qf/category/datagrid',
		"categoryName" : searchText
	});
}

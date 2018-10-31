$(function() {
	$('#dg').datagrid(
					{
						url : 'qf/course/datagrid',
						title : '课程管理',
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
									field : 'courseName',
									title : '类目名',
									width : 100
								}, {
									field : 'description',
									title : '描述',
									width : 100
								}, {
									field : 'categoryId',
									title : '所属类目',
									width : 100,
//									sortable : true,
								}, {
									field : 'tag',
									title : '标签',
									width : 110
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
});

function reset() {
	$('#id').val("");
	$('#courseName').val("");
	$('#description').val("");
	$('#tag').val("");
}

function addPage() {
	$('#dlg').dialog({
		title : '添加课程',
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
	$('categoryName').val(row.categoryName);
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
	var courseName = $('courseName').val();
	var type = $('#dirType').combobox('getValue');
	var description = $('#description').val();
	var tag = $('#tag').val();
	if (categoryName == null || categoryName == '') {
		$.messager.alert('系统提示', '类目名不能为空!');
		return;
	}
	if (tag == null || tag == '') {
		$.messager.alert('系统提示', '标签不能为空!');
		return;
	}
	var category = new Object();
	category.categoryName = categoryName;
	category.description = description;
	category.tag = tag;
	var data = JSON.stringify(category);
	$.ajax({
		url : 'qf/course/addCourse',
		type : 'POST',
		data : data,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		success : function(res) {
			if (res.resultCode == 200) {
				$.messager.alert("系统提示", "添加课程成功!");
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
			$.messager.alert('系统提示', '课程名不能为空!');
			return;
		}
		if (tag == null || tag == '') {
			$.messager.alert('系统提示', '标签不能为空!');
			return;
		}
	}
	var category = new Object();
	category.id = parseInt($('#id').val());
	category.courseName = courseName;
	category.description = $('#description').val();;
	category.tag = tag;
	var data = JSON.stringify(category);
	$.ajax({
		url : 'qf/course/editCourse',
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

function deleteCourse() {
	var selectedRows = $('#dg').datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条记录！');
		return;
	}
	var row = selectedRows[0];
	$.messager.confirm("系统提示", "您确定要删除这条数据吗?", function(r) {
		if (r) {
			$.ajax({
				url : 'qf/course/deleteCourse/' + row.id,
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

function searchCourse() {
	var searchText = $('#c_courseName').val();
	$("#dg").datagrid('load', {
		url: 'qf/course/datagrid',
		"courseName" : searchText
	});
}

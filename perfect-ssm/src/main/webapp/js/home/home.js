/* 	checkCookie(); */
var url;

function addTab(url, text, iconCls) {
	var content = "<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='"
			+ url + "'></iframe>";
	$("#tabs").tabs("add", {
		title : text,
		iconCls : iconCls,
		closable : true,
		content : content
	});
}

function openTab(text, url, iconCls) {
	if ($("#tabs").tabs("exists", text)) {
		$("#tabs").tabs("close", text);
		addTab(url, text, iconCls);
		$("#tabs").tabs("select", text);
	} else {
		addTab(url, text, iconCls);
	}
}

function logout() {
	$.messager.confirm("系统提示", "您确定要退出系统吗", function(r) {
		if (r) {
			$.ajax({
				url : 'users/logout',
				type : 'POST',
				success : function(res) {
					if (res.resultCode == 200) {
						clearCookie();
					} else {
						alert(res.message);
					}
				},
				error : function() {
					alert('登出失败，请稍后再试!');
				}
			})
		}
	});
}
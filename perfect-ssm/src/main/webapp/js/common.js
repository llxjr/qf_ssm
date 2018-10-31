function setCookie(name, value) {
	var Days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
}

function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}

function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if (cval != null)
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

function checkCookie() {
	if (getCookie("userName") == null || getCookie("roleName") == null) {
		alert("未登录!");
		window.location.href = "login";
	}
}

function clearCookie() {
	delCookie("userName");
	delCookie("roleName");
	window.location.href = "login";
}

function formatterdate(val, row) {
	if (val != null) {
		var date = new Date(val);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
				+ date.getDate();
	}
}
/**
 * 格式化日期（不含时间）
 */
function formatterdate1(val, row) {
	if (val != null) {
		var date = new Date(val);
		return date.getFullYear()
				+ "-"// "年"
				+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
						+ (date.getMonth() + 1)) + "-"// "月"
				+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate());
	}
}
/**
 * 格式化日期（含时间"00:00:00"）
 */
function formatterdate2(val, row) {
	if (val != null) {
		var date = new Date(val);
		return date.getFullYear()
				+ "-"// "年"
				+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
						+ (date.getMonth() + 1)) + "-"// "月"
				+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate())
				+ " " + "00:00:00";
	}
}
/**
 * 格式化去日期（含时间）
 */
function formatterdate3(val, row) {
	if (val != null) {
		var date = new Date(val);
		return date.getFullYear()
				+ "-"// "年"
				+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
						+ (date.getMonth() + 1))
				+ "-"// "月"
				+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate())
				+ " "
				+ (date.getHours() < 10 ? "0" + date.getHours() : date
						.getHours())
				+ ":"
				+ (date.getMinutes() < 10 ? "0" + date.getMinutes() : date
						.getMinutes())
				+ ":"
				+ (date.getSeconds() < 10 ? "0" + date.getSeconds() : date
						.getSeconds());
	}
}
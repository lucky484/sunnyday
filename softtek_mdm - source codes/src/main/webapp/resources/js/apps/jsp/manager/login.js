var _type=$("meta[name='_type']").attr("content");
var _message=$("meta[name='_message']").attr("content");
var _url=$("meta[name='_url']").attr("content");
if (_message.trim().length > 0) {
	$(".notify").notify({
		type : _type,
		message : {
			html : false,
			text : _message
		}
	}).show();
}

var errmsg = $("#message_error").text().trim();
if (errmsg.trim().length > 0) {
	$(".notify").notify({
		type : "danger",
		message : {
			html : false,
			text : errmsg
		}
	}).show();
}
$("#imgObj").click(function() {
	var src = $("#imgObj").attr("src");
	$("#imgObj").attr("src", changeImg(src));
});
function changeImg(url) {
	var timestamp = (new Date()).valueOf();
	url = _url;
	if ((url.indexOf("&") >= 0)) {
		url = url + "×tamp=" + timestamp;
	} else {
		url = url + "?timestamp=" + timestamp;
	}
	return url;
}
var validator = $('#signin').parsley();
$('#signin').submit(function() {
	validator.validate();
	if (validator.isValid()) {
		return true;
	} else {
		return false;
	}
});
window.localStorage.clear();
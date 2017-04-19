$(function() {
	$('#addProjectManagerCommentForm').validate({
		focusInvalid : false, // do not focus the last invalid input
		unhighlight : function(element) { // revert the change dony by
			// hightlight
			$(element).next().remove();
			$(element).next().next().remove();
			$(element).removeClass("error-textarea");
		},
    	highlight : function(element) { // hightlight error inputs
    		$(element).removeClass("focus");
	    	$(element).addClass('error-textarea');
	    	$(element).parent().find("label").remove();
	    	var label = "<label for='comment' class='error-textarea-msg'>请输入评论内容</label>";
	    	$(element).after(label);
	    	//$(element).parent().find("label").addClass('error-textarea-msg');
		},
		submitHandler : function(form) {
			submitComment(form);
		},
		rules : {
			comment : {
				required : true
			}
		},
		messages : {
			comment : {
				required : "请输入评论内容"
			},
		},
	});
});

$('#addProjectManagerCommentForm .btn').keypress(function(e) {
	if (e.which == 13) {
		submitComment("#addProjectManagerCommentForm");
		return false;
	}
});

function getCommentHtml(comment) {
	return '<div class="control-group">' + '评论人：<i>@' + comment.critic + '&nbsp;&nbsp;</i>' + '评论时间：<i>' + new Date(comment.createTime).Format("yyyy-MM-dd hh:mm:ss") + '</i>' + '<p>' + comment.comment + '</p><hr style="width:45%"/></div>'
}

function submitComment(form) {
	$(form).ajaxSubmit({
		url : "addComment.do",
		type : 'post',
		success : function(data) {
			if (data.success == true) {
				// 动态插入评论html内容
				if ($("#commentsDiv").html()=='') {
					$("#commentsDiv").append("<h4>评论</h4><hr style='width:45%'/><div class='commentsList'></div>");
					$(".commentsList").append(getCommentHtml(data.comment));
				} else {
					$(getCommentHtml(data.comment)).insertBefore($(".commentsList .control-group")[0]);
				}
				$(form).resetForm();
				$(".commentsList .control-group:first-child").pulsate({
				    color: "#bf1c56",
				    repeat: 10,
				});
			}
		}
	});
}
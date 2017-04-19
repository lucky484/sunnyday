$(function() {
	$('#addOutsourcingManageCommentForm').validate({
		errorElement : 'label', // default input error message container
		errorClass : 'help-inline', // default input error message class
		focusInvalid : false, // do not focus the last invalid input
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
		highlight : function(element) {
			$(element).closest('.control-group').addClass('error');
		},
		unhighlight : function(element) { // revert the change dony by
			$(element).closest('.control-group').removeClass('error').addClass('success');
		},
		success : function(label) {
			label.addClass('valid').addClass('help-inline ok').closest('.control-group').removeClass('error').addClass('success');
		},
		submitHandler : function(form) {
			submitComment(form);
		}
	});
});

$('#addOutsourcingManageCommentForm .btn').keypress(function(e) {
	if (e.which == 13) {
		submitComment("#addOutsourcingManageCommentForm");
		return false;
	}
});

function getCommentHtml(comment) {
	return '<div class="control-group">' + '评论人：<i class="blue">@' + comment.critic + '&nbsp;&nbsp;</i>' + '评论时间：<i>' + new Date(comment.createTime).Format("yyyy-MM-dd hh:mm:ss") + '</i>' + '<p>' + comment.comment + '</p><hr /></div>'
}

function submitComment(form) {
	$(form).ajaxSubmit({
		url : "projectsManagement/outsourcingManage/addComment.do",
		type : 'post',
		success : function(data) {
			if (data.success == true) {
				// 动态插入评论html内容
				if ($("#commentsDiv").html()=='') {
					$("#commentsDiv").append("<h3>评论</h3><hr /><div class='commentsList'></div>");
					$(".commentsList").append(getCommentHtml(data.comment));
				} else {
					$(getCommentHtml(data.comment)).insertBefore($(".commentsList .control-group")[0]);
				}
				$(form).resetForm();
				$(form).find(".control-group").removeClass('success').find("label").removeClass('valid help-inline ok');
				App.scrollTo($('.commentsList'), -200);
				$(".commentsList .control-group:first-child").pulsate({
				    color: "#bf1c56",
				    repeat: 10,
				});
			}
		}
	});
}
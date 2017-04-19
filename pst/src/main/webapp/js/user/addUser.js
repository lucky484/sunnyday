$(function() {
	// 判断中文
	jQuery.validator.methods.checkname = function(value, element, param) {
		var reg = "^[\u4e00-\u9fa5]+$";
		var r = value.match(reg);
		if (r) {
			return true;
		} else {
			return false;
		}
	};
	// 判断英文
	jQuery.validator.methods.checkeng = function(value, element, param) {
		var reg = "[a-zA-Z]";
		var r = value.match(reg);
		if (r) {
			return true;
		} else {
			return false;
		}
	};
	// 判断英文字母和数字
	jQuery.validator.methods.checkengandnum = function(value, element, param) {
		var reg = "^[A-Za-z0-9]+$";
		var r = value.match(reg);
		if (r) {
			return true;
		} else {
			return false;
		}
	};
});

var validator;
var form = $('#addUserForm');
var error = $('.alert-error', form);
$(function() {
	validator = form.validate({
		errorElement : 'span', // default input error message container
		errorClass : 'help-inline', // default input error message class
		focusInvalid : false, // do not focus the last invalid input
		ignore : "",
		unhighlight : function(element) { // revert the change dony by
			// hightlight
			$(element).closest('.control-group').removeClass('error').addClass('success'); // set
		},

		invalidHandler : function(event, validator) { // display error alert
			// on form submit
			error.show();
			App.scrollTo(error, -200);
		},

		highlight : function(element) { // hightlight error inputs
			$(element).closest('.help-inline').removeClass('ok');
			$(element).closest('.control-group').removeClass('success').addClass('error');
		},
		submitHandler : function(form) {
			error.hide();
			$(form).ajaxSubmit({
				url : "usersManagement/users/addUser.do",
				type : 'post',
				success : function(data) {
					if (data.success) {
						window.location.href = basePath + "usersManagement/users/userList.do";
					} else {
						$.gritter.add({
							title : '提交失败',
							text : data.errmsg
						});
					}
				},
			});
		},
		success : function(label) {
			label.addClass('valid').addClass('help-inline ok').closest('.control-group').removeClass('error').addClass('success');
		},
		rules : {
			userName : {
				required : true,
				maxlength : 30,
				remote: {
				    url: "usersManagement/users/checkUserName.do",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式    (一定是json 数据)
				    data: {                     //要传递的数据
				        username: function() {
				            return $("#userName").val();
				        }
				    }
				}
			},
			userPassword : {
				required : true,
				maxlength : 30,
				checkengandnum : "#userPassword"
			},
			englishName : {
				required : true,
				maxlength : 45,
				checkeng : "#englishName"
			},
			chineseName : {
				required : true,
				maxlength : 20,
				checkname : "#chineseName"
			},
			email : {
				required : true,
				email : true
			}
		},
		messages : {
			userName : {
				required : "请输入用户名",
				maxlength : "用户名长度不能超过30",
				remote : "用户名已存在，请更换"
			},
			userPassword : {
				required : "请输入用户密码",
				maxlength : "用户密码长度不能超过30",
				checkengandnum : "用户密码只能为英文字母和数字"
			},
			englishName : {
				required : "请输入英文名",
				maxlength : "英文名长度不能超过45",
				checkeng : "英文名只含有英文字母"
			},
			chineseName : {
				required : "请输入中文名",
				maxlength : "中文名长度不能超过20",
				checkname : "请输入中文名"
			},
			email : {
				required : "请输入邮箱",
				email : "请输入正确的邮箱格式"
			},
		}
	});
})

$('.chosen', form).change(function() {
	form.validate().element($(this));
});
$("#isNotification_box").change(function(){
	if($("#isNotification_box").prop("checked") == false){
		$("#isNotification").val(1);
	}else{
		$("#isNotification").val(0);
	}
});
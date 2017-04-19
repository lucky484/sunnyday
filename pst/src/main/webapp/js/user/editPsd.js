$(function() {
	// 判断英文字母和数字
	jQuery.validator.methods.checkengandnum = function(value, element, param) {
		var reg = "^[A-Za-z0-9]+$";
		var r = value.match(reg);
		if (r) {
			return true;
		} else {
			return false;
		}
	}
	// 修改密码不能与初始密码相同
	jQuery.validator.methods.checkPsd = function(value, element, param) {
		var password = $("input[name='initPassword']").val();
		var userPassword = $("input[name='userPassword']").val();
		if (password == userPassword) {
			return false;
		}else{
			return true;
		}
	}
	//输入修改密码前应先输入初始密码
	jQuery.validator.methods.checkPsd1 = function(value, element, param) {
		var password = $("input[name='initPassword']").val();
		var userPassword = $("input[name='userPassword']").val();
		if (password == "" && userPassword != "") {
			return false;
		}else{
			return true;
		}
	}
	// 确认密码与修改密码相同
	jQuery.validator.methods.checkPsd2 = function(value, element, param) {
		var psd = $("input[name='psd']").val();
		var userPassword = $("input[name='userPassword']").val();
		if (psd == userPassword) {
			return true;
		}else{
			return false;
		}
	}
	//输入确认密码前应先输入修改密码
	jQuery.validator.methods.checkPsd3 = function(value, element, param) {
		var psd = $("input[name='psd']").val();
		var userPassword = $("input[name='userPassword']").val();
		if (userPassword == "" && psd != "") {
			return false;
		}else{
			return true;
		}
	}
});

var validator;
var form = $('#editPsdForm');
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
				url : "usersManagement/users/updatePsd.do",
				type : 'post',
				success : function(data) {
					if (data.success) {
						window.location.href = basePath + "projectsManagement/projects/projectsList.do";
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
			initPassword : {
				required : true,
				maxlength : 30,
				checkengandnum : "#initPassword",
				remote: {
				    url: "usersManagement/users/checkPsd.do",     //后台处理程序
				    type: "post",               //数据发送方式
				    dataType: "json",           //接受数据格式    (一定是json 数据)
				    data: {                     //要传递的数据
				    	initPassword: function() {
				            return $("#initPassword").val();
				        },
				        userId : function() {
				            return $("#userId").val();
				        }
				    }
				}
			},
			userPassword : {
				required : true,
				maxlength : 30,
				checkengandnum : "#userPassword",
				checkPsd1 : "#userPassword",
				checkPsd : "#userPassword"
			},
			psd : {
				required : true,
				maxlength : 30,
				checkengandnum : "#psd",
				checkPsd3 : "#psd",
				checkPsd2 : "#psd"
			}
		},
		messages : {
			initPassword : {
				required : "请输入初始密码",
				maxlength : "初始密码长度不能超过30",
				checkengandnum : "初始密码只能为英文字母和数字",
				remote : "初始密码错误"
			},
			userPassword : {
				required : "请输入新密码",
				maxlength : "新密码长度不能超过30",
				checkengandnum : "新密码只能为英文字母和数字",
				checkPsd : "新密码不能与初始密码相同",
				checkPsd1 : "请先输入初始密码"
			},
			psd : {
				required : "请输入确认密码",
				maxlength : "确认密码长度不能超过30",
				checkengandnum : "确认密码只能为英文字母和数字",
				checkPsd2 : "确认密码与修改密码不同",
				checkPsd3 : "请先输入修改密码"
			}
		}
	});
})

$('.chosen', form).change(function() {
	form.validate().element($(this));
});
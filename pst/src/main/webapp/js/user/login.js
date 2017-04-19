var Login = function() {
	return {
		// main function to initiate the module
		init : function() {
			$('.login-form').validate({
				errorElement : 'label', // default input error message container
				errorClass : 'help-inline', // default input error message class
				focusInvalid : false, // do not focus the last invalid input
				rules : {
					username : {
						required : true
					},
					password : {
						required : true
					},
					remember : {
						required : false
					}
				},
				messages : {
					username : {
						required : "请输入用户名"
					},
					password : {
						required : "请输入密码"
					}
				},
				invalidHandler : function(event, validator) { // display error
					// alert on form
					// submit
					$("#errmsg").text("用户名或密码错误");
					$('.alert-error', $('.login-form')).show();
				},
				highlight : function(element) { // hightlight error inputs
					$(element).closest('.control-group').addClass('error'); // set
				},
				submitHandler : function(form) {
					$("#errmsg").text("正在登录，请稍候。。。");
					$('.alert-error', $('.login-form')).show();
					login(form);
				}
			});
			$('.login-form input').keypress(function(e) {
				if (e.which == 13) {
					$("#errmsg").text("正在登录，请稍候。。。");
					$('.alert-error', $('.login-form')).show();
					login(".login-form");
					return false;
				}
			});
		}
	};
}();

function login(form) {
	$(form).ajaxSubmit({
		url : "usersManagement/users/tologin.do",
		type : 'post',
		success : function(data) {
			if (data == "true") {
				window.location.href = basePath + "index.do"
			} else {
				$("#errmsg").text("用户名或密码错误");
				$('.alert-error', $('.login-form')).show();
			}
		}
	});
}
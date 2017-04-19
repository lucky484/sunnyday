$(function(){
	jQuery.validator.methods.checkPhone = function(value, element, param) {
		if(value != ""){
			var reg = "^[0-9]+$";
			var r = value.match(reg);
			if (r) {
				return true;
			} else {
				return false;
			}
		}else{
			return true;
		}
	}
});

var validator;
var form = $('#editOutsourcingManageForm');
var error = $('.alert-error', form);
$(function(){
	validator = form.validate({
		errorElement : 'span', // default input error message container
		errorClass : 'help-inline', // default input error message class
		focusInvalid : false, // do not focus the last invalid input
		ignore : "",
		errorPlacement : function(error, element) { // render error placement
			if (element.attr("name") == "name") {
				error.insertAfter("#name");
			} else if (element.attr("name") == "email") {
				error.insertAfter("#email");
			} else if (element.attr("name") == "phone") {
				error.insertAfter("#phone");
			} else {
				error.insertAfter(element); // for other inputs, just
											// perform
			}
		},
		unhighlight : function(element) { // revert the change dony by
			// hightlight
			$(element).closest('.control-group').removeClass('error')
					.addClass('success'); // set
		},

		invalidHandler : function(event, validator) { // display error
														// alert
			// on form submit
			error.show();
			App.scrollTo(error, -200);
		},

		highlight : function(element) { // hightlight error inputs
			$(element).closest('.help-inline').removeClass('ok');
			$(element).closest('.control-group').removeClass('success')
					.addClass('error');
		},
		submitHandler : function(form) {
			error.hide();
			$(form).ajaxSubmit(
							{
								url : "projectsManagement/outsourcingManage/updateOutsourcingManage.do",
								type : 'post',
								success : function(data) {
									if (data.success) {
										window.location.href = basePath
												+ "projectsManagement/outsourcingManage/outsourcingManage.do";
									} else {
										$.gritter.add({
											title : '提交失败',
											text : data.errmsg
										});
									}
								},
							// error : function(html) {
							// $.gritter.add({
							// title : '提交失败',
							// text : ' '
							// });
							// }
							});
		},
		success : function(label) {
			label.addClass('valid').addClass('help-inline ok').closest(
					'.control-group').removeClass('error').addClass(
					'success');
		},
		rules : {
			name : {
				required : true,
				maxlength : 50
			},
			email : {
				required : true,
				email : true
			},
			phone : {
				required : true,
				checkPhone : "#phone"
			}
		},
		messages : {
			name : {
				required : "请输入姓名",
				maxlength : "姓名长度不能超过50"
			},
			email : {
				required : "请输入邮箱",
			    email : "请输入正确的邮箱格式"	
			},
			phone : {
				required : "请输入手机号码",
				checkPhone : "请输入正确的手机号码",
			},
		}
	});
});
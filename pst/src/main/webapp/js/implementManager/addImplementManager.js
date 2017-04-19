
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
var form = $('#addImplementForm');
$(function(){
	validator = form.validate({
		focusInvalid : false, // do not focus the last invalid input
		ignore : "",
		unhighlight : function(element) { // revert the change dony by
			// hightlight
			$(element).next().remove();
			$(element).removeClass("error");
		},
    	highlight : function(element) { // hightlight error inputs
	    	$(element).addClass('error');
		},
		submitHandler : function(form) {
			$(form).ajaxSubmit({
				url : "insertImplementManager.do",
				type : 'post',
				success : function(data) {
					if (data.success) {
						location.replace("implementManagerList.do");
					} else {
						$.gritter.add({
							title : '提交失败',
							text : data.errmsg
						});
					}
				},
			});
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
$(function() {
	// 判断数字
	jQuery.validator.methods.checknumber = function(value, element, param) {
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
	};
});

var validator;
var form = $('#addCustomerForm');
$(function() {
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
				url : "insertCustomer.do",
				type : 'post',
				success : function(data) {
					if (data.result == 1) {
						location.replace("customerList.do");
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
			companyName : {
				required : true,
				maxlength : 50
			},
			customerName : {
				required : true,
				maxlength : 20
			},
			customerNameShort : {
				required : true,
				maxlength : 50
			},
			customerTitle : {
				required : true,
				maxlength : 20
			},
			customerPhone : {
				required : true,
				maxlength : 20,
				checknumber : "#customerPhone"
			},
			companyAddress : {
				maxlength : 33
			},
			companyPhone : {
				maxlength : 20,
				checknumber : "#companyPhone"
			},
			bank : {
				maxlength : 50
			},
			paymentAccount : {
				maxlength : 30,
				checknumber : "#paymentAccount"
			},
			companyIdNumber : {
				maxlength : 20
			}
		},
		messages : {
			companyName : {
				required : "请输入公司名称",
				maxlength : "公司名称长度不能超过50"
			},
			customerName : {
				required : "请输入客户姓名",
				maxlength : "客户姓名长度不能超过20"
			},
			customerNameShort : {
				required : "请输入客户简称",
				maxlength : "客户简称长度不能超过20"
			},
			customerTitle : {
				required : "请输入客户头衔",
				maxlength : "客户头衔长度不能超过20"
			},
			customerPhone : {
				required : "请输入客户电话",
				maxlength : "客户电话长度不能超过20",
				checknumber : "客户电话应该为数字"
			},
			companyAddress : {
				maxlength : "公司地址长度不能超过33"
			},
			companyPhone : {
				maxlength : "公司电话长度不能超过20",
				checknumber : "公司电话应该为数字"
			},
			bank : {
				maxlength : " 开户银行长度不能超过50"
			},
			paymentAccount : {
				maxlength : "付款账号长度不能超过30",
				checknumber : "付款账号应该为数字"
			},
			companyIdNumber : {
				maxlength : "公司税号长度不能超过20"
			}
		}
	});
})

$(function() {
	// 判断数字
	jQuery.validator.methods.checknumber = function(value, element, param) {
		var reg = "^[0-9]+.?[0-9]?[0-9]?$";
		var r = value.match(reg);
		if (r) {
			return true;
		} else {
			return false;
		}
	};
});

var validator;
var form = $('#updateFinanceForm');
var error = $('.alert-error', form);
$(function() {
	validator = form.validate({
		errorElement : 'span', // default input error message container
		errorClass : 'help-inline', // default input error message class
		focusInvalid : false, // do not focus the last invalid input
		ignore : "",
		errorPlacement : function(error, element) { // render error placement
			if (element.attr("name") == "projectQuotation") {
				error.insertAfter("#projectQuotationDiv");
			} else if (element.attr("name") == "cost") {
				error.insertAfter("#costDiv");
			} else {
				error.insertAfter(element); // for other inputs, just perform
			}
		},
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
				url : "projectsManagement/finance/update.do",
				type : 'post',
				success : function(data) {
					if (data.success) {
						window.location.href = basePath + "projectsManagement/finance/financeList.do";
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
			cost : {
				required : true,
				checknumber : "#cost",
			},
			returnedStatus : {
				required : true,
			},
		},
		messages : {
			cost : {
				required : "请输入成本支出",
				checknumber : "成本支出只能为精确到两位数的数字类型",
			},
			returnedStatus : {
				required : "请回款状态",
			},
		}
	});
})

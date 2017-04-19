
$(function(){
	// 判断数字
	jQuery.validator.methods.checknumber = function(value, element, param) {
		var reg = "^[0-9]*$";
		value = value.replace(/\,/g,"","");
		var r = value.match(reg);
		if (r) {
			return true;
		} else {
			return false;
		}
	};
});
var validator;
var form = $('#addFinanceFormReceive');
var error = $('.alert-error', form);
$(function() {
	validator = form
			.validate({
				errorElement : 'span', // default input error message container
				errorClass : 'help-inline', // default input error message class
				focusInvalid : false, // do not focus the last invalid input
				ignore : "",
				errorPlacement : function(error, element) { // render error
															// placement
					if (element.attr("name") == "receive1") {
						error.insertAfter("#receiveDiv");
					} else if (element.attr("name") == "remark") {
						error.insertAfter("#remark");
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
					$(form)
							.ajaxSubmit(
									{
										url : "projectsManagement/finance/addProjectFinancial.do",
										type : 'post',
										success : function(data) {
											if (data.success) {
												window.location.href = basePath
														+ "projectsManagement/finance/financeList.do";
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
					receive1 : {
						required : true,
						maxlength : 10,
						checknumber : "#receive1"
					},
					remark : {
						required : true,
						maxlength : 1500,
					}
				},
				messages : {
					receive1 : {
						required : "请输入金额",
						maxlength : "项目报价长度不能超过10",
						checknumber : "金额只能为数字类型"
					},
					remark : {
						required : "请输入备注",
						maxlength : "备注长度不能超过500个字"
					}
				}
			});
})
function formatNumber(obj){
	var number = $(obj).val();
	$(obj).val(number.replace(/\,/g,"","").replace(/(\d)(?=(?:\d{3})+$)/g, '$1,'));
}

$('#receive1', form).change(function() {
	$("#receive").val($(this).val().replace(/\,/g,"",""));
	form.validate().element($(this));
});
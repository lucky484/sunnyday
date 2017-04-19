
$(function(){
	jQuery.validator.methods.checkStartTime = function(value, element, param) {
		var startTime = $("input[name='start_time']").val();
		var leadTime = $("input[name='lead_time']").val();
		    if(leadTime == ""){
		    	return true;
		    }
		    if(leadTime != ""){
		    	if (startTime < leadTime) {
		    		return true;
		    	} else {
		    		return false;
		    	}
		    }
	}
	
	jQuery.validator.methods.checkLeadTime = function(value, element, param) {
		var startTime = $("input[name='start_time']").val();
		var leadTime = $("input[name='lead_time']").val();
		if (startTime == "") {
			return true;
		}
		if (startTime != "") {
			if (startTime < leadTime) {
				return true;
			} else {
				return false;
			}
		}
	}
	
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
var form = $('#editChanceManageForm');
var error = $('.alert-error', form);
$(function(){
	validator = form
	.validate({
		errorElement : 'span', // default input error message container
		errorClass : 'help-inline', // default input error message class
		focusInvalid : false, // do not focus the last invalid input
		ignore : "",
		errorPlacement : function(error, element) { // render error
													// placement
			if (element.attr("name") == "start_time") {
				error.insertAfter("#start_time_calendar");
			} else if (element.attr("name") == "lead_time") {
				error.insertAfter("#lead_time_calendar");
			} else if (element.attr("name") == "forecastQuotation1") {
				error.insertAfter("#forecastQuotationDiv");
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
								url : "projectsManagement/chanceManage/updateChanceManage.do",
								type : 'post',
								success : function(data) {
									if (data.success) {
										window.location.href = basePath
												+ "projectsManagement/chanceManage/chanceManage.do";
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
			projectName : {
				required : true,
				maxlength : 60
			},
			projectManager : {
				required : true
			},
			start_time : {
				required : true,
				checkStartTime : true,
			},
			lead_time : {
				required : true,
				checkLeadTime : true,
			},
			forecastQuotation1 : {
				required : true,
				checknumber : "#forecastQuotation1",
				maxlength : 10
			},
		},
		messages : {
			projectName : {
				required : "请输入项目名称",
				maxlength : "项目名称长度不能超过60"
			},
			projectManager : {
				required : "请输入项目经理"
			},
			start_time : {
				required : "请输入开始时间",
				checkStartTime : "开始时间不能大于交付时间"
			},
			lead_time : {
				required : "请输入交付时间",
				checkLeadTime : "交付时间不能小于开始时间",
			},
			forecastQuotation1 : {
				required : "请输入预估报价",
				checknumber : "预估报价只能为精确到两位数的数字类型",
				maxlength : " 预估报价长度不能超过10"
			}
		}
	});
	if (jQuery().datepicker) {
		$('.date-picker').datepicker().on('changeDate', function(ev) {
			$(this).valid();
			$(this).datepicker('hide');
		});
	}
	$("#forecastQuotation1").val(forecastQuotation.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,'));
	$("#forecastQuotation").val(forecastQuotation);
});
$('#projectManager', form).change(function() {
	$("#projectManagerName").val($(this).find("option:selected").text());
	form.validate().element($(this));
});
function formatNumber(obj){
	var number = $(obj).val();
	$(obj).val(number.replace(/\,/g,"","").replace(/(\d)(?=(?:\d{3})+$)/g, '$1,'));
}
$("#forecastQuotation1").change(function(){
	$("#forecastQuotation").val($(this).val().replace(/\,/g,"",""));
	form.validate().element($(this));
});
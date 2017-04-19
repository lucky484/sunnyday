$(function() {
	// 判断日期大小
	jQuery.validator.methods.checkStartTime1 = function(value, element, param) {
		var startTime = $("input[name='start_time']").val();
		var medialTime = $("input[name='medial_time']").val();
		var leadTime = $("input[name='lead_time']").val();
		if (medialTime == "") {
			return true;
		}
		if (medialTime != "") {
			if (startTime < medialTime) {
				return true;
			} else {
				return false;
			}
		}
	}

	jQuery.validator.methods.checkStartTime2 = function(value, element, param) {
		var startTime = $("input[name='start_time']").val();
		var medialTime = $("input[name='medial_time']").val();
		var leadTime = $("input[name='lead_time']").val();
		if (leadTime == "") {
			return true;
		}
		if (leadTime != "") {
			if (startTime < leadTime) {
				return true;
			} else {
				return false;
			}
		}
	}

	jQuery.validator.methods.checkMedialTime1 = function(value, element, param) {
		var startTime = $("input[name='start_time']").val();
		var medialTime = $("input[name='medial_time']").val();
		var leadTime = $("input[name='lead_time']").val();
		if (startTime == "") {
			return true;
		}
		if (startTime != "") {
			if (startTime < medialTime) {
				return true;
			} else {
				return false;
			}
		}
	}

	jQuery.validator.methods.checkMedialTime2 = function(value, element, param) {
		var startTime = $("input[name='start_time']").val();
		var medialTime = $("input[name='medial_time']").val();
		var leadTime = $("input[name='lead_time']").val();
		if (leadTime == "") {
			return true;
		}
		if (leadTime != "") {
			if (medialTime < leadTime) {
				return true;
			} else {
				return false;
			}
		}
	}

	jQuery.validator.methods.checkLeadTime1 = function(value, element, param) {
		var startTime = $("input[name='start_time']").val();
		var medialTime = $("input[name='medial_time']").val();
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

	jQuery.validator.methods.checkLeadTime2 = function(value, element, param) {
		var startTime = $("input[name='start_time']").val();
		var medialTime = $("input[name='medial_time']").val();
		var leadTime = $("input[name='lead_time']").val();
		if (medialTime == "") {
			return true;
		}
		if (medialTime != "") {
			if (medialTime < leadTime) {
				return true;
			} else {
				return false;
			}
		}
	}

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
	
	jQuery.validator.methods.checknumbers = function(value, element, param){
		var reg = /^\d{10}$/;
		var r = value.match(reg);
		if (r) {
			return true;
		} else {
			return false;
		}
	}
	/*
	 * //判断double jQuery.validator.methods.checknumber = function
	 * (value,element,param){ var reg = "^[0-9]+\.{0,1}[0-9]{0,1}$"; var r =
	 * value.match(reg); if(r){ return true; }else{ return false; } };
	 */
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
var form = $('#addProjectForm');
$(function() {
	validator = form.validate({
				focusInvalid : false, // do not focus the last invalid input
				ignore : "",
				errorPlacement : function(error, element) { // render error
					if (element.attr("name") == "projectQuotation1") {
						$("#projectQuotationDiv").children().eq(0).addClass("error");
						error.insertAfter("#projectQuotationDiv");
					} else if (element.attr("name") == "outsourcingQuotationStr") {
						$("#outsourcingQuotationDiv").children().eq(0).addClass("error");
						error.insertAfter("#outsourcingQuotationDiv");
					} else{
						error.insertAfter(element);
					}
				},
				unhighlight : function(element) { // revert the change dony by
					// hightlight
					$(element).prev().removeClass("error");
					$(element).removeClass("error");
				},
				
				highlight : function(element) { // hightlight error inputs
					/*$(element).closest('.help-inline').removeClass('ok');*/
					    if(!$(element).hasClass("select")){
					    	$(element).addClass('error');
					    }
				},
				submitHandler : function(form) {
					$(form).ajaxSubmit({
						url : "insert.do",
						type : 'post',
						success : function(data) {
							if (data.success) {
								location.replace( "projectsList.do");
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
					projectCode : {
						required : true,
						checknumbers : "#projectCode",
						remote: {
						    url: "checkProjectCode.do",     //后台处理程序
						    type: "post",               //数据发送方式
						    dataType: "json",           //接受数据格式    (一定是json 数据)
						    data: {                     //要传递的数据
						    	projectCode: function() {
						            return $("#projectCode").val();
						        }
						    }
						}
					},
					projectName : {
						required : true,
						maxlength : 60
					},
					projectManagerId : {
						required : true,
						maxlength : 45
					},
					implementManagerId : {
						required : true,
						maxlength : 45
					},
					start_time : {
						required : true,
						checkStartTime1 : true,
						checkStartTime2 : true
					},
					medial_time : {
						required : true,
						checkMedialTime1 : true,
						checkMedialTime2 : true
					},
					lead_time : {
						required : true,
						checkLeadTime1 : true,
						checkLeadTime2 : true
					},
					projectQuotation1 : {
						required : true,
						checknumber : "#projectQuotation1",
						maxlength : 10
					},
					projectTypeNumber : {
						required : true,
					},
					projectStatusNumber : {
						required : true,
					},
					customerId : {
						required : true,
					}
				},
				messages : {
					projectCode : {
						required : "请输入项目编号",
						checknumbers : "项目编号只能输入10位数字",
						remote : "项目编号已存在，请更换"
					},
					projectName : {
						required : "请输入项目名称",
						maxlength : "项目名称长度不能超过60"
					},
					projectManagerId : {
						required : "请选择项目经理",
						maxlength : "项目经理长度不能超过45"
					},
					implementManagerId : {
						required : "请选择客户经理",
						maxlength : "客户经理长度不能超过45"
					},
					start_time : {
						required : "请输入开始时间",
						checkStartTime1 : "开始时间不能大于内测时间",
						checkStartTime2 : "开始时间不能大于交付时间"
					},
					medial_time : {
						required : "请输入内测时间",
						checkMedialTime1 : "内测时间不能小于开始时间",
						checkMedialTime2 : "内测时间不能大于交付时间"
					},
					lead_time : {
						required : "请输入交付时间",
						checkLeadTime1 : "交付时间不能小于开始时间",
						checkLeadTime2 : "交付时间不能小于内测时间"
					},
					outsourcingQuotation : {
						required : "请输入外包价格",
						checknumber : "外包价格只能数字类型",
						maxlength : "外包价格长度不能超过10"
					},
					projectQuotation1 : {
						required : "请输入项目报价",
						checknumber : "项目报价只能为数字类型",
						maxlength : " 项目报价长度不能超过10"
					},
					projectTypeNumber : {
						required : "请选择项目类型",
					},
					projectStatusNumber : {
						required : "请选择项目状态",
					},
					customerId : {
						required : "请选择客户",
					},
					outsourcingQuotationStr : {
						required : "请输入外包价格",
						checknumber : "外包价格只能为数字类型",
						maxlength : " 项目报价长度不能超过10"
					},
				}
			});
	if(projectQuotation > 0){
		$("#projectQuotation1").val(projectQuotation.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,'));
	}
})

$('.select', form).change(function() {
	form.validate().element($(this));
});


$('#projectStatusNumber', form).change(function() {
	$("#projectStatus").val($(this).find("option:selected").text());
	form.validate().element($(this));
});

$('#projectManagerId', form).change(function() {
	$("#projectManager").val($(this).find("option:selected").text());
	form.validate().element($(this));
});

$('#implementManagerId', form).change(function() {
	$("#implementManager").val($(this).find("option:selected").text());
	form.validate().element($(this));
});

$("input[type='radio']").click(function() {
	if ($(this).val() == "1") {
		$("#outsourcingQuotationParentDiv").show();
		$("#outsourcingQuotationStr").rules("add",{required : true,checknumber : "#outsourcingQuotationStr",maxlength : 10});
	} else {
		$("#outsourcingQuotationParentDiv").hide();
		$("#outsourcingQuotationStr").rules("remove");
		$("#outsourcingQuotationStr").val('');
		$("#outsourcingQuotation").val(0);
		$("#outsourcingQuotationStr").next().remove();
	}
});
function formatNumber(obj){
	var number = $(obj).val();
	$(obj).val(number.replace(/\,/g,"","").replace(/(\d)(?=(?:\d{3})+$)/g, '$1,'));
}

$('#outsourcingQuotationStr', form).change(function() {
	$("#outsourcingQuotation").val($(this).val().replace(/\,/g,"",""));
	form.validate().element($(this));
});

$('#projectQuotation1', form).change(function() {
	$("#projectQuotation").val($(this).val().replace(/\,/g,"",""));
	form.validate().element($(this));
});
/* 上传多个文件的js代码  */
//点击浏览按钮事件
function upload(obj){
   var uploadFileName = $(obj).val();
	   var index = uploadFileName.lastIndexOf("\\");
	   if(index != -1){
	        var tempFileName = uploadFileName.substring(index+1,uploadFileName.length);
	        $(obj).parent().children().eq(0).val(tempFileName);
	    }else{
	    	 $(obj).parent().children().eq(0).val(uploadFileName);
	    }
	   $(obj).parent().children().eq(1).attr("style","display:none;");
	   $(obj).parent().children().eq(2).removeAttr("style");
	   $(obj).parent().next().next().removeAttr("style");
} 
 //点击删除按钮事件
function delFile(obj){
   $(obj).prev().children().eq(0).val('');
   $(obj).prev().children().eq(3).val('');
   $(obj).prev().children().eq(1).removeAttr("style","display:none;");
   $(obj).prev().children().eq(2).attr("style","display:none;");
   $(obj).next().attr("style","display:none;");
}
 //点击继续添加按钮事件
function insertFile(obj){
    var div = ['<div class="row cl">'+
               '<label class="form-label col-xs-4 col-sm-2"></label>'+
			   '<div class="formControls col-xs-8 col-sm-9">'+ 
				'<span class="btn-upload form-group">'+
					'<input class="input-text" type="text" style="width:200px">'+
					'<a href="javascript:void();" class="btn btn-primary upload-btn btn-location"><i class="Hui-iconfont">&#xe642;</i> 浏览</a>'+
					'<a href="javascript:void();" class="btn btn-warning btn-location" style="display:none;"><i class="Hui-iconfont">&#xe6df;</i> 变更</a>'+
					'<input type="file" multiple name="file" class="input-file" onchange="upload(this);" />'+
			'</span>'+
				'<span href="javascript:void();" class="btn btn-danger btn-left" onclick="delEle(this);"><i class="Hui-iconfont">&#xe60b;</i> 删除</span>'+
				'<span href="javascript:void();" class="btn btn-success btn-left" style="display:none;" onclick="insertFile(this);"><i class="Hui-iconfont">&#xe604;</i> 继续添加</span>'+
			 '</div>'+
		'</div>'].join('');
    $(obj).parent().parent().after(div);
}

function delEle(obj){
   $(obj).parent().parent().remove();
}

$(function() {
/*	// 判断中文
	jQuery.validator.methods.checkname = function(value, element, param) {
		var reg = "^[\u4e00-\u9fa5]+$";
		var r = value.match(reg);
		if (r) {
			return true;
		} else {
			return false;
		}
	};*/
	// 判断数字
	jQuery.validator.methods.checknumber = function(value, element, param) {
		var reg = "^[0-9]+$";
		value = value.replace(/\,/g,"","");
		var r = value.match(reg);
		if (r) {
			return true;
		} else {
			return false;
		}
	};
	// //判断double
	// jQuery.validator.methods.checknumber = function (value,element,param){
	// var reg = "^[0-9]+\.{0,1}[0-9]{0,1}$";
	// var r = value.match(reg);
	// if(r){
	// return true;
	// }else{
	// return false;
	// }
	// };
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
var form = $('#addCRForm');
$(function() {
	validator = form.validate({
		focusInvalid : false, // do not focus the last invalid input
		ignore : "",
		errorPlacement : function(error, element) { // render error placement
			if (element.attr("name") == "addConfirmQuotation1") {
				$("#addConfirmQuotationDiv").children().eq(0).addClass("error");
				error.insertAfter("#addConfirmQuotationDiv");
			} else {
				error.insertAfter(element); // for other inputs, just perform
			}
		},
		unhighlight : function(element) { // revert the change dony by
			// hightlight
			$(element).prev().removeClass("error");
			$(element).removeClass("error");
		},

		highlight : function(element) { // hightlight error inputs
			$(element).addClass('error');
		},
		submitHandler : function(form) {
			$(form).ajaxSubmit({
				url : "projectsManagement/projects/Requirement/addCR.do",
				type : 'post',
				success : function(data) {
					if (data.success) {
						window.location.href = basePath + "projectsManagement/projects/Requirement/crList.do?projectId=" + data.projectId;
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
			addCRDescribe : {
				required : true,
				maxlength : 200
			},
			addEstimatedWorkload : {
				required : true,
				checknumber : "#estimatedWorkload",
				maxlength : 10
			},
			addPresenter : {
				required : true,
				maxlength : 45
			},
			addConfirmor : {
				required : true,
				maxlength : 45
			},
			addEstimateQuotation : {
				required : true,
				checknumber : "#estimateQuotation",
				maxlength : 10
			},
			addConfirmQuotation1 : {
				required : true,
				checknumber : "#addConfirmQuotation1",
				maxlength : 10
			}
		},
		messages : {
			addCRDescribe : {
				required : "请输入需求描述",
				maxlength : "需求描述不能超过200字"
			},
			addEstimatedWorkload : {
				required : "请输入预估工作量",
				checknumber : "预估工作量只能为数字类型",
				maxlength : "预估工作量长度不能超过10"
			},
			addPresenter : {
				required : "请输入提交人",
				maxlength : "提交人长度不能超过45"
			},
			addConfirmor : {
				required : "请输入确认人",
				maxlength : "确认人长度不能超过45"
			},
			addEstimateQuotation : {
				required : "请输入预估报价",
				checknumber : "预估报价只能为数字类型",
				maxlength : "预估报价长度不能超过10"
			},
			addConfirmQuotation1 : {
				required : "请输入确认报价",
				checknumber : "确认报价只能为数字类型",
				maxlength : "确认报价长度不能超过10"
			}
		}
	});
})
function formatNumber(obj){
	var number = $(obj).val();
	$(obj).val(number.replace(/\,/g,"","").replace(/(\d)(?=(?:\d{3})+$)/g, '$1,'));
}
$('#addConfirmQuotation1', form).change(function() {
	$("#addConfirmQuotation").val($(this).val().replace(/\,/g,"",""));
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

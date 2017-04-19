
	$(function() {
            	//判断中文
            	jQuery.validator.methods.checkname = function (value,element,param){
					var reg = "^[\u4e00-\u9fa5]+$";
					var r = value.match(reg);
					if(r){
						return true;
					}else{
						return false;
					}
				};
				//判断数字
				jQuery.validator.methods.checknumber = function (value,element,param){
					var reg = "^[0-9]+$";
					value = value.replace(/\,/g,"","");
					var r = value.match(reg);
					if(r){
						return true;
					}else{
						return false;
					}
				};
//				//判断double
//				jQuery.validator.methods.checknumber = function (value,element,param){
//					var reg = "^[0-9]+\.{0,1}[0-9]{0,1}$";
//					var r = value.match(reg);
//					if(r){
//						return true;
//					}else{
//						return false;
//					}
//				};
				//判断英文字母和数字
				jQuery.validator.methods.checkengandnum = function (value,element,param){
					var reg = "^[A-Za-z0-9]+$";
					var r = value.match(reg);
					if(r){
						return true;
					}else{
						return false;
					}
				};
			});
			
	var validator;
	var form = $('#CRForm');
	$(function() {
		validator = form.validate({
			focusInvalid : false, // do not focus the last invalid input
			ignore : "",
			errorPlacement : function(error, element) { // render error placement
				if (element.attr("name") == "confirmQuotation1") {
					$("#confirmQuotationDiv").children().eq(0).addClass("error");
					error.insertAfter("#confirmQuotationDiv");
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
					url : "projectsManagement/projects/Requirement/updateCR.do",
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
						crDescribe : {
							required : true,
							maxlength : 200
						},
						estimatedWorkload : {
							required : true,
							checknumber : "#estimatedWorkload",
							maxlength : 10
						},
						presenter : {
							required : true,
							maxlength : 45
						},
						confirmor : {
							required : true,
							maxlength : 45
						},
						estimateQuotation : {
							required : true,
							checknumber : "#estimateQuotation",
							maxlength : 10
						},
						confirmQuotation : {
							required : true,
							checknumber : "#confirmQuotation",
							maxlength : 10
						}
					},
					messages : {
						crDescribe : {
							required : "请输入需求描述",
							maxlength : "需求描述不能超过200字"
						},
						estimatedWorkload : {
							required : "请输入预估工作量",
							checknumber : "预估工作量只能为数字类型",
							maxlength : "预估工作量长度不能超过10"
						},
						presenter : {
							required : "请输入提交人",
							maxlength : "提交人长度不能超过45"
						},
						confirmor : {
							required : "请输入确认人",
							maxlength : "确认人长度不能超过45"
						},
						estimateQuotation : {
							required : "请输入预估报价",
							checknumber : "预估报价只能为数字类型",
							maxlength : "预估报价长度不能超过10"
						},
						confirmQuotation : {
							required : "请输入确认报价",
							checknumber : "确认报价只能为数字类型",
							maxlength : "确认报价长度不能超过10"
						}
					}
				});
		var confirmQuotation = $("#confirmQuotation1").val();
		$("#confirmQuotation1").val(confirmQuotation.replace(/(\d)(?=(?:\d{3})+$)/g, '$1,'));
            })
function formatNumber(obj){
	var number = $(obj).val();
	$(obj).val(number.replace(/\,/g,"","").replace(/(\d)(?=(?:\d{3})+$)/g, '$1,'));
}
$('#confirmQuotation1', form).change(function() {
	$("#confirmQuotation").val($(this).val().replace(/\,/g,"",""));
	form.validate().element($(this));
});          
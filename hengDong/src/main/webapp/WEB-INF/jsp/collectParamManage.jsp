<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!doctype html>
<html class="no-js" lang="">
	<input type="hidden" id="hiddenfaceCompareFlag"  value="${paramSet.faceCompareFlag }">
	<input type="hidden" id="hiddencardCompareFlag"  value="${paramSet.cardCompareFlag }">
	<input type="hidden" id="hiddenfaceCardCompFlag"   value="${paramSet.faceCardCompFlag }">
	<input type="hidden" id="hiddenctrlSyncCycle"  value="${paramSet.ctrlSyncCycle }">
	<input type="hidden" id="hiddenremarkDesc"  value="${paramSet.remarkDesc }">
	<input type="hidden" id="hiddentotalSyncCycle"  value="${paramSet.totalSyncCycle }">
	<input type="hidden" id="hiddenfaceCardCompAlarmVal"  value="${paramSet.faceCardCompAlarmVal }">
	<input type="hidden" id="hiddenfaceCompAlarmVal"  value="${paramSet.faceCompAlarmVal }">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="pragma" CONTENT="no-cache"> 
		<meta http-equiv="Cache-Control" CONTENT="no-cache, must-revalidate"> 
		<meta http-equiv="expires" CONTENT="0"> 
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>多模态高通量人员特征信息识别卡口系统</title>
        <title>采集参数管理</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/normalize.css">
        <link rel="stylesheet" href="../css/main.css">
        <link rel="stylesheet" href="../css/remodal.css">
    </head>
    <body>
        <div class="container wrapper_c ">
            <div class="page-wrapper">
                <div class="page-con">
                    <div class="match-hd">
                        <div class="match-title-box">
                            参数管理
                            <span>采集参数管理</span>
                        </div>
							<form enctype="multipart/form-data" id="paramsForm" name="paramsForm">
                            <input type="hidden" id="hiddenFlag" name="flag"  value="${flag }">
                            <div style="width:100%;height:100%;margin-top:45px;">
								<div style="width:98%;margin:0 1% 0 1%">
								<table style="table-layout:fixed;">
								<tr style="height:70px">
									<td style="width:23%">缉控人员特征库同步频率（小时）</td>
									<td style="width:10%"><input type="text" name="ctrlSyncCycle" style="width:100%;text-align:center" class="form-control fl-100" value="${paramSet.ctrlSyncCycle }" name="ctrlSyncCycle" id="ctrlSyncCycle"></td>
									<td style="width:17%;padding-left:80px">人脸比对</td>
									<td style="width:12%">
									<select class="form-control fl-99" name="faceCompareFlag" id="faceCompareFlag" name="faceCompareFlag">
									<option value="1">启用</option>
									<option value="0">停用</option>
									</select></td>
									<td style="width:35%">备注</td>
								</tr>
								
								<tr style="height:70px">
									<td>
									全量人员特征库同步频率（小时）
									</td>
									<td><input type="text" style="width:100%;text-align:center" id="totalSyncCycle" name = "totalSyncCycle" class="form-control fl-100" value="${paramSet.totalSyncCycle }" /></td>
									<td style="padding-left:80px">身份证对比</td>
									<td>
									<select class="form-control fl-99" name="cardCompareFlag" id="cardCompareFlag">
									<option value="1">启用</option>
									<option value="0">停用</option>
									</select>
									</td>
										<td rowspan="3" colspan="3" style="width:35%">
											<textarea id="remarkDesc" name="remarkDesc" rows="8" cols="30" style="margin:0;width:100%;height:200px;outline:1px;border-radius:4px;outline-color: #222;" maxlength="300">${paramSet.remarkDesc }</textarea>
										</td>
								</tr>
								
								<tr style="height:70px"><td>人证合一比对的相似度的预警阈值(%)</td>
									<td><input type="text" class="form-control fl-100" style="width:100%;text-align:center" value="${paramSet.faceCardCompAlarmVal }" id="faceCardCompAlarmVal" name = "faceCardCompAlarmVal"></td>
									<td style="padding-left:80px;width:18px;">
									人证合一比对
									</td><td>
										<select class="form-control fl-99" name="faceCardCompFlag"  id="faceCardCompFlag">
									<option value="1">启用</option>
									<option value="0">停用</option>
									</select>
									</td></tr>
								<tr style="height:70px"><td>人脸比对的相似度的预警阈值(%)</td><td style="word-break:break-all"><input type="text" value="${paramSet.faceCompAlarmVal }" id="faceCompAlarmVal" name="faceCompAlarmVal" style="width:100%;text-align:center" class="form-control fl-100">
								</td><td></td><td></td></tr>
								</table>
								<div style="width:300px;margin-left:38%;margin-top:5%"><div style="float:left"><input type="button" id="submit" onclick="setParams()" value="提交" style="width:120px;height:45px;border-radius:4px;background:#348ce3;border:0px;color:#fff;font-size:16px;font-family:"微软雅黑";"></div>
								<div style="float:right;"><input type="button" value="重置" id="reset_btn" style="background:#348ce3;width:120px;height:45px;border-radius:4px;border:0px;color:#fff;font-size:16px;font-family:'微软雅黑';"></div></div>
								</div>
								</div>
                         </form>
                    </div>
                </div>
            </div>
        </div>
        
        <!--提示tips-->
		<div class="remodal delete-box" data-remodal-id="tip_info">
			<div class="modal-hd">
				<a href="javascript" data-remodal-action="close" class="modal-close"></a>
			</div>
			<div class="modal-bd">
				<p id="model_info"></p>
				<div class="btn-group">
					<button data-remodal-action="confirm" class="btn">是</button>
				</div>
			</div>
		</div>
        <script src="../js/vendor/jquery-1.11.3.min.js"></script>
        <script src="../js/plugins.js"></script>
        <script src="../js/main.js"></script>
        <script src="../js/jquery.validate.min.js"></script>
        <script src="../js/remodal.min.js"></script>
        <script>
        	// 初始化模态框
	        $(function(){
	            $('.fancybox').fancybox();
	        });
            function getdate(id) {
                  var today = new Date();
                  var y = today.toLocaleDateString();
                  var h = today.getHours();
                  var m = today.getMinutes();
                  var s = today.getSeconds();
                  if (s < 10) {
                        s = "0" + s;
                  }
                  if (m < 10) {
                        m = "0" + m;
                  }
                  $(id).text(y + " " +h + " : " + m + " : " + s);
                  setTimeout(function() {
                        getdate(id)
                  }, 500);
            }
            $(function(){

                getdate('#curr-time');



                //左侧导航
                $("#site-menu").metisMenu();

                //对比区域设置等高
                $('.contrast').each(function(){  
            
                        var highestBox = 0;
                        $('.contrast-item', this).each(function(){
                        
                            if($(this).height() > highestBox) 
                               highestBox = $(this).height(); 
                        });  
                        
                        $('.contrast-item',this).height(highestBox);
                        
                    
                });    
                //初始化
                init();

            });
            function init(){
				$("#faceCompareFlag").val($("#hiddenfaceCompareFlag").val());
				$("#cardCompareFlag").val($("#hiddencardCompareFlag").val());
				$("#faceCardCompFlag").val($("#hiddenfaceCardCompFlag").val());
				$("#remarkDesc").val($("#hiddenremarkDesc").val());
				
				$("#reset_btn").click(function(){
					$("#faceCompareFlag").val(1);
					$("#cardCompareFlag").val(1);
					$("#faceCardCompFlag").val(1);					
					$("#ctrlSyncCycle").val(1);
					$("#remarkDesc").val('');
					$("#totalSyncCycle").val(1);
					$("#faceCardCompAlarmVal").val(90);
					$("#faceCompAlarmVal").val(10);

					// 获得表单参数
	            	var formData = $("form[name=paramsForm]").serialize();

		            $.ajax({
		            	  url: "getCollectParamManage.do",
		            	  method : 'post',
		            	  data : formData
		            	}).done(function(data) {
		            		$("#model_info").html('<p>'+'参数初始化成功!'+'</p>');
							var tip_info= $('[data-remodal-id="tip_info"]').remodal();
							tip_info.open();
		            });
				});
            }

            function setParams(){
            	if($("#paramsForm").valid()){
	            	// 获得表单参数
	            	var formData = $("form[name=paramsForm]").serialize();
	
		            $.ajax({
		            	  url: "getCollectParamManage.do",
		            	  method : 'post',
		            	  data : formData
		            	}).done(function(data) {
		            		$("#model_info").html('<p>'+'提交成功!'+'</p>');
		            		$('[data-remodal-id="tip_info"]').remodal().open();
		            });
	            }
            }

          //下面开始做校验
		    $(function() {
				$.validator.methods.checkip = function (value,element,param){
					var reg = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$";
					if(value === 0){
						return false;
					}
					if(!value){
						return true;
					}
					var r = value.match(reg);
					if(r){
						return true;
					}else{
						return false;
					}
				};
			});

		// 清除错误信息
			function cleanClass(){
				$(".error").html('');
   		}
		
		var validator;
		$(function() {
			validator = $("#paramsForm").validate({
				//出错时添加的标签
		        errorElement: "span",
				onfocusout : function(element) {
					$(element).valid();
				},
				rules : {
					ctrlSyncCycle : {
						required : true,
						digits : true,
					},
					remarkDesc : {
						maxlength : 300,
					},
					totalSyncCycle : {
						required : true,
						digits : true,
					},
					faceCardCompAlarmVal : {
						required : true,
						range : [0,100]
					},
					faceCompAlarmVal : {
						required : true,
						range : [0,100]
					}
				},
				success: function(label) {
		            //正确时的样式
		            label.text(" ").addClass("success");
		        },
		        errorPlacement: function (error, element) {
			        debugger;
	                /* if (element.parent().find("span[for='" + element.attr("id") + "']") != null) {
	                    element.parent().find("span[for='" + element.attr("id") + "']").remove();
	                } */
	               /*  var tr1 = element.parent().parent();
	                tr1.after('<tr style="font-size:1em;">'+
								'<td colspan="2" style="width:23%"></td>'+
	    	                 '</tr>');
	                error.attr("style","text-align:right;");
	                tr1.next().find("td").html(error); */

	                var tr1 = element.parent();
	                error.attr("style","text-align:right;white-space: nowrap;display: inherit;");
	                error.appendTo(tr1);
	            },
				messages : {
					ctrlSyncCycle : {
						required : "必填项",
						digits : "请输入正确的值",
					},
					remarkDesc : {
						maxlength : "备注不能超过300个字",
					},
					totalSyncCycle : {
						required : "必填项",
						digits : "请输入正确的值",
					},
					faceCardCompAlarmVal : {
						required : "必填项",
						range : "阀值范围为0~100"
					},
					faceCompAlarmVal : {
						required : "必填项",
						range : "阀值范围为0~100"
					}
				}
        	});
		});
        </script>
    </body>
</html>

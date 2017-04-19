<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/customer/compliantListPages" var="pagesUrl" />
<spring:url
	value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js"
	var="dataTableJs" />
<script src="${dataTableJs}"></script>
<spring:url
	value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js"
	var="dataTableBootstrapJs" />
<script src="${dataTableBootstrapJs}"></script>	
<spring:url value="/resources/js/jquery.tmpl.js" var="jqueryTmplJs" />
<script src="${jqueryTmplJs}"></script>
<spring:url value="/institution/device/rules/testing" var="testingUrl"/>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />

<script type="text/javascript">
	var languageUrl;
	var lang = "${dtLangUrl}";
	var str = lang.substring(lang.lastIndexOf("/")+1,lang.lastIndexOf("."));
	var str1 = lang.substring(0,lang.lastIndexOf("/"));
	var str2 = lang.substring(lang.lastIndexOf("."),lang.length);
	if(navigator.language != "undefined" && navigator.language != null){
		    languageUrl = str1 + "/" + str + "_" + navigator.language + str2;
	}else{
		    languageUrl = str1 + "/" + str + "_en-US" + str2;
	}
	function checkDevice(rid){
		//检查规则是根据devicerule Id来进行的
		//DeviceRuleController的最后一个方法,/institution/device/rules/testing
		$.post("${testingUrl}",{rid:rid},function(result){
			$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
		},"json");
		
	}
		
	function loadCompliantDT(uid,did){
		var csrf="${_csrf.token}";
		//自动加载datatable
			$('#compliantList').DataTable({
				"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
					"searching" : false,
					"stateSave" : true,
					"ordering" : false,
					"bSort" : false,
					"pageLength" : 10,
					"pagingType" : "full_numbers",
					"serverSide" : true,
					"bDestroy" : true,
					"oLanguage":{
						"sUrl":languageUrl
					},
					"ajax" : {
						//"url" : "${pagesUrl}?uid="+uid+"&did="+did,
						//"type" : "get",
						//"dataSrc" : "data",
						"dataType":'json',
						"type" : "POST",
						"url" : "${pagesUrl}",
						"data" : {"uid":uid,"did":did,"_csrf":csrf}
						
					},
					"columns":[
							  {data : "deviceRule.id"},
				              {data : "status"},
				              {data : "deviceRule.name"}, 
				              {data : "violate_time"},
				              {data : "deviceRule.next_check_time"},
				              {data : "id"},
				            ],
				            "columnDefs" : [
								{
								    "targets": [0],
								    "visible": false
								},
								{
									"targets" : [1],
									"render" : function(
											data, type,
											full, meta) {
										return full.status==1?'<fmt:message key="tiles.views.customer.compliant.index.table.illegal"/>':'<fmt:message key="tiles.views.customer.compliant.index.table.legal"/>';
									}
								},
								{
									targets : [4],
									render : function(
											data, type,
											full, meta) {
										if(full.deviceRule.next_check_time == null || full.deviceRule.next_check_time == ''){
											return '';
										}else{
											return new Date(parseInt(full.deviceRule.next_check_time)).Format("yyyy-MM-dd h:m:s");
										}
									}
								},
								{
	            					targets : [5],
	            					render : function(
	            							data, type,
	            							full, meta) {
	            						var operationList=full.operation;
	            						var dealStr="";
	            						for(var i=0;i<operationList.length;i++){
	            							var data=returnData(operationList[i].deviceRuleOperationRecord.type,
	            									operationList[i].deviceRuleOperationRecord.condition,
	            									operationList[i].deviceRuleOperationRecord.value,
	            									0);
	            							dealStr=dealStr+","+data.firstStr+"--"+data.secondStr;
	            						}
	            						return dealStr.substr(1,dealStr.length-1);
	            					}
	            				}/* ,
								{
									"targets" : 6,
									"render" : function(data, type, row) {
										var toggleDiv1 = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown"><i class="i  i-settings"></i></a><ul class="dropdown-menu" style="margin-left:-100px;margin-top:-80px;">';
										var toggleDiv2 = '</ul>';
										var device_operate = '<li><a href="javascript:void(0);" onclick="checkDevice('+row.deviceRule.id+');"><i class="glyphicon glyphicon-refresh"></i>&nbsp;<fmt:message key="tiles.views.customer.compliant.index.table.instantcheck"/></a></li>';
										return toggleDiv1+device_operate+toggleDiv2;
										return '';
									}
								} */        
				                          
				            ]
			   });
		}
	
	//返回需要的data类型
	function returnData(type,condition,value,isRule){
		var firstStr="";
		var secondStr="为";
		var thirdStr="";
		if(isRule==0){
			switch(type){
				case 1:{
					firstStr="通知";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="管理员邮件通知";
						}break;
						case 2:{
							secondStr="短信通知";
						}break;
						case 3:{
							secondStr="推送通知";
						}break;
						case 4:{
							secondStr="邮件通知";
						}break;
					}
				}break;
				case 2:{
					firstStr="指令";
					switch(condition){
						case 1:{
							secondStr="企业数据擦除";
							switch(value){
								case "1":{
									thirdStr="所有设备";
								}break;
								case "2":{
									thirdStr="个人设备";
								}break;
								case "2":{
									thirdStr="企业设备";
								}break;
							}
						}break;
						case 0:{
							secondStr="恢复出厂设置";
							thirdStr="企业设备";
						}break;
					}
				}break;
				case 3:{
					firstStr="终端设备";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="禁止登录";
						}break;
						case 2:{
							secondStr="禁止访问应用资源";
						}break;
						case 3:{
							secondStr="禁止访问应用资源";
						}break;
					}
				}break;
				case 4:{
					firstStr="策略变更";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="Android系统";
						}break;
						case 0:{
							secondStr="iOS系统";
						}break;
					}
				}break;
				case 5:{
					firstStr="安全邮件";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="邮件擦除";
						}break;
						case 2:{
							secondStr="禁止访问";
						}break;
					}
				}break;
				case 6:{
					firstStr="应用限制";
					secondStr="限制使用";
					thirdStr=value;
				}break;
			}
		}else{
			switch(type){
				case 1:{
					firstStr="应用黑白名单";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="黑名单";
						}break;
						case 0:{
							secondStr="白名单";
						}break;
					}
				}break;
				case 2:{
					firstStr="破解状态";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="已破解";
						}break;
						case 0:{
							secondStr="未破解";
						}break;
					}
				}break;
				case 3:{
					firstStr="终端型号";
					secondStr="为";
					thirdStr=value;
				}break;
				case 4:{
					firstStr="操作系统版本";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="(=)等于";
						}break;
						case 2:{
							secondStr="(>)大于";
						}break;
						case 3:{
							secondStr="(<)小于";
						}break;
						case 4:{
							secondStr="(>=)大于等于";
						}break;
						case 5:{
							secondStr="(<=)小于等于";
						}break;
					}
				}break;
				case 5:{
					firstStr="锁屏密码";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="已配置";
						}break;
						case 0:{
							secondStr="未配置";
						}break;
					}
				}break;
				case 6:{
					firstStr="设备归属";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="企业设备";
						}break;
						case 0:{
							secondStr="个人设备";
						}break;
					}
				}break;
				case 7:{
					firstStr="时间范围";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="在范围内";
						}break;
						case 0:{
							secondStr="在范围外";
						}break;
					}
				}break;
				case 8:{
					firstStr="IMSI校验";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="一致";
						}break;
						case 0:{
							secondStr="不一致";
						}break;
					}
				}break;
				case 9:{
					firstStr="设备状态";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="监控中";
						}break;
						case 2:{
							secondStr="待监控";
						}break;
						case 3:{
							secondStr="注销中";
						}break;
						case 4:{
							secondStr="托管中";
						}break;
						case 5:{
							secondStr="未丢失";
						}break;
						case 6:{
							secondStr="已丢失";
						}break;
						case 7:{
							secondStr="未监控";
						}break;
					}
				}break;
				case 10:{
					firstStr="SIM卡数量";
					secondStr=">1(大于1)";
					thirdStr="";
				}break;
				case 11:{
					firstStr="SD卡数量";
					secondStr=">=1(大于等于1)";
					thirdStr="";
				}break;
			}
		}
		var data={firstStr:firstStr,firstVal:type,
			secondStr:secondStr,secondVal:condition,
			thirdStr:thirdStr};
		return data;
	}
	
</script>

<jsp:include page="../common/common.jsp"></jsp:include>
<jsp:include page="../common/common_style.jsp"></jsp:include>

<script type="text/javascript">
	$(function(){
		//获取当前用户的id
		var uid = $('#uid').val();
		//获取当前设备的id
		var did = ''
		//从后台去合规性列表
		$('#leftSelect').change(function(){
			did = $('#leftSelect').children('option:selected').val(); 
			loadCompliantDT(uid,did);
		});
		if(did == ''){
			did = $('#leftSelect option:first').val();
		    loadCompliantDT(uid,did);
		}
		
	});
</script>
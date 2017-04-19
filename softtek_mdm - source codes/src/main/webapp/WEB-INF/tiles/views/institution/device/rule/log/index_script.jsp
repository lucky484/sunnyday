<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spring:url value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js" var="dataTableJs" />
<script src="${dataTableJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js" var="dataTableBootstrapJs" />
<script src="${dataTableBootstrapJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />

<spring:url value="/institution/device/rules/logpage" var="logpageUrl"/>
<spring:url value="/institution/device/rules/del-logs" var="delLogUrl"/>


<script>

//=============================== datatables国际化
      var languageUrl;
	  var lang = "${dtLangUrl}";
	  var str = lang.substring(lang.lastIndexOf("/")+1,lang.lastIndexOf("."));
	  var str1 = lang.substring(0,lang.lastIndexOf("/"));
	  var str2 = lang.substring(lang.lastIndexOf("."),lang.length);
	  var nlang=navigator.language;
	  var nlang=navigator.language;
	  var nlang=navigator.language;
		if(nlang.toLowerCase().indexOf("zh")>=0){
			languageUrl = str1 + "/" + str + "_zh-CN" + str2;
		}else{
			languageUrl = str1 + "/" + str + "_en-US" + str2;
		}
//=======================


$(function(){
	loadData()
});

function searchAgainstRoles()
{
	loadData();
}

function cleanAgainstRoles()
{
	$("#rolename").val('');
	$("#username").val('');
	loadData();
}

//DataTable 加载
function loadData()
{
	var rolename = $("#rolename").val();
	var username = $("#username").val();
	$('#table-rule-log').DataTable({
		"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
   		"autoWidth": false,
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
			"url" : "${logpageUrl}",
			"type" : "get",
			"dataSrc" : "data",
			"data" : {
				"rolename" : rolename,
				"username" : username
			}
		},
		"columns" : [ {data : "id"}, 
		              {data : "id"},
		              {data : "id"}, 
		              {data : "deviceRule.name"}, 
		              {data : "id"}, 
		              {data : "id"}, 
		              {data : "id"}
		            ],
		"columnDefs" : [
				{
					targets : [1],
					render : function(
							data, type,
							full, meta) {
						if(full.deviceBasicInfo==null||full.deviceBasicInfo.device_name==null){
							return "-----";
						}else{
							return full.deviceBasicInfo.device_name;
						}
					}
				},
				{
					targets : [0],
					render : function(
							data, type,
							full, meta) {
						if(full.deviceBasicInfo==null||full.deviceBasicInfo.user==null||full.deviceBasicInfo.user.username==null||full.deviceBasicInfo.user.username==""){
							return "-----"
						}else{
							return full.deviceBasicInfo.user.username;
						}
					}
				},
				{
					targets : [2],
					render : function(
							data, type,
							full, meta) {
						if(full.deviceBasicInfo==null||full.deviceBasicInfo.sn==null){
							return "-----";
						}else{
							return full.deviceBasicInfo.sn;
						}
					}
				},
				{
					targets : [ 6 ],
					render : function(
							data, type,
							full, meta) {
						return new Date(parseInt(full.violate_time)).Format("yyyy-MM-dd hh:mm:ss");
					}
				},
				{
					targets : [ 4 ],
					render : function(
							data, type,
							full, meta) {
						var data=returnData(full.deviceRuleItemRecord.type,
								full.deviceRuleItemRecord.condition,
								full.deviceRuleItemRecord.value,
								1);
						var dealStr;
						if(data.thirdStr!=""){
							dealStr=data.firstStr+"--"+data.secondStr+"--"+data.thirdStr;
						}else{
							dealStr=data.firstStr+"--"+data.secondStr;
						}
						return dealStr;
					}
				},
				{
					targets : [ 5 ],
					render : function(
							data, type,
							full, meta) {
						var data=returnData(full.deviceRuleItemRecord.type,
								full.deviceRuleItemRecord.condition,
								full.deviceRuleItemRecord.value,
								1);
						var pwd="---";
						if(full.extra!=null&&full.extra!=""){
							pwd=full.extra;
						}
						return pwd;
					}
				}
				]
	});
}

	//返回需要的data类型
	function returnData(type,condition,value,isRule){
		var firstStr="";
		var secondStr="<fmt:message key='tiles.views.institution.device.rule.table.is'/>";
		var thirdStr="";
		if(isRule==0){
			switch(type){
				case 1:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.inform'/>";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.email'/>";
						}break;
						case 2:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.sms'/>";
						}break;
						case 3:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.push'/>";
						}break;
						case 4:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.email.inform'/>";
						}break;
					}
				}break;
				case 2:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.order'/>";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.erase.enterp'/>";
							switch(value){
								case "1":{
									thirdStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.deviceall'/>";
								}break;
								case "2":{
									thirdStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.person'/>";
								}break;
								case "2":{
									thirdStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.enterp'/>";
								}break;
							}
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.backto'/>";
							thirdStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.enterp'/>";
						}break;
					}
				}break;
				case 3:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.termial'/>";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.forbidden.signin'/>";
						}break;
						case 2:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.forbidden.app'/>";
						}break;
						case 3:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.tmpl.forbidden.file'/>";
						}break;
					}
				}break;
				case 4:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.changestragy'/>";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.android'/>";
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.ios'/>";
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
				case 7:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.opera.changelock'/>";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.table.yes'/>";
						}break;
					}
				}break;
			}
		}else{
			switch(type){
				case 1:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.blackandwhite'/>";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.black'/>";
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.white'/>";
						}break;
					}
				}break;
				case 2:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.root'/>";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.root.enable'/>";
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.root.disable'/>";
						}break;
					}
				}break;
				case 3:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.no'/>";
					secondStr="<fmt:message key='tiles.views.institution.device.rule.table.is'/>";
					thirdStr=value;
				}break;
				case 4:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.osversion'/>";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.eq'/>";
						}break;
						case 2:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.gt'/>";
						}break;
						case 3:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.lt'/>";
						}break;
						case 4:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.gteq'/>";
						}break;
						case 5:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.comp.lteq'/>";
						}break;
					}
				}break;
				case 5:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.lock'/>";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.config.enable'/>";
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.config.disable'/>";
						}break;
					}
				}break;
				case 6:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.belong'/>";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.enterp'/>";
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.device.person'/>";
						}break;
					}
				}break;
				case 7:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.timerange'/>";
					thirdStr=value;
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.range.in'/>";
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.range.out'/>";
						}break;
					}
				}break;
				case 8:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.imsi'/>";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.version.eq'/>";
						}break;
						case 0:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.version.neq'/>";
						}break;
					}
				}break;
				case 9:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.status'/>";
					thirdStr="";
					switch(condition){
						case 1:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.in'/>";
						}break;
						case 2:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.wait'/>";
						}break;
						case 3:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.outing'/>";
						}break;
						case 4:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.inpipe'/>";
						}break;
						case 5:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.have'/>";
						}break;
						case 6:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.lost'/>";
						}break;
						case 7:{
							secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.mirror.out'/>";
						}break;
					}
				}break;
				case 10:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.sim'/>";
					thirdStr="";
					switch(condition){
					case 0:{
						secondStr="0";
					}break;
					case 1:{
						secondStr="1";
					}break;
					case 2:{
						secondStr="2";
					}break;
					}
					
				}break;
				case 11:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.sd'/>";
					secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.gteqone'/>";
					thirdStr="";
				}break;
				case 12:{
					firstStr="<fmt:message key='tiles.views.institution.device.rule.add.type.choose.gps'/>";
					secondStr="<fmt:message key='tiles.views.institution.device.rule.add.type.condition.gps.close'/>";
					thirdStr="";
				}break;
			}
		}
		var data={firstStr:firstStr,firstVal:type,
			secondStr:secondStr,secondVal:condition,
			thirdStr:thirdStr};
		return data;
	}
	
	function delIllegeRecords(){
		var csrf="${_csrf.token}";
		$.post("${delLogUrl}",{_csrf:csrf},function(result){
			$(".notify").notify({type:result.type,message: { html: false, text: result.message}}).show();
			loadData();
		},"json");
	}
	  //查询模块的公共样式的设置和一些公共事件
    //查询框显隐
		$(".search-toggle a").click(function(){
					if($(this).hasClass("hide1")){
						$(this).removeClass("hide1");
						$(this).removeAttr("style");
						$(this).text("");
						$(this).text('<fmt:message key="tiles.institution.comm.expand.search.tip" />');
						$(".search-mod").hide();
					}else{
						$(this).addClass("hide1");
						$(this).removeAttr("style");
						$(this).text("");
						$(this).text('<fmt:message key="tiles.institution.comm.close.search.tip" />');
						$(".search-mod").show();
					}
				});
		$(".Js_dropMod").hover(function(){
			var _this = $(this);
			if(_this.find(".select-list").find("li").length<=0) return;
			_this.find(".select-list").show().find("li").show();
		},function(){
			var _this = $(this);
			_this.find(".select-list").hide().find("li").removeClass("hover");
		});
		$(".Js_dropMod").find("a").on("click",function(e){
			if($(this).parents("ul").siblings(".Js_curVal").find("input:text").length<=0){
				$(this).parents("ul").siblings(".Js_curVal").text($(this).text()).css("color","#5A5A5A")
			}else{
				$(this).parents("ul").siblings(".Js_curVal").find("input:text").val($(this).text().replace("&lt;","<").replace("&gt;",">")).css("color","#5A5A5A")
			}
			$(this).parents("ul").siblings(".Js_hiddenVal").attr("normal",$(this).text())
			$(this).parents("ul").siblings(".Js_hiddenVal").val($(this).attr("rel"))
			$(this).parents("ul.select-list").hide()
		})
</script>
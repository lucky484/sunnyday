<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spring:url value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js" var="dataTableJs" />
<script src="${dataTableJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js" var="dataTableBootstrapJs" />
<script src="${dataTableBootstrapJs}"></script>
<spring:url value="/resources/js/jquery.tmpl.js" var="jqueryTmplJs" />
<script src="${jqueryTmplJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
<script type="text/javascript">

//=============================== datatables国际化
  var languageUrl;
  var lang = "${dtLangUrl}";
  var str = lang.substring(lang.lastIndexOf("/")+1,lang.lastIndexOf("."));
  var str1 = lang.substring(0,lang.lastIndexOf("/"));
  var str2 = lang.substring(lang.lastIndexOf("."),lang.length);
  var nlang=navigator.language;
	if(nlang.toLowerCase().indexOf("zh")>=0){
		languageUrl = str1 + "/" + str + "_zh-CN" + str2;
	}else{
		languageUrl = str1 + "/" + str + "_en-US" + str2;
	}
loadDateTable();
function loadDateTable()
{
	var behaviorName = $("#behaviorName").val();
	
	var behaviorContent = $("#behaviorContent").val();
	  
	$('#bWLogTb').DataTable(
	{
		"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
		"searching" : false,
		"stateSave" : true,
		"ordering" : false,
		"bSort" : false,
		"serverSide" : true,
		"pageLength" : 10,
		"pagingType" : "full_numbers",
		"bDestroy" : true,
		"oLanguage": {
			"sUrl":languageUrl
	    },
		"ajax" : {
			"dataType" : 'json',
			"type" : "POST",
			"url" : ctx+"/institution/netbehavior/log/pages?now="+ new Date().getTime(),
			"data" : {"name":behaviorName,"content":behaviorContent,"_csrf":"${_csrf.token}"}
		},
		"columns" : [
		{
			data : "loginAccount"
		},{
			data : "userName"
		}, {
			data : "deviceName"
		}, {
			data : "sn"
		}, {
			data : "surfUrl"
		}, {
			data : "conetent"
		}, {
			data : "surfTime"
		}],
		columnDefs : [
				{
					targets : [ 0 ],
					render : 
						function(data, type, full, meta) 
						{
							return full.loginAccount;
						}
				},
				{
					targets : [ 1 ],
					render : 
						function(data, type, full, meta) 
						{
							return full.userName;
						}
				},
				{
					targets : [ 2 ],
					render : 
						function(data, type, full, meta) 
						{
							return full.deviceName;
						}
				},
				{
					targets : [ 3 ],
					render : 
						function(data, type, full, meta) 
						{
							return full.sn;
						}
				},
				{
					targets : [ 4 ],
					render : 
						function(data, type, full, meta) 
						{
						var url = full.surfUrl;
						var tipUrl = url;
						if(url.length>30) {
							url = url.substring(0,30)+"...";
						}
						return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+url+'"><span class="text-ellipsis">'+url+'</span><div>';
						}
				},
				{
					targets : [ 5 ],
					render : 
						function(data, type, full, meta) 
						{
							var conetent = full.conetent;
							var tipContent = conetent;
							if(tipContent.length>30) {
								conetent = conetent.substring(0,30)+"...";
							}
							return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+tipContent+'"><span class="text-ellipsis">'+conetent+'</span><div>';
						}
				},
				{
					targets : [ 6 ],
					render : 
						function(data, type, full, meta) 
						{
						 var formatstyle;
							var date = full.surfTime;
							if(date == null || date == "" || date == undefined)
							{
								formatstyle = '—';
							}
							else
							{
								formatstyle = formatCurrentTimeMillis(date);
							}
							
							return formatstyle;
						}
				}]

	});
}

  function getStrByLength(length,value)
  {
	    var subStr;
		if (value.length > length)
		{
			subStr = value.substring(0, length);
			return '<div title="'+ value + '">' + subStr + '...</div>';
		}
		
		return value;
  }

  //查询名单列表
  function searchBwLog(){
	   $('#bWLogTb').dataTable().fnDestroy();
	   loadDateTable();
	   $('#bWLogTb').dataTable().fnDraw();
  }
 
  //清除名单列表
  function cleanBwLog(){
	   $('#bWLogTb').dataTable().fnDestroy();
	   $("#behaviorName").val("");
	   $("#behaviorContent").val("");
	   loadDateTable();
	   $('#bWLogTb').dataTable().fnDraw();
  }
  
  function formatCurrentTimeMillis (dateTime) {
		var date = new Date(dateTime);
		var y = date.getFullYear();
		var m = (date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
          	+ (date.getMonth() + 1);
		var d = date.getDate() < 10 ? "0" + date.getDate() : date
              .getDate();
		var h = date.getHours() < 10 ? "0" + date.getHours() : date
              .getHours();
		var mi = date.getMinutes() < 10 ? "0" + date.getMinutes() : date
              .getMinutes();
		
		var s = date.getSeconds() < 10 ? "0" + date.getSeconds() : date
              .getSeconds();

	    var datetime = y + "-" + m + "-" + d + " " + h + ":" + mi + ":" + s;
	    return datetime;
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
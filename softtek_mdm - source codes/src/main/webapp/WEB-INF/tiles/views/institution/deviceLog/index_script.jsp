<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
	<script>
		//=============================== datatables国际化
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
	    $(function(){
	    	loadData();
	    });
	    
	    function loadData()
	    {
	    	var eventType = $("#eventType").val();
	    	var devicename = $("#devicename").val();
	    	var username = $("#username").val();
		  	  $('#deviceLog').dataTable({
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
						"url" : ctx + "/institution/deviceLog/queryAllDeviceLog",
						"dataSrc" : "data",
						"type" : "get",
						"data" : 
							{
								"eventType" : eventType,
								"devicename" : devicename,
								"username" : username
							}
					},
					columns : [
		           {
						data : "userName"
					},
					{
						data : "deviceName"
					}, 
					{
						 data: "eventType"
					},{
						data : "operateContent"
					},{
						data : "operateTime"
					},
					{
						data : "name"
					}],
					"columnDefs" :[
					               {
						className: "col-sm-2 datatb-max-width-devicelog",
						targets : [0],
						render : function(data, type, full,
								meta) {
							
							return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+full.userName+'"><span class="text-ellipsis">'+full.userName+'</span><div>';
						}

					},{
						className: "col-lg-1 datatb-max-width-devicelog",
						targets : [1],
						render : function(data, type, full,
								meta) {
							
							return full.deviceName;
						}

					},{
						className: "col-lg-1 datatb-max-width-devicelog",
						"targets" : 2,
						"render" : function(data, type, full,
								meta) {
							if(full.eventType == "0"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.0"/>';
							}else if(full.eventType == "1"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.1"/>';
							}else if(full.eventType == "2"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.2"/>';
							}else if(full.eventType == "3"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.3"/>';
							}else if(full.eventType == "4"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.4"/>';
							}else if(full.eventType == "5"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.5"/>';
							}else if(full.eventType == "6"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.6"/>';
							}else if(full.eventType == "7"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.7"/>';
							}else if(full.eventType == "8"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.8"/>';
							}else if(full.eventType == "9"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.9"/>';
							}else if(full.eventType == "10"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.10"/>';
							}else if(full.eventType == "11"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.11"/>';
							}else if(full.eventType == "12"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.12"/>';
							}else if(full.eventType == "13"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.13"/>';
							}else if(full.eventType == "14"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.14"/>';
							}else if(full.eventType == "15"){
								return '<fmt:message key="tiles.views.customer.device.index.eventtype.15"/>';
							}else{
								return '';
							}
								}
					},
					{
						className: "col-lg-4 datatb-max-width-sysmanagelog", 
						"targets" : 3,
						"render" : function(data, type, row) {
							var str;
							if(row.operateContent.length>30) {
								str= '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+row.operateContent+'"><span class="text-ellipsis">'+row.operateContent.substring(0,30)+"..."+'</span><div>';
							}else{
								 str= '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+row.operateContent+'"><span class="text-ellipsis">'+row.operateContent+'</span><div>';
							}
						return str
						}
					},]
		  	  });
	    }
	    
	    function searchDeviceLogs()
	    {
	    	loadData();
	    	$('#deviceLog').dataTable().fnDraw();
	    }
	    
	    function cleanDeviceLogs()
	    {
	    	$("#eventType").val('');
	    	$("#devicename").val('');
	    	$("#username").val('');
	    	$(".Js_curVal").find("input").val('<fmt:message key="tiles.institution.log.operate.all.type" />');
	    	loadData();
	    	$('#deviceLog').dataTable().fnDraw();
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
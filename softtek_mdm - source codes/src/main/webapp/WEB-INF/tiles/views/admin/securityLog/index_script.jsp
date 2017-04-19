<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
	<script>
	// =============================== datatables国际化
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
	$("#clear").click(function(){
		$('#securityLog').dataTable().fnDestroy();
		  loadData();
  	  $('#securityLog').dataTable().fnDraw();
	});
	function searchDeviceLists(){
		  $('#securityLog').dataTable().fnDestroy();
		  loadData();
    	  $('#securityLog').dataTable().fnDraw();
	}
	function cleanDeviceLists(){
		  $("#eventType").val('');
		  $(".Js_curVal").find("input").val('<fmt:message key="tiles.institution.log.security.log.all.type" />');
		  $('#securityLog').dataTable().fnDestroy();
		  loadData();
	}
	  function loadData(){
	  var eventType = $("#eventType").val();
	  $('#securityLog').dataTable({   
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
	  					"url" : "securityEventLog/queryAllSecurityLog",
	  					"dataSrc" : "data",
	  					"data" : {
	  						"eventType" : eventType
	  					}
	  				},
	  				columns : [
	  				{
	  					 data: "eventType"
	  				},{
	  					data : "level"
	  				},{
	  					data : "createDateStr"
	  				}, {
	  					data : "operateContent"
	  				}],
	  				"columnDefs" : [
									{   
										className: "col-lg-3 datatb-max-width", 
										"targets" : 0,
										"render" : function(data, type, row) {
										    if(row.eventType == '2'){
												return '<fmt:message key="tiles.institution.log.security.password.error" />';
											}else if(row.eventType == '3'){
												return '<fmt:message key="tiles.institution.log.security.url.error" />';
											}else if(row.eventType == '4'){
												return '<fmt:message key="tiles.institution.log.security.manager.login.fail" />';
											}
										}
									},
									{   
										className: "col-lg-1",
										"targets" : 1,
										"render" : function(data, type, row) {
										      if(row.level == '1'){
										    	  return '<fmt:message key="tiles.institution.log.security.level.nomal" />';
										      }else{
										      	  return '<fmt:message key="tiles.institution.log.security.level。important" />';
										      }
										}
									},
									{   
										className: "col-lg-3 datatb-max-width",
										"targets" : 2,
										"render" : function(data, type, row) {
										     return row.createDateStr;
										}
									},
									{   
										className: "col-lg-4 datatb-max-width-security",
										"targets" : 3,
										"render" : function(data, type, row) {
										     return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+row.operateContent+'"><span class="text-ellipsis">'+row.operateContent+'</span><div>';
										}
									}
									]
	  			});
	  
	  }
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
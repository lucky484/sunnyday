<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
<script>
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
	  loadDT();
  });
  
  function loadDT()
  {
	  var seletedStatus = $("#seletedStatus").val();
	  var userName = $("#userName").val();
	  
	  $('#sysManagerLog').dataTable(
	  			{   
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
	  					"url" : "sysmanageLog/queryAllOperateLog",
	  					"dataSrc" : "data",
	  					"data" : {
	  						"seletedStatus" : seletedStatus,
	  						"userName" : userName
	  					}
	  				},
	  				columns : [
	  				{
	  					 data: "username"
	  				},{
	  					data : "userType"
	  				}, {
	  					data : "operateType"
	  				}, {
	  					data : "operateTimeStr"
	  				}, {
	  					data : "operateContent"
	  				}, {
	  					data : "orgName"
	  				}],
	  				"columnDefs" : [
									{
										className: "col-lg-1 datatb-max-width-sysmanagelog", 
										"targets" : 0,
										"render" : function(data, type, row) {
										return row.username;
										}
									},
									{
										className: "col-lg-1 datatb-max-width-sysmanagelog", 
										"targets" : 1,
										"render" : function(data, type, row) {
										return row.operateType;
										}
									},
									{
										className: "col-lg-2 datatb-max-width-sysmanagelog", 
										"targets" : 2,
										"render" : function(data, type, row) {
											if(row.userType == '1'){
												return '<fmt:message key="tiles.views.admin.index.manager.table.user_type.admin" />';
											}else if(row.userType == '3'){
												return '<fmt:message key="web.institution.dptmanager.index.label" />';
											}else if(row.userType == '2'){
												return '<fmt:message key="logs.user.type.org.manager" />';
											}else if(row.userType == '4'){
												return '<fmt:message key="logs.user.type.custom" />';
											}
										}
									},
									{
										className: "col-lg-2 datatb-max-width-sysmanagelog", 
										"targets" : 3,
										"render" : function(data, type, row) {
										return row.operateTimeStr;
										}
									},
									{
										className: "col-lg-2 datatb-max-width-sysmanagelog", 
										"targets" : 4,
										"render" : function(data, type, row) {
										return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+row.operateContent+'"><span class="text-ellipsis">'+row.operateContent+'</span><div>';
										}
									},
									{
										className: "col-lg-2 datatb-max-width-sysmanagelog", 
										"targets" : 5,
										"render" : function(data, type, row) {
										      if(row.orgName == "" || row.orgName == null){
										    	  return '<fmt:message key="tiles.institution.log.operate.log.whole" />';
										      }
										      return row.orgName;
										}
									}]
	  			});
  }
      
      function searchOptLogLists()
      {
    	  $('#sysManagerLog').dataTable().fnDestroy();
    	  loadDT();
    	  $('#sysManagerLog').dataTable().fnDraw();
      }
      
      function cleanOptLogLists()
      {
    	  $("#seletedStatus").val('');
    	  $("#userName").val('');
    	  $(".Js_curVal").find("input").val('<fmt:message key="tiles.institution.log.security.log.all.type" />');
    	  $('#sysManagerLog').dataTable().fnDestroy();
    	  loadDT();
    	  $('#sysManagerLog').dataTable().fnDraw();
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
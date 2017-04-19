<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spring:url value="/customer/getAllDeviceLogs" var="pagesUrl" />
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
<spring:url value="/resources/js/datepicker/bootstrap-datetimepicker.js" var="datetimePickerJs" />  
<script src="${datetimePickerJs}"></script>
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
	function getEventType(){
		return $("#eventSelect").children('option:selected').val();
	}
	//自动去后台加载设备日志列表
	function loadDeviceLogDT(did){
		//选择事件类型
		//var type = getEventType();
		
		//alert(type);
		var eventType = $("#eventType").val();
		//选择查询开始时间
		var startTime = $('#timeStart').val();
		//选择查询结束时间
		var endTime = $('#timeEnd').val();
		//用户id	  
		var uid = $('#uid').val();
		
		var csrf="${_csrf.token}";
		
		$('#deviceLog').DataTable(
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
				"dataType":'json',
				"type" : "POST",
				"url" : "${pagesUrl}",
				"data" : {"eventType":eventType,"startTime":startTime,"endTime":endTime,"did":did,"uid":uid,"_csrf":csrf}
			},
			"columns" : [ {
				data : "eventType"
			}, {
				data : "usermodel.realname"
			}, {
				data : "operateDate"
			}, {
				data : "operateContent"
			}],
			columnDefs : 
				[ {
					"targets" : 0,
					"render" : function(data, type, row) {
									if(row.eventType == "0"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.0"/>';
									}else if(row.eventType == "1"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.1"/>';
									}else if(row.eventType == "2"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.2"/>';
									}else if(row.eventType == "3"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.3"/>';
									}else if(row.eventType == "4"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.4"/>';
									}else if(row.eventType == "5"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.5"/>';
									}else if(row.eventType == "6"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.6"/>';
									}else if(row.eventType == "7"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.7"/>';
									}else if(row.eventType == "8"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.8"/>';
									}else if(row.eventType == "9"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.9"/>';
									}else if(row.eventType == "10"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.10"/>';
									}else if(row.eventType == "11"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.11"/>';
									}else if(row.eventType == "12"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.12"/>';
									}else if(row.eventType == "13"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.13"/>';
									}else if(row.eventType == "14"){
										return '<fmt:message key="tiles.views.customer.device.index.eventtype.14"/>';
									}
								return '';
							}
					},
				{
						targets : [ 2 ],
						render : 
							function(data, type, full, meta) 
							{
							 	var formatstyle;
								var date = full.operateDate;
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
					} ]

			});	
	}
	
	//查询设备日志
	function searchDeviceLog(){
		//$('#deviceLog').dataTable().fnClearTable();
		var did = $('#leftSelect').children('option:selected').val();  
		loadDeviceLogDT(did);
		
	}
	//清空设备日志
	function cleanDeviceLog(){
		//$('#deviceLog').dataTable().fnClearTable();
		$('#timeStart').val("");
		$('#timeEnd').val("");
		var did = $('#leftSelect').children('option:selected').val(); 
		/* $("#eventSelect").each(function(){  
			$(this).attr('val',$(this).find('option:first').val());    //重置bootstrap-select显示  
			$(this).find("option").attr("selected", false);                    //重置原生select的值 
			$(this).find("option:first").attr("selected", true);  
		}); */ 
		//var eventType =$('#eventSelect option:first').val();
		$("#eventType").val('')
		$(".Js_curVal").find("input").val('<fmt:message key="jsp.views.customer.device.js.allcategory"/>');
		loadDeviceLogDT(did);
				
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

</script>

<jsp:include page="../common/common.jsp"></jsp:include>
<jsp:include page="../common/common_style.jsp"></jsp:include>


<script type="text/javascript">
	$(function(){
		//日期时间选择器,需要校验：开始时间不能比结束时间早，结束时间不能比开始时间早
		$("#timeStart").datetimepicker({
		    format: "yyyy-mm-dd",
		    autoclose: true,
		    language:'cn',
            todayHighlight: true,
		    minView: "month",
		    maxView: "decade",
		    todayBtn: true,
		    lang:"ch",
		    pickerPosition: "bottom-left"
		}).on("click",function(ev){
		    $("#timeStart").datetimepicker("setEndDate", $("#timeEnd").val());
		});
		$("#timeEnd").datetimepicker({
		    format: "yyyy-mm-dd",
		    autoclose: true,
		    language:'cn',
            todayHighlight: true,
		    minView: "month",
		    maxView: "decade",
		    todayBtn: true,
		    lang:"ch",
		    pickerPosition: "bottom-left"
		}).on("click", function (ev) {
		    $("#timeEnd").datetimepicker("setStartDate", $("#timeStart").val());
		});
		var did = '';
		$('#leftSelect').change(function(){
			did = $(this).children('option:selected').val();  
			//从后台去加载对应的设备日志列表
			loadDeviceLogDT(did);
		});
	 	if(did == ''){
			did = $('#leftSelect option:first').val();
		} 
			loadDeviceLogDT(did);
	});
	//查询框显隐
	$(".search-toggle a").click(function(){
				if($(this).hasClass("hide1")){
					$(this).removeClass("hide1");
					$(this).removeAttr("style");
					$(this).text("");
					$(this).text('<fmt:message key="jsp.views.customer.device.js.openquery"/>');
					$(".search-mod").hide();
				}else{
					$(this).addClass("hide1");
					$(this).removeAttr("style");
					$(this).text("");
					$(this).text('<fmt:message key="jsp.views.customer.device.js.closequery"/>');
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
	});
</script>
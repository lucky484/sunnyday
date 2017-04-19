<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/resources/js/jquery.tmpl.js" var="tmplJs" />
<spring:url value="/resources/js/jquery.cookie.js" var="cookieJs" />
<spring:url value="/resources/js/echarts/echarts-all.js" var="echartsJs" />
<spring:url value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js" var="dataTableJs" />
<script src="${dataTableJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js" var="dataTableBootstrapJs" />
<script src="${dataTableBootstrapJs}"></script>
<spring:url value="/resources/js/datepicker/bootstrap-datetimepicker.js" var="datetimePickerJs" />  
<spring:url value="/institution/systemStatistics/device/getalldevicestatistics" var="pagesUrl"/>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
<script src="${datetimePickerJs}"></script>
<script src="${tmplJs}"></script>
<script src="${cookieJs}"></script>
<script src="${echartsJs}"></script>
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
function loadDeviceStatisticsTable(timeStart,timeEnd,searchType){
	var csrf="${_csrf.token}"; 
	$('#deviceStatisticsLists').DataTable({
		"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
		"searching" : false,
		"stateSave" : true,
		"ordering" : false,
		"bSort" : false,
		"pageLength" : 10,
		"pagingType" : "full_numbers",
		"serverSide" : true,
		"bDestroy" : true,
		"oLanguage": {
			"sUrl":languageUrl
	    },
		"ajax" : {
			"dataType":'json',
			"type" : "GET",
			"url" : "${pagesUrl}",
			"data" : {"timeStart":timeStart,"timeEnd":timeEnd,"searchType":searchType,"_csrf":csrf}
		},
		"columns":[
		              //{data : "id"}, 
		              {data : "create_time"}, 
		              {data : "total_device"}, 
		              {data : "active_device"},
		              {data : "irr_status"},
		              {data : "leave_device"},
		              {data : "monitor_device"},
		              {data : "noactivation_device"},
		              {data : "new_device"},
		              {data : "delete_device"}
		          ],
		"columnDefs":[
						{
					"targets" : [0],
					"render" : function(data, type,full, meta) {
						if(full.create_time == null || full.create_time == ''){
						return '';
								}else{
							return new Date(parseInt(full.create_time)).Format("yyyy-MM-dd");
							}
					}
				}
						                          
				     ]	
		});
	
}

//加载用户统计图表
function loadDeviceStatisticsChart(timeStart,timeEnd,searchType){
	  //从后台去加载所有的用户统计数据
	  var xdata = [];
	  var totaldata = [];
	  var activedata = [];
	  var irrdata = [];
	  var leavedata = [];
	  var monitordata = [];
	  var noactivationdata = [];
	  var newdata = [];
	  var deletedata=[];
	  var length = 0;
	  $.ajax({
			"dataType": 'json',
			"data": {"timeStart":timeStart,"timeEnd":timeEnd,"searchType":searchType},
			'async':false,
			"type": "GET",
			"url": ctx + "/institution/systemStatistics/device/chart", 
			success: function(data) {
				length = data.length;
				$.each(data,function(index,obj){
					xdata.push(new Date(parseInt(obj.create_time)).Format("yyyy-MM-dd"));
					totaldata.push(obj.total_device);
					activedata.push(obj.active_device);
					irrdata.push(obj.irr_status);
					leavedata.push(obj.leave_device);
					monitordata.push(obj.monitor_device);
					noactivationdata.push(obj.noactivation_device);
					newdata.push(obj.new_device);
					deletedata.push(obj.delete_device);
				});
			}
		});
	if(length == 1){
		 
		drawBarChart(xdata,totaldata,activedata,irrdata,leavedata,monitordata,noactivationdata,newdata,deletedata);
	}else{
		drawLineChart(xdata,totaldata,activedata,irrdata,leavedata,monitordata,noactivationdata,newdata,deletedata);
	} 
}

//根据条件进行查找	
function search(){
	var timeStart = $("#timeStart").val();
	var timeEnd = $("#timeEnd").val();
	//查询条件
	var searchType = $('.search-current').attr('val');
	if(timeStart != "" && timeEnd != ""){
	if(searchType == undefined){
		loadDeviceStatisticsTable(timeStart,timeEnd,'');
		//加载图表
		loadDeviceStatisticsChart(timeStart,timeEnd,'');
	}else{
		loadDeviceStatisticsTable(timeStart,timeEnd,searchType);
		loadDeviceStatisticsChart(timeStart,timeEnd,searchType);
	}}else{
		$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.index.script.timetip"/>'}}).show();
	}
	
}
//根据条件置空查询
function reset(){
	$("#timeStart").val("");
	$("#timeEnd").val("");
	$(".search").each(function(){
		$(this).removeClass("search-current");
    });
	//重置头部的条件
	$('.search').eq(2).addClass("search-current");
	//并重新去加载当月的数据值
	var searchType = $('.search-current').attr('val');
	loadDeviceStatisticsTable('','',searchType);
	loadDeviceStatisticsChart('','',searchType);
}

//导出统计的table信息
function exportTable(){
	var timeStart = $("#timeStart").val();
	var timeEnd = $("#timeEnd").val();
	//查询条件
	var searchType = $('.search-current').attr('val');
	if(searchType == undefined){
		searchType = '';
	}
	$("#timeStartExport").val(timeStart);
	$("#timeEndExport").val(timeEnd);
	$("#searchTypeExport").val(searchType);
	$("#exportForm").submit();
}

$(function(){
	//头部切换部分，每次切换之后重新加载下面的统计图片
	$(".search").click(function(){
		$(".search").each(function(){
    		$(this).removeClass("search-current");
	    });
		$(this).addClass("search-current");
		//重置查询条件
		$("#timeStart").val("");
		$("#timeEnd").val("");
		//根据当前选中的查询条件去查询"
		var searchType = $(this).attr("val");
		//searchType 1 按天 2 按周 3 按月
		//去后台加载需要统计的数据，并以json格式的数据返回到前台,一个是生成图表，另一个是加载到对应的table中
		loadDeviceStatisticsTable('','',searchType);
		//加载图表
		loadDeviceStatisticsChart('','',searchType);
    });	
	
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
		$(".search").each(function(){
    		$(this).removeClass("search-current");
	    });
	    $("#timeEnd").datetimepicker("setStartDate", $("#timeStart").val());
	});
	//初始化的时候进行加载默认是按照月来统计的
	$("#timeStart").val("");
	$("#timeEnd").val("");
	//根据当前选中的查询条件去查询"
	var searchType = $('.search-current').attr('val');
	//searchType 1 按天 2 按周 3 按月
	//去后台加载需要统计的数据，并以json格式的数据返回到前台,一个是生成图表，另一个是加载到对应的table中
	loadDeviceStatisticsTable('','',searchType);
	//加载图表
	loadDeviceStatisticsChart('','',searchType);
	
});
Date.prototype.Format = function(fmt)   
{   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}  


//画出折线图
function drawLineChart(xdata,totaldata,activedata,irrdata,leavedata,monitordata,noactivationdata,newdata,deletedata){
	 var myChart = echarts.init(document.getElementById('deviceChart')); 
	  option = {
			    title : {
			    	
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.totalDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.activeDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.irrDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.leaveDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.monitorDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.noactivationDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.newDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.deleteDevice"/>']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    grid:{borderWidth: 0},
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : false,
			            splitLine:{show:false},
			            data : xdata
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			        }
			    ],
			    series : [
			        {
			            name:'<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.totalDevice"/>',
			            type:'line',
			            data:totaldata
			        },
			        {
			            name:'<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.activeDevice"/>',
			            type:'line',
			            data:activedata
			        },
			        {
			            name:'<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.irrDevice"/>',
			            type:'line',
			            data:irrdata
			        },
			        {
			            name:'<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.leaveDevice"/>',
			            type:'line',
			            data:leavedata
			        },
			        {
			            name:'<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.monitorDevice"/>',
			            type:'line',
			            data:monitordata
			        },
			        {
			            name:'<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.noactivationDevice"/>',
			            type:'line',
			            data:noactivationdata
			        },
			        {
			            name:'<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.newDevice"/>',
			            type:'line',
			            data:newdata
			        },
			        {
			            name:'<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.deleteDevice"/>',
			            type:'line',
			            data:deletedata
			        }
			    ]
			};

   // 为echarts对象加载数据 
   myChart.setOption(option); 
}

//画出bar图片
function drawBarChart(xdata,totaldata,activedata,irrdata,leavedata,monitordata,noactivationdata,newdata,deletedata){
	 var myChart = echarts.init(document.getElementById('deviceChart')); 
	 var option = {
             tooltip: {
                 show: true
             },
             legend: {
            	 data:['<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.totalDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.activeDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.irrDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.leaveDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.monitorDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.noactivationDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.newDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.deleteDevice"/>'],
             	 textStyle:{color:'#9C9C9C',fontWeight:"bolder",fontSize: 18},
             	 x : 'center',
                 selectedMode:false
             },
            // color:['#92A8CD','#efa842','#9bca63','#A47D7C'],
             toolbox: {
			        show : true,
			        feature : {
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
		      calculable : true,
		      grid:{borderWidth: 0},
             xAxis : [
                 {
                     type : 'category',
                     data : ['<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.totalDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.activeDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.irrDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.leaveDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.monitorDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.noactivationDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.newDevice"/>','<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.deleteDevice"/>']
                 }
             ],
             yAxis : [
                 {
                     type : 'value'
                 }
             ],
             series : [
                 {
                     "name":'<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.bar"/>',
                     "type":"bar",
                     "data":[totaldata[0],activedata[0],irrdata[0],leavedata[0],monitordata[0],noactivationdata[0],newdata[0],deletedata[0]]
                 }
             ]
         };
 
         // 为echarts对象加载数据 
         myChart.setOption(option); 
     }
</script>
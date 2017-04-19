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
<spring:url value="/institution/systemStatistics/flux/getAllFluxStatistics" var="pagesUrl"/>
<spring:url value="/institution/systemStatistics/flux/getAllTotalFluxStatistics" var="totalUserPagesUrl"/>
<script src="${datetimePickerJs}"></script>
<script src="${tmplJs}"></script>
<script src="${cookieJs}"></script>
<script src="${echartsJs}"></script>
<jsp:include page="../common/common_script.jsp"></jsp:include>
<jsp:include page="../common/common_style.jsp"></jsp:include>
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
function loadUserStatisticsTable(timeStart,timeEnd,searchType){
	var csrf="${_csrf.token}"; 
	$('#userStatisticsLists').DataTable({
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
			"type" : "GET",
			"url" : "${pagesUrl}",
			"data" : {"timeStart":timeStart,"timeEnd":timeEnd,"searchType":searchType}
		},
		"columns":[
		              {data : "createTime"},
		              {data : "totalFlux"}
		          ],
		 "columnDefs":[
						{
							"targets" : [0],
							"render" : function(data, type,full, meta) {
								if(full.createTime == null || full.createTime == ''){
									return '';
								}else{
									return new Date(parseInt(full.createTime)).Format("yyyy-MM-dd");
								}
							}
						},
						{
							"targets" : [1],
							"render" : function(data, type,full, meta) {
								if(full.totalFlux == null || full.totalFlux == ''){
									return '0';
								}else{
									/* return '<a href="javascript:void(0);" class="text-primary"  onclick="viewTotalUser('+full.createTime+');">'
									+ full.totalFlux
									+ '</a>'; */
									if(full.totalFlux<=0){
										full.totalFlux=0;
									}
									return full.totalFlux+'M';
								}
							}
						} 
					  ]
		});
	
}

function LoadTotalUserLists(){
	var csrf="${_csrf.token}"; 
	var device_name = $('#device_name').val();
	var search_date = $('#totalUserSearchDate').val();
	$('#totalUserList').DataTable({
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
			"type" : "GET",
			"url" : "${totalUserPagesUrl}",
			"data" : {"device_name":device_name,"search_date":search_date}
		},
		"columns":[
		           	  {data : "createTime"},
		              {data : "device_name"},
		              {data : "user.realname"}, 
		              /*{data : "departName"}, */
		              {data : "flux"}
		          ],
		 "columnDefs":[
						{
							"targets" : [0],
							"render" : function(data, type,full, meta) {
								if(full.createTime == null || full.createTime == ''){
									return '';
								}else{
									return new Date(parseInt(full.createTime)).Format("yyyy-MM-dd");
								}
							}
						}
					  ]
		});
	
}

//查看所有的用户信息
function viewTotalUser(createDate){
	var create_time = new Date(parseInt(createDate)).Format("yyyy-MM-dd");
	$('#totalUserSearchDate').val(create_time);
	$('#device_name').val('');
	$('#viewTotalUserModel').modal('show');
	LoadTotalUserLists();
}

function exportTotalUser(){
	//其实这个导出是带有条件的导出，不能使用ajax的导出
	var form = $("<form></form>");
    form.attr('action',ctx + "/institution/systemStatistics/flux/total/export");
    var input1 = $('<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>');
    form.append(input1);
    var device_name = $("#device_name").val();
	var create_time = $('#totalUserSearchDate').val();
	//查询条件
    var input2 = $('<input type="hidden" name="device_name" value="'+device_name+'"/>');
    var input3 = $('<input type="hidden" name="create_time" value="'+create_time+'"/>');
    form.append(input2);
    form.append(input3);
    form.attr('method','post');
    //form.attr('enctype','multipart/form-data');
    form.submit();
	
}

function searchTotalUser(){
	var device_name = $('#device_name').val();
	LoadTotalUserLists();
	$('#totalUserList').dataTable().fnDraw();
}

function clearTotalUser(){
	$('#device_name').val('');
	LoadTotalUserLists();
	$('#totalUserList').dataTable().fnDraw();
}

//根据条件进行查找	
function search(){
	var timeStart = $("#timeStart").val();
	var timeEnd = $("#timeEnd").val();
	var searchType = $('.search-current').attr('val');
	if(timeStart != "" && timeEnd != ""){
	if(searchType == undefined){
		loadUserStatisticsTable(timeStart,timeEnd,'');
		//加载图表
		loadUserStatisticsChart(timeStart,timeEnd,'');
	}else{
		loadUserStatisticsTable(timeStart,timeEnd,searchType);
		//加载图表
		loadUserStatisticsChart(timeStart,timeEnd,searchType);
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
	$('.head-middle').css('display','block'); 
	loadUserStatisticsTable('','',searchType);
	//加载图表
	loadUserStatisticsChart('','',searchType);
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
	/* //其实这个导出是带有条件的导出，不能使用ajax的导出
	var form = $("<form></form>");
    form.attr('action',ctx + "/institution/systemStatistics/flux/export");
    var input1 = $('<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>');
    form.append(input1);
    var timeStart = $("#timeStart").val();
	var timeEnd = $("#timeEnd").val();
	//查询条件
	var searchType = $('.search-current').attr('val');
	if(searchType == undefined){
		searchType = '';
	}
    var input2 = $('<input type="hidden" name="timeStart" value="'+timeStart+'"/>');
    var input3 = $('<input type="hidden" name="timeEnd" value="'+timeEnd+'"/>');
    var input4 = $('<input type="hidden" name="searchType" value="'+searchType+'"/>');
    form.append(input2);
    form.append(input3);
    form.append(input4);
    form.attr('method','post');
    //form.attr('enctype','multipart/form-data');
    form.submit();
	 */
}
//画出折线图
function drawLineChart(xdata,totaldata,activedata,inactivedata,deletedata){
	 var myChart = echarts.init(document.getElementById('userChart')); 
	  option = {
			    title : {
			    	
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['<fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.script.sumfulx"/>']
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
	                    axisLabel : {
	                         formatter: '{value} MB'
	                    }
			        }
			    ],
			    series : [
			        {
			            name:'<fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.script.sumfulx"/>',
			            type:'line',
			            data:totaldata
			        }
			    ]
			};
	     // 为echarts对象加载数据 
	     myChart.setOption(option); 
}

//画出bar图片
function drawBarChart(xdata,totaldata,activedata,inactivedata,deletedata){
	 var myChart = echarts.init(document.getElementById('userChart')); 
	 var option = {
             tooltip: {
                 show: true
             },
             legend: {
                 data:['<fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.script.tooltip"/>'],
             	 textStyle:{color:'#9C9C9C',fontWeight:"bolder",fontSize: 18},
             	 x : 'center',
                 selectedMode:false
             },
             color:['#92A8CD','#efa842','#9bca63','#A47D7C'],
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
                     data : ['<fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.script.sumfulx"/>']
                 }
             ],
             yAxis : [
                 {
                     type : 'value',
                     axisLabel : {
                         formatter: '{value} MB'
                     }
                 }
             ],
             series : [
                 {
                     "name":'<fmt:message key="tiles.views.institution.operationalStatistics.fluxStatistics.script.tooltip"/>',
                     "type":"bar",
                     "data":[totaldata[0]]
                 }
             ]
         };
         // 为echarts对象加载数据 
         myChart.setOption(option); 
     }

//加载用户统计图表
function loadUserStatisticsChart(timeStart,timeEnd,searchType){
	  //从后台去加载所有的用户统计数据
	  var xdata = [];
	  var totaldata = [];
	  var activedata = [];
	  var inactivedata = [];
	  var deletedata = [];
	  var length = 0;
	  $.ajax({
			"dataType": 'json',
			"data": {"timeStart":timeStart,"timeEnd":timeEnd,"searchType":searchType},
			'async':false,
			"type": "GET",
			"url": ctx + "/institution/systemStatistics/flux/chart",
			success: function(data) {
				if(data != null){
					length = data.length;
					$.each(data,function(index,obj){
						xdata.push(new Date(parseInt(obj.createTime)).Format("yyyy-MM-dd"));
						totaldata.push(obj.totalFlux);
					});
				}
			}
		});
	if(length == 1){
		drawBarChart(xdata,totaldata);
	}else{
		drawLineChart(xdata,totaldata);
	} 
}

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
		var searchType = $('.search-current').attr('val');
		//当选择按照当日查询的时候需要把日期选择框隐藏
		//searchType 1 按天 2 按周 3 按月
		//去后台加载需要统计的数据，并以json格式的数据返回到前台,一个是生成图表，另一个是加载到对应的table中
		loadUserStatisticsTable('','',searchType);
		//加载图表
		loadUserStatisticsChart('','',searchType);
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
		//参考liantong mdm效果
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
	loadUserStatisticsTable('','',searchType);
	//加载图表
	loadUserStatisticsChart('','',searchType);
	
});
</script>
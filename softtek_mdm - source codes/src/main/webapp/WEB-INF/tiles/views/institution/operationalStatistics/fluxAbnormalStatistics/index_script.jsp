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
<spring:url value="/institution/systemStatistics/flux/abnormal/getallfluxabonormalstatistics" var="pagesUrl"/>
<spring:url value="/institution/systemStatistics/flux/abnormal/appfluxlsit" var="apppagesUrl"/>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />

<script src="${datetimePickerJs}"></script>
<script src="${tmplJs}"></script>
<script src="${cookieJs}"></script>
<script src="${echartsJs}"></script>
<script type="text/javascript">
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



//加载数据 datatable
function loadFluxStatisticsTable(){
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
			"type" : "POST",
			"url" : "${pagesUrl}",
			"data" : {"_csrf":csrf}
		},
		"columns":[
		              {data : "realName"}, 
		              {data : "userName"}, 
		              {data : "deviceName"}, 
		              {data : "statisticalTime"},
		              {data : "flux"},
		              {data : "flux"}
		          ],
		"columnDefs":[
					{
						"targets" : [3],
						"render" : function(data, type,full, meta) {
							if(full.statisticalTime == null || full.statisticalTime == ''){
							return '';
									}else{
								return new Date(parseInt(full.statisticalTime)).Format("yyyy-MM-dd");
								}
						}
					},
						{
					"targets" : [4],
					"render" : function(data, type,full, meta) {
						return full.flux+"M";
					}
				},
				{
					"targets" : [5],
					"render" : function(data, type,full, meta) {
						return '<a href="javascript:abDetail('+full.id+','+full.userId+',\''+full.sn+'\','+full.statisticalTime+')" style="color: #428bca;" class="btn-mr"><fmt:message key="tiles.aside.menu.device.flux.abnormal.flux.detail.select"/></a>';
					}
				}
						                          
				     ]	
		});
	
}


//加载数据 datatable
function loadAppFluxStatisticsTable(id,userId,sn,statisticalTime){
	var csrf="${_csrf.token}"; 
	$('#appFluxListTable').DataTable({
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
			"type" : "POST",
			"url" : "${apppagesUrl}",
			"data" : {"id":id,"userId":userId,"sn":sn,"_csrf":csrf}
		},
		"columns":[
		              {data : "appName"}, 
		              {data : "flux"},
		              {data : "flux"}
		          ],
		"columnDefs":[
					{
						"targets" : [0],
						"render" : function(data, type,full, meta) {
							return full.name;
						}
					},
					{
						"targets" : [1],
						"render" : function(data, type,full, meta) {
							return full.app_flux+"M";
						}
					},
					{
						"targets" : [2],
						"render" : function(data, type,full, meta) {
							return new Date(parseInt(statisticalTime)).Format("yyyy-MM-dd");
						}
					}
						                          
				     ]	
		});
	
}


function abDetail(id,userId,sn,statisticalTime){
	$("#myModal").modal();
	$("#hidenuserId").val(userId);
	$("#hidenSn").val(sn);
	loadAppFluxStatisticsTable(id,userId,sn,statisticalTime);
	loadDeviceStatisticsChart(id,userId,sn);
}


//加载用户统计图表
function loadDeviceStatisticsChart(id,userId,sn){
	  //从后台去加载所有的用户统计数据
	  var xdata = [];
	  var fluxdata=[];
	  var length = 0;
	  $.ajax({
			"dataType": 'json',
			"data": {"id":id,"userId":userId,"sn":sn},
			'async':false,
			"type": "GET",
			"url": ctx + "/institution/systemStatistics/flux/abnormal/chart", 
			success: function(data) {
				length = data.length;
				$.each(data,function(index,obj){
					xdata.push(new Date(parseInt(obj.statisticalTime)).Format("yyyy-MM-dd"));
					fluxdata.push(obj.flux);
				});
			}
		});
	if(length == 1){
		drawBarChart(xdata,fluxdata);
	}else{
		drawLineChart(xdata,fluxdata);
	} 
}


$(function(){
	//去后台加载需要统计的数据，并以json格式的数据返回到前台,一个是生成图表，另一个是加载到对应的table中
	loadFluxStatisticsTable();
});



//画出折线图
function drawLineChart(xdata,fluxdata){
	 var myChart = echarts.init(document.getElementById('fluxChart')); 
	  option = {
			    title : {
			    	
			    },
			    tooltip : {
			        trigger: 'axis'
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
			            name:'流量(M)',
			            type:'line',
			            data:fluxdata
			        }
			    ]
			};

   // 为echarts对象加载数据 
   myChart.setOption(option); 
}

//画出bar图片
function drawBarChart(xdata,flux){
	 var myChart = echarts.init(document.getElementById('fluxChart')); 
	 var option = {
             tooltip: {
                 show: true
             },
             legend: {
            	 data:['<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.totalDevice"/>'],
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
                     data : ['<fmt:message key="tiles.views.institution.operationalStatistics.deviceStatistics.index.table.totalDevice"/>']
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
                     "data":[flux[0]]
                 }
             ]
         };
 
         // 为echarts对象加载数据 
         myChart.setOption(option); 
     }
     
     
 
//格式化日期     
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

</script>
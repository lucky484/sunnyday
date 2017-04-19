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
<spring:url value="/institution/systemStatistics/getAllUserStatistics" var="pagesUrl"/>
<script src="${datetimePickerJs}"></script>
<script src="${tmplJs}"></script>
<script src="${cookieJs}"></script>
<script src="${echartsJs}"></script>
<script type="text/javascript">
	//画柱形图的js函数
	function drawBarChart(containerid,title,lengenddata,xadata,seriesdata){
		var seresData = mergeBarData(seriesdata,title);
		var retChart = echarts.init(document.getElementById(containerid));
		var option = {
				    title : {
				        text: title,
				        x : 'center',
				        textStyle:{color:'#9C9C9C',fontWeight:"normal"}
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				    	show:false,
				        data:lengenddata
				    },
				    color:['#92A8CD','#efa842','#9bca63',  '#A47D7C', '#B5CA92'],//颜色值
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
				            data : xadata,
				            splitLine:{show:false}
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series :seresData
				};
                retChart.setOption(option);
                return retChart;   
	}
	
	function mergeBarData(data,name){
		var retAry=[];
		if(data instanceof Array){
			var seriedata={
				            name:name,
				            type:'bar',
				            data:data,
				            markPoint : {
				                data : [
				                    {type : 'max', name: '<fmt:message key="jsp.views.institution.js.common.max"/>'},
				                    {type : 'min', name: '<fmt:message key="jsp.views.institution.js.common.min"/>'}
				                ]
				            },
				            markLine : {
				                data : [
				                    {type : 'average', name: '<fmt:message key="jsp.views.institution.js.common.ava"/>'}
				                ]
				            }
				        }
			retAry.push(seriedata);
			return retAry;
		}
		else //如果传入的是对象类型的
		{
			
		}
	}
	
</script>
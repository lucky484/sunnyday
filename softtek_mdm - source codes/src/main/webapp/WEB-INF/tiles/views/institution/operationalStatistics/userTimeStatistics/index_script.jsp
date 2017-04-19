<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/resources/js/echarts/echarts-all.js" var="echartsJs" />
<script src="${echartsJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js" var="dataTableJs" />
<script src="${dataTableJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js" var="dataTableBootstrapJs" />
<script src="${dataTableBootstrapJs}"></script>
<spring:url value="/institution/userTimes/queryUserTime" var="queryUserTime"/>
<spring:url value="/institution/userTimes/queryAllUserTime" var="queryAllUserTime"/>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
<spring:url value="/resources/js/datepicker/bootstrap-datetimepicker.js" var="datetimePickerJs" />
<script src="${datetimePickerJs}"></script>
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
    $(function(){
    	var timeType = $(".search-current").attr("val");
    	loadData(timeType);
    	loadUserTimeChart(timeType);
    });
     
    function loadData(timeType,startTime,endTime){
    	var csrf="${_csrf.token}"; 
    	$('#usertime').DataTable({
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
    			"url" : "${queryAllUserTime}",
                "data" : {searchType:timeType,startTime:startTime,endTime:endTime,"_csrf":csrf}
    		},
    		"columns":[
    		              {data : "createTime"}, 
    		              {data : "remark"}, 
    		              {data : "userTimeMax"},
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
    				}]	
    		});
    }
    
     function loadUserTimeChart(timeType,startTime,endTime){
    	 var csrf = "${_csrf.token}";
    	 var userTime = {};
    	  $.ajax({
  			"dataType": 'json',
  		  	"data": {"searchType":timeType,startTime:startTime,endTime:endTime,_csrf:csrf},
  			'async':false,
  			"type": "post",
  			"url": "${queryUserTime}", 
  			success: function(data) {
			 userTime = data.usertime;
  			}
  		});
    	 drawBarChart(userTime);
     }
     
     function drawBarChart(userTime){
    	 var myChart = echarts.init(document.getElementById('userTimeChart')); 
    	 option = {
 			    tooltip: {
 	                 show: true
 	             },
 			    legend: {
 			        data:['<fmt:message key="tiles.views.institution.operationalStatistics.userTimeStatistics.user.times"/>'],
 			        textStyle:{color:'#9C9C9C',fontWeight:"bolder",fontSize: 9},
 			        x : 'center',
 	                selectedMode:false
 			    },
 			    toolbox: {
 			        show : true,
 			        feature : {
 			            magicType : {show: true, type: ['line', 'bar']},
 			            restore : {show: true},
 			            saveAsImage : {show: true,
 			            	           title:'<fmt:message key="tiles.views.institution.operationalStatistics.userTimeStatistics.user.status.png"/>'}
 			        }
 			    },
 			    calculable : true,
 			    grid:{borderWidth: 0},
 			    xAxis : [
 			        {
 			            type : 'category',
 			            data : ['<fmt:message key="tiles.views.institution.user.time.from.zero.to.one"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.one.to.two"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.two.to.three"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.three.to.four"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.four.to.five"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.five.to.six"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.six.to.seven"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.seven.to.eight"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.eight.to.nine"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.nine.to.ten"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.ten.to.eleven"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.eleven.to.twelve"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.twelve.to.thirteen"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.thirteen.to.fourteen"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.fourteen.to.fifteen"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.fifteen.to.sixteen"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.sixteen.to.seventeen"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.seventeen.to.eighteen"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.eighteen.to.nineteen"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.nineteen.to.twenty"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.twenty.to.twentyOne"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.twentyOne.to.twentyTwo"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.twentyTwo.to.twentyThree"/>',
 			                    '<fmt:message key="tiles.views.institution.user.time.from.twentyThree.to.zero"/>'
 			                   ],
	 			       axisLabel:{
	 		                show:true,
	 		                interval: 'auto',    // {number}
	 		                margin: 8,
	 		                textStyle: {
	 		                    fontFamily: 'Microsoft YaHei',
	 		                    fontSize: 10,
	 		                }
	 		            }
 			        }
 			    ],
 			    yAxis : [
 			        {
 			            type : 'value',
 			        }
 			    ],
 			    series : [
 		 			        {
 		 			            "name":'<fmt:message key="tiles.views.institution.operationalStatistics.userTimeStatistics.user.times"/>',
 		 			            "type":'bar',
 		 			            "data":[userTime.zero==null?0:userTime.zero,userTime.one==null?0:userTime.one,userTime.two==null?0:userTime.two,
 		 			                  userTime.three==null?0:userTime.three,userTime.four==null?0:userTime.four,userTime.five==null?0:userTime.five,
 		 			                  userTime.six==null?0:userTime.six,userTime.seven==null?0:userTime.seven,userTime.eight==null?0:userTime.eight,
 		 			                  userTime.nine==null?0:userTime.nine,userTime.ten==null?0:userTime.ten,userTime.eleven==null?0:userTime.eleven,
 		 			                  userTime.twelve==null?0:userTime.twelve,userTime.thirteen==null?0:userTime.thirteen,userTime.fourteen==null?0:userTime.fourteen,
 		 			                  userTime.fifteen==null?0:userTime.fifteen,userTime.sixteen==null?0:userTime.sixteen,userTime.seventeen==null?0:userTime.seventeen,
 		 			                  userTime.eighteen==null?0:userTime.eighteen,userTime.nineteen==null?0:userTime.nineteen,userTime.twenty==null?0:userTime.twenty,
 		 			                  userTime.twentyOne==null?0:userTime.twentyOne,userTime.twentyTwo==null?0:userTime.twentyTwo,userTime.twentyThree==null?0:userTime.twentyThree]
 		 			        }
 		 			    ]
 			};
    	 // 为echarts对象加载数据 
         myChart.setOption(option); 
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
     $(".search").click(function(){
    		$(".search").each(function(){
        		$(this).removeClass("search-current");
    	    });
    	    $(this).addClass("search-current");
    		$("#startTime").val("");
    		$("#endTime").val("");
    		var timeType = $(this).attr("val");
    		loadData(timeType);
    		loadUserTimeChart(timeType);
     });
 	//日期时间选择器,需要校验：开始时间不能比结束时间早，结束时间不能比开始时间早
 	$("#startTime").datetimepicker({
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
 	    $("#startTime").datetimepicker("setEndDate", $("#timeEnd").val());
 	});
 	$("#endTime").datetimepicker({
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
	    $("#endTime").datetimepicker("setStartDate", $("#timeStart").val());
	});
 	
 	function search(){
 		var startTime = $("#startTime").val();
 		var endTime = $("#endTime").val();
 		loadData('',startTime,endTime);
 		loadUserTimeChart('',startTime,endTime);
 	}
 	//根据条件置空查询
 	function reset(){
 		$("#startTime").val("");
 		$("#endTime").val("");
 		$(".search").each(function(){
 			$(this).removeClass("search-current");
 	    });
 		//重置头部的条件
 		$('.search').eq(2).addClass("search-current");
 		//并重新去加载当月的数据值
 		var timeType = $('.search-current').attr('val');
 		loadData(timeType);
 		loadUserTimeChart(timeType);
 	}
</script>
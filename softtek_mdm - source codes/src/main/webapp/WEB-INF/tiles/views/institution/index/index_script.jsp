<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spring:url value="/resources/js/jquery.cookie.js" var="cookieJs" />
<script src="${cookieJs}"></script>
<spring:url value="/institution/exchange" var="exchangeUrl"/>
    <script type="text/javascript">
    function clickUrl(menu,sub) {
    	activeMenu(menu);
    	activeSub(sub);
	}
    function clickUrl(menu,sub,status) {
    	activeMenu(menu);
    	activeSub(sub);
    	var devicestatus=status;
    	$.cookie('device-tags',null,{path:"/"});
    	$.cookie("device-tags",devicestatus,{path:"/"});
	}
    
    function clickUrlIos(menu,sub,status) {
    	activeMenu(menu);
    	activeSub(sub);
    	var type=status;
    	$.cookie('device-type-tags',null,{path:"/"});
    	$.cookie("device-type-tags",type,{path:"/"});
	}
    function activeMenu(value){
    	var storage=window.localStorage;
    	storage.menu=value;
    }
    function activeSub(value){
    	var storage=window.localStorage;
    	storage.sub=value;
    }
    function onExchange(obj){
    	var iid=$(obj).val();
    	if(iid.trim().length>0){
    		var csrf="${_csrf.token}";
    		$.post("${exchangeUrl}",{iid:parseInt(iid),_csrf:csrf},function(){
    			window.sessionStorage.removeItem("userTreeselected");
    			window.location.reload();
    		})
    	}
    }
    
    $(function(){
// 第一个pie
var monitoredCount=${map.monitoredCount};
var notMonitoredCount=${map.notMonitoredCount};
var cancellationCount=${map.cancellationCount};
var trusteeshipCount=${map.trusteeshipCount};
var deviceLostCount=${map.deviceLostCount};
// 第二个pie
// 违规设备
// 合规设备
var irrCount=${map.irrCount};
var notirrCount=${map.deviceCount} - ${map.irrCount};
var da2 = [
          {
            label: '<fmt:message key="tiles.views.institution.device.rule.add.type.condition.mirror.irrcount"/>',
            data: irrCount
          },    
          {
            label: '<fmt:message key="tiles.views.institution.device.rule.add.type.condition.mirror.notirrcount"/>',
            data: notirrCount
          }
        ];
    var da = [
      {
        label: '<fmt:message key="web.institution.index.script.status2"/>',
        data: trusteeshipCount
      },    
      {
        label: '<fmt:message key="web.institution.index.script.status1"/>',
        data: monitoredCount
      },
      {
        label: '<fmt:message key="web.institution.index.script.status3"/>',
        data: notMonitoredCount
      },
      
      {
        label: '<fmt:message key="web.institution.index.script.status4"/>',
        data: cancellationCount
      },
      {
          label: '<fmt:message key="tiles.views.institution.device.rule.add.type.condition.mirror.lost"/>',
          data: deviceLostCount
        }
    ], 
    da1 = [],
    series = Math.floor(Math.random() * 4) + 3;

    for (var i = 0; i < series; i++) {
      da1[i] = {
        label: "Series" + (i + 1),
        data: Math.floor(Math.random() * 100) + 1
      }
    }

    $("#flot-pie-donut").length && $.plot($("#flot-pie-donut"), da, {
      series: {
        pie: {
          innerRadius: 0.4,
          show: true,
          stroke: {
            width: 0
          },
          label: {
            show: true,
             threshold: 0.02,
             formatter: function(label, series){
                return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'+label+'<br/>'+series.percent.toFixed(2)+'%</div>';
            }, 
            background: { opacity: 0.8 } ,
           
          },

        }
      },
    
      grid: {
    	  //关闭鼠标悬浮
           hoverable: true, 
          clickable: false
      },
      colors:[
              '#FF6666',//第一个颜色，欢迎加入Highcharts学习交流群294191384
              '##33CC66', //。。。。
              '#FF8C00',//第二个颜色
              '#00BFFF',//第三个颜色
              '#3B3B3B'
            ],
      tooltip: true,
      tooltipOpts: {
        content: "%s: %p.0%",
        onHover: function(flotItem, $tooltipEl){
        	$tooltipEl[0].style.background="rgb(0,0,0)";
        	if(flotItem.series.label=='<fmt:message key="web.institution.index.script.status1"/>'){
        	$tooltipEl[0].textContent=monitoredCount+'<fmt:message key="web.institution.index.script.unit"/>';
        	}else if(flotItem.series.label=='<fmt:message key="web.institution.index.script.status2"/>'){
            	$tooltipEl[0].textContent=trusteeshipCount+'<fmt:message key="web.institution.index.script.unit"/>';
            	}else if(flotItem.series.label=='<fmt:message key="web.institution.index.script.status3"/>'){
            	$tooltipEl[0].textContent=notMonitoredCount+'<fmt:message key="web.institution.index.script.unit"/>';
            	}else if(flotItem.series.label=='<fmt:message key="web.institution.index.script.status4"/>'){
            	$tooltipEl[0].textContent=cancellationCount+'<fmt:message key="web.institution.index.script.unit"/>';
            	}else if(flotItem.series.label=='<fmt:message key="tiles.views.institution.device.rule.add.type.condition.mirror.lost"/>'){
           		$tooltipEl[0].textContent=deviceLostCount+'<fmt:message key="web.institution.index.script.unit"/>';
            	}
        	
        }
      }
    });

    
    $("#flot-pie-donut2").length && $.plot($("#flot-pie-donut2"), da2, {
        series: {
          pie: {
            innerRadius: 0.4,
            show: true,
            stroke: {
              width: 0
            },
            label: {
              show: true,
               threshold: 0.02,
               formatter: function(label, series){
                  return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'+label+'<br/>'+series.percent.toFixed(2)+'%</div>';
              }, 
              background: { opacity: 0.8 } ,
             
            },

          }
        },
      
        grid: {
      	  //关闭鼠标悬浮
             hoverable: true, 
            clickable: false
        },
        tooltip: true,
        tooltipOpts: {
          content: "%s: %p.0%",
          onHover: function(flotItem, $tooltipEl){
          	$tooltipEl[0].style.background="rgb(0,0,0)";
          	if(flotItem.series.label=='<fmt:message key="tiles.views.institution.device.rule.add.type.condition.mirror.irrcount"/>'){
            	$tooltipEl[0].textContent=irrCount+'<fmt:message key="web.institution.index.script.unit"/>';
            	}else if(flotItem.series.label=='<fmt:message key="tiles.views.institution.device.rule.add.type.condition.mirror.notirrcount"/>'){
                	$tooltipEl[0].textContent=notirrCount+'<fmt:message key="web.institution.index.script.unit"/>';
                	}
          }
        }
      });
    
    
    
    
    $("#flot-pie").length && $.plot($("#flot-pie"), da, {
      series: {
        pie: {
          combine: {
                color: "#999",
                length:0.00001
              },
          show: true
        }
      },    
    
      legend: {
        show: false
      },
      grid: {
          hoverable: true,
          clickable: false
      },
      tooltip: true,
      tooltipOpts: {
        content: "%s: %p.0%"
      }
    });
    
    });
  </script>
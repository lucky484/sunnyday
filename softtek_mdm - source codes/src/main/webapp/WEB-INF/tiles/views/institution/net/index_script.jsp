<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
  <spring:url value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js" var="dataTableJs" />
  <script src="${dataTableJs}"></script>
  <spring:url value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js" var="dataTableBootstrapJs" />
  <script src="${dataTableBootstrapJs}"></script>
  <spring:url value="/resources/js/jquery.tmpl.js" var="jqueryTmplJs" />
  <script src="${jqueryTmplJs}"></script>
  <spring:url value="/resources/js/echarts/echarts-all.js" var="echartsJs" />
  <script src="${echartsJs}"></script>
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
    
   // 加载系统关键字
   function loadNetWords(){
       var type = $("#searchWebsiteType").val();
       //加载策略列表数据
       var dataTable = $('#netTable').dataTable({
                   "dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
                   "autoWidth": false,
                   "searching" : false,
                   "stateSave" : true,
                   "ordering" : false,
                   "bSort" : false,
                   "serverSide" : true,
                   "pageLength" : 10,
                   "pagingType" : "full_numbers",
                   "bDestroy" : true,
                   "oLanguage": {
                       "sUrl": languageUrl
                   },
                   "ajax" : {
                       "dataType" : 'json',
                       "type" : "get",
                       "url" : ctx+"/institution/net/queryAll?now="+ new Date().getTime(),
                       "data" : {"type":type}
                  },
                  "columnDefs" : [
                  {
                      "targets" : [0],
                      "render" : function(data, type, row) {
                          return row.websiteName;
                      }
                 },
                 {
                     "targets" : [1],
                     "render" : function(data, type, row) {
                         return row.count;
                     }
                },

               {
                   "targets" : [2],
                   "render" : function(data, type, row) {
                      var btns = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">';
                       btns += '<i class="i  i-settings"></i>';
                       btns += '</a>';
                       btns += '<ul class="dropdown-menu" style="margin-left:50px;margin-top:-30px;">';
                       btns += '<li><a href="javascript:find('+row.websiteType+')"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="general.jsp.find.label"/></a></li>';
                       btns += '</ul>';
                       return btns;
                   }
            }]
       });
   }
   
   // 加载url列表
   function loadUrlWords(type){
        var name = $("#searchName").val();
        var urlTable = $('#urlTable').dataTable({
                 "dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
                 "autoWidth": false,
                 "searching" : false,
                 "stateSave" : true,
                 "ordering" : false,
                 "bSort" : false,
                 "serverSide" : true,
                 "pageLength" : 10,
                 "pagingType" : "full_numbers",
                 "bDestroy" : true,
                 "oLanguage": {
                     "sUrl": languageUrl
                 },
                 "ajax" : {
                     "dataType" : 'json',
                     "type" : "GET",
                     "url" : ctx+"/institution/net/find?now="+ new Date().getTime(),
                     "data" : {"type":type,"name":name}
                },
                "columnDefs" : [
                {
                   "targets" : [0],
                   "render" : function(data, type, row) {
                       return row.websiteName;
                   }
               },{
                   "targets" : [1],
                   "render" : function(data, type, row) {
                       return row.surfUsername;
                   }
               } ,{
                   "targets" : [2],
                   "render" : function(data, type, row) {
                       var type = row.surfUsertype;
                       if(type==4){
                           return "普通用户";
                       }
                   }
               },{
                   "targets" : [3],
                   "render" : function(data, type, row) {
                       return row.departmentName;
                   }
               },{
                   "targets" : [4],
                   "render" : function(data, type, row) {
                       var url = row.surfUrl;
                       if(url != null && url != ""){
                          if(url.length > 30){
                          url = url.substring(0,30)+" ...";
                          }
                          return '<a title="'+row.surfUrl+'" >' + url + '</a>';
                       }
                       return "";
                   }
               }, {
                   "targets" : [5],
                   "render" : function(data, type, row) {
                       var desc = row.conetent;
                       if(desc!=null&&desc!=""){
                           if(desc.length > 30){
                               desc = desc.substring(0,30) + " ...";
                               return '<a title="'+row.conetent+'" >' + desc + '</a>';
                           }
                       }
                       return '<a title="'+row.conetent+'" >' + desc + '</a>';
                   }
               }, {
                   "targets" : [6],
                   "render" : function(data, type, row) {
                       var surftime = row.surftime;
                       if(surftime!=null&&surftime != "" && surftime.indexOf(".")>0){
                           return surftime = surftime.substring(0,surftime.length-2);
                       }
                       return surftime;
                   }
               }]
         });
   }
   
   // 查看用户访问的网址
   function find(type){
       $("#myUrlModal").modal("show")
       $("#type").val(type);
       loadUrlWords(type);
   }
   
   //画出折线图
   function drawNetChart(xdata,totaldata){
     var myChart = echarts.init(document.getElementById('netCharts')); 
     option = {
                title : {
                    
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['<fmt:message key="tiles.views.institution.net.index.count"/>']
                },
                toolbox: {
                    show : true,
                    feature : {
                        magicType : {show: true, type: ['line','bar']},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                grid:{borderWidth: 1},
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
                        name:'访问量',
                        type:'line',
                        data:totaldata
                    }
                ]
            };
       // 为echarts对象加载数据 
       myChart.setOption(option); 
    }
   
   // 加载图表
   function loadChart(){
       $.ajax({
           "dataType" : 'json',
           "type": "GET",
           "url":ctx+"/institution/net/getCountByWebsite?now="+ new Date().getTime(),
           "data":{},
           "success": function(data){
               if(data!=null){
                   $("#netCharts").attr("style","height:400px;width:100%;");
                    var xdata = data.website;
                   var totaldata = data.accessAmount;
                   drawNetChart(xdata,totaldata);
               }
           }
       });
   }
   
   // 查询网址记录
   function searchUrl(){
	   var type = $("#type").val();
       loadUrlWords(type);
       $('#urlTable').dataTable().fnDraw();
   }
   
   // 清除网址记录
   function cleanUrl(){
       var type = $("#type").val();
	   $("#searchName").val("");
       loadUrlWords(type);
       $('#urlTable').dataTable().fnDraw();
   }
   
   //查询上网记录
   function searchNet(){
       loadNetWords();
       $('#systemWordsTable').dataTable().fnDraw();
   }
   
   //清除上网记录
   function cleanNet(){
       $("#searchWebsiteType option:first").prop("selected", 'selected');
       loadNetWords();
       $('#systemWordsTable').dataTable().fnDraw();
   }
   
   $(function() {
       loadChart();  
       loadNetWords(); 
   });
</script>
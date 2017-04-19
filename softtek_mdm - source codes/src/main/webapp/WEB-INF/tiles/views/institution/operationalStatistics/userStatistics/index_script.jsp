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
<spring:url value="/institution/systemStatistics/user/getAllUserStatistics" var="pagesUrl"/>
<spring:url value="/institution/systemStatistics/user/getAllTotalUserStatistics" var="totalUserPagesUrl"/>
<spring:url value="/institution/systemStatistics/user/getAllActiveUserStatistics" var="activeUserPagesUrl"/>
<spring:url value="/institution/systemStatistics/user/getAllInActiveUserStatistics" var="inActiveUserPagesUrl"/>
<spring:url value="/institution/systemStatistics/user/getAllDeleteUserStatistics" var="deleteUserPagesUrl"/>
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
		              {data : "createDate"},
		              {data : "totalUser"}, 
		              {data : "activeUser"},
		              {data : "inActiveUser"},
		              {data : "deleteUser"}
		          ],
		 "columnDefs":[
						{
							"targets" : [0],
							"render" : function(data, type,full, meta) {
								if(full.createDate == null || full.createDate == ''){
									return '';
								}else{
									return new Date(parseInt(full.createDate)).Format("yyyy-MM-dd");
								}
							}
						}/*,
						{
							"targets" : [1],
							"render" : function(data, type,full, meta) {
								if(full.totalUser == null || full.totalUser == ''){
									return '0';
								}else{
									return '<a href="javascript:void(0);" class="text-primary"  onclick="viewTotalUser('+full.createDate+');">'
									+ full.totalUser
									+ '</a>';
								}
							}
						},
						{
							"targets" : [2],
							"render" : function(data, type,full, meta) {
								if(full.activeUser == null || full.activeUser == ''){
									return '0';
								}else{
									return '<a href="javascript:void(0);" class="text-primary"  onclick="viewActiveUser('+full.createDate+');">'
									+ full.activeUser
									+ '</a>';
								}
								
							}
						},
						{
							"targets" : [3],
							"render" : function(data, type,full, meta) {
								if(full.inActiveUser == null){
									return '0';
								}else{
									return '<a href="javascript:void(0);" class="text-primary"  onclick="viewInActiveUser('+full.createDate+');">'
									+ full.inActiveUser
									+ '</a>';
								}
							}
						},
						{
							"targets" : [4],
							"render" : function(data, type,full, meta) {
								if(full.deleteUser == null){
									return '0';
								}else{
									return '<a href="javascript:void(0);" class="text-primary"  onclick="viewDeleteUser('+full.createDate+');">'
									+ full.deleteUser
									+ '</a>';
								}
							}
						}*/
					  ] 
		});
	
}


function LoadTotalUserLists(){
	var csrf="${_csrf.token}"; 
	var user_name = $('#totalUser_user_name').val();
	var real_name = $('#totalUser_real_name').val();
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
			"data" : {"user_name":user_name,"real_name":real_name,"search_date":search_date}
		},
		"columns":[
		              {data : "realname"},
		              {data : "username"}, 
		              {data : "departName"},
		              {data : "createTime"}
		          ],
		 "columnDefs":[
						{
							"targets" : [3],
							"render" : function(data, type,full, meta) {
								if(full.createTime == null || full.createTime == ''){
									return '';
								}else{
									return new Date(parseInt(full.createTime)).Format("yyyy-MM-dd hh:mm:ss");
								}
							}
						}
					  ]
		});
	
}

function LoadActiveUserLists(){
	var csrf="${_csrf.token}"; 
	var user_name = $('#activeUser_user_name').val();
	var real_name = $('#activeUser_real_name').val();
	var search_date = $('#activeUserSearchDate').val();
	$('#activeUserList').DataTable({
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
			"url" : "${activeUserPagesUrl}",
			"data" : {"user_name":user_name,"real_name":real_name,"search_date":search_date}
		},
		"columns":[
		              {data : "realname"},
		              {data : "username"}, 
		              {data : "departName"},
		              {data : "createTime"}
		          ],
		 "columnDefs":[
						{
							"targets" : [3],
							"render" : function(data, type,full, meta) {
								if(full.createTime == null || full.createTime == ''){
									return '';
								}else{
									return new Date(parseInt(full.createTime)).Format("yyyy-MM-dd hh:mm:ss");
								}
							}
						}
					  ]
		});
	
}

function LoadInActiveUserLists(){
	var csrf="${_csrf.token}"; 
	var user_name = $('#inActiveUser_user_name').val();
	var real_name = $('#inActiveUser_real_name').val();
	var search_date = $('#inActiveUserSearchDate').val();
	$('#inActiveUserList').DataTable({
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
			"url" : "${inActiveUserPagesUrl}",
			"data" : {"user_name":user_name,"real_name":real_name,"search_date":search_date}
		},
		"columns":[
		              {data : "realname"},
		              {data : "username"}, 
		              {data : "departName"},
		              {data : "createTime"}
		          ],
		 "columnDefs":[
						{
							"targets" : [3],
							"render" : function(data, type,full, meta) {
								if(full.createTime == null || full.createTime == ''){
									return '';
								}else{
									return new Date(parseInt(full.createTime)).Format("yyyy-MM-dd hh:mm:ss");
								}
							}
						}
					  ]
		});
	
}

function LoadDeleteUserLists(){
	var csrf="${_csrf.token}"; 
	var user_name = $('#deleteUser_user_name').val();
	var real_name = $('#deleteUser_real_name').val();
	var search_date = $('#deleteUserSearchDate').val();
	$('#deleteUserList').DataTable({
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
			"url" : "${deleteUserPagesUrl}",
			"data" : {"user_name":user_name,"real_name":real_name,"search_date":search_date}
		},
		"columns":[
		              {data : "realname"},
		              {data : "username"}, 
		              {data : "departName"},
		              {data : "createTime"}
		          ],
		 "columnDefs":[
						{
							"targets" : [3],
							"render" : function(data, type,full, meta) {
								if(full.createTime == null || full.createTime == ''){
									return '';
								}else{
									return new Date(parseInt(full.createTime)).Format("yyyy-MM-dd hh:mm:ss");
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
	$("#totalUser_real_name").val('');
	$("#totalUser_user_name").val('');
	$('#viewTotalUserModel').modal('show');
	LoadTotalUserLists();
}
//查看所有的激活用户信息
function viewActiveUser(createDate){
	var create_time = new Date(parseInt(createDate)).Format("yyyy-MM-dd");
	$('#activeUserSearchDate').val(create_time);
	$("#activeUser_real_name").val('');
	$("#activeUser_user_name").val('');
	$('#viewActiveUserModel').modal('show');
	LoadActiveUserLists();
}
//查看所有的未激活用户信息
function viewInActiveUser(createDate){
	var create_time = new Date(parseInt(createDate)).Format("yyyy-MM-dd");
	$('#inActiveUserSearchDate').val(create_time);
	$("#inActiveUser_real_name").val('');
	$("#inActiveUser_user_name").val('');
	$('#viewInActiveUserModel').modal('show');
	LoadInActiveUserLists();
}
//查看所有的删除用户信息
function viewDeleteUser(createDate){
	var create_time = new Date(parseInt(createDate)).Format("yyyy-MM-dd");
	$('#deleteUserSearchDate').val(create_time);
	$("#deleteUser_real_name").val('');
	$("#deleteUser_user_name").val('');
	$('#viewDeleteUserModel').modal('show');
	LoadDeleteUserLists();
}

function exportTotalUser(){
	//其实这个导出是带有条件的导出，不能使用ajax的导出
	var form = $("<form></form>");
    form.attr('action',ctx + "/institution/systemStatistics/user/total/export");
    var input1 = $('<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>');
    form.append(input1);
    var real_name = $("#totalUser_real_name").val();
	var user_name = $("#totalUser_user_name").val();
	var create_time = $('#totalUserSearchDate').val();
	//查询条件
    var input2 = $('<input type="hidden" name="real_name" value="'+real_name+'"/>');
    var input3 = $('<input type="hidden" name="user_name" value="'+user_name+'"/>');
    var input4 = $('<input type="hidden" name="create_time" value="'+create_time+'"/>');
    form.append(input2);
    form.append(input3);
    form.append(input4);
    form.attr('method','post');
    //form.attr('enctype','multipart/form-data');
    form.submit();
	
}

function searchTotalUser(){
	var real_name = $('#totalUser_real_name').val();
	var user_name = $('#totalUser_user_name').val();
	LoadTotalUserLists();
	$('#totalUserList').dataTable().fnDraw();
}

function clearTotalUser(){
	$('#totalUser_real_name').val('');
	$('#totalUser_user_name').val('');
	LoadTotalUserLists();
	$('#totalUserList').dataTable().fnDraw();
}

function exportActiveUser(){
	//其实这个导出是带有条件的导出，不能使用ajax的导出
	var form = $("<form></form>");
    form.attr('action',ctx + "/institution/systemStatistics/user/active/export");
    var input1 = $('<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>');
    form.append(input1);
    var real_name = $("#activeUser_real_name").val();
	var user_name = $("#activeUser_user_name").val();
	var create_time = $('#activeUserSearchDate').val();
	//查询条件
    var input2 = $('<input type="hidden" name="real_name" value="'+real_name+'"/>');
    var input3 = $('<input type="hidden" name="user_name" value="'+user_name+'"/>');
    var input4 = $('<input type="hidden" name="create_time" value="'+create_time+'"/>');
    form.append(input2);
    form.append(input3);
    form.append(input4);
    form.attr('method','post');
    //form.attr('enctype','multipart/form-data');
    form.submit();
	
}

function searchActiveUser(){
	var real_name = $('#activeUser_real_name').val();
	var user_name = $('#activeUser_user_name').val();
	LoadActiveUserLists();
	$('#activeUserList').dataTable().fnDraw();
	
}

function clearActiveUser(){
	$('#activeUser_real_name').val('');
	$('#activeUser_user_name').val('');
	LoadActiveUserLists();
	$('#activeUserList').dataTable().fnDraw();
	
}

function exportInActiveUser(){
	//其实这个导出是带有条件的导出，不能使用ajax的导出
	var form = $("<form></form>");
    form.attr('action',ctx + "/institution/systemStatistics/user/inActive/export");
    var input1 = $('<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>');
    form.append(input1);
    var real_name = $("#inActiveUser_real_name").val();
	var user_name = $("#inActiveUser_user_name").val();
	var create_time = $('#inActiveUserSearchDate').val();
	//查询条件
    var input2 = $('<input type="hidden" name="real_name" value="'+real_name+'"/>');
    var input3 = $('<input type="hidden" name="user_name" value="'+user_name+'"/>');
    var input4 = $('<input type="hidden" name="create_time" value="'+create_time+'"/>');
    form.append(input2);
    form.append(input3);
    form.append(input4);
    form.attr('method','post');
    //form.attr('enctype','multipart/form-data');
    form.submit();
	
}

function searchInActiveUser(){
	var real_name = $('#inActiveUser_real_name').val();
	var user_name = $('#inActiveUser_user_name').val();
	LoadInActiveUserLists();
	$('#inActiveUserList').dataTable().fnDraw();
	
}

function clearInActiveUser(){
	$('#inActiveUser_real_name').val('');
	$('#inActiveUser_user_name').val('');
	LoadInActiveUserLists();
	$('#inActiveUserList').dataTable().fnDraw();
	
}

function exportDeleteUser(){
	//其实这个导出是带有条件的导出，不能使用ajax的导出
	var form = $("<form></form>");
    form.attr('action',ctx + "/institution/systemStatistics/user/delete/export");
    var input1 = $('<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>');
    form.append(input1);
    var real_name = $("#deleteUser_real_name").val();
	var user_name = $("#deleteUser_user_name").val();
	var create_time = $('#deleteUserSearchDate').val();
	//查询条件
    var input2 = $('<input type="hidden" name="real_name" value="'+real_name+'"/>');
    var input3 = $('<input type="hidden" name="user_name" value="'+user_name+'"/>');
    var input4 = $('<input type="hidden" name="create_time" value="'+create_time+'"/>');
    form.append(input2);
    form.append(input3);
    form.append(input4);
    form.attr('method','post');
    //form.attr('enctype','multipart/form-data');
    form.submit();
	
}

function searchDeleteUser(){
	var real_name = $('#deleteUser_real_name').val();
	var user_name = $('#deleteUser_user_name').val();
	LoadDeleteUserLists();
	$('#deleteUserList').dataTable().fnDraw();
	
}

function clearDeleteUser(){
	$('#deleteUser_real_name').val('');
	$('#deleteUser_user_name').val('');
	LoadDeleteUserLists();
	$('#deleteUserList').dataTable().fnDraw();
	
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
	}
	}else{
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
	/* //其实这个导出是带有条件的导出，不能使用ajax的导出
	var form = $("<form></form>");
    form.attr('action',ctx + "/institution/systemStatistics/user/export");
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
    form.attr('method','post'); */
    //form.attr('enctype','multipart/form-data');
    //form.submit();
  	$("#exportForm").submit();
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
			        data:['<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.totalUsers"/>','<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.activeUsers"/>','<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.newUsers"/>','<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.deleteUsers"/>']
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
			            name:'<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.totalUsers"/>',
			            type:'line',
			            data:totaldata
			        },
			        {
			            name:'<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.activeUsers"/>',
			            type:'line',
			            data:activedata
			        },
			        {
			            name:'<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.newUsers"/>',
			            type:'line',
			            data:inactivedata
			        },
			        {
			            name:'<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.deleteUsers"/>',
			            type:'line',
			            data:deletedata
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
                 data:['<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.bar"/>'],
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
                     data : ['<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.totalUsers"/>','<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.activeUsers"/>','<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.newUsers"/>','<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.deleteUsers"/>']
                 }
             ],
             yAxis : [
                 {
                     type : 'value'
                 }
             ],
             series : [
                 {
                     "name":'<fmt:message key="tiles.views.institution.operationalStatistics.userStatistics.index.bar"/>',
                     "type":"bar",
                     "data":[totaldata[0], activedata[0], inactivedata[0], deletedata[0]]
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
			"url": ctx + "/institution/systemStatistics/user/chart",
			success: function(data) {
				length = data.length;
				$.each(data,function(index,obj){
					xdata.push(new Date(parseInt(obj.createDate)).Format("yyyy-MM-dd"));
					totaldata.push(obj.totalUser);
					activedata.push(obj.activeUser);
					inactivedata.push(obj.inActiveUser);
					deletedata.push(obj.deleteUser);
				});
			}
		});
	if(length == 1){
		drawBarChart(xdata,totaldata,activedata,inactivedata,deletedata);
	}else{
		drawLineChart(xdata,totaldata,activedata,inactivedata,deletedata);
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
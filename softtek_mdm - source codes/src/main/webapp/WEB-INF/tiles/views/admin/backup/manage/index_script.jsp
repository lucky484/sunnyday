<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/resources/js/jquery.tmpl.js" var="tmplJs" />
<spring:url value="/resources/js/jquery.cookie.js" var="cookieJs" />
<script src="${tmplJs}"></script>
<script src="${cookieJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js" var="dataTableJs" />
<script src="${dataTableJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js" var="dataTableBootstrapJs" />
<script src="${dataTableBootstrapJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />

<script type="text/javascript">
	//=============================== datatables国际化
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
	//加载数据备份列表
	function LoadBackUpList(){
		//var name = $('#name').val();
		var csrf="${_csrf.token}"; 
		$('#backupList').DataTable({
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
				"data" : {"name":name,"_csrf":csrf}
			},
			"columns":[
			              {data : "id"}, 
			              {data : "msgTheme"}, 
			              {data : "createUser"}, 
			              {data : "createTime"},
			              {data : ""},
			              {data : ""},
			              {data : ""}
			          ],
			"columnDefs":[
							{
							    "targets": [0],
							    "visible": false
							},
							 {
								"targets" : [3],
								"render" : function(
										data, type,
										full, meta) {
									if(full.createTime == null || full.createTime == ''){
										return '';
									}else{
										return new Date(parseInt(full.createTime)).Format("yyyy-MM-dd hh:mm:ss");
									}
								}
							},
							{
								"targets" : [4],
								"render" : function(data, type, row) {
									return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
									+ '<i class="i  i-settings"></i>'
									+ '</a>'
									+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-60px;">'
									+ '<li><a href="javascript:void(0);" onclick="viewPicAndTxtMsg('+row.id+')">'
									+ '<i class="glyphicon glyphicon-eye-open"></i>&nbsp;查看</a></li>'
									+ '<li><a href="javascript:void(0);" onclick="updatePicAndTxtMsg('+row.id+')">'
									+ '<i class="fa fa-pencil"></i>&nbsp;修改</a></li>'
									+ '<li><a href="javascript:void(0);" onclick="deletePicAndTxtMsg('+row.id+')">'
									+ '<i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;删除</span></a></li>'
									+ '</ul>';
								}
							}   
							                          
					  ]	
			});
	}
	
	
	//查找
	function searchBackupLists(){
		
	}
	//清空
	function cleanBackupLists(){
		
	}
	
	$(function(){
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
		
		LoadBackUpList();
	});
	

</script>
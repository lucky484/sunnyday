<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:url value="/customer/appListPages" var="pagesUrl" />
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
	function loadAppListDT(id){
		//自动加载datatable
			$('#appList').DataTable({
				"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
				"searching" : false,
				"stateSave" : true,
				"ordering" : false,
				"bSort" : false,
				"pageLength" : 10,
				"pagingType" : "full_numbers",
				"serverSide" : true,
				"bDestroy" : true,
				"oLanguage":{
					"sUrl":languageUrl
				},
				"ajax" : {
					"url" : "${pagesUrl}?id="+id,
					"type" : "get",
					"dataSrc" : "data",
				},
				"columns" : [
				              {data : "name"}, 
				              {data : "appid"}, 
				              {data : "app_version"},
				              {data : "app_status"}
				            ]	
			});
		}
</script>

<jsp:include page="../common/common.jsp"></jsp:include>
<jsp:include page="../common/common_style.jsp"></jsp:include>

<script type="text/javascript">
	$(function(){
		//从后台去加载手机端中所有应用的列表
		var id = '';
		$('#leftSelect').change(function(){
			id = $(this).children('option:selected').val();  
			loadAppListDT(id);
		});
		if(id == ''){
			id = $('#leftSelect option:first').val();
			loadAppListDT(id);
		}
	});
</script>
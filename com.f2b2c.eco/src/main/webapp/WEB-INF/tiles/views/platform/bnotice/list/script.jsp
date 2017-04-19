<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/bnotice/page" var="bnoticePageUrl" />
<spring:url value="/farm/bnotice/new" var="bnoticeNewUrl" />
<spring:url value="/farm/bnotice/del" var="delBNoticeUrl" />
<spring:url value="/api/bnotice/detail" var="BNoticeDetailUrl" />
<spring:url
	value="/resources/platform/js/plugins/easyUI/jquery.easyui.min.js"
	var="easyUIUrl" />
<script type="text/javascript" src="${easyUIUrl}"></script>
<script>
	var params = {};
	var dataTable;
	
	function getBNoticeList(){
	$("#bnoticeTable").dataTable({
			"dom":"<'m-r m-t-lg pull-right'f>t<'row col-sm-12'<'col-sm-4'l>r<'col-sm-4'i><'pull-right'p>>",
		    "autoWidth": false,
		    "searching" : false,
			"stateSave" : true,
			"ordering" : false,
			"bSort":false,
			"pageLength" : 10,
			"pagingType" : "full_numbers",
			"serverSide" : true,
			"bDestroy" : true,
			"ajax" : {
				"url" : "${bnoticePageUrl}",
				"type" : "POST",
				"dataSrc" : "content",
				"dataType":'json',
			},
			"columns" : [{
				data : "title",
				data : "title",
				data : "id"
				
			}  ],
			"columnDefs" : [
					{
							targets : [0],
							"render" : function(data, type,full, meta) {
								return full.title;
							}
							},{
							targets : [1],
							"render" : function(data, type,full, meta) {
								return formatCurrentTimeMillis(full.createTime);
								}
						},{
							targets : [2],
							"render" : function(data, type,full, meta) {
								return '<a href="${BNoticeDetailUrl}?id='+full.id +'">&nbsp;查看</a> <a onclick="delBNotice('+full.id +')">&nbsp;删除</a>';
							}
			},
			]
	});
	}

	function bNoticeDetail(id) {
		$.ajax({
			"dataType" : 'json',
			"type" : "GET",
			"async" : false,
			"url" : "${BNoticeDetailUrl}",
			"data" : {
				"id" : id
			}
		});
	}
	
	function delBNotice(id) {
		$.ajax({
			"dataType" : 'json',
			"type" : "POST",
			"async" : false,
			"url" : "${delBNoticeUrl}",
			"data" : {
				"id" : id
			},
			"success" : function(data) {
				getBNoticeList();
			}
		});
	}
	//jquery初始化的时候加载商品列表
	$(function() {
		getBNoticeList();
	});

	function formatCurrentTimeMillis(dateTime) {
		return new Date(dateTime).Format("yyyy-MM-dd hh:mm:ss");
	}
	
	function newBNoticePage(){
		window.location.href="${bnoticeNewUrl}";
	}

</script>
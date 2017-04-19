<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spring:url
	value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js"
	var="dataTableJs" />
<script src="${dataTableJs}"></script>
<spring:url value="/institution/netbehavior/blackwitelist/checkBwListName" var="ckBwListNameUrl" />
<spring:url
	value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js"
	var="dataTableBootstrapJs" />
<script src="${dataTableBootstrapJs}"></script>
<spring:url value="/resources/js/jquery.tmpl.js" var="jqueryTmplJs" />
<script src="${jqueryTmplJs}"></script>
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
	var csrf="${_csrf.token}";
	var blackWhiteRemarkValidator = $('#blackWhiteRemark').parsley();
	var blackWhiteListNameValidator = $('#blackWhiteListName').parsley();
	var netUrlNameValidator = $('#netUrlName').parsley();
	var netUrlAddressValidator = $('#netUrlAddress').parsley();
	var netNameWithUrlArr = [];
/* 	var netNameAndUrl; */
	
	$(function() {
		var bwListId = $("#blackWhiteListId").val();
		//检查用户名称是否重复
		$(".add-user-name").parsley().addAsyncValidator(
			'checkNameIsExist',function(xhr){
				return !(xhr.responseText.indexOf('true') >= 0); 
			},ctx + "/institution/netbehavior/blackwitelist/checkBwListName?now=" + new Date().getTime(),
		{ "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {"bwListId": bwListId,"_csrf":csrf} } );
		
		loadDateTable();
	});
	
	// 新增黑白名单
	function addNewBwList() {
		originalname = '';
		/* $("#netListValue").css("display","none"); */
		$("a[title=updateHref]").hide();
		$(".parsley-errors-list filled").remove()
		$("a[title=addHref]").show();
		$('a[updateHref]').hide();
		$("#blackWhiteListId").val("");
		$("#netUrlStr").val("");
		$("#blackWhiteListName").val("");
		$("#blackWhiteRemark").val("");
		$("#netUrlName").val("");
		$("#netUrlNames").val("");
		$("#netUrlAddress").val("");
		$("#blackWhiteListType").val("0");
		$("#bWListsDiv").html('');
		$("#blackWhiteListType").attr("disabled", false);
		netNameWithUrlArr = [];
		blackWhiteRemarkValidator.reset();
		blackWhiteListNameValidator.reset();
		netUrlNameValidator.reset();
		netUrlAddressValidator.reset();
		$("#modelTitle").html("<fmt:message key='tiles.views.netbehaviormanager.blackwhitelist.add.bwlist'/>");
		$("#addModal").modal("show");
	}

	function loadDateTable() {
		var blackWhiteListName = $("#name").val();
		var blackWhiteListType = $("#type").val();

		$('#bWListTb')
				.DataTable(
						{
							"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
							"searching" : false,
							"stateSave" : true,
							"ordering" : false,
							"bSort" : false,
							"serverSide" : true,
							"pageLength" : 10,
							"pagingType" : "full_numbers",
							"bDestroy" : true,
							"oLanguage" : {
								"sUrl":languageUrl
							},
							"ajax" : {
								"dataType" : 'json',
								"type" : "POST",
								"url" : ctx
										+ "/institution/netbehavior/blackwitelist/pages?now="
										+ new Date().getTime(),
								"data" : {
									"blackWhiteListName" : blackWhiteListName,
									"blackWhiteListType" : blackWhiteListType,
									"_csrf" : csrf
								}
							},
							"columns" : [ {
								data : "type"
							}, {
								data : "blackWhiteName"
							}, {
								data : "remark"
							}, {
								data : "netCount"
							}, {
								data : "createUserName"
							}, {
								data : "createDate"
							}, {
								data : ""
							} ],
							columnDefs : [
										{
											targets : [ 0 ],
											render : function(data, type, full,
													meta) {
												if (full.type == "1"
														|| full.type == 1) {
													bWName = "<fmt:message key='tiles.views.netbehaviormanager.blackwhitelist.white'/>";
												} else {
													bWName = "<fmt:message key='tiles.views.netbehaviormanager.blackwhitelist.black'/>";
												}

												return bWName;
											}
										},
									{
										className:"datatb-max-width-blackwitelist",
										targets : [ 1 ],
										render : function(data, type, full,
												meta) {
											return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+full.blackWhiteName+'"><span class="text-ellipsis"><a href="javascript:void(0)" class="text-primary" onclick="viewMemberInfo('
													+ "'"
													+ full.id
													+ "'"
													+ ')">'
													+ full.blackWhiteName
													+ '</a></span><div>';
										}
									},
									{
										className:"datatb-max-width-blackwitelist",
										targets : [ 2 ],
										render : function(data, type, full,
												meta) {
											if(full.remark != null){
												return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+full.remark+'"><span class="text-ellipsis">'
													+ full.remark
													+ '</span><div>';
											}else{
												return "";
											}
										}
									},
									{
										targets : [ 5 ],
										render : 
											function(data, type, full, meta) 
											{
											    var formatstyle;
												var date = full.createDate;
												if(date == null || date == "" || date == undefined)
												{
													formatstyle = '—';
												}
												else
												{
													formatstyle = formatCurrentTimeMillis(date);
												}
												
												return formatstyle;
											}
									}, 
/* 									{
										targets : [ 5 ],
										render : 
											function(data, type, full, meta) 
											{
											 	var formatstyle;
												var date = full.updateDate;
												if(date == null || date == "" || date == undefined)
												{
													formatstyle = '—';
												}
												else
												{
													formatstyle = formatCurrentTimeMillis(date);
												}
												
												return formatstyle;
											}
									}, */
									{
										targets : [ 6 ],
										render : function(data, type, full,
												meta) {
											var retunlist;
											if("${softtek_manager.auth}"=="0"&&"${softtek_manager.user}"!=""){
												retunlist = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
													+ '<i class="i  i-settings"></i>'
													+ '</a>'
													+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-70px;">'
													+ '<li><a href="javascript:void(0)" onclick="viewMemberInfo('
													+ "'"
													+ full.id
													+ "'"
													+ ')"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="tiles.views.institution.message.table.operation.view"/></a></li></ul>';
											}else{
											var isenable=0;
											if("${softtek_manager.user}"!=""&&"${softtek_manager.id}"!=full.createUserId){
												isenable=1;
											}
											retunlist = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
													+ '<i class="i  i-settings"></i>'
													+ '</a>'
													+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-70px;">'
													+ '<li><a href="javascript:void(0)" onclick="viewMemberInfo('
													+ "'"
													+ full.id
													+ "'"
													+ ')"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="tiles.views.institution.message.table.operation.view"/></a></li>'
													+ ' <li><a href="javascript:void(0);" onclick="modifyBwList('+ full.id
													+ ','+isenable+')"><i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.views.institution.message.table.operation.update"/></a></li>'
													+ '<li><a href="javascript:void(0);" onclick="deleteNameList('
													+ "'"
													+ full.id
													+ "'"
													+ ')"><i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;<fmt:message key="tiles.views.institution.message.table.operation.delete"/></span></a></li>'
													+ '</ul>'}

											return retunlist;
										}
									} ]

						});
	}

	function viewMemberInfo(id) {
		var bwObj;
		$.ajax({
			"dataType" : 'json',
			"type" : "GET",
			"async" : false,
			"url" : ctx
					+ "/institution/netbehavior/blackwitelist/queryBwList?now="
					+ new Date().getTime(),
			"data" : {
				"blackWhiteListId" : id,
				"_csrf":csrf
			},
			"success" : function(data) {
				if (data.type == "success") {
					bwObj = data.bwList;
					getViewhModelView(bwObj);
					$("#viewModal").modal("show");
					var tmpstr = data.message;
				}
				else
				{
					$(".notify").notify({
						type : data.type,
						message : {
							html : false,
							text : data.message
						}
					}).show();
				}
			}
		});

	}

	function modifyBwList(id, isenable) {
		if(isenable==1){
			Modal.confirm().on(function(e){
				if(e==true){
					_modify(id);
				}
			});
		}else{
			_modify(id);
		}
	}
	
	function _modify(id){
		$("a[title=updateHref]").show();
		$("a[title=addHref]").hide();
		$("#modelTitle").html("<fmt:message key='tiles.views.netbehaviormanager.blackwhitelist.update.bwlist'/>");
		$("#blackWhiteListType").attr("disabled", true);
		
		blackWhiteRemarkValidator.reset();
		blackWhiteListNameValidator.reset();
		netUrlNameValidator.reset();
		netUrlAddressValidator.reset();

		var bwObj;
		$.ajax({
			"dataType" : 'json',
			"type" : "GET",
			"async" : false,
			"url" : ctx
					+ "/institution/netbehavior/blackwitelist/queryBwList?now="
					+ new Date().getTime(),
			"data" : {
				"blackWhiteListId" : id,
				"_csrf":csrf
			},
			"success" : function(data) {
				if (data.type == "success") {
					bwObj = data.bwList;
					$("#netUrlNames").val('');
					$("#netUrlStr").val('');
					getModifyModelView(bwObj);
					$("#netUrlName").val("");
					$("#netUrlAddress").val("");
					
					var bwListId = $("#blackWhiteListId").val();
					//检查用户名称是否重复
					$(".add-user-name").parsley().addAsyncValidator(
						'checkNameIsExist',function(xhr){
							return !(xhr.responseText.indexOf('true') >= 0); 
						},ctx + "/institution/netbehavior/blackwitelist/checkBwListName?now=" + new Date().getTime(),
					{ "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {"bwListId": bwListId} } );
					
					$("#addModal").modal("show");
					var tmpstr = data.message;
				}
				else
				{
					$(".notify").notify({
						type : data.type,
						message : {
							html : false,
							text : data.message
						}
					}).show();
				}
			}
		});
	}

	function getViewhModelView(bwObj) {
		if (null != bwObj && undefined != bwObj) {
			$("#viewBlackWhiteListId").val(bwObj.id);
			$("#viewBlackWhiteListName").val(bwObj.blackWhiteName);
			$("#viewBlackWhiteListId").val(bwObj.type);
			$("#viewBlackWhiteRemark").val(bwObj.remark);
			
			netNameWithUrlArr = [];
			var urlObjs = bwObj.bWUrlList;
			if (null != urlObjs && urlObjs.length > 0) 
			{
				for (var i = 0; i < urlObjs.length; i++) 
				{
					var urlname = urlObjs[i].urlName;
					var urlpath = urlObjs[i].url;
					netNameWithUrlArr.push({urlName : urlname, url : urlpath})
				}
				displayViewPart(netNameWithUrlArr);
			}
		}
	}

	function getModifyModelView(bwObj) {
		if (null != bwObj && undefined != bwObj) {
			$("#blackWhiteListId").val(bwObj.id);
			$("#blackWhiteListName").val(bwObj.blackWhiteName);
			$("#blackWhiteListType").val(bwObj.type);
			$("#blackWhiteRemark").val(bwObj.remark);
			
			netNameWithUrlArr = [];
			var urlObjs = bwObj.bWUrlList;
			if (null != urlObjs && urlObjs.length > 0) 
			{
				for (var i = 0; i < urlObjs.length; i++) 
				{
					var urlname = urlObjs[i].urlName;
					var urlpath = urlObjs[i].url;
					netNameWithUrlArr.push({urlName : urlname, url : urlpath})
				}
				displayApplication(netNameWithUrlArr);
			}
		}
	}

	//展示查看应用
	function displayViewPart(netNameWithUrlArr) {
		var html = '';
		if (netNameWithUrlArr != null && netNameWithUrlArr.length > 0) {
			for (var i=0; i<netNameWithUrlArr.length; i++)
			{
				var obj = netNameWithUrlArr[i];
				html += '<div class="col-lg-12 paddingWidth appBody">';
				html += '<div class="col-lg-4 paddingWidth">';
				html += obj.urlName;
				html += '</div>';
				html += '<div class="col-lg-4 paddingWidth">';
				
				var urlVar = obj.url;
				var subStr;
				if (urlVar.length > 25)
				{
					subStr = urlVar.substring(0,25);
					html +='<div title="'+ urlVar + '">' + subStr + '...</div>';
				}
				else
				{
					html +='<div>' + urlVar + '</div>';
				}
				html += '</div>';
				html += '<div class="col-lg-1">';
				html += '</div>';
				html += '</div>';
			}
		}
		$("#bWListsDivView").html(html);
	}

	//查询名单列表
	function searchBwLists() {
		$('#bWListTb').dataTable().fnDestroy();
		loadDateTable();
		$('#bWListTb').dataTable().fnDraw();
	}

	//清除名单列表
	function cleanBwLists() {
		$('#bWListTb').dataTable().fnDestroy();
		$("#name").val("");
		$("#type").val("");
		$(".Js_curVal").find("input").val('<fmt:message key="tiles.views.netbehaviormanager.blackwhitelist.kind.all.condition"/>');
		loadDateTable();
		$('#bWListTb').dataTable().fnDraw();
	}

	// 名单删除提示功能
	function deleteNameList(id) {
		$.ajax({
			"dataType": 'json',
			"data": {"id":id,"_csrf":csrf},
			"type": "POST",
			"url": ctx + "/institution/netbehavior/blackwitelist/isOrginalCreateUser",
			success: function(data) {
				$("#delId").val(id);
				if (data.isCreateUser)
				{
					$("#delNameListModal").modal();
				}
				else
				{
					Modal.confirm().on(function(e){
						if(e==true){
							$("#delNameListModal").modal();
						}
					});
					
				}
			}
		});
	}

	//删除名单
	function delNameList() {
		var id = $("#delId").val();
		$.ajax({
			"dataType" : 'json',
			"type" : "GET",
			"url" : ctx
					+ "/institution/netbehavior/blackwitelist/delNameList?now="
					+ new Date().getTime(),
			"data" : {
				"blackWhiteListId" : id,
				"_csrf":csrf
			},
			"success" : function(data) {
				$(".notify").notify({
					type : data.type,
					message : {
						html : false,
						text : data.message
					}
				}).show();
				if (data.type == "success") {
					$("#delNameListModal").modal("hide");
					loadDateTable();
				}
				return false;
			}
		});
	}

	function refresh() {
		window.location.reload();  
	}	
	// 新增应用
	function addNewBwListUrl() {
		netUrlNameValidator.validate();
		netUrlAddressValidator.validate();
		if (!netUrlNameValidator.isValid() || !netUrlAddressValidator.isValid())
		{
			return false;
		}
		var netUrlName = $("#netUrlName").val();
		var netUrlAddress = $("#netUrlAddress").val();
		
		if (checkIsExist(netNameWithUrlArr, netUrlName))
		{
			$(".notify").notify({
				type : 'warning',
				message : {
					html : false,
					text : "<fmt:message key='tiles.views.netbehaviormanager.blackwhitelist.has.constains'/>"
				}
			}).show();
			return false;
		}
		
		netNameWithUrlArr.push({urlName : netUrlName, url : netUrlAddress});
		$("#netUrlName").val('');
		$("#netUrlAddress").val('');
		displayApplication(netNameWithUrlArr);
	}
	
	function checkUrl(str) {
		var RegUrl = new RegExp();
		RegUrl.compile("^[A-Za-z]+://[A-Za-z0-9-_]+\\.[A-Za-z0-9-_%&\?\/.=]+$");
		if (!RegUrl.test(str)) {
			return false;
		}
		return true;
	}

	// 删除应用
	function removeByName(id) {
		var netUrlStr = $("#netUrlStr").val();
		var netUrlNames = $("#netUrlNames").val();
		var newAppStr = "";
		if (netUrlStr != null && netUrlStr != "") {
			netUrlStr = netUrlStr.substring(0, netUrlStr.length - 1);
			var appArr = netUrlStr.split('#');
			for (var i = 0; i < appArr.length; i++) {
				var arr = appArr[i].split(",");
				if (arr[0] != id) {
					newAppStr += appArr[i] + "#";
				}
			}
			$("#netUrlNames").val(netUrlNames.replace(id + ",", ""));
			$("#netUrlStr").val(newAppStr);
			displayApplication(newAppStr);
		}
	}

	// 展示应用
	function displayApplication(netNameWithUrlArr) {
		var arr = netNameWithUrlArr;
		$("#bWListsDiv").html("");
		var length = arr.length;
		if (arr != null && length > 0) {
			for (var i=0; i< length; i++)
			{
			var html = '';
                var obj = netNameWithUrlArr[i];
				html = '<div class="col-lg-12 paddingWidth appBody">';
				 html += '<div class="col-lg-4 paddingWidth">';
				html += obj.urlName;
				html += '</div>';
				html += '<div class="col-lg-4 paddingWidth">';
				
				var urlVar = obj.url;
				var subStr;
				if (urlVar.length > 25)
				{
					subStr = urlVar.substring(0,25);
					html +='<div title="'+ urlVar + '">' + subStr + '...</div>';
				}
				else
				{
					html +='<div>' + urlVar + '</div>';
				}
				
				html += '</div>';
				html += '<div class="col-lg-1" onclick="removeBwUrlsByName('
						+ "'" + obj.urlName + "'" + ')">';
				html += '<i class="glyphicon glyphicon-remove limargin"></i>';
				html += '</div>'; 
				$("#bWListsDiv").append(html);
			}
		}
		
	}

	function checkValue()
	{
		blackWhiteRemarkValidator.validate();
		if(!blackWhiteRemarkValidator.isValid()){
			return false;	
		}
	}
	
	// 保存黑白名单
	function saveNetBehaviorBlackWhitelList() {
		
		blackWhiteListNameValidator.validate();
		blackWhiteRemarkValidator.validate();
		if(!blackWhiteListNameValidator.isValid() || !blackWhiteRemarkValidator.isValid()){
			return false;	
		}
		
/* 		var html = $("#bWListsDiv").html();
		if (html == null || html == '' || html == undefined)
		{
			$("#netListValue").css("display","");
			return false;
		} */
		
		var blackWhiteListId = $("#blackWhiteListId").val();
		var netUrlStr = $("#netUrlStr").val();
		var blackWhiteListType = $("#blackWhiteListType").val();
		var blackWhiteListName = $("#blackWhiteListName").val();
		var blackWhiteRemark = $("#blackWhiteRemark").val();
		$("#addModal #saveBtn a").eq(0).attr("disabled","true");
		var postData = {
			params : {}
		};
		postData.params.blackWhiteListId = blackWhiteListId;
		postData.params.netNameWithUrlArr = parseToJsonStr(netNameWithUrlArr);
		postData.params.blackWhiteListName = blackWhiteListName;
		postData.params.blackWhiteRemark = blackWhiteRemark;
		postData.params.blackWhiteListType = blackWhiteListType;
		postData._csrf=csrf;
		$.ajax({
			"dataType" : 'json',
			"data" : postData,
			"type" : "POST",
			"url" : ctx
					+ "/institution/netbehavior/blackwitelist/saveBwList?now="
					+ new Date().getTime(),
			"success" : function(data) {
				$("#addModal #saveBtn a").eq(0).removeAttr("disabled");
				$(".notify").notify({
					type : data.type,
					message : {
						html : false,
						text : data.message
					}
				}).show();
				if (data.type == "success") {
					$("#addModal").modal("hide");
					loadDateTable();
				}
				return false;
			}
		});
	}

	// 保存黑白名单
	function updateNetBehaviorBlackWhitelList() {
		blackWhiteRemarkValidator.validate();
		if(!blackWhiteRemarkValidator.isValid()){
			return false;	
		}
		/*		var html = $("#bWListsDiv").html();
 		if (html == null || html == '' || html == undefined)
		{
			$("#netListValue").css("display","");
			return false;
		} */
		$("#addModal #saveBtn a").eq(1).attr("disabled","true");
		var blackWhiteListId = $("#blackWhiteListId").val();
		var netUrlStr = $("#netUrlStr").val();
		var blackWhiteListType = $("#blackWhiteListType").val();
		var blackWhiteListName = $("#blackWhiteListName").val();
		var blackWhiteRemark = $("#blackWhiteRemark").val();
		var postData = {
			params : {}
		};
		postData.params.blackWhiteListId = blackWhiteListId;
		postData.params.netNameWithUrlArr = parseToJsonStr(netNameWithUrlArr);
		postData.params.blackWhiteListName = blackWhiteListName;
		postData.params.blackWhiteRemark = blackWhiteRemark;
		postData.params.blackWhiteListType = blackWhiteListType;
		postData._csrf=csrf;
		$.ajax({
			"dataType" : 'json',
			"data" : postData,
			"type" : "POST",
			"url" : ctx
					+ "/institution/netbehavior/blackwitelist/updateBwList?now="
					+ new Date().getTime(),
			"success" : function(data) {
				$("#addModal #saveBtn a").eq(1).removeAttr("disabled");
				$(".notify").notify({
					type : data.type,
					message : {
						html : false,
						text : data.message
					}
				}).show();
				if (data.type == "success") {
					$("#addModal").modal("hide");
					loadDateTable();
				}
				return false;
			}
		});
	}
	
	function parseToJsonStr(arr)
	{
		if (null!= arr && arr.length > 0)
		{
			return JSON.stringify(arr).toString();
		}
		
		return "";
		
	}
	
	function checkIsExist(arr, comparevar)
	{
		if (null != arr && arr.length > 0)
		{
			for (var i = 0; i < arr.length; i++)
			{
				var obj = arr[i];
				if (obj.urlName == comparevar)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	function removeBwUrlsByName(comparevar)
	{
		var arr = [];
		if (null != netNameWithUrlArr && netNameWithUrlArr.length > 0)
		{
			for (var i = 0; i < netNameWithUrlArr.length; i++)
			{
				var obj = netNameWithUrlArr[i];
				if (obj.urlName != comparevar)
				{
					arr.push(obj);
				}
			}
		}
		
		netNameWithUrlArr = arr;
		displayApplication(netNameWithUrlArr);
	}
	
	function formatCurrentTimeMillis (dateTime) {
		var date = new Date(dateTime);
		var y = date.getFullYear();
		var m = (date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
            	+ (date.getMonth() + 1);
		var d = date.getDate() < 10 ? "0" + date.getDate() : date
                .getDate();
		var h = date.getHours() < 10 ? "0" + date.getHours() : date
                .getHours();
		var mi = date.getMinutes() < 10 ? "0" + date.getMinutes() : date
                .getMinutes();
		
		var s = date.getSeconds() < 10 ? "0" + date.getSeconds() : date
                .getSeconds();

	    var datetime = y + "-" + m + "-" + d + " " + h + ":" + mi + ":" + s;
	    return datetime;
	}
		//查询模块的公共样式的设置和一些公共事件
	   //查询框显隐
			$(".search-toggle a").click(function(){
						if($(this).hasClass("hide1")){
							$(this).removeClass("hide1");
							$(this).removeAttr("style");
							$(this).text("");
							$(this).text('<fmt:message key="tiles.institution.comm.expand.search.tip" />');
							$(".search-mod").hide();
						}else{
							$(this).addClass("hide1");
							$(this).removeAttr("style");
							$(this).text("");
							$(this).text('<fmt:message key="tiles.institution.comm.close.search.tip" />');
							$(".search-mod").show();
						}
					});
		
			$(".Js_dropMod").hover(function(){
				var _this = $(this);
				if(_this.find(".select-list").find("li").length<=0) return;
				_this.find(".select-list").show().find("li").show();
			},function(){
				var _this = $(this);
				_this.find(".select-list").hide().find("li").removeClass("hover");
			});
			
			$(".Js_dropMod").find("a").on("click",function(e){
				if($(this).parents("ul").siblings(".Js_curVal").find("input:text").length<=0){
					$(this).parents("ul").siblings(".Js_curVal").text($(this).text()).css("color","#5A5A5A")
				}else{
					$(this).parents("ul").siblings(".Js_curVal").find("input:text").val($(this).text().replace("&lt;","<").replace("&gt;",">")).css("color","#5A5A5A")
				}
				$(this).parents("ul").siblings(".Js_hiddenVal").attr("normal",$(this).text())
				$(this).parents("ul").siblings(".Js_hiddenVal").val($(this).attr("rel"))
				$(this).parents("ul.select-list").hide()
			})
			
			
		//点击上传文件
		$(".a-upload").on("change","input[type='file']",function(){
		    var filePath=$(this).val();
		    if(filePath.indexOf("xls")!=-1 || filePath.indexOf("xlsx")!=-1){
		        $(".fileerrorTip").html("").hide();
		        var arr=filePath.split('\\');
		        var fileName=arr[arr.length-1];
		        $(".showFileName").val(fileName);
		    }else{
		        $(".showFileName").val("");
		        $(".fileerrorTip").html("<fmt:message key='tiles.views.user.index.script.user.excel.upload.tip'/>").show();
		        return false 
		    }
		});
			
	//导入导出操作 start
	function openExcelModal()
	{
		$("#excelModal").modal();
		$("#file").val("");
		$(".showFileName").val("");
		$(".fileerrorTip").html("");
	}
	//导出模板		
	function exportUserModel()
	{
		$("#getexportModel").submit();
	}	
	
	//导入用户
	function importUsers() {
		var filePath = $("#file").val();
		if (filePath.indexOf("xls") != -1 || filePath.indexOf("xlsx") != -1) {
			$(".fileerrorTip").html("").hide();
			var arr = filePath.split('\\');
			var fileName = arr[arr.length - 1];
			$(".showFileName").val(fileName);
			$('#rownumbers').html("");
			$('#excelModal').modal("hide");
			$('#loadingModal').modal(open);
			var csrf = "${_csrf.token}";
			var formData = new FormData();
			formData.append("files", $("#file")[0].files[0]);
			 $.ajax({
					 data : formData,
					type : "POST",
					url : ctx + "/institution/netbehavior/blackwitelist/import",
					cache : false,
					headers : {
						"${_csrf.headerName}" : "${_csrf.token}",
					},
					contentType : false,
					dataType : "json",
					processData : false,
					success : function(result) {
						$('#loadingModal').modal("hide");
						$('#successModal').modal(open);
						// 如果返回数据提示 excel是空的
						if(result.resultnull!=null){
							$('#rownumbers').removeClass('hidden');
							$("#successModal").find("h1").html('<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>');
							$('#rownumbers').html('<fmt:message key="tiles.views.user.index.script.user.excel.nullupload"/>');
							$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>'}}).show();
						}else if(result.success!=null){
							var list =result.list;
							for(var i = 0;i<list.length;i++){
								var url = list[i].url;
								var urlName = list[i].urlName;
								if(!checkIsExist(netNameWithUrlArr, urlName)){
									netNameWithUrlArr.push({urlName : urlName, url : url});
								}
								//excelAddNewBwListUrl(urlName,url);
							}
							displayApplication(netNameWithUrlArr);
							$("#successModal").find("h1").html('<fmt:message key="tiles.views.user.index.script.user.excel.import.success"/>');
							$('#rownumbers').addClass('hidden');
							$('#rownumbers').html(result.success);
							$(".notify").notify({type:"success",message: { html: false, text:'<fmt:message key="tiles.views.user.index.script.user.excel.import.success"/>'}}).show();
						}else{
							$('#rownumbers').removeClass('hidden');
							$("#successModal").find("h1").html('<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>');
							$('#rownumbers').html(result.messages);
							$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>'}}).show();
						}
					},
					error:function(data){
						$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>'}}).show();
					}
				});	 
		} else {
			$(".showFileName").val("");
			$(".fileerrorTip").html("<fmt:message key='tiles.views.user.index.script.user.excel.upload.tip'/>").show();
		}
	}
			
			
</script>
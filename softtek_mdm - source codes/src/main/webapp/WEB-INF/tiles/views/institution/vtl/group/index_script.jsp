<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spring:url value="/institution/vtl/exists" var="existsUrl" />
<spring:url value="/institution/vtl/existsedit" var="existseditUrl" />
<spring:url value="/institution/vtl/existseditgroup"
	var="existseditGroupUrl" />
<spring:url value="/institution/vtl/delete" var="deleteUrl" />
<spring:url value="/institution/vtl/deletecol" var="deleteColUrl" />
<spring:url value="/resources/js/jquery.tmpl.js" var="tmplJs" />
<spring:url value="/resources/js/jquery.cookie.js" var="cookieJs" />
<spring:url value="/institution/user/checkname" var="ckNameUrl" />
<script src="${cookieJs}"></script>
<script src="${tmplJs}"></script>
<script type="text/javascript">
	$(function() { 
		$("#v_name").parsley().addAsyncValidator('existsValidate',
				function(xhr) {
					return (xhr.responseText.indexOf('success') >= 0);
				}, "${existsUrl}", {
					"type" : "GET",
					"dataType" : "json",
					"contentType" : "application/json; charset=utf-8",
					"data" : {}
				});

		//根据ID和类型删除
		$("#delcol").click('on', function() {
			var type = $(".active-tag").attr("data-type");
			var id = $(".active-tag").attr("data-id");
			if (type == "parent") {
				var name = $(".active-tag").attr("vtlcolname");
				var csrf = "${_csrf.token}";
				$.post("${deleteColUrl}", {
					id : id,
					type : type,
					name : name,
					_csrf : csrf
				}, function() {
					window.location.reload();
				});
			} else if (type == "children") {
				var name = $(".active-tag").attr("data-name");
				var csrf = "${_csrf.token}";
				$.post("${deleteUrl}", {
					id : id,
					type : type,
					name : name,
					_csrf : csrf
				}, function() {
					window.location.reload();
				});
			}
			return true;
		});

		var validator = $('#v_name').parsley();
		$('#addfrm').submit(
				function() {
					
					validator.validate();
					if (validator.isValid()) {
						$("#submitaddfrm").attr("disabled","true");
						$.cookie('new-name', null);
						$.cookie('new-name', $(".new_name").val());
						var position = 1;
						var namelist = "";
						$('#note-items >li').each(
								function() {
									namelist = $(this).find("strong").text()
											.trim()
											+ "softtek"
											+ position
											+ "support"
											+ namelist;
									position++;
								});
						$("#namelistmodal").attr('value', namelist);
						return true;
						window.location.reload();
					} else {
						return false;
					}
				});

		$('#update').submit(function() {
			$.cookie('update-name-children', null);
			$.cookie('update-name-children', $(".active-tag").attr('data-id'));
		});

		var validatoradd = $('.text-add-group').parsley();
		
		//新增modal框 输入虚拟组名称添加到list中的 "添加"事件
		$(".btn-add-group")
				.on(
						"click",
						function() {
							validatoradd.validate();
							if (validatoradd.isValid()) {
								var name = $(".text-add-group").val().trim();
								var flag = true;
								$("#note-items > li").each(
										function() {
											if (name == $(this).find("strong")
													.text().trim()) {
												//$("#note-items").addClass("parsley-error");
												//$("#virgroupName").removeClass("hidden");
												$(".notify").notify({
													type : "danger",
													message : {
														html : false,
														text : '<fmt:message key="tiles.institution.vtl.group.notify.label"/>'
													}
												}).show();
												$(".text-add-group").val('');
												flag = false;
												return true;
											}
										});
								if (flag == true) {
									//if($("#note-items").hasClass("parsley-error")){
									//$("#note-items").removeClass("parsley-error");} 
									//$("#virgroupName").addClass("hidden");
									$("#note-items")
											.append(
													"<li class='list-group-item hover' onclick='chooseLi(this)'><div class='view' id=''><button class='close hover-action' onclick='deleteLi(this)'>×</button><div class='note-name text-ellipsis'><strong>"
															+ name
															+ "</strong></div></div></li>");
									$(".text-add-group").val('');
									$(".text-add-group").focus();
								}
							}
						});
		
		//index界面输入虚拟组名称添加到list中的 "添加"事件
		var parsleyadd = $('.text-add-group-1').parsley();
		$(".btn-add-group-1")
				.on(
						"click",
						function() {
							parsleyadd.validate();
							if (parsleyadd.isValid()) {
								var name = $(".text-add-group-1").val().trim();
								var flag = true;
								$("#note-items-1 > li").each(
										function() {
											if (name == $(this).find("strong")
													.text().trim()) {
												$(".notify").notify({
													type : "danger",
													message : {
														html : false,
														text : '<fmt:message key="tiles.institution.vtl.group.notify.label"/>'
													}
												}).show();
												$(".text-add-group-1").val('');
												flag = false;
												return true;
											}
										});
								if (flag == true) {
									$("#note-items-1")
											.append(
													"<li class='list-group-item hover' onclick='chooseitem(this)'><div class='view' id=''><button class='close hover-action' onclick='deleteitem(this)'>×</button><div class='note-name text-ellipsis'><strong>"
															+ name
															+ "</strong></div></div></li>");
									$(".text-add-group-1").val('');
									$(".text-add-group-1").focus();
								}
								$(".text-add-group-1").removeClass(
										"parsley-success");
							}
						});
		//modal框点击上移
		$(".btn-arrow-up").on(
				"click",
				function() {
					var count = 0;
					var current = 0;
					var flag = false;
					$("#note-items > li").each(function() {
						if (flag == false && !$(this).hasClass('avtive')) {
							current++;
						}
						if ($(this).hasClass('active')) {
							flag = true;
						}
						count++;
					});

					if (current > 1 && flag == true) {
						var preli = $("#note-items").find("li").eq(current - 2)
								.prop("outerHTML");
						$("#note-items > li").eq(current - 2).remove();
						$("#note-items > li").eq(current - 2).after(preli);
					}
				});
		//index点击上移
		$(".btn-arrow-up-1").on(
				"click",
				function() {
					var count = 0;
					var current = 0;
					var flag = false;
					$("#note-items-1 > li").each(function() {
						if (flag == false && !$(this).hasClass('avtive')) {
							current++;
						}
						if ($(this).hasClass('active')) {
							flag = true;
						}
						count++;
					});

					if (current > 1  && flag == true) {
						var preli = $("#note-items-1").find("li").eq(
								current - 2).prop("outerHTML");
						$("#note-items-1 > li").eq(current - 2).remove();
						$("#note-items-1 > li").eq(current - 2).after(preli);
					}
				});
		//modal点击下移
		$(".btn-arrow-down").on(
				"click",
				function() {
					var count = 0;
					var current = 0;
					var flag = false;
					$("#note-items > li").each(function() {
						if (flag == false && !$(this).hasClass('avtive')) {
							current++;
						}
						if ($(this).hasClass('active')) {
							flag = true;
						}
						count++;
					});

					if (current < count) {
						var nextli = $("#note-items").find("li")
								.eq(current - 1).prop("outerHTML");
						$("#note-items > li").eq(current - 1).remove();
						$("#note-items > li").eq(current - 1).after(nextli);
					}
				});
		//index点击下移
		$(".btn-arrow-down-1").on(
				"click",
				function() {
					var count = 0;
					var current = 0;
					var flag = false;
					$("#note-items-1 > li").each(function() {
						if (flag == false && !$(this).hasClass('avtive')) {
							current++;
						}
						if ($(this).hasClass('active')) {
							flag = true;
						}
						count++;
					});

					if (current < count) {
						var nextli = $("#note-items-1").find("li").eq(
								current - 1).prop("outerHTML");
						$("#note-items-1 > li").eq(current - 1).remove();
						$("#note-items-1 > li").eq(current - 1).after(nextli);
					}
				});
	});
	/* $("#updateL").click(
			function() {
				$('#update').submit(
						function() {
							if (validatoredit.isValid() == null) {
								$.cookie('update-name-children', null);
								$.cookie('update-name-children', $(
										".active-tag").attr('data-id'));
							} else if (validatoredit.isValid() == true) {
								$.cookie('update-name-children', null);
								$.cookie('update-name-children', $(
										".active-tag").attr('data-id'));
							} else {
								return false;
							}

						});
			}); */
	$("#updateL").click(
					function() {
						var createby=$(".active-tag").attr("create-by");
						if(!$("#virtualCol_name").hasClass("parsley-error")){
					 if("${softtek_manager.user}"!='' && "${softtek_manager.id}" != createby ){
						 $('#warningModalvtl').modal(open);
					}else{
						modifysub();
					}	
					}});
	$("#updateLs").click(
					function() {
						var createby=$(".active-tag").attr("create-by");
						if(!$("#virtlgroup_name").hasClass("parsley-error")){
						if("${softtek_manager.user}"!='' && "${softtek_manager.id}" != createby ){
							$('#warningModalgroup').modal(open);
								}
									}
					});
	function addModal() {
		$(".text-add-group").val("");
		$("#v_name").val("");
		$("#note-items").html("");
		if ($("#v_name").hasClass("parsley-success")) {
			$("#v_name").removeClass("parsley-success");
		}
		if ($("#v_name").hasClass("parsley-error")) {
			$("#v_name").removeClass("parsley-error");
		}
		if ($("#v_name").siblings("ul")) {
			$("#v_name").siblings("ul").remove();
		}
		if ($(".text-add-group").hasClass("parsley-success")) {
			$(".text-add-group").removeClass("parsley-success");
		}
		if ($(".text-add-group").hasClass("parsley-error")) {
			$(".text-add-group").removeClass("parsley-error");
		}
		if ($(".text-add-group").siblings("ul")) {
			$(".text-add-group").siblings("ul").remove();
		}
		//$("#note-items").removeClass("parsley-error");
		//$("#virgroupName").addClass("hidden");
	}
	//点击选择事件
	function chooseitem(obj) {
		$("#note-items-1 > li").each(
				function() {
					if ($(obj).find("strong").text().trim() == $(this).find(
							"strong").text().trim()) {
						$(this).addClass("active");
					} else {
						$(this).removeClass("active");
					}
				});
	}
	//点击选择事件
	function chooseLi(obj) {
		$("#note-items > li").each(
				function() {
					if ($(obj).find("strong").text().trim() == $(this).find(
							"strong").text().trim()) {
						$(this).addClass("active");
					} else {
						$(this).removeClass("active");
					}
				});
	}
	//删除#note-items > li事件
	function deleteLi(obj) {
		$("#note-items > li").each(
				function() {
					if ($(obj).siblings().find("strong").text().trim() == $(
							this).find("strong").text().trim()) {
						$(this).remove();
					}
				});
	}
	//点击一级菜单Tree
	function liClick(ob) {
		
		if ($("#virtualCol_name").hasClass("parsley-success")) {
			$("#virtualCol_name").removeClass("parsley-success")
		}
		if ($("#virtualCol_name").hasClass("parsley-error")) {
			$("#virtualCol_name").removeClass("parsley-error")
		}
		if ($("#virtualCol_name").siblings("ul")) {
			$("#virtualCol_name").siblings("ul").remove();
		}
		if ($(".text-add-group-1").hasClass("parsley-success")) {
			$(".text-add-group-1").removeClass("parsley-success");
		}
		if ($(".text-add-group-1").hasClass("parsley-error")) {
			$(".text-add-group-1").removeClass("parsley-error");
		}
		if ($(".text-add-group-1").siblings("ul")) {
			$(".text-add-group-1").siblings("ul").remove();
		}

		//获取集合ul下面循环的每一个li
		obj = $(ob).children("li");
		//去掉所有背景
		$('#tree').children("ul").children("li").removeClass("background");
		$(obj).parent().parent().find("li").removeClass("background");
		//文本框置为空
		$(".text-add-group-1").val('');
		$(ob).children("li").addClass("background");
		//去掉所有集合、虚拟组的class:active标签
		$("ul").find("li").removeClass("active-tag");
		//当前选中的加上class="active"
		$(obj).addClass("active-tag");
		//把集合更新界面移除隐藏
		$('#updateColfrm').removeClass('hidden');
		//组更新界面隐藏
		$('.d_base').addClass('hidden');
		//如果所属集合下的虚拟组是隐藏的  移除hidden 把"+"号隐藏   "-"显示
		if ($(obj).parent().next("ul").is(':hidden') == true) {
			$(obj).parent().next("ul").removeClass("hidden");
			$(ob).find("span").first().next().removeClass("hidden");
			$(ob).find("span").first().addClass("hidden");
		}
		//如果所属集合下的虚拟组是显示的添加hidden  把"-"号隐藏   "+"显示
		else {
			$(obj).parent().next("ul").addClass("hidden");
			$(ob).find("span").first().removeClass("hidden");
			$(ob).find("span").first().next().addClass("hidden");
		}
		//把集合id传到界面隐藏表单中
		$('#virtualCol_id').val($(obj).attr('data-id'));
		//获得单选/多选标记(data-selected)
		var troggle = {
			i : $(obj).attr("data-selected")
		};
		$("#updateColfrm").find(".form-group").eq(1).remove();
		$("#updateColfrm").find(".form-group").eq(0).append(
				$("#troggleTmpl").tmpl(troggle));

		$('#virtualCol_name').val($(obj).attr('vtlcolName'));
		var colid = $(obj).attr('data-id');

		var arr = [];

		$(obj).parent().next("ul").find("li").each(function(i, item) {
			arr.push(item.textContent);
		});
		$("#note-items-1").html('');
		
		var createby=$(".active-tag").attr("create-by");
		 if("${softtek_manager.user}"!='' && "${softtek_manager.id}" != createby ){
			 $("#updateL").attr("type","button");
		}else{
			$("#updateL").attr("type","submit");
		}	
		 
		for (var i = 0; i < arr.length; i++) {
			var data = {
				name : arr[i]
			};
			$("#note-items-1").append($("#infoTmpl").tmpl(data));
			parentId = $(obj).attr("data-id");
		}
		//检查用户名称是否重复
		var cid = $(".active-tag").attr("data-id");
		$("#virtualCol_name").parsley().addAsyncValidator('existsValidate1',
				function(xhr) {
					return (xhr.responseText.indexOf('success') >= 0);
				}, "${existseditUrl}", {
					"type" : "GET",
					"dataType" : "json",
					"contentType" : "application/json; charset=utf-8",
					"data" : {
						id : cid
					}
				});

	};

	//点击二级菜单Tree
	function liClick2(obj) {
		$('#tree').children("ul").children("li").removeClass("background");
		$(obj).parent().parent().parent().find("li").removeClass("background");
		$(obj).addClass("background");
		$("ul").find("li").removeClass("active-tag");
		$(obj).addClass("active-tag");
		$('#updateColfrm').addClass('hidden');
		$('.d_base').removeClass('hidden');
		$('#virtlgroup_name').val($(obj).attr('data-name'));
		$('#virtlgroup_id').val($(obj).attr('data-id'));
		var createby=$(".active-tag").attr("create-by");
		 if("${softtek_manager.user}"!='' && "${softtek_manager.id}" != createby ){
			 $("#updateLs").attr("type","button");
		}else{
			$("#updateLs").attr("type","submit");
		}
		//检查用户名称是否重复
		var ccid = $(".active-tag").attr("parentid");
		var gid = $(".active-tag").attr("data-id");
		$("#virtlgroup_id").parsley().addAsyncValidator('existsValidate2',
				function(xhr) {
					return (xhr.responseText.indexOf('success') >= 0);
				}, "${existseditGroupUrl}", {
					"type" : "GET",
					"dataType" : "json",
					"contentType" : "application/json; charset=utf-8",
					"data" : {
						gid : gid,
						cid : ccid
					}
				});
		if ($("#virtlgroup_name").hasClass("parsley-success")) {
			$("#virtlgroup_name").removeClass("parsley-success");
		}
		if ($("#virtlgroup_name").hasClass("parsley-error")) {
			$("#virtlgroup_name").removeClass("parsley-error");
		}
		if ($("#virtlgroup_name").siblings("ul")) {
			$("#virtlgroup_name").siblings("ul").remove();
		}

	};

	$(function() {
		$("#tree-list").each(function() {
			$(this).children().first().click();
		});
		if ($.cookie("new-name") != "null" && $.cookie("new-name") != undefined) {
			var name = $.cookie("new-name");
			$("li[vtlcolname=" + name + "]").click();
			$.cookie("new-name", null);
		} else if ($.cookie("update-name-children") != "null"
				&& $.cookie("update-name-children") != undefined) {
			var id = $.cookie("update-name-children");
			$("li[data-id=" + id + "]").parent().parent().prev().click();
			$.cookie("update-name-children", null);
		} else if ($.cookie("update-name-parent") != "null"
				&& $.cookie("update-name-parent") != undefined) {
			var id = $.cookie("update-name-parent");
			$("li[data-id=" + id + "]").parent().click();
			$.cookie("update-name-parent", null);
		} 

	});
	function parsleymodalfrm() {
		/* var validator = $("#v_name").parsley();
		 validator.validate(); */
		return false;
	}

	function btnReset() {
		$(function() {
			$("#tree > ul").each(function() {
				if ($(this).attr('vtlcolid') == $('#virtualCol_id').val()) {
					liClick(this);
				}
			});
		})
	}
	function deletecol(){
		var type = $(".active-tag").attr("data-type");
		var id = $(".active-tag").attr("data-id");
		if (type == "parent") {
			var name = $(".active-tag").attr("vtlcolname");
			var csrf = "${_csrf.token}";
			$.post("${deleteColUrl}", {
				id : id,
				type : type,
				name : name,
				_csrf : csrf
			}, function() {
				window.location.reload();
			});
		} else if (type == "children") {
			var name = $(".active-tag").attr("data-name");
			var csrf = "${_csrf.token}";
			$.post("${deleteUrl}", {
				id : id,
				type : type,
				name : name,
				_csrf : csrf
			}, function() {
				window.location.reload();
			});
		}
		return true;
	}; 
	function modifysub(){
		/* $('#updateColfrm').submit();
		return true; */
		$('#updateColfrm').submit(
				function() {
					var validatoredit = $(
							'#virtualCol_name')
							.parsley();
					//修改用户名称是否大于1
					validatoredit.validate();
					if (validatoredit.isValid() == null) {
						$.cookie('update-name-parent',
								null);
						$.cookie('update-name-parent',
								$(".active-tag").attr('data-id'));
						var position = 1;
						var namelist = "";
						$('#note-items-1 >li')
								.each(
										function() {
											namelist = $(
													this)
													.find(
															"strong")
													.text()
													.trim()
													+ "softtek"
													+ position
													+ "support"
													+ namelist;
											position++;
										});
						$("#namelist").attr('value',
								namelist);
						return true;
					} else if (validatoredit.isValid() == true) {
						$.cookie('update-name-parent',
								null);
						$.cookie('update-name-parent',$(".active-tag").attr('data-id'));
						var position = 1;
						var namelist = "";
						$('#note-items-1 >li')
								.each(
										function() {
											namelist = $(
													this)
													.find(
															"strong")
													.text()
													.trim()
													+ "softtek"
													+ position
													+ "support"
													+ namelist;
											position++;
										});
						$("#namelist").attr('value',
								namelist);
						return true;
					} else {
						return false;
					}
				});
	}
	$(function(){
		$('#updateColfrm').submit(
				function() {
					var validatoredit = $(
							'#virtualCol_name')
							.parsley();
					//修改用户名称是否大于1
					validatoredit.validate();
					if (validatoredit.isValid() == null) {
						$.cookie('update-name-parent',
								null);
						$.cookie('update-name-parent',
								$(".active-tag").attr('data-id'));
						var position = 1;
						var namelist = "";
						$('#note-items-1 >li')
								.each(
										function() {
											namelist = $(
													this)
													.find(
															"strong")
													.text()
													.trim()
													+ "softtek"
													+ position
													+ "support"
													+ namelist;
											position++;
										});
						$("#namelist").attr('value',
								namelist);
						return true;
					} else if (validatoredit.isValid() == true) {
						$.cookie('update-name-parent',
								null);
						$.cookie('update-name-parent',$(".active-tag").attr('data-id'));
						var position = 1;
						var namelist = "";
						$('#note-items-1 >li')
								.each(
										function() {
											namelist = $(
													this)
													.find(
															"strong")
													.text()
													.trim()
													+ "softtek"
													+ position
													+ "support"
													+ namelist;
											position++;
										});
						$("#namelist").attr('value',
								namelist);
						return true;
					} else {
						return false;
					}
				});
		
		
		$("#modifyColTips").on("click", function() {
			modifysub();
		});
		
		
	});
	
	$(".btn-delete").on("click", function() {
		var type = $(".active-tag").attr("data-type");
		var id = $(".active-tag").attr("data-id");
		var text = $("#delModalTree").find("h3").text();
		var createby=$(".active-tag").attr("create-by");
		if (type == "children") {
			if("${softtek_manager.user}"!='' &&"${softtek_manager.id}"!=createby){
				$('#warningModal').modal(open);
				$('#warningModal').find("button").last().attr("onclick","deletecol()");
				//$("#delModalTree").find("h3").text("<fmt:message key='tiles.institution.vtl.group.delete.manager.messages'/>");
			}else{
				$('#delModalTree').modal(open);
				$("#delModalTree").find("h3").text("<fmt:message key='tiles.institution.vtl.group.delete.group.label'/>");
			}
		} else {
			if("${softtek_manager.user}"!='' &&"${softtek_manager.id}"!=createby){
				$('#warningModal').modal(open);
				$('#warningModal').find("button").last().attr("onclick","deletecol()");
				//$("#delModalTree").find("h3").text("<fmt:message key='tiles.institution.vtl.group.delete.manager.messages'/>");
			}else{
				$('#delModalTree').modal(open);
				$("#delModalTree").find("h3").text("<fmt:message key='tiles.institution.vtl.group.delete.col.label'/>");
			}
				}
		return true;
	});
</script>

<script id="infoTmpl" type="text/x-jquery-tmpl">
<li class="list-group-item hover" onclick="chooseitem(this)">
	<div class="view" id="">
		<button class="close hover-action" onclick="deleteitem(this)">×</button>
		<div class="note-name text-ellipsis" childrenid="">
			<strong>{{= name}}</strong>
		</div>
	</div>
</li>
</script>

<script id="troggleTmpl" type="text/x-jquery-tmpl">
<div class="form-group"> 
<label class="col-lg-2 control-label"><fmt:message key='tiles.institution.vtl.group.radio.label'/> <i class="i i-question" data-toggle="tooltip" data-placement="top" title="<fmt:message key='tiles.institution.vtl.group.radio.tags.label'/>"></i></label>
<div class="col-lg-10">
	{{if i==0}}
       <div class="radio i-checks col-lg-3">
		<label> <input type="radio" name="virtualCollection.multiple" value="1"> <i></i><fmt:message key='tiles.institution.vtl.group.radio.yes.label'/>
		</label>
		</div>
		<div class="radio i-checks col-lg-3">
		<label> <input type="radio" name="virtualCollection.multiple" value="0" checked>
			<i></i><fmt:message key='tiles.institution.vtl.group.radio.no.label'/>
		</label>
		</div>
    {{else}}
           <div class="radio i-checks col-lg-3">
		<label> <input type="radio" name="virtualCollection.multiple" value="1" checked> <i></i><fmt:message key='tiles.institution.vtl.group.radio.yes.label'/>
		</label>
		</div>
		<div class="radio i-checks col-lg-3">
		<label> <input type="radio" name="virtualCollection.multiple" value="0" >
			<i></i><fmt:message key='tiles.institution.vtl.group.radio.no.label'/>
		</label>
		</div>
    {{/if}}
</div>
</div>
</script>

<script id="troggleTmpl" type="text/x-jquery-tmpl">


</script>
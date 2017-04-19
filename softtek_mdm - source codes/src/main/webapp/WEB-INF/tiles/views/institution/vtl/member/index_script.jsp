<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<spring:url value="/institution/vtl/exists" var="existsUrl" />
<spring:url value="/institution/vtl/querymember" var="querymember" />
<spring:url value="/institution/vtl/querymemberradio" var="querymemberradio" />
<spring:url value="/institution/vtl/delete" var="deleteUrl" />
<spring:url value="/resources/js/jquery.tmpl.js" var="tmplJs" />
<spring:url value="/resources/js/jquery.cookie.js" var="cookieJs" />
<spring:url value="/institution/vtl/querymbpagene" var="querymbpagene" />
<spring:url value="/institution/vtl/querymbpagee" var="querymbpagee" />
<spring:url value="/institution/vtl/insertmember" var="insertmember" />
<spring:url value="/institution/vtl/deletemember" var="deletemember" />
<spring:url value="/institution/vtl/querymbpageneradio" var="querymbpageneradio" />
<spring:url value="/institution/vtl/querymbpageeradio" var="querymbpageeradio" />
<spring:url value="/institution/exceltest/getvirmodel" var="getVirModel" />
<spring:url value="/institution/excel/importvirmember" var="importvirmember" />
<script src="${tmplJs}"></script>
<script src="${cookieJs}"></script>
<script id="troggleTmpl" type="text/x-jquery-tmpl">


</script>

<script type="text/javascript">
   //点击切换活动状态-已存在用户
	function chooseitem(obj) {
		$(obj).toggleClass("active");
	}
	//点击切换活动状态-不存在的用户
	function chooseLi(obj) {
		$(obj).toggleClass("active");
	}
	
	//加入用户
	function insertmember() {
		$(".btn-arrow-right").attr("disabled","true"); 
		 $.cookie('tag-active',null);
		 $.cookie('tag-active-id',null);
		   var arr=new Array;
		   var arr2=new Array;
		   $.each($(".active.left-list"),function(i,obj){
		          arr[i] = $(this).find("strong").attr("data-id");
		          arr2[i]= $(this).find("strong").attr("uname");
		        }); 
		    var colid=$(".active-tag").attr("parentid");
		    var groupid=$(".active-tag").attr("data-id");
		    var userid=arr.toString();
		    var uname=arr2.toString();
		    var gname=$(".active-tag").attr("data-name");
		    var csrf="${_csrf.token}";
		    $.post("${insertmember}", {colid:colid,groupid:groupid,userid:userid,gname:gname,uname:uname,_csrf:csrf},function(){
		    	$.cookie('tag-active', $(".active-tag").attr("data-name"));
		    	$.cookie('tag-active-id',$(".active-tag").attr("data-id"));
		    	 window.location.reload(); 
		    });
		   
		}
	//删除用户
    function deletemember() {
	   var arr=new Array;
	   var arr2=new Array;
	   $.each($(".active.right-list"),function(i,obj){
	          arr[i] = $(this).find("strong").attr("data-id");
	          arr2[i]= $(this).find("strong").attr("uname");
	        }); 
	   var userid=arr.toString();
	   var uname=arr2.toString();
	   var colid=$(".active-tag").attr("parentid");
	   var groupid=$(".active-tag").attr("data-id");
	   var gname=$(".active-tag").attr("data-name");
	   var csrf="${_csrf.token}";
		    $.post("${deletemember}", {userid:userid,groupid:groupid,colid:colid,uname:uname,gname:gname,_csrf:csrf},function(){ 
		    	$.cookie('tag-active', $(".active-tag").attr("data-name"));
		    	$.cookie('tag-active-id',$(".active-tag").attr("data-id"));  
		    	window.location.reload()
		    });
	}
	//不存在用户  模糊查询功能(过滤)
	function queryMember() {
		var csrf="${_csrf.token}";
		var nameready=$("#non-existence-name").val();
		$("#non-existence-name-submit").val(nameready);
		var name = $("#non-existence-name-submit").val();
		var id = $(".active-tag").attr('data-id');
		if($(".active-tag").attr('data-selected')=="1"){
			$.post("${querymember}", {
				id : id,name:name,_csrf:csrf
			}, function(data) {
				$("#note-items").html('');
				$(".prevPageNoexist").attr("pageNumber", $.parseJSON(data).page);
				$(".nextPageNoexist").attr("pageNumber", $.parseJSON(data).page);
				$(".nextPageNoexist").attr("max-page", $.parseJSON(data).maxpageleft);
				$("#totalityleft").html('');
				$("#totalityleft").append($.parseJSON(data).totalityleft);
				$.each($.parseJSON(data).noexistentlist, function(n, value) {
					$("#note-items").append(
							'<li class="list-group-item hover left-list" onclick="chooseLi(this)">'
									+ '<div class="view" id="">'
									+ '<div class="note-name text-ellipsis z">'
									+ '<strong data-id="'+value.id+'" uname="'+ value.realname + '">'
									+ value.realname + '('+value.username+')</strong>' + '</div>'
									+ '</div>' + '</li>');
				});
			});
			}else{
				$.post("${querymemberradio}", {
					id : id,name:name,_csrf:csrf
				}, function(data) {
					$("#note-items").html('');
					$(".prevPageNoexist").attr("pageNumber", $.parseJSON(data).page);
					$(".nextPageNoexist").attr("pageNumber", $.parseJSON(data).page);
					$(".nextPageNoexist").attr("max-page", $.parseJSON(data).maxpageleft);
					$("#totalityleft").html('');
					$("#totalityleft").append($.parseJSON(data).totalityleft);
					$.each($.parseJSON(data).noexistentlist, function(n, value) {
						$("#note-items").append(
								'<li class="list-group-item hover left-list" onclick="chooseLi(this)">'
										+ '<div class="view" id="">'
										+ '<div class="note-name text-ellipsis z">'
										+ '<strong data-id="'+value.id+'" uname="'+ value.realname + '">'
										+ value.realname + '('+value.username+')</strong>' + '</div>'
										+ '</div>' + '</li>');
					});
					
				});
				
			}
		};
		//存在用户  模糊查询功能(过滤)
		function queryexistMember() {
			var csrf="${_csrf.token}";
			var nameready=$("#existent-member").val();
			$("#existence-name-submit").val(nameready);
			var name=$("#existence-name-submit").val();
			var id = $(".active-tag").attr('data-id');
			$.post("${querymember}", {
				id : id,name:name,_csrf:csrf
			}, function(data) {
				$("#non-existence-name").val("");
				$("#non-existence-name-submit").val("");
				$("#note-items-1").html('');
				$(".prevPageexist").attr("pageNumber", $.parseJSON(data).page);
				$(".nextPageexist").attr("pageNumber", $.parseJSON(data).page);
				$(".nextPageexist").attr("max-page", $.parseJSON(data).maxpageright);
				$("#totalityright").html('');
				$("#totalityright").append($.parseJSON(data).totalityright);
				$.each($.parseJSON(data).existlist, function(n, value) {
					$("#note-items-1").append(
							'<li class="list-group-item hover right-list" onclick="chooseitem(this)">'
									+ '<div class="view" id="">'
									+ '<div class="note-name text-ellipsis">'
									+ '<strong data-id="'+value.id+'" uname="'+ value.realname + '">'
									+ value.realname + '('+value.username+')</strong>' + '</div>'
									+ '</div>' + '</li>');
				});
			});
		}
	
	//点击一级菜单Tree
	function liClick(ob) {
		//获取集合ul下面循环的每一个li
		obj = $(ob).children("li");
		//去掉所有背景
		$('#tree').children("ul").children("li").removeClass("background");
		if ($(ob).next("ul").is(':hidden') == true) {
			$(obj).parent().next("ul").removeClass("hidden");
			$(ob).find("span").first().next().removeClass("hidden");
			$(ob).find("span").first().addClass("hidden");

		} else {
			$(ob).next("ul").addClass("hidden");
			$(ob).find("span").first().removeClass("hidden");
			$(ob).find("span").first().next().addClass("hidden");
		}

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

	};

	//点击二级菜单Tree
	function querymembers(obj) {
		var csrf="${_csrf.token}";
		var id = $(obj).attr('data-id');
		if($(obj).attr('data-selected')=="1"){
		$.post("${querymember}", {
			id : id,_csrf:csrf
		}, function(data) {
			$("#non-existence-name").val("");
			$("#non-existence-name-submit").val("");
			$("#note-items").html('');
			$("#note-items-1").html('');
			$(".prevPageNoexist").attr("pageNumber", $.parseJSON(data).page);
			$(".nextPageNoexist").attr("pageNumber", $.parseJSON(data).page);
			$(".prevPageexist").attr("pageNumber", $.parseJSON(data).page);
			$(".nextPageexist").attr("pageNumber", $.parseJSON(data).page);
			$(".nextPageNoexist").attr("max-page", $.parseJSON(data).maxpageleft);
			$(".nextPageexist").attr("max-page", $.parseJSON(data).maxpageright);
			$("#virtual_name").html('');
			$("#virtual_name_1").html('');
			$("#virtual_name").append($(obj).attr("data-name"));
			$("#virtual_name_1").append($(obj).attr("data-name"));
			$("#totalityleft").html('');
			$("#totalityright").html('');
			$("#totalityleft").append($.parseJSON(data).totalityleft);
			$("#totalityright").append($.parseJSON(data).totalityright);
			$.each($.parseJSON(data).noexistentlist, function(n, value) {
				$("#note-items").append(
						'<li class="list-group-item hover left-list" onclick="chooseLi(this)">'
								+ '<div class="view" id="">'
								+ '<div class="note-name text-ellipsis z">'
								+ '<strong data-id="'+value.id+'" uname="'+ value.realname + '">'
								+ value.realname + '('+value.username+')</strong>' + '</div>'
								+ '</div>' + '</li>');
			});
			$.each($.parseJSON(data).existlist, function(n, value) {
				$("#note-items-1").append(
						'<li class="list-group-item hover right-list" onclick="chooseitem(this)">'
								+ '<div class="view" id="">'
								+ '<div class="note-name text-ellipsis">'
								+ '<strong data-id="'+value.id+'" uname="'+value.realname+'">'
								+ value.realname + '('+value.username+')</strong>' + '</div>'
								+ '</div>' + '</li>');
			});
		});
		}else{
			$.post("${querymemberradio}", {
				id : id,_csrf:csrf
			}, function(data) {
				$("#non-existence-name").val("");
				$("#non-existence-name-submit").val("");
				$("#note-items").html('');
				$("#note-items-1").html('');
				$(".prevPageNoexist").attr("pageNumber", $.parseJSON(data).page);
				$(".nextPageNoexist").attr("pageNumber", $.parseJSON(data).page);
				$(".prevPageexist").attr("pageNumber", $.parseJSON(data).page);
				$(".nextPageexist").attr("pageNumber", $.parseJSON(data).page);
				$(".nextPageNoexist").attr("max-page", $.parseJSON(data).maxpageleft);
				$(".nextPageexist").attr("max-page", $.parseJSON(data).maxpageright);
				$("#virtual_name").html('');
				$("#virtual_name_1").html('');
				$("#virtual_name").append($(obj).attr("data-name"));
				$("#virtual_name_1").append($(obj).attr("data-name"));
				$("#totalityleft").html('');
				$("#totalityright").html('');
				$("#totalityleft").append($.parseJSON(data).totalityleft);
				$("#totalityright").append($.parseJSON(data).totalityright);
				$.each($.parseJSON(data).noexistentlist, function(n, value) {
					$("#note-items").append(
							'<li class="list-group-item hover left-list" onclick="chooseLi(this)">'
									+ '<div class="view" id="">'
									+ '<div class="note-name text-ellipsis z">'
									+ '<strong data-id="'+value.id+'" uname="'+ value.realname + '">'
									+ value.realname + '('+value.username+')</strong>' + '</div>'
									+ '</div>' + '</li>');
				});
				$.each($.parseJSON(data).existlist, function(n, value) {
					$("#note-items-1").append(
							'<li class="list-group-item hover right-list" onclick="chooseitem(this)">'
									+ '<div class="view" id="">'
									+ '<div class="note-name text-ellipsis">'
									+ '<strong data-id="'+value.id+'" uname="'+ value.realname + '">'
									+ value.realname + '('+value.username+')</strong>' + '</div>'
									+ '</div>' + '</li>');
				});
			});
			
		}

		$('#tree').children("ul").children("li").removeClass("background");
		$(obj).parent().parent().parent().find("li").removeClass("background");
		$(obj).addClass("background");
		$("ul").find("li").removeClass("active-tag");
		$(obj).addClass("active-tag");
		$(".prevPageNoexist")
				.attr("group-id", $(".active-tag").attr("data-id"));
		$(".nextPageNoexist")
				.attr("group-id", $(".active-tag").attr("data-id"));
		$(".prevPageexist").attr("group-id", $(".active-tag").attr("data-id"));
		$(".nextPageexist").attr("group-id", $(".active-tag").attr("data-id"));
		$('#updateColfrm').addClass('hidden');
		$('.d_base').removeClass('hidden');

	};
	//上一页不存在的用户
	function prevPageNoexist(obj) {
		var name=$("#non-existence-name-submit").val();
		var id = $(obj).attr("group-id");
		var pageNumber =  parseInt($(obj).attr("pageNumber"));
		if($(".active-tag").attr('data-selected')=="1"){
		if(pageNumber==1){
		   var index=0;
		$.get("${querymbpagene}", {id : id,index:index,name:name}, function(data) {
			 $("#note-items").html('');
				$.each($.parseJSON(data).noexistentlist, function(n, value) {
					$("#note-items").append(
							'<li class="list-group-item hover left-list" onclick="chooseLi(this)">'
									+ '<div class="view" id="">'
									+ '<div class="note-name text-ellipsis">'
									+ '<strong data-id="'+value.id+'" uname="'+ value.realname + '">'
									+ value.realname + '('+value.username+')</strong>' + '</div>'
									+ '</div>' + '</li>');
				});										
				});
						}
		else {
			var index = pageNumber * 10-20;
		   $.get("${querymbpagene}", {id : id,index :index,name:name}, function(data) {
			 $("#note-items").html('');
				$(".prevPageNoexist").attr("pageNumber", pageNumber-1);
				$(".nextPageNoexist").attr("pageNumber", pageNumber-1);
				$.each($.parseJSON(data).noexistentlist, function(n, value) {
					$("#note-items").append(
							'<li class="list-group-item hover left-list" onclick="chooseLi(this)">'
									+ '<div class="view" id="">'
									+ '<div class="note-name text-ellipsis">'
									+ '<strong data-id="'+value.id+'" uname="'+ value.realname + '">'
									+ value.realname + '('+value.username+')</strong>' + '</div>'
									+ '</div>' + '</li>');
				});	
		})
		}
		}else{
			if(pageNumber==1){
				   var index=0;
				$.get("${querymbpageneradio}", {id : id,index:index,name:name}, function(data) {
					 $("#note-items").html('');
						$.each($.parseJSON(data).noexistentlist, function(n, value) {
							$("#note-items").append(
									'<li class="list-group-item hover left-list" onclick="chooseLi(this)">'
											+ '<div class="view" id="">'
											+ '<div class="note-name text-ellipsis">'
											+ '<strong data-id="'+value.id+'" uname="'+ value.realname + '">'
											+ value.realname + '('+value.username+')</strong>' + '</div>'
											+ '</div>' + '</li>');
						});										
						});
								}
				else {
					var index = pageNumber * 10-20;
				   $.get("${querymbpageneradio}", {id : id,index :index,name:name}, function(data) {
					 $("#note-items").html('');
						$(".prevPageNoexist").attr("pageNumber", pageNumber-1);
						$(".nextPageNoexist").attr("pageNumber", pageNumber-1);
						$.each($.parseJSON(data).noexistentlist, function(n, value) {
							$("#note-items").append(
									'<li class="list-group-item hover left-list" onclick="chooseLi(this)">'
											+ '<div class="view" id="">'
											+ '<div class="note-name text-ellipsis">'
											+ '<strong data-id="'+value.id+'" uname="'+ value.realname + '">'
											+ value.realname + '('+value.username+')</strong>' + '</div>'
											+ '</div>' + '</li>');
						});	
				})
				}
		}
		}
	
	//下一页不存在的用户
	function nextPageNoexist(obj) {
		var name=$("#non-existence-name-submit").val();
		var id = $(obj).attr("group-id");
		var pageNumber = parseInt($(obj).attr("pageNumber"));
		var index = pageNumber * 10;
		if($(".active-tag").attr('data-selected')=="1"){
		 $.get("${querymbpagene}", {id : id,index :index,name:name}, function(data) {
			 var page= $.parseJSON(data).maxpage;
			 if(pageNumber<page){
			 $("#note-items").html('');
			 $(".prevPageNoexist").attr("pageNumber", pageNumber+1);
			 $(".nextPageNoexist").attr("pageNumber", pageNumber+1);
				$.each($.parseJSON(data).noexistentlist, function(n, value) {
					$("#note-items").append(
							'<li class="list-group-item hover left-list" onclick="chooseLi(this)">'
									+ '<div class="view" id="">'
									+ '<div class="note-name text-ellipsis">'
									+ '<strong data-id="'+value.id+'" uname="'+ value.realname + '">'
									+ value.realname + '('+value.username+')</strong>' + '</div>'
									+ '</div>' + '</li>');
				});}else{
				
				}
				
		 })
		 }else{ 
			 $.get("${querymbpageneradio}", {id : id,index :index,name:name}, function(data) { 
			 var page= $.parseJSON(data).maxpage;
			 if(pageNumber<page){
			 $("#note-items").html('');
			 $(".prevPageNoexist").attr("pageNumber", pageNumber+1);
			 $(".nextPageNoexist").attr("pageNumber", pageNumber+1);
				$.each($.parseJSON(data).noexistentlist, function(n, value) {
					$("#note-items").append(
							'<li class="list-group-item hover left-list" onclick="chooseLi(this)">'
									+ '<div class="view" id="">'
									+ '<div class="note-name text-ellipsis">'
									+ '<strong data-id="'+value.id+'" uname="'+ value.realname + '">'
									+ value.realname + '('+value.username+')</strong>' + '</div>'
									+ '</div>' + '</li>');
				});}else{
				
				}
				
		 })
			 
		 }
		}; 
		//上一页存在的用户
		function prevPageExist(obj) {
			var name=$("#existence-name-submit").val();
			var id = $(obj).attr("group-id");
			var pageNumber =  parseInt($(obj).attr("pageNumber"));
		if(pageNumber==1){
			   var index=0;
			$.get("${querymbpagee}", {id : id,index :index,name:name}, function(data) {
				 $("#note-items-1").html('');
					$.each($.parseJSON(data).existentlist, function(n, value) {
						$("#note-items-1").append(
								'<li class="list-group-item hover right-list" onclick="chooseLi(this)">'
										+ '<div class="view" id="">'
										+ '<div class="note-name text-ellipsis">'
										+ '<strong data-id="'+value.id+'">'
										+ value.realname + '('+value.username+')</strong>' + '</div>'
										+ '</div>' + '</li>');
					});										
					})
					}
			else{
				var index = pageNumber * 10-20;
			   $.get("${querymbpagee}", {id : id,index :index,name:name}, function(data) {
				   $("#note-items-1").html('');
					$(".prevPageexist").attr("pageNumber", pageNumber-1);
					$(".nextPageexist").attr("pageNumber", pageNumber-1);
					$.each($.parseJSON(data).existentlist, function(n, value) {
						$("#note-items-1").append(
								'<li class="list-group-item hover right-list" onclick="chooseitem(this)">'
										+ '<div class="view" id="">'
										+ '<div class="note-name text-ellipsis">'
										+ '<strong data-id="'+value.id+'">'
										+ value.realname + '('+value.username+')</strong>' + '</div>'
										+ '</div>' + '</li>');
					});	
			})
			}
			}
		
	//下一页存在的用户
		function nextPageExist(obj) {
			var name=$("#existence-name-submit").val();
		    var id = $(obj).attr("group-id");
			var pageNumber = parseInt($(obj).attr("pageNumber"));
			var index = pageNumber * 10;
			 $.get("${querymbpagee}", {id : id,index :index,name:name}, function(data) {
				var page= $.parseJSON(data).maxpage;
				 if(pageNumber<page){
				 $("#note-items-1").html('');
				 $(".prevPageexist").attr("pageNumber", pageNumber+1);
				 $(".nextPageexist").attr("pageNumber", pageNumber+1);
					$.each($.parseJSON(data).existentlist, function(n, value) {
						$("#note-items-1").append(
								'<li class="list-group-item hover right-list" onclick="chooseitem(this)">'
										+ '<div class="view" id="">'
										+ '<div class="note-name text-ellipsis">'
										+ '<strong data-id="'+value.id+'">'
										+ value.realname + '('+value.username+')</strong>' + '</div>'
										+ '</div>' + '</li>');
					});}
			 })
			}; 

			function exportVirModel() {
				$("#getvirmodel").submit();
				}; 	
			//============================
			
			//=======================
		function openmyModal() {
			$('#myModal').modal(open);
			$("#file").val("");
			$("#disabledimportVirMember").removeAttr("disabled");
			$(".showFileName").val("");
			$(".fileerrorTip").html("");
		}
			function importVirMember() {
				var filePath=$("#file").val();
				if(filePath.indexOf("xls")!=-1 || filePath.indexOf("xlsx")!=-1){
					$("#disabledimportVirMember").attr("disabled","true");
			        $(".fileerrorTip").html("").hide();
			        var arr=filePath.split('\\');
			        var fileName=arr[arr.length-1];
			        $(".showFileName").val(fileName);
			    
				$('#rownumbers').html("");
				$('#myModal').modal("hide");
				$('#loadingModal').modal(open);
				var id=$(".active-tag").attr("data-id");
				var colid=$(".active-tag").attr("parentid");
				var isradio=$(".active-tag").attr("data-selected");
				var csrf = "${_csrf.token}";
			 	var formData = new FormData();
					formData.append("files", $("#file")[0].files[0]);
					formData.append("id",id);
					formData.append("colid",colid);
					formData.append("isradio",isradio); 
				 $.ajax({
					data:formData,
					type: "POST",
					url: ctx + "/institution/vtl/importvirmember",
					cache: false,
					headers: {
						"${_csrf.headerName}":"${_csrf.token}",
		        	},
		        	contentType:false,
					processData: false,
					success: function(result) {
						$('#loadingModal').modal("hide");
						$('#successModal').modal(open);
						if($.parseJSON(result).resultnull!=null){
							$('#rownumbers').removeClass('hidden');
							$("#successModal").find("h1").html('<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>');
							$('#rownumbers').html('<fmt:message key="tiles.views.user.index.script.user.excel.nullupload"/>');
							$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>'}}).show();
						}else if($.parseJSON(result).success!=null){
							$("#successModal").find("h1").html('<fmt:message key="tiles.views.user.index.script.user.excel.import.success"/>');
							$('#rownumbers').addClass('hidden');
							$('#rownumbers').html($.parseJSON(result).success);
							$(".notify").notify({type:"success",message: { html: false, text:'<fmt:message key="tiles.views.user.index.script.user.excel.import.success"/>'}}).show();
							$.cookie('tag-active',null);
							$.cookie('tag-active-id',null);
							$.cookie('tag-active', $(".active-tag").attr("data-name"));
						    $.cookie('tag-active-id',$(".active-tag").attr("data-id"));
						}else{
							$('#rownumbers').removeClass('hidden');
							$("#successModal").find("h1").html('<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>');
							$('#rownumbers').html($.parseJSON(result).messages);
							$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>'}}).show();
						}
					},
					error:function(data){
						$(".notify").notify({type:"warning",message: { html: false, text:'<fmt:message key="tiles.views.user.index.script.user.excel.import.failed"/>'}}).show();
					}
				});	 
				 }else{
					 $(".showFileName").val("");
				     $(".fileerrorTip").html('<fmt:message key="tiles.views.user.index.script.user.excel.upload.tip"/>').show();
				 }
				}
			function refresh() {
				window.location.reload();  
			}	
			$(function() {
				$("#showFileName").disable = true;
				if($.cookie("tag-active")=="null" ||$.cookie("tag-active")==undefined){ 
				$("#tree-gourp-list").each(function() {
					$(this).children().children().first().click();
					$(".active-tag").parent().parent().prev().click()
				});
				} 
				 else{
					 var name=$.cookie("tag-active");
					 var id=$.cookie("tag-active-id");
					$("li[data-id="+id+"]").click();
					$(".active-tag").parent().parent().prev().click()
					$.cookie("tag-active",null);
					$.cookie("tag-active-id",null);
				} 
				
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
			});
</script>
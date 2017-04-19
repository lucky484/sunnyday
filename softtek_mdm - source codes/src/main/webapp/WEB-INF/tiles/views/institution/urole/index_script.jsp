<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:url
	value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js"
	var="dataTableJs" />
<script src="${dataTableJs}"></script>
<spring:url
	value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js"
	var="dataTableBootstrapJs" />
<script src="${dataTableBootstrapJs}"></script>
<spring:url value="/institution/urole/pages" var="pagesUrl" />
<spring:url value="/institution/urole/query" var="queryUrl" />
<spring:url value="/institution/urole/delete" var="deleteUrl" />
<spring:url value="/institution/urole/account" var="accountUrl" />
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
 
 $(function(){
	  autoLoad();
 });
 
 //显示新增角色模态框
 function add(){
	 $("#addRoleFrm").parsley().reset();
	 document.forms[0].reset();
	 $("#addRoleModal").find(":checked").each(function(){
		 $(this).removeAttr("checked");
	 });
	 $("#addRoleModal").modal(open);
 }
 
 function chooseAuth(obj){
	 if($(obj).hasClass("fa-chevron-right")){
		 $(obj).removeClass("fa-chevron-right");
		 $(obj).addClass("fa-chevron-down");
		 $(obj).parent().siblings().each(function(){
			 $(this).removeClass("hidden");
		 });
	 }else{
		 $(obj).removeClass("fa-chevron-down");
		 $(obj).addClass("fa-chevron-right");
		 $(obj).parent().siblings().each(function(){
			 $(this).addClass("hidden");
		 });
	 }
	 
 }
 //选择菜单
 function chooseMenu(obj){
	 if(!$(obj).is(":checked")){
		 //取消选中
		 
		//将下属菜单都选中
		 $(obj).parent().parent().parent().find(":checkbox").each(function(k,ck){
			 	var v=$(ck).val();
    			var islast=$(ck).attr('data-last');
    			var ishead=$(ck).attr('data-head');
    			var txt=$(ck).parent().text().trim();
    		 if(ishead=="true"){
    			 $(ck).parent().html('<input type="checkbox"  data-head="'+ishead+'" data-last="'+islast+'" onclick="chooseMenu(this)" value="'+v+'"><i></i>'+'<span class="font-bold">'+txt+'</span>');
    		 }else{
    			 if(islast=="false"){
    				 $(ck).parent().html('<input type="checkbox"  data-head="'+ishead+'" data-last="'+islast+'" onclick="chooseMenu(this)" value="'+v+'"><i></i>'+txt); 
    			 }else{
    				 if($(obj).attr("data-last")=="false"){
    					 $(ck).parent().html('<input type="checkbox" data-head="'+ishead+'" data-last="'+islast+'" onclick="chooseMenu(this)" value="'+v+'"><i></i>'+txt);
    				 }else{
    					 if(v==$(obj).val()){
	    					 $(ck).parent().html('<input type="checkbox" data-head="'+ishead+'" data-last="'+islast+'" onclick="chooseMenu(this)" value="'+v+'"><i></i>'+txt);
	    				 }
    				 }
    			 }
    		 }
			
		 });
		 
		 
	 }else{
		 //选中
		 //将上级菜单选中
		 if($(obj).attr('data-head')=="false"){
			 if($(obj).attr('data-last')=="false"){
				 //二级菜单
				 var pobj=$(obj).parent().parent().parent().parent().children(".checkbox").find("input");
				 var v=$(pobj).val();
    			 var islast=$(pobj).attr('data-last');
    			 var ishead=$(pobj).attr('data-head');
    			 var txt=$(pobj).parent().text().trim();
    			 $(pobj).parent().html('<input type="checkbox" checked data-head="'+ishead+'" data-last="'+islast+'" onclick="chooseMenu(this)" value="'+v+'"><i></i>'+'<span class="font-bold">'+txt+'</span>');
			 }else{
				 var pobj=$(obj).parent().parent().parent().parent().parent().children(".checkbox").find("input");
				 var v=$(pobj).val();
    			 var islast=$(pobj).attr('data-last');
    			 var ishead=$(pobj).attr('data-head');
    			 var txt=$(pobj).parent().text().trim();
    			 $(pobj).parent().html('<input type="checkbox" checked data-head="'+ishead+'" data-last="'+islast+'" onclick="chooseMenu(this)" value="'+v+'"><i></i>'+'<span class="font-bold">'+txt+'</span>');
				//三级菜单
				  pobj=$(obj).parent().parent().parent().parent().children(".checkbox").find("input");
				  v=$(pobj).val();
    			  islast=$(pobj).attr('data-last');
    			  ishead=$(pobj).attr('data-head');
    			  txt=$(pobj).parent().text().trim();
    			 $(pobj).parent().html('<input type="checkbox" checked data-head="'+ishead+'" data-last="'+islast+'" onclick="chooseMenu(this)" value="'+v+'"><i></i>'+'<span class="font-bold">'+txt+'</span>');
			 }
			
		 }
		 
		 //将下属菜单都选中
			 $(obj).parent().parent().parent().find(":checkbox").each(function(k,ck){
				 	var v=$(ck).val();
	    			var islast=$(ck).attr('data-last');
	    			var ishead=$(ck).attr('data-head');
	    			var txt=$(ck).parent().text().trim();
	    		 if(ishead=="true"){
	    			 $(ck).parent().html('<input type="checkbox" checked data-head="'+ishead+'" data-last="'+islast+'" onclick="chooseMenu(this)" value="'+v+'"><i></i>'+'<span class="font-bold">'+txt+'</span>');
	    		 }else{
	    			 if(islast=="false"){
	    				 $(ck).parent().html('<input type="checkbox" checked data-head="'+ishead+'" data-last="'+islast+'" onclick="chooseMenu(this)" value="'+v+'"><i></i>'+txt); 
	    			 }else{
	    				 if($(obj).attr("data-last")=="false"){
	    					 $(ck).parent().html('<input type="checkbox" checked data-head="'+ishead+'" data-last="'+islast+'" onclick="chooseMenu(this)" value="'+v+'"><i></i>'+txt);
	    				 }else{
	    					 if(v==$(obj).val()){
		    					 $(ck).parent().html('<input type="checkbox" checked data-head="'+ishead+'" data-last="'+islast+'" onclick="chooseMenu(this)" value="'+v+'"><i></i>'+txt);
		    				 }
	    				 }
	    				 
	    			 }
	    			 
	    		 }
				
			 });
	 }
	if($("#addRoleFrm").find(":checked").length>0){
		$("#ckrequire-error").addClass("hidden");
	}
 }
    
 //查询角色信息
  function query(rid){
  	$("#queryRoleModal").find(":checkbox").each(function(){
	var v=$(this).val();
	var txt=$(this).parent().text().trim();
	$(this).parent().html('<input type="checkbox"  value="'+v+'"><i></i>'+txt);
	});
  	$.get("${queryUrl}",{rid:rid},function(result){
  		$(".q-r-n").val(result.role.name);
  		$(".q-r-m").text(result.role.mark);
  		
  		
  		$("#queryRoleModal").find(":checkbox").each(function(){
  			var v=$(this).val();
  			var txt=$(this).parent().text().trim();
  			for(var i=0;i<result.list.length;i++){
  				if($(this).val().trim()==result.list[i].menu.id.toString()){
  					$(this).parent().html('<input type="checkbox" checked value="'+v+'"><i></i>'+txt);
  				}
  			}
  			
  		});
  	},"json");
  }
    
//修改角色信息
  function modify(rid){
	 $("#updateRoleFrm").parsley().reset();
	 $("#modifyRoleModal").find(":checked").each(function(){
		 $(this).removeAttr("checked");
 	});
  	$.get("${queryUrl}",{rid:rid},function(result){
  		$(".r_id").val(result.role.id);
  		$(".m-r-n").val(result.role.name);
  		$(".m-r-m").text(result.role.mark);
  		$("#modifyRoleModal").find(":checkbox").each(function(){
  			for(var i=0;i<result.list.length;i++){
  				if($(this).val().trim()==result.list[i].menu.id.toString()){
  					var v=$(this).val();
      				var txt=$(this).parent().text().trim();
      				var islast=$(this).attr('data-last');
  	    			var ishead=$(this).attr('data-head');
  	    			if(ishead=="true"){
  	    				$(this).parent().html('<input type="checkbox" checked data-head="'+ishead+'" data-last="'+islast+'" onclick="chooseMenu(this)" value="'+v+'"><i></i>'+'<span class="font-bold">'+txt+'</span>');
  	    			}else{
  	    				if(islast!="true"){
  	    					$(this).parent().html('<input type="checkbox" checked data-head="'+ishead+'" data-last="'+islast+'" onclick="chooseMenu(this)" value="'+v+'"><i></i>'+txt);
  	    				}else{
  	    					$(this).parent().html('<input type="checkbox" checked data-head="'+ishead+'" data-last="'+islast+'" value="'+v+'"><i></i>'+txt);
  	    				}
  	    			}
  				}
  			}
  		});
  		
  		//修改校验名称是否存在
  		var modify_rid=$(".r_id").val().trim();
  		$(".role-modify-name").parsley().addAsyncValidator('modifyExistsValidate',function(xhr){
  					return xhr.responseText.indexOf('true') >= 0; 
  				},"${accountUrl}",
  				 { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8","data": { "rid": modify_rid } } );
  		},"json");
  }
    
  //显示删除用户模态框
  function deleteRole(rid){
  	$('#delModal').find(".role-del-pk").val(rid);
  	$('#delModal').modal(open);
  }
  
  //从服务器删除
  function truncateRole(){
  	var rid=$('#delModal').find(".role-del-pk").val();
  	var csrf="${_csrf.token}";
  	$.post("${deleteUrl}",{rid:rid,_csrf:csrf},function(result){
  		$('#delModal').modal("hide");
  		result=eval('['+result+']');
 			$(".notify").notify({type:result[0].type,message: { html: false, text: result[0].message}}).show();
 			autoLoad();
 			
  	})
  }
    
  $(function(){
	  var msg="${msg}";
		if(msg.trim().length>0){
			$(".notify").notify({type:"${type}",message: { html: false, text: '${msg}'}}).show();
		}
		
		//新增校验名称是否存在
		$(".role-name").parsley().addAsyncValidator(
			'existsValidate',function(xhr){
				return xhr.responseText.indexOf('true') >= 0; 
			},"${accountUrl}",
			 { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8"} );
		
		
		
	//添加角色
	var validator = $('#addRoleFrm').parsley();
  	$("#addRoleFrm").submit(function(){
  		var rids="";
  		
  		$("#addRoleModal").find(":checkbox").each(function(){
  			if($(this).is(":checked")){
  				rids=$(this).val()+","+rids;
  			}
  		});
		rids=rids.substr(0,rids.length-1);
		$("input[name='rids']").attr('value',rids);
		validator.validate();
        if(validator.isValid()){
        	if(rids.length==0){
    			//$(".notify").notify({type:"danger",message: { html: false, text: "请选择必选字段"}}).show();
    			$("#ckrequire-error").removeClass("hidden");
    			return false;
    		}else{
    			$(".add-Role").attr("disabled","true");
    			return true;
    		}
        }else{
        	if(rids.length==0){
    			$(".notify").notify({type:"danger",message: { html: false, text: "<fmt:message key='tiles.views.institution.urule.newly.rule.mustrequired'/>"}}).show();
    			return false;
    		}else{
    			$(".add-Role").attr("disabled","true");
    			return true;
    		}
        }
  	});
  	
  	//修改角色
  	modify_name= $('#updateRoleFrm').parsley();
  	$("#updateRoleFrm").submit(function(){
  		var  modify_name= $('.role-modify-name').parsley();
  		var rids="";
  		if($("#updateRoleFrm").find(":checked").length==0){
  			$("#ckrequire-modify-error").removeClass("hidden");
  			return false;
  		}
  		$("#updateRoleFrm").find(":checkbox").each(function(){
  			if($(this).is(":checked")){
  				rids=$(this).val()+","+rids;
  			}
  		});
		rids=rids.substr(0,rids.length-1);
		$("input[name='m_rids']").attr('value',rids);
		modify_name.validate();
        if(modify_name.isValid()){
        	if(rids.length==0){
    			$(".notify").notify({type:"danger",message: { html: false, text: "<fmt:message key='tiles.views.institution.urule.choose'/>"}}).show();
    			return false;
    		}
        	return true;
        }
  	});
});
  
  function searchRoleLists()
  {
	  autoLoad();
	  $('#roleTable').dataTable().fnDraw();
  }
  
  function cleanRoleLists()
  {
	  $("#searchrolename").val('');
	  autoLoad();
	  $('#roleTable').dataTable().fnDraw();
  }
  
  //自动加载datetable
  function autoLoad(){
	  var searchrolename = $("#searchrolename").val();
	  $('#roleTable').dataTable(
				{
					"dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
					/* "dom": 'rt<"bottom"pli><"clear">', */
				    "autoWidth": false,
				    "searching" : false,
					"stateSave" : true,
					"ordering" : false,
					"bSort":false,
					"pageLength" : 10,
					"pagingType" : "full_numbers",
					"serverSide" : true,
					"bDestroy" : true,
					"oLanguage":{
						"sUrl":languageUrl
					},
					"ajax" : {
						"url" : "${pagesUrl}",
						"type" : "get",
						"dataSrc" : "data",
						"data" :
							{
								"searchrolename" : searchrolename
							}
					},
					"columns" : [ {
						data : "name"
					}, {
						data : "mark"
					},
					{
						data : "mark"
					},
					],
					columnDefs : [
							{
								className: "col-lg-3",
								targets : [ 0 ],
								render : function(
										data, type,
										full, meta) {
									return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+full.name+'"><span class="text-ellipsis"><a href="javascript:void(0);" class="text-primary" onclick="query('+full.id+')" data-toggle="modal" data-target="#queryRoleModal">'+full.name+'</a></span><div>';
								}
							},
							{
								className: "col-lg-9 datatb-max-width-rolemark",
								targets : [ 1 ],
								render : function(
										data, type,
										full, meta) {
									return '<div data-toggle="tooltip"  class="atip" data-placement="bottom" onMouseOver="$(this).tooltip('+"'show'"+')" data-title="'+full.mark+'"><span class="text-ellipsis">'+full.mark+'</span><div>';
								}
							},
							{
								targets : [ 2 ],
								render : function(
										data, type,
										full, meta) {

									return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
											+ '<i class="i  i-settings"></i>'
											+ '</a>'
											+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-70px;">'
											+ '<li><a href="javascript:void(0)" onclick="query('+full.id+')" data-toggle="modal" data-target="#queryRoleModal"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="tiles.views.institution.urule.view"/></a></li>'
											+ ' <li><a href="javascript:void(0);" onclick="modify('+full.id+')" data-toggle="modal" data-target="#modifyRoleModal"><i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.views.institution.urule.modify"/></a></li>'
											+ '<li><a href="javascript:void(0);" onclick="deleteRole('+full.id+')"><i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;<fmt:message key="tiles.views.institution.device.rule.add.type.rule.delete"/></span></a></li>'
											+ '</ul>';
								}
							}, ]
				});
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
  </script>
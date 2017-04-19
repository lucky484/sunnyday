<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	var parentId;
	var cuttentParentId;
	var moveDepartment;
	$(function() {
		var defaultData = ${tree};
		$('#tree').treeview({
			expandIcon : 'glyphicon glyphicon-chevron-right',
			collapseIcon : 'glyphicon glyphicon-chevron-down',
			color : "#428bca",
			showBorder : false,
			data : defaultData,
		});
        
		$('#moveTree').treeview({
			color : "#428bca",
			showBorder : false,
			data : defaultData
		});
		var departmentId = $("#departmentId").val();
		var myNode=$('#tree').treeview('getUnselected');
		if(departmentId != "" && departmentId != null){
	        for(var i=0;i<myNode.length;i++){
	        	if(parseInt(departmentId) == myNode[i].tags.id){
	        		getParent(myNode[i]);
	        		$('#tree').treeview('selectNode', [ myNode[i].nodeId, {silent : true} ]);
	        		window.sessionStorage.removeItem("departmentId");
	        		window.sessionStorage.removeItem("moveDepartmentId");
	        		loadData(myNode[i]);
	        	}
	        }
		}else if(window.sessionStorage.moveDepartmentId != undefined && window.sessionStorage.moveDepartmentId != "null"){
			for(var i=0;i<myNode.length;i++){
	        	if(parseInt(window.sessionStorage.moveDepartmentId) == myNode[i].tags.id){
	        		getParent(myNode[i]);
	        		$('#tree').treeview('selectNode', [ myNode[i].nodeId, {silent : true} ]);
	        		window.sessionStorage.removeItem("departmentId");
	        		loadData(myNode[i]);
	        	}
	        }
		}else if(window.sessionStorage.departmentId != undefined && window.sessionStorage.departmentId != null){
			for(var i=0;i<myNode.length;i++){
	        	if(parseInt(window.sessionStorage.departmentId) == myNode[i].tags.id){
	        		getParent(myNode[i]);
	        		$('#tree').treeview('selectNode', [ myNode[i].nodeId, {silent : true} ]);
	        		loadData(myNode[i]);
	        	}
	        }
		}else{
			$('#tree').treeview('selectNode', [0, {silent : true} ]);
			var node = $('#tree').treeview('getNode', [ 0, {silent : true} ]);
			showNodeInfo(node);
		}
		$('#tree').on('nodeSelected',function(event, data) {
				loadData(data);
		});
		function loadData(data){
			$("#ste_modify_frm").parsley().reset();
			moveDepartment = data.tags.name; //要移动部门的名称
			parentId = data.tags.id;
			 $(".btn-toolbar").show();
			if(data.tags.parent != null){
			cuttentParentId = data.tags.parent.id;
			}
			if (data.nodeId == 0) {
				var node = $('#tree').treeview('getNode',[ 0, {silent : true} ]);
				showNodeInfo(node);
			} else {
				$('.d_base').addClass('hidden');
				$(".btn-toolbar").hide();
				if("${softtek_manager.auth}"!="0"||"${softtek_manager.user}"==""){
					$('#btn-del').removeClass('hidden');
					$('.btn-move').removeClass("hidden");
					$(".btn-toolbar").show();
				}else{
					$('#updateOrg').addClass('hidden');
				}
				$('#ste_modify_frm_id').attr("value",data.tags.id);
				$('#ste_modify_frm_name').val(data.tags.name);
				$('#ste_modify_frm_name_hide').val(data.tags.name);
				$('#ste_modify_frm_sub').text(data.tags.parent.name);
				$('.modal-frm-sub').text(data.tags.name);
				$('.modal-frm-id').attr('value', data.tags.id);
				$('.department_name').text(data.tags.name);
				$('#ste_modify_frm_memo').val(data.tags.mark);
				$('#ste_modify_frm_time').text(data.tags.createDateStr);
				$('#ste_modify_frm_createtype').val(data.tags.createType);
				$('#ste_modify_frm_weight').val(data.tags.weight);
				$("#ste_modify_frm_email").val(data.tags.email);
				$('#ste_modify_frm').removeClass('hidden');
			}
			//修改的异步校验
			$("#ste_modify_frm_name").parsley().addAsyncValidator(
							'checknameUpdate',
							function(xhr) {
								return !(xhr.responseJSON == true);
							},
							'org/checkName',
							{
								"type" : "GET",
								"dataType" : "json",
								"contentType" : "application/json; charset=utf-8",
								"data" : {
									nameInit : $("#ste_modify_frm_name_hide").val(),
									id : cuttentParentId
								}
							},'json');
		}
		function showNodeInfo(node) {
			if("${softtek_manager.user}" != ""){
				$(".btn-toolbar").hide();
			}
			$('#ste_modify_frm').addClass('hidden');
			$('.btn-move').addClass("hidden");
			$('.modal-frm-sub').text(node.tags.name);
			$('.modal-frm-id').attr('value', node.tags.id);
			$('#btn-del').addClass('hidden');
			$('.d_base').removeClass('hidden');
			$('.d_name').text(node.tags.name);
			$('.d_master').text("<fmt:message key='tiles.institution.org.department.jsp_script' />");
			$('.d_memo').text(node.tags.mark.trim() == "" ? "<fmt:message key='tiles.institution.org.department.not.have' />" : node.tags.mark.trim());
		}
		function getParent(node){
			var obj = node;
			while(obj.parentId != null){
				var parentNode = $('#tree').treeview('getParent',  [obj.nodeId, {silent : true} ]);
				$('#tree').treeview('expandNode', [ parentNode.nodeId,{ silent: true}]);
				obj = parentNode;
			}
		}
		function timeStamp2String(time) {
			var datetime = new Date();
			datetime.setTime(time);
			var year = datetime.getFullYear();
			var month = datetime.getMonth() + 1;
			var date = datetime.getDate();
			var hour = datetime.getHours();
			var minute = datetime.getMinutes();
			var second = datetime.getSeconds();
			return year + "-" + month + "-" + date + " " + hour + ":" + minute+ ":" + second;
		}
	});
	var id;
	var parentDepartmentName;
	var result;
	$(function() {
	var csrf = "${_csrf.token}";
		$("#moveTree").on('nodeSelected', function(event, data) {
			id = data.tags.id;
			$.post("org/queryChildName",{id:id,name:moveDepartment,_csrf:csrf},function(data){
				result = data;
			},'json');
		});
	});
	//移动部门
	$("#submit").click(function() {
		window.sessionStorage.moveDepartmentId = parentId;
		//移动的部门和目的部门的校验
		if (id == parentId) {
			$("#moveDepartmentP").modal();
		} else {
			//移动的目标部门中是否有同名部门的校验
			if (id == cuttentParentId) {
				$("#moveDepartmentPr").modal();
			} else {
				//如果目的部门中存在同名部门则提示消息
				if(!result){
					$("#sameNameMsg").modal();
				}else{
					var csrf = "${_csrf.token}";
					$.post("org/queryParentById",{id:id,_csrf:csrf},function(data) {
						if (data.structure != null) {
							var parentId1 = data.structure.parent.id;
							if (parentId1 == parentId) {
								$("#moveDepartment").modal();
							} else {
								var _this = $(this);
								_this.attr("disabled",true);
								var csrf = "${_csrf.token}";
								$.post("org/updateParentIdById", {
									id : id,
									parentId : parentId,
									_csrf:csrf
								}, function(data) {
									window.location.reload();
								});
							}
						} else {
							var _this = $(this);
							_this.attr("disabled",true);
							var csrf = "${_csrf.token}";
							$.post("org/updateParentIdById", {
								id : id,
								parentId : parentId,
								_csrf:csrf
							}, function(data) {
								window.location.reload();
							});
						}
					}, 'json');
				}
			}
		}
	});
	//判断删除部门时是否有关联用户
	$("#btn-del").click(function() {
			var csrf = "${_csrf.token}";
			if("${softtek_manager.user}" != ""){
				var node = $("#tree").treeview('getSelected');
				var createBy;
				for(var i=0;i<node.length;i++){
					createBy = node[i].tags.createBy;
				}
				var id = "${softtek_manager.user.id}";
				if(createBy != parseInt(id)){
					Modal.confirm().on(function(e){
						if(e==true){
							$.post("org/queryExitsUser?id="+ $("#ste_modify_frm_id").val()+"&_csrf="+csrf,
									function(data) {
										if (data.count != 0) {
											$("#show").modal();
										} else {
											$("#delModal").modal();
										}
									}, 'json');
						}
					});
				}else{
					$.post("org/queryExitsUser?id="+ $("#ste_modify_frm_id").val()+"&_csrf="+csrf,
							function(data) {
								if (data.count != 0) {
									$("#show").modal();
								} else {
									$("#delModal").modal();
								}
							}, 'json');
				}
			}else{
				$.post("org/queryExitsUser?id="+ $("#ste_modify_frm_id").val()+"&_csrf="+csrf,
						function(data) {
							if (data.count != 0) {
								$("#show").modal();
							} else {
								$("#delModal").modal();
							}
						}, 'json');
				}
			});
/* 	//部门管理员删除
	$("#deleteOrg").click(function(){
	
	}); */
	//删除部门
	$("#delBtn").click(function() {
				var csrf = "${_csrf.token}";
				$.post("org/deleteStructureById?id="+ $("#ste_modify_frm_id").val()+"&_csrf="+csrf, function(data) {
					if (data.result = data.idList.length) {
						window.location.reload();
					}
				}, 'json');
			});
	$("#insertOrg").click(function() {
		if($("input[name='parent.id']").val().trim().length==0){
			$(".notify").notify({type:"danger",message: { html: false, text: "<fmt:message key='tiles.institution.org.department.parentid.not.set'/>"}}).show();
			return false;
		}
		$("#myModal").modal();
		$("#insertForm")[0].reset();
		$("#insertForm").parsley().reset();
		//异步校验部门名称
		$("#name").parsley().addAsyncValidator('checkname', function(xhr) {
			return !(xhr.responseJSON == true);
		}, 'org/queryExitsName', {
			"type" : "GET",
			"dataType" : "json",
			"contentType" : "application/json; charset=utf-8",
			"data" : {
				id : $(".modal-frm-id").val()
			}
		},'json');

	});
	$("#updateOrg").click(function(){
		window.sessionStorage.removeItem("moveDepartmentId");
		var id = $("#ste_modify_frm_id").val();
		window.sessionStorage.departmentId = id;
		if("${softtek_manager.user}" != ""){
			var node = $("#tree").treeview('getSelected');
			var createBy;
			for(var i=0;i<node.length;i++){
				createBy = node[i].tags.createBy;
			}
			var id = "${softtek_manager.user.id}";
			if(createBy != parseInt(id)){
				Modal.confirm().on(function(e){
					if(e==true){
						$("#ste_modify_frm").submit();
					}
				});
			}else{
				$("#ste_modify_frm").submit();
			}
		}else{
			$("#ste_modify_frm").submit();
		}
	});
/* 	$("#confirm").click(function(){
		$("#ste_modify_frm").submit();
	}); */
	
	$("#move").click(function(){
		if("${softtek_manager.user}" != ""){
			var node = $("#tree").treeview('getSelected');
			var createBy;
			for(var i=0;i<node.length;i++){
				createBy = node[i].tags.createBy;
			}
			var id = "${softtek_manager.user.id}";
			if(createBy != parseInt(id)){
				Modal.confirm().on(function(e){
					if(e==true){
						$("#moveModal").modal();
					}
				});
			}else{
				$("#moveModal").modal();
			}
		}else{
			$("#moveModal").modal();
		}
	});
	/* $("#moveOrg").click(function(){
		$("#moveModal").modal();
	}); */
</script>
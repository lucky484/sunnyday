<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.ResourceBundle"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/institution/picAndTxtMeg/getAllPicAndTxts" var="pagesUrl" />
<spring:url value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js" var="dataTableJs" />
<script src="${dataTableJs}"></script>
<spring:url value="/resources/js/autocomplete/jquery-ui.min.js" var="jqueryUiJs"/>
<script src="${jqueryUiJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js" var="dataTableBootstrapJs" />
<script src="${dataTableBootstrapJs}"></script>
<spring:url value="/resources/js/jquery.tmpl.js" var="jqueryTmplJs" />
<script src="${jqueryTmplJs}"></script>
<spring:url value="/resources/js/summernote/summernote.js" var="summernoteJs" />
<script src="${summernoteJs}"></script>
<spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
<spring:url value="/institution/picAndTxtMeg/validMsTitle" var="ckNameUrl" />
<spring:url value="/institution/picAndTxtMeg/viewMember" var="viewMember" />
<%ResourceBundle res = ResourceBundle.getBundle("file"); %> 
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
var idArray = [];
var nameArray = [];
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
	
	function getDisplayPath(){
			
	}
	
	// 新增图文消息
	function addPicAndTxtMsg() {
		$('#addFrm').parsley().reset();
		document.forms[0].reset();
		idArray = [];
	    nameArray = [];
		$('#editor').summernote('code','');
		$("#addFrm").find(".parsley-success").each(function(){
			$(this).removeClass("parsley-success");
		});
		
		$('#tree').treeview('collapseAll', { silent: true });
		
		//$("#modelTitle").html("新增图文消息");
		$("#msgTitle").parsley().addAsyncValidator(
				'existsValidate',function(xhr){
					return !(xhr.responseText.indexOf('true') >= 0); 
				},"${ckNameUrl}",
				 { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {}});
		$("#addModal").modal(open);
	}
	//查看图文消息
	function viewPicAndTxtMsg(id){
		//$("#modelTitle").html("显示图文消息");
		//从后台取出对应的图文消息显示到页面上
		var csrf="${_csrf.token}"; 
		idArray = [];
	    nameArray = [];
		$.ajax({
			"dataType": 'json',
			"data": {"id":id,"_csrf":csrf},
			"type": "POST",
			"url": ctx + "/institution/picAndTxtMeg/getPicAndTxtById",
			success: function(data) {
				var treeData = ${tree};
				//生成树形结构
				$('#viewTree').treeview({
					color : "#428bca",
					showBorder : false,
					collapseAll : true,
					showCheckbox : true,
					multiSelect : true,
					highlightSelected:false,
					data : treeData
				});
				
				/* $('#viewTree').treeview('expandAll', { silent: true }); */
				$('#viewTree').treeview('collapseAll', { silent: true });
				/* $('#viewTree').treeview('disableAll', { silent: true }); */
				$('#showMsgTitle').val(data.pushMobileMsgModel.msgTheme);
				$('#showEditor').summernote('code',data.pushMobileMsgModel.content);
				
				
				// 初始化设置未选中
	        	// 部门
	        	var departIds = "";
				var departIdArr = data.pushMobileMsgModel.departIds.split(',');
	    	   	var nodes = $('#viewTree').treeview('getUnselected');
	    		$.each(departIdArr, function(i,val){       
					for (var i = 0; i < nodes.length; i++) {
						if (nodes[i].tags.id == val) {
							departIds += nodes[i].tags.id + ",";
							$('#viewTree').treeview('checkNode',[ nodes[i].nodeId, {silent : true} ]);
						}
					}
	        	});
	    		
	    		var nodeChecked=$('#viewTree').treeview('getChecked');
				banCheckedAction(nodeChecked);
	    		
	    		var virtualList = data.pushMobileMsgModel.virtualIds.split(',');
	        	$.each(virtualList, function(i,val){    
	        		var virtualId = $("#viewVirtu"+val);
	        		virtualId.prop("checked",true);
		        });
	        	
	        	var userList = data.userList;
	        	$.each(userList, function(i,val){ 
	        		viewDisplayArray(val.id, val.realname+"("+val.username+")", 0);
		        }); 
			}
		});
		
		$('#showValidatePart .note-editable').attr('contenteditable','false');
		$('#showValidatePart .note-control-selection-bg').click(function(){
			$(this).attr('display','none');
		});
		/* $('#addModal').modal('show'); */
		
		$('#showModal').modal('show');
	}
	
	function banCheckedAction(obj)
	{
		$('#viewTree').on('nodeChecked', function(event, data) {
			var ii =0;
			for(var i=0; i < obj.length;i++)
			{
				
				if (data.nodeId == obj[i].nodeId)
				{
					ii++;
				}
			}
			
			if (ii > 0)
			{
				$('#viewTree').treeview('checkNode', [ data.nodeId, { silent: true } ]);
			}
			else
			{
				$('#viewTree').treeview('uncheckNode', [data.nodeId, { silent: true } ]);
			}
		});
		
		
		$('#viewTree').on('nodeUnchecked', function(event, data) {
			var ii =0;
			for(var i=0; i < obj.length;i++)
			{
				
				if (data.nodeId == obj[i].nodeId)
				{
					ii++;
				}
			}
			
			if (ii > 0)
			{
				$('#viewTree').treeview('checkNode', [data.nodeId, { silent: true } ]);
			}
			else
			{
				$('#viewTree').treeview('uncheckNode', [ data.nodeId, { silent: true } ]);
				
			}
		});
	}
	
	
	//修改图文消息
	function updatePicAndTxtMsg(id, isenable){
		
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
		idArray = [];
	    nameArray = [];
		$('#updateFrm').parsley().reset();
		document.forms[0].reset();
		$("#updateFrm").find(".parsley-success").each(function(){
			$(this).removeClass("parsley-success");
		});
		//$("#modelTitle").html("修改图文消息");
		//从后台取出对应的图文消息显示到页面上
		var csrf="${_csrf.token}"; 
		$.ajax({
			"dataType": 'json',
			"data": {"id":id,"_csrf":csrf},
			"type": "POST",
			"url": ctx + "/institution/picAndTxtMeg/getPicAndTxtById",
			success: function(data) {
				
				var treeData = ${tree};
				//生成树形结构
				$('#eidtTree').treeview({
					color : "#428bca",
					collapseAll : true,
					showBorder : false,
					showCheckbox : true,
					multiSelect : true,
					highlightSelected:false,
					data : treeData
				});
				
				$('#eidtTree').treeview('collapseAll', { silent: true });
				/* $('#eidtTree').treeview('disableAll', { silent: true }); */
				$('#showMsgTitle').val(data.pushMobileMsgModel.msgTheme);
				
				$('#msgId').val(data.pushMobileMsgModel.id);
				$('#editMsgTitle').val(data.pushMobileMsgModel.msgTheme);
				$('#editEditor').summernote('code',data.pushMobileMsgModel.content);
				
				
				// 初始化设置未选中
	        	// 部门	        		
	        	var departIds = "";
				var departIdArr = data.pushMobileMsgModel.departIds.split(',');
	    	   	var nodes = $('#eidtTree').treeview('getUnselected');
	    		$.each(departIdArr, function(i,val){       
					for (var i = 0; i < nodes.length; i++) {
						if (nodes[i].tags.id == val) {
							departIds += nodes[i].tags.id + ",";
							$('#eidtTree').treeview('checkNode',[ nodes[i].nodeId, {silent : true} ]);
						}
					}
	        	});
	    		
	    		var virtualList = data.pushMobileMsgModel.virtualIds.split(',');
	        	$.each(virtualList, function(i,val){    
	        		var virtualId = $("#eidtVirtu"+val);
	        		virtualId.prop("checked",true);
		        });
	        	
	        	var userList = data.userList;
	        	$.each(userList, function(i,val){ 
	        		viewDisplayArray(val.id, val.realname+"("+val.username+")", 1);
		        }); 
			}
		});
		
		$("#editMsgTitle").parsley().addAsyncValidator(
				'existsEditValidate',function(xhr){
					return !(xhr.responseText.indexOf('true') >= 0); 
				},"${ckNameUrl}",
				 { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {"titleId":id}});
		$("#editModal").modal(open);
	}
	
	//修改图文消息
	function editMessage(){
		var validator = $('#updateFrm').parsley();
		validator.validate();
		if(validator.isValid()){
			var msgContent =  $('#editEditor').summernote('code').replace('<p><br></p>','');
			if(msgContent == null || msgContent == ''){
				$("#modifyWarningMsg").removeClass('hidden');
				return false;
			}else{
				var id = $('#msgId').val();
				var msgTitle = $('#editMsgTitle').val();
				var msgContent =  $('#editEditor').summernote('code');
				
				
				var departIds="";
				var nodeChecked=$('#eidtTree').treeview('getChecked');
				if(nodeChecked.length>0){
					for(var i=0;i<nodeChecked.length;i++){
						departIds=nodeChecked[i].tags.id+","+departIds;
					}
					departIds=departIds.substr(0,departIds.length-1);
				}
				
			 	var virtualIds = "";
			  	$('input[name="editVirtualIds"]:checked').each(function(){ 
				   var tempVal = $(this).val();
				   virtualIds+=tempVal+",";
			   	}); 
			   	if(virtualIds!=""){
				   virtualIds = virtualIds.substring(0,virtualIds.length-1);
			   	}
			   	
			   	var userIds = "";
				if(null!=idArray&&idArray.length>0){
					for(var i=0;i<idArray.length;i++){
						userIds += idArray[i]+",";
					}
					if(userIds!=""){
						userIds = userIds.substring(0,userIds.length-1);
					}
				}
				
				if (virtualIds =='' && userIds == '' && departIds == '')
				{
					$(".notify").notify({
						type : 'error',
						message : {
							html : false,
							text : "<fmt:message key='tiles.views.institution.device.rule.table.inputatleastone'/>"
						}
					}).show();
					
					return;
				}
				
				
				//从后台取出对应的图文消息显示到页面上
				var csrf="${_csrf.token}"; 
				$.ajax({
					"dataType": 'json',
					"data": {"id":id,
						"msgTitle":msgTitle,
						"msgContent":msgContent,
						"_csrf":csrf,
						"departIds":departIds,
						"virtualIds":virtualIds,
						"userIds":userIds},
					"type": "POST",
					"url": ctx + "/institution/picAndTxtMeg/updateMessage",
					success: function(data) {
						$('#editMsgTitle').val('');
						$('#editEditor').summernote('code','');
						$("#editModal").modal("hide");
						window.location.reload();
						//$('#succEditModal').modal('show');
					}
				});
			}
		}
	}
		
	//保存图文消息
	function saveMessage(){
		var validator = $('#addFrm').parsley();
		validator.validate();
		if(validator.isValid()){
			var msgContent =  $('#editor').summernote('code').replace('<p><br></p>','');
			if(msgContent == null || msgContent == ''){
				$("#showWarningMsg").removeClass('hidden');
				return false;
			}else{
				$("#showWarningMsg").addClass('hidden');
				var msgTitle = $('#msgTitle').val();
				var msgContent = $('#editor').summernote('code');
				
				var departIds="";
				var nodeChecked=$('#tree').treeview('getChecked');
				if(nodeChecked.length>0){
					for(var i=0;i<nodeChecked.length;i++){
						departIds=nodeChecked[i].tags.id+","+departIds;
					}
					departIds=departIds.substr(0,departIds.length-1);
				}
				
			 	var virtualIds = "";
			  	$('input[name="virtualIds"]:checked').each(function(){ 
				   var tempVal = $(this).val();
				   virtualIds+=tempVal+",";
			   	}); 
			   	if(virtualIds!=""){
				   virtualIds = virtualIds.substring(0,virtualIds.length-1);
			   	}
			   	
			   	var userIds = "";
				if(null!=idArray&&idArray.length>0){
					for(var i=0;i<idArray.length;i++){
						userIds += idArray[i]+",";
					}
					if(userIds!=""){
						userIds = userIds.substring(0,userIds.length-1);
					}
				}
				
				if (virtualIds =='' && userIds == '' && departIds == '')
				{
					$(".notify").notify({
						type : 'error',
						message : {
							html : false,
							text : "<fmt:message key='tiles.views.institution.device.rule.table.inputatleastone'/>"
						}
					}).show();
					
					return;
				}
				$("#addModal #saveBtn a").eq(0).attr("disabled","true");
				//将内容中的图片地址和文字分开保存
				//将编辑器中的内容保存到数据库中
				var csrf="${_csrf.token}"; 
				$.ajax({
					"dataType": 'json',
					"data": {"msgTitle":msgTitle,
							 "msgContent":msgContent,
							 "_csrf":csrf,
							 "departIds":departIds,
							 "virtualIds":virtualIds,
							 "userIds":userIds },
					"type": "POST",
					"url": ctx + "/institution/picAndTxtMeg/saveMessage",
					success: function(data) {
						$("#addModal #saveBtn a").eq(0).removeAttr("disabled");
						$('#msgTitle').val('');
						$('#editor').summernote('code','');
						$("#addModal").modal("hide");
						window.location.reload();
						//$('#succModal').modal('show');
					}
				});
			}
		}
	}
	
	function cancelSaveMessage(){
		//去掉form中的验证
		$('#addFrm').parsley().reset();
		document.forms[0].reset();
		$("#addFrm").find(".parsley-success").each(function(){
			$(this).removeClass("parsley-success");
		});
		$('#addModal').modal(open);
		$('#msgTitle').val('');
		$('#editor').summernote('code','');
		
	}
	//删除图文消息
	function deletePicAndTxtMsg(id){
		var csrf="${_csrf.token}";
		$.ajax({
			"dataType": 'json',
			"data": {"id":id,"_csrf":csrf},
			"type": "POST",
			"url": ctx + "/institution/picAndTxtMeg/isMessageCreateUser",
			success: function(data) {
				$('#msgId').val(id);
				if (data.isCreateUser)
				{
					$("#delPicAndTxt").modal();
				}
				else
				{
					Modal.confirm().on(function(e){
						if(e==true){
							$("#delPicAndTxt").modal();
						}
					});
				}
			}
		});
	}
	
	function deleteMessage(){
		var id = $('#msgId').val();
		var csrf="${_csrf.token}"; 
		$.ajax({
			"dataType": 'json',
			"data": {"id":id,"_csrf":csrf},
			"type": "POST",
			"url": ctx + "/institution/picAndTxtMeg/deleteMessage",
			success: function(data) {
				$('#delPicAndTxt').modal('hide');
				window.location.reload();
				//$('#succDelModal').modal('show');
			}
		});
	}
	
	//查询图文消息方法
	function searchMsgLists(){
		//$('#PicAndTxtMsgList').dataTable().fnClearTable();
		var name = $('#name').val();
		LoadMsgDtList();
		$('#PicAndTxtMsgList').dataTable().fnDraw();
	}
	
	//清空图文消息方法
	function cleanMsgLists(){
		//$('#PicAndTxtMsgList').dataTable().fnClearTable();
		$('#name').val("");
		LoadMsgDtList();
		$('#PicAndTxtMsgList').dataTable().fnDraw();
	}
		
	$('#successTitle').on('click',function(){
		$("#sendTitle").modal('hide');
	});
		
	$('#successContent').on('click',function(){
		$("#sendContent").modal('hide');
	});
	
	$('#btnSuccess').on('click',function(){
		$("#succModal").modal('hide');
		window.location.reload();
	});
	
	$('#btnEditSuccess').on('click',function(){
		$("#succEditModal").modal('hide');
		window.location.reload();
	});
	
	$('#btnDelSuccess').on('click',function(){
		$("#succDelModal").modal('hide');
		window.location.reload();
	});
	
	//上传文件的时候向后台发送请求
	function sendFile(file, editor, $editable){
		
		var data = new FormData();
		for(var i=0;i<file.length;i++){
			data.append("files", file[i]);
		}
		$.ajax({
			data:data,
			type: "POST",
			url: ctx + "/institution/picAndTxtMeg/uploadFile",
			cache: false,
			headers: {
				"${_csrf.headerName}":"${_csrf.token}",
        	},
			contentType: false,
			processData: false,
			success: function(data) {
			var result = $.parseJSON(data);
			var content = result.content;
			if(content!=null && content.length>0){
				var ip = window.location.host;
				var ipAdd = ip.split(":")[0];
				for(var i=0;i<content.length;i++){
					$('#editor').summernote('editor.insertImage',content[i]);
				}
			  }
			}
		});
	}
	
	function sendUpdateFile(file, editor, $editable){
		var data = new FormData();
		for(var i=0;i<file.length;i++){
			data.append("files", file[i]);
		}
		$.ajax({
			data: data,
			type: "POST",
			url: ctx + "/institution/picAndTxtMeg/uploadFile",
			cache: false,
			headers: {
				"${_csrf.headerName}":"${_csrf.token}",
        	},
			contentType: false,
			processData: false,
			success: function(data) {
			var result = $.parseJSON(data);
			var content = result.content;
			if(content!=null && content.length>0){
				var ip = window.location.host;
				var ipAdd = ip.split(":")[0];
				for(var i=0;i<content.length;i++){
					$('#editEditor').summernote('editor.insertImage', content[i]);
				}
			  }
			}
		});
	}
	
	$(function(){
		//新增的时候显示编辑器
		$('#editor').summernote({
			 height: 250,
			 focus:true,
			 toolbar: [
				 ['style', ['style']], 
			     ['style', ['bold', 'italic', 'underline', 'clear']],
			     ['fontsize', ['fontsize']],
			     ['color', ['color']],
			     ['para', ['ul', 'ol', 'paragraph']],
			     ['height', ['height']],
			     ['insert', ['picture']],
			     ['view', ['fullscreen', 'codeview']]
			],
			 callbacks: {
				 onImageUpload: function(files, editor, $editable) {
	                 sendFile(files,editor,$editable);
	             }
             }
		});
		
		//查看的时候显示编辑器
		$('#showEditor').summernote({
			 height: 250,
			 focus:true,
			 toolbar: [],
		});
		//修改编辑器
		$('#editEditor').summernote({
			 height: 250,
			 focus:true,
			 toolbar: [
				 ['style', ['style']], 
			     ['style', ['bold', 'italic', 'underline', 'clear']],
			     ['fontsize', ['fontsize']],
			     ['color', ['color']],
			     ['para', ['ul', 'ol', 'paragraph']],
			     ['height', ['height']],
			     ['insert', ['picture']], 
			     ['view', ['fullscreen', 'codeview']],
			],
			 callbacks: {
				 onImageUpload: function(files, editor, $editable) {
					 sendUpdateFile(files,editor,$editable);
	             }
            }
			
		});
		
		LoadMsgDtList();
	})
	
	function closeModal(obj){
		//$(obj).parent().parent().parent().parent().remove();
		$(obj).parent().parent().parent().parent().modal('hide');
		if(!$("body").hasClass("modal-open")){
			$("body").addClass("modal-open");
		}
		
	}
	
	
	var defaultData = ${tree};
	//生成树形结构
	$('#tree').treeview({
		color : "#428bca",
		showBorder : false,
		showCheckbox : true,
		multiSelect : true,
		highlightSelected:false,
		data : defaultData
	});
	// 选中部门节点
    $('#tree').on('nodeChecked', function(event, data) {
      var nodeId = data.tags.id;
     // var nodeId = data.nodeId;
     //	var nodeIds = nodeId + ",";
    //	var chooseDepartIds = $("#chooseDepartIds").val();
    //	$("#chooseDepartIds").val(chooseDepartIds+nodeIds);
		selectNodeLoop(data,1);
    });
	// 不选中部门节点
    $('#tree').on('nodeUnchecked', function(event, data) {
    	var nodeId = data.tags.id;
		selectNodeLoop(data,0);
    });
	
	//递归选择部门节点
	function selectNodeLoop(data,flag){
		var tempNodes=data.nodes;
		if(tempNodes!=undefined){
			for(var i=0;i<tempNodes.length;i++){
				if(flag==1){
					$('#tree').treeview('checkNode', [ tempNodes[i].nodeId, { silent: true } ]);
				}else{
					$('#tree').treeview('uncheckNode', [ tempNodes[i].nodeId, { silent: true } ]);
				}
				 selectNodeLoop(tempNodes[i],flag);
			}
		}
		return ;
	}
	
	$("#policyName").autocomplete({
        source: function(request,response){
             	var name = $("#policyName").val();
			var userIds = "";
			if(null!=idArray&&idArray.length>0){
				for(var i=0;i<idArray.length;i++){
					userIds += idArray[i]+",";
				}
			}
        	if(name!=null&&name!=""){
        		name = '%'+name+'%';
	        	$.ajax({
	    			"dataType" : 'json',
	                "type": "get",
	                "url":ctx+"/institution/device/policy/findUserByName?now="+ new Date().getTime(),
	                "data": {"name":name,"userids":userIds},
	                "success": function(data){
	                	response($.map(data, function(item) {
                            return { label: item.realname+'('+item.username+')', value: item.id}
                        }));
	                } 
	            });
        	}
         },select:function(event,ui){
           	 eventId = ui.item.value;
        	 addArray(ui.item.value,ui.item.label); 
         },close:function(event,ui){
        	 if(eventId!=null&&eventId!=""){
        		 $("#policyName").val("");
        	 }
        	 eventId="";
         }
     });
	
	
	$("#editPolicyName").autocomplete({
		source: function(request,response){
         	var name = $("#editPolicyName").val();
		var userIds = "";
		if(null!=idArray&&idArray.length>0){
			for(var i=0;i<idArray.length;i++){
				userIds += idArray[i]+",";
			}
		}
    	if(name!=null&&name!=""){
    		name = '%'+name+'%';
        	$.ajax({
    			"dataType" : 'json',
                "type": "get",
                "url":ctx+"/institution/device/policy/findUserByName?now="+ new Date().getTime(),
                "data": {"name":name,"userids":userIds},
                "success": function(data){
                	response($.map(data, function(item) {
                        return { label: item.realname+'('+item.username+')', value: item.id}
                    }));
                } 
            });
    	}
     },select:function(event,ui){
       	 eventId = ui.item.value;
       	 viewDisplayArray(ui.item.value,ui.item.label); 
     },close:function(event,ui){
    	 if(eventId!=null&&eventId!=""){
    		 $("#editPolicyName").val("");
    	 }
    	 eventId="";
     }
	});
	
	// 显示
	function addArray(id,name){
		var user = new Object();
		user.id = id;
		user.name = name;
		nameArray.push(user);
		idArray.push(id);
		displayUsers(nameArray,null);
	}
	
	// 显示
	function displayArray(id,name){
		var user = new Object();
		user.id = id;
		user.name = name;
		nameArray.push(user);
		idArray.push(id);
		displayUsers(nameArray,0);
	}
	
	function viewDisplayArray(id,name, type){
		var user = new Object();
		user.id = id;
		user.name = name;
		nameArray.push(user);
		idArray.push(id);
		viewDisplayUsers(nameArray,type);
	}
	
	function displayUsers(nameArray,tag){
		if(null!=nameArray&&nameArray.length>0){
			var listr  = "";
			for(var j=0;j<nameArray.length;j++){
				var user = nameArray[j];
				if(tag==0){
       			   listr += '<li class="userLi"><div class="col-lg-10">'+user.name+'</div><div class="col-lg-1"><a onclick="removeArray('+user.id+',\''+user.name+'\')"></div></li>';
				} else {
	       		   listr += '<li class="userLi"><div class="col-lg-10">'+user.name+'</div><div class="col-lg-1"><a onclick="removeArray('+user.id+',\''+user.name+'\')"><i class="glyphicon glyphicon-remove limargin"></i></div></li>';
	
				}
				}
			$("#policyVirtualRight").html(listr);
		} else {
			$("#policyVirtualRight").html("");
		}
	}
	
	function viewDisplayUsers(nameArray,tag){
		if(null!=nameArray&&nameArray.length>0){
			var listr  = "";
			for(var j=0;j<nameArray.length;j++){
				var user = nameArray[j];
				if(tag==0){
       			   listr += '<li class="userLi"><div class="col-lg-10">'+user.name+'</div><div class="col-lg-1"><a onclick="removeArrayWithEdit('+user.id+',\''+user.name+'\')"></div></li>';
				} else {
	       		   listr += '<li class="userLi"><div class="col-lg-10">'+user.name+'</div><div class="col-lg-1"><a onclick="removeArrayWithEdit('+user.id+',\''+user.name+'\')"><i class="glyphicon glyphicon-remove limargin"></i></div></li>';
	
				}
				}
			if (tag == 0)
			{
				$("#viewPolicyVirtualRight").html(listr);
			}
			else{
				$("#eidtPolicyVirtualRight").html(listr);
			}
			
		} else {
			if (tag == 0){
				$("#viewPolicyVirtualRight").html("");
			}
			else
			{
				$("#eidtPolicyVirtualRight").html("");
			}
			
		}
	}
	
	// 显示
	function removeArray(id,name){
		var newIdArr = [];
		var newNameArr = [];
		if(null!=idArray&&idArray.length>0){
			for(var i=0;i<idArray.length;i++){
				var obj = idArray[i];
				if(obj!=id){
					newIdArr.push(obj);
				}
			}
		}
		idArray = newIdArr;
		if(null!=nameArray&&nameArray.length>0){
			for(var j=0;j<nameArray.length;j++){
				var obj = nameArray[j];
				if(obj.id!=id){
					var user = new Object();
					user.id = obj.id;
					user.name = obj.name;
					newNameArr.push(user);
				}
			}
		}
		nameArray = newNameArr;
		displayUsers(newNameArr,null);
	}
	
	// 显示
	function removeArrayWithEdit(id,name){
		var newIdArr = [];
		var newNameArr = [];
		if(null!=idArray&&idArray.length>0){
			for(var i=0;i<idArray.length;i++){
				var obj = idArray[i];
				if(obj!=id){
					newIdArr.push(obj);
				}
			}
		}
		idArray = newIdArr;
		if(null!=nameArray&&nameArray.length>0){
			for(var j=0;j<nameArray.length;j++){
				var obj = nameArray[j];
				if(obj.id!=id){
					var user = new Object();
					user.id = obj.id;
					user.name = obj.name;
					newNameArr.push(user);
				}
			}
		}
		nameArray = newNameArr;
		viewDisplayUsers(newNameArr,null);
	}
	
	function savePushPicMsg()
	{
		var departIds="";
		var nodeChecked=$('#tree').treeview('getChecked');
		if(nodeChecked.length>0){
			for(var i=0;i<nodeChecked.length;i++){
				departIds=nodeChecked[i].tags.id+","+departIds;
			}
			departIds=departIds.substr(0,departIds.length-1);
		}
	   var virtualIds = "";
	   $('input[name="virtualIds"]:checked').each(function(){ 
		   var tempVal = $(this).val();
		   virtualIds+=tempVal+",";
	   }); 
	   if(virtualIds!=""){
		   virtualIds = virtualIds.substring(0,virtualIds.length-1);
	   }
	}
	
	function sendPushMsg(id){
		var csrf="${_csrf.token}";
		$.ajax({
			"dataType": 'json',
			"data": {"id":id,"_csrf":csrf},
			"type": "get",
			"url": ctx + "/institution/picAndTxtMeg/sendPushMsg",
			success: function(data) {
				LoadMsgDtList();
			}
		});
	}
	
	//加载图文消息列表
	function LoadMsgDtList(){
		var name = $('#name').val();
		var csrf="${_csrf.token}"; 
			$('#PicAndTxtMsgList').DataTable({
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
				              {data : "msgTheme"}, 
				              {data : "userCount"}, 
				              {data : ""},
				              {data : ""},
				              {data : ""}
				          ],
				"columnDefs":[
								{
								    "targets": [0],
								    "render" : function(
											data, type,
											full, meta) {
											return full.msgTheme;
									}
								},
								{
								    "targets": [1],
								    "render" : function(
											data, type,
											full, meta) {
											return "<a href='javascript:void(0)' class='text-primary' onclick='viewUserInfo("+full.id+")'>"+full.userCount+"</a>";
									}
								},
								 {
									"targets" : [2],
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
									"targets" : [3],
									"render" : function(
											data, type,
											full, meta) {
										if(full.pushTime == null || full.pushTime == ''){
											return '';
										}else{
											return new Date(parseInt(full.pushTime)).Format("yyyy-MM-dd hh:mm:ss");
										}
									}
								},
								{
									"targets" : [4],
									"render" : function(data, type, row) {
										
										var isenable=0;
										if("${softtek_manager.user}"!=""&&"${softtek_manager.id}"!=row.createUserId){
											isenable=1;
										} 
										
										if("${softtek_manager.auth}"=="0"&&"${softtek_manager.user}"!=""){
											return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
											+ '<i class="i  i-settings"></i>'
											+ '</a>'
											+ '<ul class="dropdown-menu" style="margin-top:-60px;">'
											+ '<li><a href="javascript:void(0);" onclick="viewPicAndTxtMsg('+row.id+')">'
											+ '<i class="glyphicon glyphicon-eye-open"></i>&nbsp;<fmt:message key="tiles.views.institution.message.table.operation.view"/></a></li></ul>';
										}
										
										return '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">'
										+ '<i class="i  i-settings"></i>'
										+ '</a>'
										+ '<ul class="dropdown-menu" style="margin-left:-100px;margin-top:-60px;">'
										+ '<li><a href="javascript:void(0);" onclick="viewPicAndTxtMsg('+row.id+')">'
										+ '<i class="glyphicon glyphicon-eye-open"></i>&nbsp;<fmt:message key="tiles.views.institution.message.table.operation.view"/></a></li>'
										+ '<li><a href="javascript:void(0);" onclick="updatePicAndTxtMsg('+row.id+ ','+isenable+')">'
										+ '<i class="fa fa-pencil"></i>&nbsp;<fmt:message key="tiles.views.institution.message.table.operation.update"/></a></li>'
										+ '<li><a href="javascript:void(0);" onclick="sendPushMsg('+row.id+')">'
										+ '<i class="fa fa-commenting-o"></i>&nbsp;<fmt:message key="tiles.views.institution.message.table.operation.sendpushmsg"/></a></li>'
										+ '<li><a href="javascript:void(0);" onclick="deletePicAndTxtMsg('+row.id+')">'
										+ '<i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;<fmt:message key="tiles.views.institution.message.table.operation.delete"/></span></a></li>'
										+ '</ul>';
									}
								}   
								                          
						  ]	
				});
		}
	
    
	function viewUserInfo(msgId){
		 var csrf="${_csrf.token}"; 
		 $('#viewMemberTable').DataTable({
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
					"url" : "${viewMember}",
					"data" : {"id":msgId,"_csrf":csrf}
				},
				"columns":[
				              {data : ""}, 
				              {data : ""}
				          ],
				"columnDefs":[
								{
								    "targets": [0],
								    "render" : function(
											data, type,
											full, meta) {
											return full.username;
									}
								},
								{
								    "targets": [1],
								    "render" : function(
											data, type,
											full, meta) {
											return full.realname;
									}
								}
						  ]	
				});
		$("#memberModal").modal(open);
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
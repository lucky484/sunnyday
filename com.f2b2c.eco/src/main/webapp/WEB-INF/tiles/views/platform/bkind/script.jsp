<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/resources/platform/js/plugins/treeview/bootstrap-treeview.js" var="treeviewUrl"/>
<script src="${treeviewUrl}"></script>
<spring:url value="/farm/bkind/datatable/json" var="datatableUrl"/>
<spring:url value="/farm/bkind/btree/json" var="tree"/>
<spring:url value="/resources/market/js/jquery.tmpl.js" var="tmplJs" /> 
<spring:url value="/farm/bkind/index" var="bKindIndexUrl"/>
<script src="${tmplJs}"></script>
<script>
	var dataTable;
	var parent_id = -1;
	var catalog;
	
	// jquary dataTables
	$(function() {
		$("#imgIsHide1").html($("#iconTmpl").tmpl());
			// --- dataTables start ----
			dataTable = $("#editable").dataTable({
				"dom":"<'m-r m-t-lg pull-right'f>t<'row col-sm-12'<'col-sm-4'l>r<'col-sm-4'i><'pull-right'p>>",
			    "autoWidth": false,
			    "searching" : false,
				"stateSave" : true,
				"ordering" : false,
				"bSort":false,
				"pageLength" : 10,
				"pagingType" : "simple_numbers",
				"serverSide" : true,
				"bDestroy" : true,
				"ajax" : {
					"url" : "${datatableUrl}",
					"type" : "get",
					"dataSrc" : "content",
					"dataType":'json',
					"data" :function(d){
						d.parent_id = parent_id;
					}
				},
				"columns" : [ {
					data : "kindName"
				}, {
					data : "iconUrl"
				}, {
					data : "remark"
				}],
				"columnDefs" : [
					{
						targets : [1],
						"render" : function(data, type,full, meta) {
							if(data){
								return '<img width="50px" height="50px" src="'+data+'"/>'
							}else{
								return '-'
							}
						}
					},
					{
						targets : [2],
						"render" : function(data, type,full, meta) {
							if(!data){
								return '-'
							}else{
								return data
							}
						}
					},
					{
						targets : [3],
							"render" : function(data, type,full, meta) {
								var operation = '<a href="javascript:;" onclick="modifyKind(this)" kicon="'+ full.iconUrl +'"  kpic="'+ full.picUrl +'" kid='+ full.id +' kname='+ full.kindName +' kcatalog='+ full.catalog +' kpcatalog="'+ full.parent.catalog +'" kremark="'+ full.remark +'" class=" btn-mr">修改</a>'
									operation += '<a href="javascript:delKind('+full.id+')" class="btn-mr">删除</a>'
								return operation
							}
					}   
				]
		});	
		// --- dataTables end ---	
		
		// --- treeview start ---
	
		$.post('${tree}',function(data){
			$("#treeview1").treeview({
				expandIcon: "glyphicon glyphicon-plus",
				collapseIcon: "glyphicon glyphicon-minus",
				showIcon:false,
				nodeIcon:"",
				data: data,
				onNodeSelected: function(e,data) {
					parent_id = data.id
					catalog = data.catalog
					$("#imgIsHide1").html("");
					$("#imgIsHide2").html("");
					if(catalog != 0 || catalog == ""){
						$("#isCommission").html("");
					}else{
						$("#isCommission").html($("#isCommissionTmpl").tmpl()); 
					}
					if(data.level == 3){
					//	三级只允许上传icon
						$("#addKindModal").addClass("hide");
					}else if(data.level == 1){
						$("#imgIsHide2").html($("#adventTmpl").tmpl());
						$("#addKindModal").removeClass("hide");
					}else if(data.level == 2){
						$("#imgIsHide1").html($("#iconTmpl").tmpl());
						$("#addKindModal").removeClass("hide");
					}else if(data.level == 0){
						$("#imgIsHide1").html($("#iconTmpl").tmpl());
					}
					dataTable.fnDraw();
				}
			});
		 $('#treeview1').treeview('selectNode', [ 0, { silent: true } ])
		});
		
		// --- treeview end ---
		// --- newModal start --- 
		  $('#newModal').on('hide.bs.modal', function () {
			    $("input[name='kindName']").val('')
				$("select[name='catalog']").val(1)
				$("select[name='catalog']").removeAttr('disabled')
				$("input[name='remark']").val('')
				$("#kind-parent-id").val('')
				$("#kind-id").val('')
				$("#Img").attr('src','')
				$("#file_upload").val('')
				$("#file_upload3").val('')
		    }
		  )
		 // --- newModal end --- 
	 });
	
	function newKind(){
		$(".modal-oper").text('新增')
		if(catalog===0||catalog===1||catalog==='0'||catalog==='1'){// 上级是水果就是水果
			$("select[name='catalog']").val(catalog)
			$("select[name='catalog']").attr('disabled','disabled') //readonly="readonly"
			$("#catalogVal").val(catalog);
		}else{
			$("select[name='catalog']").val("1");
			$("select[name='catalog']").attr('disabled','disabled') //readonly="readonly"
			$("#catalogVal").val("1");
		}
		$("#kind-parent-id").val(parent_id)
		$("#Img3").attr('src','');
		$("#newModal").modal({backdrop: 'static', keyboard: false});
	}
	
	function modifyKind(e){
		$(".modal-oper").text('修改');
		var $e = $(e);
		var kpcatalog = $e.attr('kpcatalog');
		$("input[name='kindName']").val($e.attr('kname'));
		if(kpcatalog===0||kpcatalog===1||catalog==='0'||catalog==='1'){// 上级是水果就是水果
			$("select[name='catalog']").attr('disabled','disabled')
			$("#catalogVal").val(catalog);
		}
			$("select[name='catalog']").val($e.attr('kcatalog'))
			
		var kremark = $e.attr('kremark');
		if(kremark&&kremark!='null'){
			$("input[name='remark']").val($e.attr('kremark'));
		}
			
		$("#kind-parent-id").val(parent_id);
		$("#kind-id").val($e.attr('kid'));
		var kicon = $e.attr('kicon');
		if(kicon){
			$("#Img").attr('src', kicon);
		}
		var kpic = $e.attr('kpic');
		if(kpic){
			$("#Img3").attr('src', kpic);
		}
		$("#newModal").modal({backdrop: 'static', keyboard: false});
	}
	
	function delKind(id){
		 $.post('del',{id: id}, function(data){
			 if(data.status == 200){
				 $.notify("info", "删除成功");
			 }else{
				 $.notify("error", data.msg);
			 }
			 // window.location.href="${bKindIndexUrl}";
			dataTable.fnDraw();// 当前页面  
		 });
	}
	 
	 function formatCurrentTimeMillis (dateTime) {
		return new Date(dateTime).Format("yyyy-MM-dd hh:mm:ss");
		
	}
	 
	function change_photo(){
	    PreviewImage($("input[name='file_upload2']")[0], 'Img', 'Imgdiv');
	}
	function change_photo3(){
	    PreviewImage($("input[name='file_upload3']")[0], 'Img3', 'Imgdiv3');
	}
	 /* preview view */
	function PreviewImage(fileObj,imgPreviewId,divPreviewId){  
		    var allowExtention=".jpg,.bmp,.gif,.png";//允许上传文件的后缀名document.getElementById("hfAllowPicSuffix").value;  
		    var extention=fileObj.value.substring(fileObj.value.lastIndexOf(".")+1).toLowerCase();              
		    var browserVersion= window.navigator.userAgent.toUpperCase();  
		    if(allowExtention.indexOf(extention)>-1){   
		        if(fileObj.files){//HTML5实现预览，兼容chrome、火狐7+等  
		            if(window.FileReader){  
		                var reader = new FileReader();   
		                reader.onload = function(e){  
		                    document.getElementById(imgPreviewId).setAttribute("src",e.target.result);  
		                }    
		                reader.readAsDataURL(fileObj.files[0]);  
		            }else if(browserVersion.indexOf("SAFARI")>-1){  
		                alert("不支持Safari6.0以下浏览器的图片预览!");  
		            }  
		        }else if (browserVersion.indexOf("MSIE")>-1){  
		            if(browserVersion.indexOf("MSIE 6")>-1){//ie6  
		                document.getElementById(imgPreviewId).setAttribute("src",fileObj.value);  
		            }else{//ie[7-9]  
		                fileObj.select();  
		                if(browserVersion.indexOf("MSIE 9")>-1)  
		                    fileObj.blur();//不加上document.selection.createRange().text在ie9会拒绝访问  
		                var newPreview =document.getElementById(divPreviewId+"New");  
		                if(newPreview==null){  
		                    newPreview =document.createElement("div");  
		                    newPreview.setAttribute("id",divPreviewId+"New");  
		                    newPreview.style.width = document.getElementById(imgPreviewId).width+"px";  
		                    newPreview.style.height = document.getElementById(imgPreviewId).height+"px";  
		                    newPreview.style.border="solid 1px #d2e2e2";  
		                }  
		                newPreview.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + document.selection.createRange().text + "')";                              
		                var tempDivPreview=document.getElementById(divPreviewId);  
		                tempDivPreview.parentNode.insertBefore(newPreview,tempDivPreview);  
		                tempDivPreview.style.display="none";                      
		            }  
		        }else if(browserVersion.indexOf("FIREFOX")>-1){//firefox  
		            var firefoxVersion= parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);  
		            if(firefoxVersion<7){//firefox7以下版本  
		                document.getElementById(imgPreviewId).setAttribute("src",fileObj.files[0].getAsDataURL());  
		            }else{//firefox7.0+                      
		                document.getElementById(imgPreviewId).setAttribute("src",window.URL.createObjectURL(fileObj.files[0]));  
		            }  
		        }else{  
		            document.getElementById(imgPreviewId).setAttribute("src",fileObj.value);  
		        }           
		    }else{  
		        alert("仅支持"+allowExtention+"为后缀名的文件!"); 
		        fileObj.value="";//清空选中文件  
		        if(browserVersion.indexOf("MSIE")>-1){                          
		            fileObj.select();  
		            document.selection.clear();  
		        }
		        if(imgPreviewId == 'Img'){
		        	$("#Img").attr('src','')
	        	}else{
	        		$("#Img3").attr('src','')
	        	}
		    }  
		}
		function closeDlg() {
			$('#dlg').dialog('close');
			$('#myForm')[0].reset();
			$('#myForm').form('clear');
			$("#Img").attr('src','');
			$("#Img3").attr('src','');
		}
		
</script>

<script id="iconTmpl" type="text/x-jquery-tmpl">
 <div class="col-md-6">
	<label>图标</label> <input type="file" id="file_upload" name="file_upload2" onchange="change_photo()" class="form-control" required ></div>
	<div class="col-md-6">
		<label>预览</label><div id="Imgdiv">
	        <img id="Img" width="34px" height="34px"/>
	    </div>
	</div>	
</script>
<script id="adventTmpl" type="text/x-jquery-tmpl">
 	<div class="col-md-6">
		<label>广告图</label> <input type="file" id="file_upload3" name="file_upload3" onchange="change_photo3()" class="form-control"
		 required >
	</div>
	<div class="col-md-6">
		<label>预览</label><div id="Imgdiv3">
	        <img id="Img3" width="200px" height="50px"/>
	    </div>
	</div>	
<div class="col-md-12" style="color: red;">
	只有二级分类可以上传广告图
</div>							
</script>


<script id="isCommissionTmpl" type="text/x-jquery-tmpl">
			<div class="col-md-6">
				<label >是否参与佣金</label>
				<select name="isCommission" class="form-control">
					<option value="0">不参与</option>
					<option value="1">参与</option>
				</select>
			</div>
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/home/wap/querypage" var="queryUrl" />
<spring:url value="/farm/home/wap/del" var="delUrl" />
<spring:url value="/farm/home/wap/addpage" var="fwapAddPageUrl" />
<script>
$(function(){
	$("#editable").dataTable({
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
			"url" : "${queryUrl}",
			"type" : "get",
			"dataType" :"json",
			"dataSrc":"content",
			"data" :{"name":name},
		}, 
		"columns" : [ {
			"data" : "wap_img_url"
		}, 
		/* {
			"data" : "wap_img_url"
		},  */
		{
			"data" : ""
		}],
		"columnDefs" : [ 
		{
		"targets" : 0,
		"bSortable" : false,
		"render" : function(data, type, full, row) {
				if(full){
					return '<img width="100px" height="100px" src="'+full.wapImgUrl+'"/>'
				}else{
					return '-'
				}
			}
		},
		{
		"targets" : 1,
		"bSortable" : false,
		"render" : function(data, type, full, row) {
			return '<a href="javascript:;" onclick="add('+full.id+')"  id="'+full.id+'" url="'+ full.wapImgUrl +'" class="btn mini light-blue">修改 </a>'
					+'<a href="javascript:del('+full.id+')" class="btn mini light-blue">删除 </a>';
			}
		} ]
	});
	
	/* 模态框 */
	$('#newModel').on('hide.bs.modal',function(){
		$("input[name='file']").val('');
		$("#wap-parent-id").val('');
		$("#wap-id").val('');
		$("#Img").attr('src','');
		$("#file").val('');
	})
})

/* 新增功能 */
/* function add(){
	$(".modal-oper").text('新增');
	$("#wap-parent-id").val();
	$("#newModal").modal();
} */


/* 新增功能 */
function add(id){
	if(id !=null){
		window.location.href="${fwapAddPageUrl}?id="+id;
	}else{
		window.location.href="${fwapAddPageUrl}";
	}
}
/* 修改功能 */
function update(e){
	$(".modal-oper").text('修改');
	var $e = $(e);
	$("#wap-parent-id").val();
	$("#wap-id").val($e.attr('id'))
	var url = $e.attr('url');
	if(url){
		$("#Img").attr('src', url);
	}
	$("#newModal").modal();
}

/* 删除功能*/
function del(id) {
// 	$("#deleteConfirm").modal();
// 	$("#deleteApplyBtn").unbind("click").bind("click", function() {
// 		$.post('del.do', {
// 			id : id
// 		}, function(data) {
// 			alert("删除成功")
// 			document.location.reload()// 当前页面  
// 		});
// 	});
	$.ajax({
		"dataType" : 'json',
		"type" : 'post',
		"async" : false,
		"url" : '${delUrl}',
		"data" : {
			"id" : id
		},
		"success" : function(data) {
			debugger
			if (data) {
				swal({
					title: "成功!",   
					text: "删除成功",   
					type: "info", 
				} , function(){window.location.reload()});
			}else{
				swal({
					title: "失败!",   
					text: "删除失败",   
					type: "info", 
				} , function(){window.location.reload()});
			}
		},
		"error" : function(data) {
			debugger
			alert("修改成功");
		}
	})
}

function change_photo(){
    PreviewImage($("input[name='file']")[0], 'Img', 'Imgdiv');
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
	        $("#Img").attr('src','')
	    }  
	}
	
function closeDlg() {
	$('#dlg').dialog('close');
	$('#myForm')[0].reset();
	$('#myForm').form('clear');
	$("#Img").attr('src','')
}
</script>
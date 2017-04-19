<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/resources/platform/js/plugins/treeview/bootstrap-treeview.js" var="treeviewUrl"/>

<script src="${treeviewUrl}"></script>
<spring:url value="/farm/admin/kind/datatable/json" var="datatableUrl"/>
<spring:url value="/farm/admin/kind/enable" var="enableUrl"/>
<spring:url value="/farm/admin/kind/page" var="fKindIndexUrl"/>
<spring:url value="/farm/admin/kind/update-fkind-weight" var="modiftFKindWeightUrl"/>

<script>
    var dataTable;
    var parent_id = 1000;
    var catalog;
    // jquary dataTables
    $(function() {
            // --- dataTables start ----
            loadDT();
        // --- dataTables end ---    
        
        // --- treeview start ---
        $.post('btree/json',function(data){
            $("#treeview1").treeview({
                expandIcon: "glyphicon glyphicon-plus",
                collapseIcon: "glyphicon glyphicon-minus",
                showIcon:false,
                nodeIcon:"",
                data: data,
                onNodeSelected: function(e, o,data) {
                    parent_id = o.id
                    catalog = o.catalog
                    dataTable.fnDraw();
                    if(o.level == 2){
                    	$("#newKindBtn").addClass("hide");
                    }else{
                    	$("#newKindBtn").removeClass("hide");
                    }
                }
            })
        })
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
                $("weight").val('');  
                $("weightHiden").val('');
            }
          )
         // --- newModal end --- 
     });
    
    function loadDT(){
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
                            if(full.isEnable == 1){
                                return "启用中";
                            }else if (full.isEnable == 0){
                                
                                return "禁用中";
                            }else{
                                return "";
                            }
                        }
                },{
                    targets : [4],
                    "render" : function(data, type,full, meta) {
                        if(full.isEnable == 1){
                            var operation = '<a href="javascript:;" onclick="modifyKind(this)" kicon="'+ full.iconUrl +'" weight='+ full.weight +'  kid='+ full.id +' kname='+ full.kindName +' kcatalog='+ full.catalog +' kpcatalog="'+ full.parent.catalog +'" kremark='+ full.remark +' class=" btn-mr">修改</a>'
                                operation += '<a href="javascript:delKind('+full.id+')" class="btn-mr">删除</a>'
                                operation += '<a href="javascript:enable('+full.id+',0)" class="btn-mr">下架分类</a>'
                                operation += '<a onclick=kind_Weight(' 
									+ full.id
									+ ','+ full.weight+ ')></i>&nbsp;设置权重</a>';
                            return operation
                        }else if (full.isEnable == 0){
                            var operation = '<a href="javascript:;" onclick="modifyKind(this)" kicon="'+ full.iconUrl +'"   weight='+ full.weight +' kid='+ full.id +' kname='+ full.kindName +' kcatalog='+ full.catalog +' kpcatalog="'+ full.parent.catalog +'" kremark='+ full.remark +' class=" btn-mr">修改</a>'
                                operation += '<a href="javascript:delKind('+full.id+')" class="btn-mr">删除</a>'
                                operation += '<a href="javascript:enable('+full.id+',1)" class="btn-mr">上架分类</a>'
                                operation += '<a onclick=kind_Weight(' 
												+ full.id
												+ ','+ full.weight+ ')></i>&nbsp;设置权重</a>';
                            return operation
                        }else{
                            return "";
                        }
                    }
            }   
            ]
    });    
    };
	function  kind_Weight(id,weight){
		$("#modifyWeight").attr("disabled",false);
		$("#kindId_modal").val(id);
		if(weight != null){
			$("#weight_modal").val(weight);
		}else{
			$("#weight_modal").val("");
		}
		 $("#modifyWeightModal").modal({backdrop: 'static', keyboard: false});
    }
    
	// 设置F端商品权重
	function modifyWeight(){
		// 防止重复提交
		$("#modifyWeight").attr("disabled",true);
		var weight = $("#weight_modal").val();
		var id = $("#kindId_modal").val();
		// 校验参数有效性
		if(weight == null || weight == ""){
			$.notify("error", "输入权值");
			$("#modifyWeight").attr("disabled",false);
			return;
		}
		if(weight <= 0 || weight >=10000){
			$.notify("error", "请输入1-9999的有效值");
			$("#modifyWeight").attr("disabled",false);
			return;
			}
		if(id != null){
			$.ajax({
				"dataType" : 'json',
				"type" : "POST",
				"async" : false,
				"url" : "${modiftFKindWeightUrl}",
				"data" : {
					"id" : id,
					"weight" : weight
				},
				"success" : function(data) {
					if(data.status == 200){
						$.notify("info", "设置权值成功");
					 	$("#modifyWeightModal").modal("hide");
					 	dataTable.fnDraw();
				 	}else{
				 		$.notify("error", "设置权值失败");
				 	}
				}
			});
		}else{
			$.notify("erro", "设置权值失败");
			return
		}
	}
	
    function newKind(){
        $(".modal-oper").text('新增')
        if(catalog===0||catalog===1||catalog==='0'||catalog==='1'){// 上级是水果就是水果
            $("select[name='catalog']").val(catalog)
            $("select[name='catalog']").attr('disabled','disabled') //readonly="readonly"
            $("#catalogVal").val(catalog);
        }else{
            $("select[name='catalog']").val("0");
            $("#catalogVal").val("0");
        }
        $("#kind-parent-id").val(parent_id)
        $("#newModal").modal({backdrop: 'static', keyboard: false});
    }
    
    function enable(id,enable){
        //原来的分类是启用状态,进行禁用操作   传递0
         $.post('${enableUrl}',{id: id,enable: enable}, function(data){
             loadDT();// 当前页面  
         })
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
        $("#weight").val($e.attr('weight'));  
        $("#weightHiden").val($e.attr('weight'));
        $("#kind-parent-id").val(parent_id);
        $("#kind-id").val($e.attr('kid'));
        var kicon = $e.attr('kicon');
        if(kicon){
            $("#Img").attr('src', kicon);
        }
        $("#newModal").modal({backdrop: 'static', keyboard: false});
    }
    function delKind(id){
       swal({
           title: "提示",   
           text: "您确认删除分类吗？删除后分类与相关商品都将无法恢复，请谨慎操作！",   
           type: "info", 
           showCancelButton: true,
           cancelButtonText: "取消"
       } , function(){
           $.post('del',{id: id}, function(data){
        	   window.location.href="${fKindIndexUrl}";   
           });
       });
    }
     
     function formatCurrentTimeMillis (dateTime) {
        return new Date(dateTime).Format("yyyy-MM-dd hh:mm:ss");
    }
     
    function change_photo(){
        PreviewImage($("input[name='file_upload2']")[0], 'Img', 'Imgdiv');
    }
     
    function change_photo2(){
        PreviewImage($("input[name='file_upload3']")[0], 'Img', 'Imgdiv');
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
        
        
	  $("#kindSubmit").submit(function(e) {
		  var hiddenId=$("#kind-id").val();
			  if(hiddenId == null || hiddenId == ''){
				 var file= $("#file_upload").val() ;
				  if(file == '' || file == null){
					  $.notify("error", "请上传图片");
					  return false;
				  }
			  }
	        }); 
</script>
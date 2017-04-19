<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url
	value="/resources/platform/js/plugins/easyUI/jquery.easyui.min.js"
	var="easyUIUrl" />
<spring:url value="/resources/platform/js/jquery-ui-1.10.4.min.js"
	var="jqueryUIUrl" />
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${jqueryUIUrl}"></script>
<script type="text/javascript" src="${easyUIUrl}"></script>
<spring:url
	value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.config.js"
	var="ueditorconfigJsUrl" />
<script type="text/javascript" src="${ueditorconfigJsUrl}"></script>
<spring:url value="/api/ueuplod/b-wap-img" var="ueuplodUrl"/>
<!-- 编辑器源码文件 -->
<spring:url
	value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.all.js"
	var="ueditorAllJsUrl" />
<spring:url value="/farm/bwap/bgoods-no-istrue" var="goodsNoIsTrue"/>
	
<script type="text/javascript" src="${ueditorAllJsUrl}"></script>


<script type="text/javascript">
	 	var typeStr = "${model.type}";
	 	var contentStr = "${model.content}";
		var ue = UE.getEditor('container',{
			catchRemoteImageEnable: false,
			toolbars: [
		                                               [
		                                                'undo', //撤销
		                                                'redo', //重做
		                                                'bold', //加粗
		                                                'italic', //斜体
		                                                'underline', //下划线
		                                                'strikethrough', //删除线
		                                                'subscript', //下标
		                                                'simpleupload', //单图上传
		                                                'superscript', //上标
		                                                'formatmatch', //格式刷
		                                                'source', //源代码
		                                                'selectall', //全选
		                                                'print', //打印
		                                                'preview', //预览
		                                                'horizontal', //分隔线
		                                                'removeformat', //清除格式
		                                                'time', //时间
		                                                'date', //日期
		                                                'insertrow', //前插入行
		                                                'insertcol', //前插入列
		                                                'mergeright', //右合并单元格
		                                                'mergedown', //下合并单元格
		                                                'deleterow', //删除行
		                                                'deletecol', //删除列
		                                                'splittorows', //拆分成行
		                                                'splittocols', //拆分成列
		                                                'splittocells', //完全拆分单元格
		                                                'deletecaption', //删除表格标题
		                                                'inserttitle', //插入标题
		                                                'mergecells', //合并多个单元格
		                                                'deletetable', //删除表格
		                                                'cleardoc', //清空文档
		                                                'insertparagraphbeforetable', //"表格前插入行"
		                                                'fontfamily', //字体
		                                                'fontsize', //字号
		                                                'paragraph', //段落格式
		                                                'edittable', //表格属性
		                                                'edittd', //单元格属性
		                                                'emotion', //表情
		                                                'spechars', //特殊字符
		                                                'justifyleft', //居左对齐
		                                                'justifyright', //居右对齐
		                                                'justifycenter', //居中对齐
		                                                'justifyjustify', //两端对齐
		                                                'forecolor', //字体颜色
		                                                'backcolor', //背景色
		                                                'fullscreen', //全屏
		                                                'imagecenter', //居中
		                                                'edittip ', //编辑提示
		                                                'customstyle', //自定义标题
		                                                'autotypeset', //自动排版
		                                                'background', //背景
		                                                'template', //模板
		                                                'inserttable', //插入表格
		                                            ]
		                                        ]
		                     });
		
		ue.addListener("ready", function() {
			showDiv();
			ue.setHeight(400);
		});
		
	 	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
	  	UE.Editor.prototype.getActionUrl = function(action) {  
		     if (action == 'uploadimage') {//跳转到后来的上传action  
		         return '${ueuplodUrl}';  
		     } else {  
		         return this._bkGetActionUrl.call(this, action);  
		     }  
		 }  
	  	
		//发布商品
		$("#wapform").submit(function(e) {
			$("#publish").attr("disabled", true);
			var file = $("#Img").attr("src");
			var detail;
			var goodsNoIsTrue = true;
			var type = $("input[name='type']:checked").val();
			if (type == 0) {
				var goodsNo= $("#goodsNo").val().trim();
				$.ajax({
					"dataType" : 'json',
					"type" : "POST",
					"async" : false,
					"url" : "${goodsNoIsTrue}",
					"data" : {
						"goodsNo" : goodsNo
					},
					"success" : function(data) {
						if (data == false) {
							goodsNoIsTrue = false;
						}else{
							detail = $("#goodsNo").val().trim();
						}
					}
					}); 
				
			} else if (type == 1) {
				detail = ue.getContent();
			} else if (type == 2) {
				detail = $("#url").val().trim();
				detail = detail.replace("http://","");
				detail = "http://"+detail;
			}
			// 验证
			if ($("#wap-id").val() == null || $("#wap-id").val()=="") {
				if (file == undefined || file == null || file == "") {
					$.notify("error", "请上传图片");
					$("#publish").attr("disabled", false)
					return false;
				}
			}
			
			if (goodsNoIsTrue == false) {
				$.notify("error", "商品编号有误,商品不存在或未上架");
				$("#publish").attr("disabled", false)
				return false;
			}
			
			if (detail == undefined || detail == null || detail == "") {
				$.notify("error", "请编辑内容");
				$("#publish").attr("disabled", false)
				return false;
			}
			$("#contentStr").val(detail);
	});

	function change_photo() {
		PreviewImage($("input[name='file']")[0], 'Img', 'Imgdiv');
	}

	/* preview view */
	function PreviewImage(fileObj, imgPreviewId, divPreviewId) {
		var allowExtention = ".jpg,.bmp,.gif,.png";//允许上传文件的后缀名document.getElementById("hfAllowPicSuffix").value;  
		var extention = fileObj.value.substring(
				fileObj.value.lastIndexOf(".") + 1).toLowerCase();
		var browserVersion = window.navigator.userAgent.toUpperCase();
		if (allowExtention.indexOf(extention) > -1) {
			if (fileObj.files) {//HTML5实现预览，兼容chrome、火狐7+等  
				if (window.FileReader) {
					var reader = new FileReader();
					reader.onload = function(e) {
						document.getElementById(imgPreviewId).setAttribute(
								"src", e.target.result);
					}
					reader.readAsDataURL(fileObj.files[0]);
				} else if (browserVersion.indexOf("SAFARI") > -1) {
					alert("不支持Safari6.0以下浏览器的图片预览!");
				}
			} else if (browserVersion.indexOf("MSIE") > -1) {
				if (browserVersion.indexOf("MSIE 6") > -1) {//ie6  
					document.getElementById(imgPreviewId).setAttribute("src",
							fileObj.value);
				} else {//ie[7-9]  
					fileObj.select();
					if (browserVersion.indexOf("MSIE 9") > -1)
						fileObj.blur();//不加上document.selection.createRange().text在ie9会拒绝访问  
					var newPreview = document.getElementById(divPreviewId
							+ "New");
					if (newPreview == null) {
						newPreview = document.createElement("div");
						newPreview.setAttribute("id", divPreviewId + "New");
						newPreview.style.width = document
								.getElementById(imgPreviewId).width
								+ "px";
						newPreview.style.height = document
								.getElementById(imgPreviewId).height
								+ "px";
						newPreview.style.border = "solid 1px #d2e2e2";
					}
					newPreview.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='"
							+ document.selection.createRange().text + "')";
					var tempDivPreview = document.getElementById(divPreviewId);
					tempDivPreview.parentNode.insertBefore(newPreview,
							tempDivPreview);
					tempDivPreview.style.display = "none";
				}
			} else if (browserVersion.indexOf("FIREFOX") > -1) {//firefox  
				var firefoxVersion = parseFloat(browserVersion.toLowerCase()
						.match(/firefox\/([\d.]+)/)[1]);
				if (firefoxVersion < 7) {//firefox7以下版本  
					document.getElementById(imgPreviewId).setAttribute("src",
							fileObj.files[0].getAsDataURL());
				} else {//firefox7.0+                      
					document.getElementById(imgPreviewId).setAttribute("src",
							window.URL.createObjectURL(fileObj.files[0]));
				}
			} else {
				document.getElementById(imgPreviewId).setAttribute("src",
						fileObj.value);
			}
		} else {
			alert("仅支持" + allowExtention + "为后缀名的文件!");
			fileObj.value = "";//清空选中文件  
			if (browserVersion.indexOf("MSIE") > -1) {
				fileObj.select();
				document.selection.clear();
			}
			$("#Img").attr('src', '')
		}
	}
	$(function() {

		$("input[name='type']").change(function() {
			showDiv();
		});
	});

	function showDiv() {
		var type = $("input[name='type']:checked").val();
		$("#ueEdiorDiv").addClass("hide");
		$("#goodsNoDiv").addClass("hide");
		$("#urlDiv").addClass("hide");
		if (type == 0) {
			$("#goodsNoDiv").removeClass("hide");
		}
		if (type == 1) {
			$("#ueEdiorDiv").removeClass("hide");
		}
		if (type == 2) {
			$("#urlDiv").removeClass("hide");
		}
	}
	
	if ("${model}" != null) {
		$("#wap-id").val("${model.id}");
		//$("#contentStr").val("${model.content}");
		if (typeStr == 0) {
			$("input[name='type'][value=0]").attr("checked", true);
			$("#goodsNo").val(contentStr);
		}
		if (typeStr == 1) {
			$("input[name='type'][value=1]").attr("checked", true);
		}
		if (typeStr == 2) {
			$("input[name='type'][value=2]").attr("checked", true);
			$("#url").val(contentStr);
		}
		$("#Img").attr("src", "${model.wapImgUrl}");
	};
</script>
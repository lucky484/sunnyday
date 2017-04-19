<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/bhelp/helpcenter" var="helpcenterUrl" />
<!-- 添加方法 -->
<spring:url value="/farm/bhelp/insert" var="insertUrl" />

<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${jqueryUIUrl}"></script>
<script type="text/javascript" src="${easyUIUrl}"></script>
<spring:url
	value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.config.js"
	var="ueditorconfigJsUrl" />
<script type="text/javascript" src="${ueditorconfigJsUrl}"></script>
<!-- 编辑器源码文件 -->
<spring:url
	value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.all.js"
	var="ueditorAllJsUrl" />
<script type="text/javascript" src="${ueditorAllJsUrl}"></script>

<!-- 编辑器上传文件 -->
<spring:url value="/api/ueuplod/b-help-img" var="ueuplodUrl"/>
<script type="text/javascript">
	var ue = UE.getEditor('container', {
		catchRemoteImageEnable : false,
		toolbars : [ [ 'undo', //撤销
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
		] ]
	});
	ue.addListener("ready", function() {
		ue.setHeight(500);
		enableAutoSave: false
	});
	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	UE.Editor.prototype.getActionUrl = function(action) {
		if (action == 'uploadimage') {//跳转到后来的上传action  
			return '${ueuplodUrl}';
		} else {
			return this._bkGetActionUrl.call(this, action);
		}
	}
	ue.addListener("ready", function () {
        ue.setDisabled();
 	});
	
	//修改
	function update(id){
		//问题
		var question = $("#question").val();
		//答案
		var answer = ue.getContent();
		//是否热点
		var style = $("input:radio[name=style]:checked").val();
		var type = $("input:radio[name=type]:checked").val();
		var status = $("input:radio[name=status]:checked").val();
		var id = $("#id").val();
		var updatedTime = $("#updatedTime").val();
		$.ajax({
            "dataType": 'json',
            "type": "POST",
            "data": {
            			"question":question,
            			"answer":answer,
            			"type":type,
            			"status":status,
            			"style":style,
            			"id":id,
            			"updated_time":updatedTime
            		},
            "url": '${pageContext.request.contextPath}/farm/bhelp/update?id'+id,
            "success": function(data) {
				debugger
				if (data) {
					swal({
						title: "成功!",   
						text: "成功",   
						type: "info", 
					} , function(){window.location.href = "${helpcenterUrl}"});
				}else{
					swal({
						title: "失败!",   
						text: "失败",   
						type: "info", 
					} , function(){window.location.reload()});
				}
			},
			"error" : function(data) {
				debugger
				alert(data.responseText);//错误内容
			}
		});
	}
</script>
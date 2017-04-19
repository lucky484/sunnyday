<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/terminal/goods/page" var="goodsListUrl" />
<spring:url value="/farm/terminal/goods/index" var="goodsIndex" />
<spring:url value="/farm/goods/fshoplist" var="goodsListUrl" />

<spring:url
	value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.config.js"
	var="ueditorconfigJsUrl" />
 <script type="text/javascript" src="${ueditorconfigJsUrl}"></script> 
<!-- 编辑器源码文件 -->
<spring:url
	value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.all.js"
	var="ueditorAllJsUrl" />
<script type="text/javascript" src="${ueditorAllJsUrl}"></script>
<spring:url value="/market/bgoods/insert-bgoods" var="insertBgoodsUrl" />
<spring:url value="/market/bgoods/bgoodsdetails" var="bgoodsdetailsUrl" />
<spring:url value="/market/bgoods/bgoodslist" var="bgoodslistUrl" />
<script type="text/javascript">
$("#sharePercent").val("${data.sharePercent}");
var ue = UE.getEditor('container',{toolbars: [
                                              [
                                               'undo', //撤销
                                               'redo', //重做
                                               'bold', //加粗
                                               'italic', //斜体
                                               'underline', //下划线
                                               'strikethrough', //删除线
                                               'subscript', //下标
                                               'simpleupload', //单图上传
                                               'insertimage', //多图上传
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
var ue2 = UE.getEditor('container2',{toolbars: [
                         [
                          'undo', //撤销
                          'redo', //重做
                          'bold', //加粗
                          'italic', //斜体
                          'underline', //下划线
                          'strikethrough', //删除线
                          'subscript', //下标
                          'fontborder', //字符边框
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
	 $("input").each(function(){
		$(this).attr("disabled",true);
	 });
	
	 $("select").each(function(){
			$(this).attr("disabled",true);
		 });
	 
	function bgoodslistUrl(){
		window.location.href="${bgoodslistUrl}";
	}   
	ue.addListener("ready", function () {
        ue.setDisabled();
 	  });
	ue2.addListener("ready", function () {
        ue2.setDisabled();
   });
    </script>
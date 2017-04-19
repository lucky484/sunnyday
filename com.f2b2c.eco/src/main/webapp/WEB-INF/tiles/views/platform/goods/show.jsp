<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/resources/platform/js/jquery.min.js" var="jqueryJsUrl"/>
<script src="${jqueryJsUrl}"></script>
<div class="wrapper wrapper-content animated fadeInRight">
	  <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                      <div class="ibox-content">
                      <div class="ibox-title">
                                    <h5>商品详情</h5>
                                </div>
                        <form method="post" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">名称：</label>
                                <div class="col-sm-6">
                                    <input type="text" id="name" class="form-control" value="${data.name}">
                                </div>
                            </div>
                           <div class="form-group">
                                <label class="col-sm-3 control-label">商品类型：</label>
                                <div class="col-sm-3">
                                   <select class="form-control m-b" name="type" id="type">
                                        <option value="">
                                        				 <c:if test="${data.type == 1}">国产商品</c:if>
                                        				 <c:if test="${data.type == 2}">进口商品</c:if>
                                        				 <c:if test="${data.type == 3}">活动商品</c:if>
                                        				 <c:if test="${data.type == 4}">其他商品</c:if>
                                        				 </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">分类：</label>
                                <input type="hidden" id="hiddenKindId" value="2">
                                <div class="col-sm-3">
                                   <select class="form-control m-b" name="account">
                                        <option value="1">${data.kind.kindName}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">国产/进口：</label>
                                <div class="col-sm-3">
                                   <select class="form-control m-b" name="account" id="madeInChina">
                                       <c:if test="${data.madeInChina == 0}">														
											<option value="0">国产</option>
										</c:if>
										 <c:if test="${data.madeInChina == 1}">														
											<option value="1">进口</option>
										</c:if>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">图片：</label>
                                <div class="col-sm-6 logoUrlDiv">
									 <c:forEach items="${urllist}" var="list">
								      	<div class="picDiv" id="picDiv2">
										<img class='goodsImg' src='${list}' />
									</div>
								 	 </c:forEach>
								</div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">单价：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" id="price" value="${data.price / 100}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">单位：</label>
                                <div class="col-sm-3">
                                  <select class="form-control m-b" name="account" id="unit">
                                        <option value="0" <c:if test="${data.unit == '0'}"> selected=true</c:if>>箱</option>
                                        <option value="1" <c:if test="${data.unit == '1'}"> selected=true</c:if> >斤</option>
                                        <option value="2"<c:if test="${data.unit == '2'}"> selected=true</c:if>>公斤</option>
                                    </select> 
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label">库存：</label>
                                <div class="col-sm-6">
                                    <input type="number" class="form-control" id="remain" value="${data.remain}">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label">产地：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="producePlace" value="${data.producePlace}">
                                </div>
                            </div>
                             <%--  <div class="form-group">
                                <label class="col-sm-3 control-label">重量：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="unitOfWeight" value="${data.unitOfWeight}">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label">规格：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="spec" value="${data.spec}">
                                </div>
                            </div>  --%>
                            <c:if test="${data.sharePercent != '' &&  data.sharePercent != null}">
	                             <div class="form-group" id="yongjin" >
	                                <label class="col-sm-3 control-label">佣金比例<span class="flower">*</span>：</label>
	                                <div class="col-sm-3" >
	                                	<div class="input-group">
		                                    <input type="number" class="form-control" value="" size="16" id="sharePercent" value="${data.sharePercent}">
		                                    <span class="input-group-addon">%</span>
	                                    </div>
	                                </div>
	                            </div> 
                            </c:if>
                          <div class="form-group"> 
                               <label class="col-sm-3 control-label">详情：</label> 
                                <div class="col-sm-6"> 
                                   <div class="row"> 
								    <script id="container" name="content" type="text/plain"> 
											 ${data.detail} 
										 </script> 

								  		<script type="text/javascript"> 
   									    </script> 
									   </div> 
									   <input type="hidden" id="details" value=""> 
                                </div> 
                             </div> 
                             <div class="form-group">
                                <label class="col-sm-3 control-label">产品参数<span class="flower">*</span>：</label>
                                <div class="col-sm-6">
                                   <div class="row">
									    <script id="container2" name="content2" type="text/plain">${data.parameter}</script>
								  		<script type="text/javascript">
   									    </script>
									   </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"></label>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
</div>


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
    
    <style>
	th{
		text-align:center;
	}
	td{
		text-align:center;
	}
	.picDiv {
	width: 100px;
	height: 100px;
	border: 1px solid #ccc;
	text-align: center;
	color: #23527c;
	cursor: pointer;
	margin: 0 10px 0 0;
	position: relative;
	float: left;
}
.goodsImg {
	width:100%;
	height:100%;
}
</style>
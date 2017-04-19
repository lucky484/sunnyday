<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 <spring:url value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.config.js" var="ueditorconfigJsUrl"/>
 <spring:url value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.config.js" var="ueditorconfigJsUrl"/>
 <script type="text/javascript" src="${ueditorconfigJsUrl}"></script>
    <!-- 编辑器源码文件 -->
    <spring:url value="/resources/market/js/plugins/ueditor1_4_3_3/ueditor.all.js" var="ueditorAllJsUrl"/>
    <script type="text/javascript" src="${ueditorAllJsUrl}"></script>
    
<style>
/* 必填项样式 */
.flower {
	color: red;
	margin-left: 2px;
	font-size: 13px;
	font-style: italic;
}
</style>
 <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                	<!-- <div class="col-sm-12">
	                	<a class="btn btn-primary radius" data-title="添加门店" onclick="shop_add();" href="javascript:void(0);">
	                		<i class="fa fa-plus">添加门店</i>
	                	</a>
                	</div> -->
                	 <div class="ibox-title">
                                    <h5>商品发布</h5>
                                </div>
                      <div class="ibox-content">
                        <form method="post" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">名称 <span class="flower">*</span>：</label>
                                <div class="col-sm-6">
                                    <input type="text" id="name" class="form-control">
                                   
                                </div>
                            </div>
                           <div class="form-group">
                                <label class="col-sm-3 control-label">商品类型：</label>
                                <div class="col-sm-3">
                                   <select class="form-control m-b" name="type" id="type">
                                        <option value="1">国产商品</option>
                                        <option value="2">进口商品</option>
                                        <option value="3">活动商品 </option>
                                        <option value="4">其他商品 </option>
                                    </select>
                                </div>
                                
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">分类<span class="flower">*</span>：</label>
                                <div class="col-sm-3">
									<select id="kindTree" name="kind.id" class="form-control" ></select>
								</div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">国产/进口：</label>
                                <div class="col-sm-3">
                                   <select class="form-control m-b" name="account" id="madeInChina">
                                        <option value="0">国产</option>
                                        <option value="1">进口</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">图片<span class="flower">*</span>：</label>
                                <div class="col-sm-6 logoUrlDiv">
								<span class="help-block m-b-none">第一张图为封面图；建议尺寸：120 x 120 像素</span>
									<ul id="sortable">
										<li>
											<div class="picDiv" id="picDiv0">
												<input type="hidden" name="logoUrl" id="logoUrl0" /> <span
													class="picHint" id="picHint0"><br />
												<br />+ 加图</span> <input type="file" name="file" class="picInput"
													multiple="multiple" id="picInput0" />
											</div>
										</li>
									</ul>
								</div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label">单价<span class="flower">*</span>：</label>
                                <div class="col-sm-3">
	                                <div class="input-group">
	                                    <input type="number" class="form-control" id="price">
	                                     <span class="input-group-addon">元</span>
	                               	</div>
                                </div>
                                
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">单位：</label>
                                
                                <div class="col-sm-3">
								<select class="form-control m-b" name="account" id="unit">
                                        <option value="0">箱</option>
                                        <option value="1">斤</option>
                                        <option value="2">公斤</option>
                                    </select> 
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label">库存<span class="flower">*</span>：</label>
                                <div class="col-sm-6">
                                    <input type="number" class="form-control" id="remain">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label">产地<span class="flower">*</span>：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="producePlace">
                                </div>
                            </div>
                           <!--  <div class="form-group">
                                <label class="col-sm-3 control-label">简介<span class="flower">*</span>：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" id="intro">
                                </div>
                            </div> -->
                            
                           <div class="form-group hide" id="yongjin" >
                                <label class="col-sm-3 control-label">佣金比例<span class="flower">*</span>：</label>
                                <div class="col-sm-3" >
                                	<div class="input-group">
	                                    <input type="number" class="form-control" value="" size="16" id="sharePercent">
	                                    <span class="input-group-addon">%</span>
                                    </div>
                                </div>
                            </div> 
                             <div class="form-group">
                                <label class="col-sm-3 control-label">详情<span class="flower">*</span>：</label>
                                <div class="col-sm-6">
                                   <div class="row">
									    <script id="container" name="content" type="text/plain"></script>
								  		<script type="text/javascript">
   									    </script>
									   </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">产品参数<span class="flower">*</span>：</label>
                                <div class="col-sm-6">
                                   <div class="row">
									    <script id="container2" name="content2" type="text/plain"></script>
								  		<script type="text/javascript">
   									    </script>
									   </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label"></label>
                                <div class="col-sm-6">
                                   <button class="btn btn-primary" type="button" id="publish">发布商品</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
      </div>
      
  
    
  
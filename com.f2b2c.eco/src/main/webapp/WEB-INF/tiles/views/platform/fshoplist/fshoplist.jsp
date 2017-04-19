<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>平台商品列表</title>
</head>
<body>
	<div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                <div class="ibox-title">
                           <h5>商品列表</h5>
                      </div>
                    <div class="ibox-content">
                    	<div class="row">
                    		<div class="col-sm-2">
                    		<input id="name" type="text" class="form-control" placeholder="请输入商品名称" />
                    		</div>
                    		<div class="col-sm-2">
                    		<input id="kindTree" class="form-control">
                    		</div>
                    		<div class="col-sm-2">
                    		<input id="location" class="form-control" placeholder="请输入产地" />
                    		</div>
                    		<div class="col-sm-2">
                    			<button class="btn-sm btn btn-primary" type="button" onclick="search();">查询</button>
								<button class="btn-sm btn" type="button" onclick="reset();">清空</button>
                    		</div>
                		</div>
                        <table class="table table-striped table-bordered table-hover dataTables-shop" id="shop2list">
                            <thead>
                                <tr>
                                 <th width="10%">商品编号</th>
                                    <th width="14%">商品名称</th>
                                    <th width="4%">商品图片</th>
                                    <th width="7%">商品类型</th> 
                                    <th width="7%">分类</th>
                                    <th width="7%">单价(元)</th>
                                    <th width="5%">单位</th>
                                    <th width="5%">库存</th>
                                    <th width="5%">产地</th>
                                    <th width="9%">省市</th>
                                    <th width="7%">发布时间</th>
                                    <th width="5%">状态</th>
                                    <th width="9%">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
      </div>
</body>
</html>

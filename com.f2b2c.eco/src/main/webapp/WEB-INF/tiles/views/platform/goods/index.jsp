<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                	<!-- <div class="col-sm-12">
	                	<a class="btn btn-primary radius" data-title="添加门店" onclick="shop_add();" href="javascript:void(0);">
	                		<i class="fa fa-plus">添加门店</i>
	                	</a>
                	</div> -->
                    <div class="ibox-content">
                    	<div class="row">
                    		<div class="col-sm-2">
                    			<input id="goodsNo" type="text" class="form-control" placeholder="请输入商品编号"/>
                    		</div>
                    		<div class="col-sm-2">
                    			<input id="goodsName" type="text" class="form-control" placeholder="请输入商品名称"/>
                    		</div>
                    		<div class="col-sm-2">
                    			<input id="shopName" type="text" class="form-control" placeholder="请输入商店名称"/>
                    		</div>
                    		<div class="col-sm-2">
                    			<select id="goodsStatus" class="form-control">
                   				  <option value="">所有状态</option>
								  <option value="0">待审核</option>
								  <option value="1">上架</option>
								  <option value="3">审核不通过</option>
                    			</select>
                    		</div>
                    		<div class="col-sm-4">
                    			<button class="btn-sm btn btn-primary" type="button" onclick="search();">查询</button>
								<button class="btn-sm btn" type="button" onclick="reset();">清空</button>
                    		</div>
                		</div>
                        <table class="table table-striped table-bordered table-hover dataTables-shop" id="goodsList">
                            <thead>
                                <tr>
                                	<th class="hidden"></th>
                                    <th width="9%">商品编号</th>
                                    <th  width="15%">商品名称</th>
                                    <th width="10%">所属店铺</th>
                                    <th width="10%">佣金比例</th>
                                    <th width="5%">商品状态</th>
                                    <th width="10%">创建时间</th>
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
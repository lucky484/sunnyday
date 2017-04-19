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
                            <input id="name" type="text" class="form-control" placeholder="门店名称"/>
                            </div>
                            <div class="col-sm-6">
                                <button class="btn-sm btn btn-primary" type="button" onclick="search();">查询</button>
                                <button class="btn-sm btn" type="button" onclick="reset();">清空</button>
                            </div>
                        </div>
                        <table class="table table-striped table-bordered table-hover dataTables-shop" id="shopList">
                            <thead>
                                <tr>
                                    <th class="hidden"></th>
                                    <th>店铺名称</th>
                                    <th>店老板</th>
                                    <th>专属顾问</th>
                                    <th>门店地址</th> 
                                    <th>授权码</th>
                                    <th>创建时间</th>
                                    <th>状态</th>
                                    <th>操作</th>
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
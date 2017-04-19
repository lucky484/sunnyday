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
                    <div class="ibox-content row">
                   		<!-- <div class="col-sm-2">
                   			<input id="name" type="text" class="form-control search-conn" placeholder="请输入店铺名称"/>
                   		</div> -->
                  		<!-- <div class="col-sm-4">
							  <div class="timestarts">
								   <input class="time-txt" type="text" id="timeStart" data-date-format="yyyy-mm-dd" size="16" placeholder="请输入开始日期"/>
							  </div>
							  <div class="time_icon">~</div>
							  <div class="timeends">
								   <input class="time-txt" type="text" id="timeEnd" data-date-format="yyyy-mm-dd" size="16" placeholder="请输入结束日期"/>
							  </div>
	    				</div>
                   		<div class="">
                   			<button class="btn-sm btn btn-primary" type="button" onclick="search();">查询</button>&nbsp;&nbsp;
							<button class="btn-sm btn" type="button" onclick="reset();">清空</button>
                   		</div> -->
                        <table class="table table-striped table-bordered table-hover dataTables-shop" id="settlementList">
                            <thead>
                                <tr>
                                	<th class="hidden"></th>
                                    <th>订单编号</th>
                                    <th>分润时间</th>
                                    <th>订单金额</th>
                                    <th>所属角色</th>
                                    <th>分润比例</th>
                                    <th>分润金额</th>
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
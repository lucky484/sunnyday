<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url value="/farm/user/adduser" var="addUserUrl" />
 <div class="wrapper wrapper-content animated fadeInRight">
	<c:if test="${USER_INSESSION.roleId == 1 || USER_INSESSION.roleId == 4}">
		<div class="row">
			<a href="${addUserUrl}" class="btn btn-primary m-l">新增用户</a>
		</div>
	</c:if>
	<div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <!-- <div class="ibox-title">
                        <h5>基本 <small>分类，查找</small></h5>
                    </div> -->
                    <div class="ibox-content">
                        <table class="table table-striped  table-bordered dataTables-userlist">
                            <thead>
                                <tr>
                                    <th>帐号</th>
                                    <th>姓名</th>
                                    <th>角色</th>
                                    <th>手机号码</th>
                                    <th>所在地区</th>
                                   <!--  <th>性别</th> -->
                                    <th>用户状态</th>
                                    <th>创建时间</th>
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
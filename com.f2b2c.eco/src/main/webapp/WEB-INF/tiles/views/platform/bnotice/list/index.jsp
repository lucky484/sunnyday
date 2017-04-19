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
<title></title>
</head>
<body>
	<div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                <div class="ibox-title">
                           <h5>公告列表</h5>
                      </div>
                    <div class="ibox-content">
                    	<div class="row">
               			 <a data-toggle="modal" onclick="newBNoticePage()" class="btn btn-primary ">发布公告</a>
                		</div>
                        <table class="table table-striped table-bordered table-hover dataTables-shop" id="bnoticeTable" style="text-align: center;">
                            <thead>
                                <tr>
                                    <th width="50%" style="text-align: center;">公告主题</th>
                                    <th width="25%" style="text-align: center;">发布时间</th>
                                    <th width="25" style="text-align: center;">操作</th> 
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

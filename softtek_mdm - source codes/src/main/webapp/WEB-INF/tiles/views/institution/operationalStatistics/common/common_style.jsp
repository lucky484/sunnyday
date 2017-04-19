<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url
	value="/resources/js/datatables-1.10.11/css/dataTables.bootstrap.css"
	var="dataTableCss" />
<link href="${dataTableCss}" rel="stylesheet" />


<style>
.chart-mod {
	width: 96%;
	margin: 0px auto;
}

.inline-chartmod {
	display: inline-block;
	*display: inline;
	*zoom: 1;
	width: 45%;
	margin: 0px 10px;
	vertical-align: middle;
}

.fl-chart {
	float: left;
}

.fr-chart {
	float: right;
}

.switch-mod {
	display: inline-block;
	*display: inline;
	*zoom: 1;
	width: 143px;
	height: 30px;
	background: url(../images/common/switch-sprite.png) no-repeat 0 top;
}

.switch-mod a:link, .switch-mod a:visited {
	display: inline-block;
	*display: inline;
	*zoom: 1;
	height: 30px;
	line-height: 30px;
	width: 71px;
	text-align: center;
}

.grap-list {
	position: absolute;
}

.grap-list a:link, .grap-list a:visited {
	display: block;
	padding: 2px;
}

.normal-switch a:link, .normal-switch a:visited {
	display: inline-block;
	*display: inline;
	*zoom: 1;
	padding: 0px 5px;
}

.normal-switch a.current:link, .normal-switch a.current:visited {
	color: #369BD7;
}
</style>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>

/*****libra add css for organization part  3/23******/

/**tree part**/
.wrapper-md #tree {
	min-width: 180px;
}

.wrapper-md .scrollable.aside-md.col-sm-3 {
	width: 20%;
	border: 1px solid #eaeef1;
	border-radius: 3px;
}

.wrapper-md .col-sm-9 {
	width: 80%;
}

.wrapper-md  .list-group .node-tree {
	color: #788288;
	border: 1px solid #eaeef1;
}

.node-tree.node-selected {
	color: #ffffff;
	background-color: #3494d2 !important;
}

#ste_modify_frm .form-group:last-child .col-lg-10 {
	text-align: center;
}
/*******move css**********/
#moveModal {
	height: 500px;
}

#moveModal .modal-body {
	height: 400px;
}

.modal-dialog .panel-body {
	height: 450px;
}

#moveModal .panel-default {
	height: 300px;
}

#moveTree {
	height: 200px;
	overflow-y: auto;
	border: 1px solid #eaeef1;
}

#moveTree .list-group .node-moveTree {
	color: #788288;
	border: 1px solid #eaeef1;
}

#moveTree .list-group .node-moveTree.node-selected {
	background: #3494d2 !important;
}

.glyphicon-minus:before {
	content: "\e114" !important;
}

.glyphicon-plus:before {
	content: "\e080" !important;
}
</style>


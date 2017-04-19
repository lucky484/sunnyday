<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>
	th{
		text-align:center;
	}
	td{
		text-align:center;
	}
	.ptop{
		padding-top:10px;
	}
	hidden{
		display:none;
	}
	.btn-mr{
		margin-right:5px;
	}
	/* clean float */
	.cleanfix{
		zoom:1;
	}
	.cleanfix:after{
		content: "";
		display: block;
		clear:both;
	}
	@media (max-width: 1366px){
		.modal-dialog {
    		width: 600px;
    		margin: 30px auto;
		}
		#newModal {
			margin-top: 25%;
		}
	}
</style>

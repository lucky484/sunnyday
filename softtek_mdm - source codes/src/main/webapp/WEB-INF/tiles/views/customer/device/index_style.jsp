<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

  <spring:url value="/resources/js/datatables-1.10.11/css/dataTables.bootstrap.css" var="dataTableCss" />
  <link href="${dataTableCss}" rel="stylesheet" />
  <spring:url value="/resources/js/datepicker/bootstrap-datetimepicker.css" var="datetimePickerCss" />
  <link href="${datetimePickerCss}" rel="stylesheet" />
  
  <style>
  	  .pos{
  	  	margin-top:-5px;
  	  }
	  .separate_div_basic{
	  	 margin-top:50px;
	  }
	  .button_left{
	  	 margin-left:10px;
	  }
	  .labelPos{
	  	 margin-top:-24px;
	  }
	      .user li:nth-child(1) a i{color: #177bbb;}
.user li:nth-child(1) > a
{
    min-width: 120px;
    text-align: center;
}
.dropdown.open > ul > li:nth-child(1) > a{ text-align: left;}
/***search*************/
.search_part{margin-top: 15px;margin-bottom:-15px;}
.searchName{
    width: 10%;
}
.searchAccount{
    width: 12%;
}
.searchStatus{
    width: 12%;
}
.icon_pos{
	margin-left:25px;
}
.searchName_val,
.searchAccount_val,
.searchStatus_val
{
    width: 16%;
    padding: 0px;
    margin-top: -5px;
}
.search_button{
	margin-top:-18px;
	margin-left:10px;
}
.datatb-max-width-user-policy{max-width:100px!important;}
.datatb-max-width-user-username{max-width:100px!important;}
.network_info{margin-left:65px;}
.users_infomation {
    margin: 20px;
}
.btn-info {
    color: #fff !important;
    background-color: #3494d2 !important;}
    
.searchName_val {
    width: 20%!important;
}   
.timestarts{
    float: left;
    margin-right: 10px;
}
.time_icon {
    float: left;
    margin-right:5px;
}
.timeends {
    float: left;
}
.device_table .row{margin-top:30px;}
.user li:nth-child(1) > a
{
    min-width: 120px;
    text-align: center;
}
#divTime{
	
}
ol, ul,li {list-style: none;}
.select-list .select-item{ *zoom:1; padding:0px -20px 0px 12px;}
.inline-select{display:inline-block;*display:inline;*zoom:1; vertical-align:middle;/*margin:2px 10px;*/}
.select-box{ position:relative; width:180px;border:1px solid #BFBFC0; height:26px;line-height:22px; background:url(../resources/images/down.png) no-repeat right center #fff;  padding-left:10px;}
.select-box .Js_curVal input,.Js_curVal input[type=text]{ width:85%; height:23px; border:none;}
.select-list{ display:none; position:absolute; z-index:5;left:-1px;top:24px; width:218px; border:1px solid #ddd; white-space:nowrap; overflow:auto; max-height:300px;background:#F0F0F0;}
.search-toggle{margin:0px 0px 16px;background:#fff;height:1px;position:relative;cursor:pointer;padding-top:8px; border-bottom:1px solid #aaa;}
.search-toggle a{line-height:13px;padding:0 0 0px 23px;background:url("../resources/images/btn_sh.jpg") 5px -17px no-repeat #fff; height:18px;*line-height:18px;position:absolute;right:0;top:2px; display:inline;}
.search-toggle a.hide1{background-position:5px 1px}
.search-mod{position:relative;background:#FAFDFE;border:1px solid #ccc;/*float:left;width:100%;*/margin: 10px 0 5px;display:none}
.search-list{ margin:5px 5px 5px -29px;float:left}
.search-item{margin: 0 1px; padding:2px 0px; display:inline-block;*display:inline;*zoom:1; vertical-align:top;}
.search-label,.search-text{ display:inline-block;*display:inline;*zoom:1; vertical-align:middle; height:22px; line-height:22px;}
.search-label{ width:80px; text-align:right;}
.type-choice{padding:5px;text-align: right;float: right;margin-top:-6px;}
.button-search{height:27px;line-height: 24px;line-height:22px\9;padding:11px 5px 0 5px;margin: 0 12px 0 5px; vertical-align:middle;/*background:url(../images/bg/bg_btn.png) no-repeat 0 0;*/font-weight: normal;display: inline-block;*display:inline;*zoom:1;cursor:pointer;outline: none}
.search-text{ border:1px solid #bbb; width:150px;padding:0 5px}
</style>
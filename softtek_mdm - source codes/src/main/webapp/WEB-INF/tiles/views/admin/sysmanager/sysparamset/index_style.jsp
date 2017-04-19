<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>

.tab-list .current {
    border-bottom: 1px solid #fff;
    background: #fff;
    color: #6888B9;
    border-bottom: 4px solid #6888B9;
}
.tab-item {
    display: inline-block;
    padding: 4px 15px;
    font-size: 12px;
    margin-right: 10px;
    /* background: #66AFEB; */
    cursor: pointer;
}
ol, ul, li {
    list-style: none;
}

.tab-list {
    margin: 0 10px 0px 10px;
    /* padding: 10px 0px 0px 5px; */
    font-size: 0px;
    position: relative;
    border-bottom: 1px solid #CACAC8;
}

ul, menu, dir {
    display: block;
    list-style-type: disc;
}

.contentPart{
	margin-top:20px
}

.mdmdetails {
    position: relative;
    border-bottom: 1px solid #aaa;
    height: 1px;
    margin: 12px 0px;
    line-height: normal;
}

.mdmfont {
    position: absolute;
    padding: 6px 15px;
    left: 0px;
    top: -18px;
    background: #fff;
    font-size: 16px;
    color: #424954;
}
.setpart{
	background:white!important;
}

.red {
	display: inline-block;
	position: relative;
	width: 6px;
	margin-right: -6px;
	vertical-align: -3px;
	color: #f00;
}

.contentPart{
	margin-top:20px;
	vertical-align:middle;
	text-align:center;
}

.rightPart{
	margin-left : 10px;
}

table{
	width:100%;
	border-collapse: collapse;
	border-spacing: 0px;
	cellspacing: 0px;
	cellpadding: 0px;
}

tr .tdleft{
	width:20%;
	text-align:right;
}

tr .tdleft2{
	width:20%;
	text-align:right;
}
tr .tdright2{
	width:60%;
	text-align:left;
	height:30px;
}
tr .tdright{
	width:60%;
	text-align:left;
	height:30px;
}

.mdmparamset td {
    white-space: nowrap;
    vertical-align: middle;
    line-height: 25px;
    padding: 4px 10px 4px 4px;
}

.right-text{
	width:60%;
}

.right-textarea{
	width:60%;
	height:150px;
	overflow:auto;
}

.contentDiv{
	width:60%;
	height:150px;
	overflow:auto;
	border: 1px solid #A9A9A9
}

input{
	height:30px;
}

thead, td {
	border: 0px;
}

a:link, a:visited {
    text-decoration: none;
    color: #333;
}

.help-cons {
    z-index: 3;
    display: none;
    position: absolute;
    width: 300px;
    padding: 15px;
    border: 1px solid #ccc;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    border-radius: 5px;
    background: #fff;
    white-space: normal;
}

/* .mdmparamset td span {
    word-wrap: break-word;
    word-break: break-all;
    white-space: normal;
} */
.help-close {
    position: absolute;
    right: 6px;
    top: 4px;
    font-family: Tahoma;
    width: 10px;
    height: 10px;
    text-align: center;
    line-height: 10px;
    cursor: pointer;
}

#sendTestEmailWithParams a:link{
	color:green;
}
/* a:visited{
	color:red;
} */

/*a  upload */
.a-upload {
    padding: 4px 10px;
    height: 30px;
    width:155px;
    line-height: 20px;
    position: relative;
    cursor: pointer;
    color: #888;
    background: #fafafa;
    border: 1px solid #ddd;
    border-radius: 4px;
    overflow: hidden;
    display: inline-block;
    *display: inline;
    *zoom: 1;
    text-align: center;
}

.a-upload  input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
    filter: alpha(opacity=0);
    cursor: pointer
}

.a-upload:hover {
    color: #444;
    background: #eee;
    border-color: #ccc;
    text-decoration: none
}
.uploadvirmodel{
 margin-top: 20px;
}
.showFileName{
width:100%;
margin-top: 2px;
margin-left: -13px;
}


#sysToolBar > li > a{
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 10px;
}
</style>
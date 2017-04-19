<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>

ol, ul {
    margin-top: 0;
    margin-bottom: 10px;
}

.flow-steps {
    overflow: hidden;
    margin: 20px 0;
}

.flow-steps li.done .first {
    background-color: #1AB394;
    background-position: -12px -115px;
}

.flow-steps li .first {
    background: #e4e4e4 url(../../resources/market/img/flow_steps_bg.png) no-repeat -12px -69px;
}

.flow-steps li span, .flow-steps li strong {
    display: block;
}

.flow-steps li.done {
    background-color: #1AB394;
    background-position: 100% -46px;
    color: #fff;
}


.flow-steps .num4 li {
    width: 222px;
}

.flow-steps li.current-prev {
    background-position: 100% -23px!important;
}

.flow-steps li.last-current {
    background-color: #0e7b65;
    color: #fff;
    background-position: 100% -161px;
}

.flow-steps li {
    float: left;
    background: #e4e4e4 url(../../resources/market/img/flow_steps_bg.png) no-repeat 100% 0;
    font-size: 14px;
    font-weight: bold;
    height: 23px;
    line-height: 23px;
    text-align: center;
    color: #404040;
    padding: 0 15px 0 0;
    overflow: hidden;
}

.flow-steps li.last-current-1 {
    color: #fff;
    background-color: #1AB394;
    background-position: 100% -184px;
}

.flow-steps li.last-current-2 {
    background-color: #0e7b65;
    color: #fff;
    background-position: 100% -46px;
}

.flow-steps li.done .first-1 {
    background-color: #0e7b65;
    background-position: -12px -115px;
}

li {
	margin: 0px;
	padding: 0px;
}

.tabs-nav {
	position: absolute;
	z-index: 50;
	left: 20px;
}

ul, ol {
	list-style: none;
}

.tabs-nav .current {
	background-position: 0 -40px;
}

.tabs-nav li {
	float: left;
	margin-right: 3px;
	padding-top: 8px;
	width: 130px;
	line-height: 21px;
	text-align: center;
	background: url(../../resources/market/img/tabview_bg.gif) no-repeat 0
		6px;
	cursor: pointer;
}

.tabs-nav a {
	color: #111 !important;
	display: block;
	width: 130px;
}

.tabs-nav .current a {
	font-size: 14px;
	color: #333;
	font-weight: bold;
}

.tabs-container .tabs-panels {
	position: relative;
	top: 29px;
	margin-bottom: 40px;
	border: 1px solid #AEC7E5;
}

.tabs-panels {
	clear: both;
	height: auto;
	overflow: hidden;
	padding: 10px 20px;
}

.info-box table {
    table-layout: fixed;
	width: 910px;
}

h1, h2, h3, h4, h5, h6 {
    font-size: 100%;
}

h4{
   margin: 0;
   padding: 0;
}

caption, th {
    text-align: left;
}

table {
	border-collapse: collapse;
	border-spacing: 0;
	font-size: inherit;
}

.order-info .misc-info td, .order-info .contact-info td, .order-info .dealer-info td
	{
	padding: 5px 10px;
}

.order-info .contact-info .nickname, .order-info .contact-info .name,
	.order-info .contact-info .mail {
	margin-right: 10px;
}

.order-info .sep-row td {
	clear: both;
}

.order-info .sep-row td {
	border-bottom: 1px solid #ddd;
}

.info-box td, .info-box th {
	line-height: 1.5;
	padding: 2px 10px;
	word-wrap: break-word;
}

.order-info .misc-info .span {
	display: block;
	float: left;
	width: 90px;
	text-align: right;
	margin-right: 10px;
}

.order-hd th{
	color: #404040;
	background: #e8f2ff;
	text-align:center;
}

.order-item .item .pic-info {
	float: left;
	margin-right: 5px;
}

.order-item .pic-info .pic {
	overflow: hidden;
	border: none;
}

.pic a img {
    vertical-align: middle;
}

.s50 img {
	max-width: 50px;
	max-height: 50px;
}

.order-item .item .txt-info {
	text-align: left;
	line-height: 18px;
	margin-left: 55px;
}

.order-info .txt-info {
	color: #808080;
}

.item {
    border-left: 1px solid #ddd;
}	

.post-fee {
    border-right: 1px solid #ddd;
}

.order-info .order .item {
    border-left: 1px solid #ddd;
}

.order-info .order-hd td, .order-info .order-item td {
    border-color: #ddd!important;
}

.order-info .order-item td {
    vertical-align: middle;
    height: 100%;
    text-align: center;
    border-bottom: 1px solid #ddd;
}

.order-info .order .order-item .order-price, .order-info .order-item td {
    border-right: 1px solid #eee;
}

.order-info .order th, .order-info {
    padding: 8px 10px;
    line-height: 130%;
    overflow: hidden;
}
.order .order-item td {
    border-bottom: 1px solid #eee;
}

.order-info .txt-info .desc {
    color: #404040;
}

.order-hd th {
	border-top: 1px solid #ddd;
    border-bottom: 1px solid #ddd;
}

.s50, .s50 a {
    width: 50px;
    height: 45px;
}	

.order-info .order-ft td {
    border: none;
    border-top: 1px solid #ccc;
}

.order-info .order-ft .get-money, .order-info .order-ft .trade-money {
    text-align: right;
    float: right;
    display: inline;
    clear: both;
    line-height: 23px;
}

.order-info .order-ft .get-money strong {
    font-size: 16px;
    font-weight: bold;
    color: #f50;
    padding-right: 5px;
}

.logistics-list{
    width: 100%!important;
}

.simple-list {
    margin-bottom: 20px;
}

.logistics-list .span {
    width: 90px;
    text-align: right;
}

.simple-list td {
    vertical-align: top;
    text-align: left;
    padding-left: 0;
}

.simple-list th, .simple-list td {
    padding: 7px 10px;
    border-bottom: 1px solid #eed;
}

.wrapper {
    margin-left: auto;
    margin-right: auto;
}

.tabs-container {
    position: relative;
    margin: 20px 0;
}
</style>

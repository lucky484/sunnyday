<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
    String ctx = request.getContextPath();
    String rootPath = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    Object count = request.getAttribute("count");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <tiles:importAttribute name="title" />
    <title>系统管理|<c:out value="${title}" /></title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <spring:url value="/resources/market/favicon.ico" var="faviconUrl"/>
    <link rel="shortcut icon" href="${faviconUrl}">
    <spring:url value="/resources/market/css/bootstrap.min14ed.css" var="bootstrapCssUrl"/>
    <link href="${bootstrapCssUrl}" rel="stylesheet">
    <spring:url value="/resources/market/css/font-awesome.min93e3.css" var="fontAwesomeCssUrl"/>
    <link href="${fontAwesomeCssUrl}" rel="stylesheet">
    <spring:url value="/resources/market/css/animate.min.css" var="animateCssUrl"/>
    <link href="${animateCssUrl}" rel="stylesheet">
    <spring:url value="/resources/market/css/style.min862f.css" var="styleCssUrl"/>
    <link href="${styleCssUrl}" rel="stylesheet">
    <spring:url value="/resources/market/css/common/jquery.pnotify.default.css" var="pnotifyCssUrl"/>
    <link href="${pnotifyCssUrl}" rel="stylesheet">
    <spring:url value="/resources/platform/css/plugins/dataTables/dataTables.bootstrap.css" var="dataTablesCssUrl"/>
    <link href="${dataTablesCssUrl}" rel="stylesheet">
     <spring:url value="/resources/platform/css/plugins/iCheck/custom.css" var="customCssUrl"/>
    <link href="${customCssUrl}" rel="stylesheet">
    <tiles:insertAttribute name="style" />
</head>
<body class="gray-bg top-navigation">
     <div>
        <div id="page-wrapper" class="gray-bg">
            <div class="row border-bottom white-bg">
               <tiles:insertTemplate template="./header/header.jsp" />
            </div>
            <div class="wrapper">
               <tiles:insertAttribute name="content" />
            </div>
        </div>
    </div>
    <tiles:insertAttribute name="modal" />
    <spring:url value="/resources/market/js/jquery.min.js" var="jqueryJsUrl"/>
    <script src="${jqueryJsUrl}"></script>
    <spring:url value="/resources/market/js/plugins/metisMenu/jquery.metisMenu.js" var="jsMetisMenuJsUrl"/>
    <script src="${jsMetisMenuJsUrl}"></script>
    <spring:url value="/resources/market/js/plugins/slimscroll/jquery.slimscroll.min.js" var="slimscrollJsUrl"/>
    <script src="${slimscrollJsUrl}"></script>
    <spring:url value="/resources/market/js/plugins/layer/layer.min.js" var="layerJsUrl"/>
    <script src="${layerJsUrl}"></script>
    <spring:url value="/resources/market/js/hplus.min.js" var="hplusJsUrl"/>
  <%--   <script src="${hplusJsUrl}"></script> --%>
    <spring:url value="/resources/market/js/plugins/pace/pace.min.js" var="paceJsUrl"/>
    <script src="${paceJsUrl}"></script>
    <spring:url value="/resources/market/js/common/date.js" var="dateJsUrl"/>
    <spring:url value="/resources/market/js/plugins/validate/jquery.validate.min.js" var="validateJsUrl"/>
    <script src="${validateJsUrl}"></script>
    <spring:url value="/resources/market/js/plugins/validate/messages_zh.min.js" var="validateZNJsUrl"/>
    <script src="${validateZNJsUrl}"></script>
    <script src="${dateJsUrl}"></script>
    <spring:url value="/resources/market/js/jquery.min.js" var="jqueryUrl" />
    <spring:url value="/resources/platform/js/plugins/fileUpload/jquery.fileupload.js" var="fileUploadUrl" />
    <spring:url value="/resources/platform/js/plugins/fileUpload/vendor/jquery.ui.widget.js" var="widgetUrl" />
    <spring:url value="/resources/platform/js/plugins/fileUpload/jquery.iframe-transport.js" var="transportUrl" />
    <spring:url value="/resources/platform/js/plugins/easyUI/jquery.easyui.min.js" var="easyUIUrl" />
    <script type="text/javascript" src="${jqueryUrl}"></script>
    <script type="text/javascript" src="${easyUIUrl}"></script>
    <script type="text/javascript" src="${widgetUrl }"></script>
    <script type="text/javascript" src="${transportUrl }"></script>
    <script type="text/javascript" src="${fileUploadUrl }"></script>
    <spring:url value="/resources/market/js/common/jquery.pnotify.js" var="pnotifyJsUrl"/>
    <script src="${pnotifyJsUrl}"></script>
    <spring:url value="/resources/market/js/common/color.message.js" var="colorMessageJsUrl"/>
    <script src="${colorMessageJsUrl}"></script>
    <spring:url value="/resources/platform/js/plugins/dataTables/jquery.dataTables.js" var="dataTablesJsUrl"/>
    <script src="${dataTablesJsUrl}"></script>
    <spring:url value="/resources/platform/js/plugins/dataTables/dataTables.bootstrap.js" var="dataTablesBootstrapJsUrl"/>
    <script src="${dataTablesBootstrapJsUrl}"></script>
    <spring:url value="/resources/market/js/bootstrap.min.js" var="bootstrapJsUrl"/>
    <script src="${bootstrapJsUrl}"></script>
      <spring:url value="/resources/market/js/plugins/iCheck/icheck.min.js" var="icheckJsUrl"/>
    <script src="${icheckJsUrl}"></script>
    <tiles:insertAttribute name="script" />
    <spring:url value="/resources/market/js/plugins/qrcodejs-master/qrcode.js" var="qrcodeJsUrl"/>
    <script type="text/javascript" src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
    <script src="${qrcodeJsUrl}"></script>
    <spring:url value="/resources/market/img/message.png" var="messageUrl"/>
     <spring:url value="/api/user/btoc-recommend-page" var="recommendPage" />
    <script type="text/javascript">
     var path = '<%=rootPath%>';
     $(function(){
         var url = "ws://" + path + "webSocketServer";
         var websocket;
         if ('WebSocket' in window) {
         websocket = new WebSocket(url);
         } else if ('MozWebSocket' in window) {
         websocket = new MozWebSocket(url);
         } else {
         websocket = new SockJS("http://"+ path +"sockjs/webSocketServer");
         }
         websocket.onopen = function (evnt) {
         };
         websocket.onmessage = function (evnt) {
              if(window.Notification && Notification.permission !== "denied") {
                 Notification.requestPermission(function(status) {    // 请求权限
                     if(status === 'granted') {
                         // 弹出一个通知
                         var n = new Notification('message', {
                             body : evnt.data,
                             icon : '${messageUrl}'
                         });
                         // 两秒后关闭通知
                         setTimeout(function() {
                             n.close();
                         }, 4000);
                     }else{
                          $.notify("success", evnt.data);
                     }
                 });
             }else{
                  $.notify("success", evnt.data);
             }
         };
         websocket.onerror = function (evnt) {
         };
         websocket.onclose = function (evnt) {
         };
         //未处理的消息提示
        var count = "${count}";
        if(count != "null" && count != "" && parseInt(count) > 0){
                  if(window.Notification && Notification.permission !== "denied") {
                     Notification.requestPermission(function(status) {    // 请求权限
                         if(status === 'granted') {
                             // 弹出一个通知
                             var n = new Notification('message', {
                                 body : "您有"+count+"笔订单未处理，请及时处理",
                                 icon : '${messageUrl}'
                             });
                             // 两秒后关闭通知
                             setTimeout(function() {
                                 n.close();
                             }, 4000);
                         }else{
                              $.notify("success","您有"+count+"笔订单未处理，请及时处理");
                         }
                     });
                 }else{
                      $.notify("success","您有"+count+"笔订单未处理，请及时处理");
                 }
        }
     });
    var ctx = '<%=ctx%>';
    //生成二维码
    function qrcode(){
        $("#recommendTable").dataTable({
            "dom":"<'m-r m-t-lg pull-right'f>t<'row col-sm-12'<'col-sm-4'>r<'col-sm-4'><'pull-right'p>>",
            "autoWidth": false,
            "searching" : false,
            "stateSave" : true,
            "ordering" : false,
            "bSort":false,
            "pageLength" : 10,
            "pagingType" : "full_numbers",
            "serverSide" : true,
            "bDestroy" : true,
            "ajax" : {
                "url" : "${recommendPage}",
                "type" : "POST",
                "dataSrc" : "content",
                "dataType":'json',
            },
            "columns" : [{
                data : "nickName",
                data : "registerTime"
            }  ],
            "columnDefs" : [
                    {
                            targets : [0],
                            "render" : function(data, type,full, meta) {
                                if(full.nickName != null){
                                    return full.nickName;
                                }else{
                                    return "匿名用户";
                                }
                                
                            }
                            },{
                            targets : [1],
                            "render" : function(data, type,full, meta) {
                                    if(full.registerTime != null){
                                        var length = full.registerTime.length-2;
                                        var TimeStr = full.registerTime.substring(0,length);
                                        return TimeStr;
                                    }else{
                                        return "";
                                    }
                                }
                        },
            ]
    });
        var qrcodeUrl = "${qrcodeUrl}";
        $("#qrcodeStr").html("");
        var qrcode = new QRCode(document.getElementById("qrcodeStr"), {
            width : 300,//设置宽高
            height : 300
        });
        qrcode.makeCode(qrcodeUrl);
            $("#qrcodeModal").modal({backdrop: 'static', keyboard: false});
        }
    
    </script>
    <!-- modal start -->
         <div class="modal inmodal" id="qrcodeModal" tabindex="-1" role="dialog" aria-hidden="true">
          <div class="modal-dialog">
              <div class="modal-content animated bounceInRight">
                      <div class="modal-header">
                       <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                       </button>
                       <h4 class="modal-title">我的分享二维码</h4>
                      </div>
                      <div class="modal-body cleanfix">
                      <div class="form-group" id="qrcodeStr"style="margin-left: 20%;"></div>
                      <div class="ibox-content">
                        <table class="table table-striped table-bordered table-hover " id="recommendTable" style="text-align: center;">
                            <thead>
                                <tr>
                                    <th>粉丝昵称</th>
                                    <th>注册时间</th>
                                </tr>
                            </thead>
                        </table>
                      </div>
                      </div>
                      <div class="modal-footer">
                          <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                      </div>
                  </div>
              </div>
          </div>
       <!-- modal end -->
</body>
</html>

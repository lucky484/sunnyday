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
<title>商品发布</title>
<spring:url value="/farm/goods/insert-fgoods" var="insertFGoodsUrl" />
<spring:url value="/farm/admin/kind/get-kinds-for-jstree"
    var="getKindsUrl" />
<spring:url value="/farm/goods/insert-fgoods-pic"
    var="uploadGoodsPicUrl" />
<spring:url value="/resources/platform/js/jquery.min.js" var="jqueryUrl" />
<spring:url
    value="/resources/platform/js/plugins/ueditor1_4_3_3/ueditor.config.js"
    var="ueditorconfigJsUrl" />
<spring:url
    value="/resources/platform/js/plugins/ueditor1_4_3_3/ueditor.all.js"
    var="ueditorAllJsUrl" />
<spring:url
    value="/resources/platform/js/plugins/easyUI/jquery.easyui.min.js"
    var="easyUIUrl" />
<spring:url
    value="/resources/platform/css/plugins/easyUI/themes/metro/easyui.css"
    var="easyUICssUrl" />
<spring:url
    value="/resources/platform/js/plugins/fileUpload/jquery.fileupload.js"
    var="fileUploadUrl" />
<spring:url
    value="/resources/platform/js/plugins/fileUpload/vendor/jquery.ui.widget.js"
    var="widgetUrl" />
<spring:url
    value="/resources/platform/js/plugins/fileUpload/jquery.iframe-transport.js"
    var="transportUrl" />
<link rel="stylesheet" type="text/css" href="${easyUICssUrl}" />
<%-- <script type="text/javascript" src="${ueditorconfigJsUrl}"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${ueditorAllJsUrl}"></script> --%>
<style>
.half {
    width: 50%;
    float: left;
}

.picDiv {
    width: 100px;
    height: 100px;
    border: 1px solid #ccc;
    text-align: center;
    color: #23527c;
    cursor: pointer;
    margin: 0 10px 0 0;
    position: relative;
    float: left;
}

.picHint {
    vertical-align: middle;
    position: absolute;
    top: 40px;
    left: 30px;
}

.picInput {
    width: 100px;
    height: 100px;
    position: absolute;
    opacity: 0;
    cursor: pointer;
}

.goodsImg {
    width:100%;
    height:100%;
}
</style>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row">
        <button class="btn btn-info" onclick="fgoodslistUrl()" style="">返回商品列表</button>
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>商品详情</h5>
                    </div>
                    <div class="ibox-content">
                        <!--  <div class="alert alert-info">
                                这边放错误提示
                        </div> -->
                        <form class="form-horizontal m-t"
                            enctype="multipart/form-data" action="${insertFGoodsUrl}" method="post">
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">名称：</label>
                                <div class="col-sm-6">
                                    <input type="text" name="name" class="form-control" value="${data.name}"> 
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">商品类型：</label>
                                <div class="col-sm-6">
                                    <select class="form-control" name="type">
                                        <option value="1" <c:if test="${data.type == '1'}"> selected=true</c:if>>国产商品</option>
                                        <option value="2" <c:if test="${data.type == '2'}"> selected=true</c:if>>进口商品</option>
                                        <option value="3" <c:if test="${data.type == '3'}"> selected=true</c:if>>活动商品</option>
                                        <option value="4" <c:if test="${data.type == '4'}"> selected=true</c:if>>其他商品 </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">分类：</label>
                                <div class="col-sm-6">
                                    <select id="kindTree" name="kind.id" class="form-control"></select>
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">进口/国产：</label>
                                <div class="col-sm-6">
                                    <select id="" name="madeInChina" class="form-control">
                                        <option value="0">进口</option>
                                        <option value="1">国产</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">图片：</label>
                                <div class="col-sm-6 logoUrlDiv">
                                <c:forEach items="${urllist}" var="list">
                                          <div class="picDiv" id="picDiv2">
                                        <img class='goodsImg' src='${list}' />
                                    </div>
                                      </c:forEach>
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">单价(元)：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" name="pricex" value="${data.price / 100}"/>
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">单位：</label>
                                <div class="col-sm-6">
                                    <select class="form-control m-b" name="unit" id="unit">
                                        <option value="0" <c:if test="${data.unit == '0'}"> selected=true</c:if>>箱</option>
                                        <option value="1" <c:if test="${data.unit == '1'}"> selected=true</c:if> >斤</option>
                                        <option value="2"<c:if test="${data.unit == '2'}"> selected=true</c:if>>公斤</option>
                                    </select> 
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">库存：</label>
                                <div class="col-sm-6">
                                    <input type="text" name="remain" class="form-control" placeholder=""  value="${data.remain}">
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">产地：</label>
                                <div class="col-sm-6">
                                    <input type="text" name="producePlace" class="form-control" placeholder=""  value="${data.producePlace}">
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">投放市场 省：</label>
                                    <div class="col-sm-3">
                                    <select class="form-control provinceSelect">
                                            <option value="${data.provinceName}" >${data.provinceName}</option>
                                    </select>
                                    </div>
                            </div>
                            
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">市：</label>
                                    <div class="col-sm-3">
                                    <select class="form-control citySelect" id="cityId" name="cityId">
                                        <option value="1">${data.cityName}</option>
                                    </select>
                                    </div>
                                
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">规格：</label>
                                <div class="col-sm-6">
                                    <input type="text" name="spec" class="form-control" value="${data.spec}"> 
                                </div>
                            </div>
                            <div class="form-group draggable">
                                <label class="col-sm-3 control-label">简介：</label>
                                <div class="col-sm-6">
                                    <input type="text" name="properties" class="form-control" value="${data.intro}"> 
                                </div>
                            </div>
<!--                             <div class="form-group draggable"> -->
<!--                                 <label class="col-sm-3 control-label">描述：</label> -->
<!--                                 <div class="col-sm-6"> -->
<!--                                     加载编辑器的容器 -->
<!--                                     <script id="container" name="detail" type="text/plain"> -->
                                    <%--     ${data.detail}
                                    </script> --%>
<!--                                 </div> -->
<!--                             </div> -->
                            <div class="form-group draggable">
                                <div class="col-sm-9 col-sm-offset-3">
                                </div>
                            </div>
                        </form>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${jqueryUrl}"></script>
    <script type="text/javascript" src="${easyUIUrl}"></script>
    <script type="text/javascript" src="${widgetUrl }"></script>
    <script type="text/javascript" src="${transportUrl }"></script>
    <script type="text/javascript" src="${fileUploadUrl }"></script>
    <script>
        //var picDiv = '<div class="picDiv"><span class="picHint">+ 加图</span> <input type="file" name="file" class="picInput" /></div>';
        //var index = 0;
        
    var cityStr;
    var citys;
    var areasMap={};
   // var ue = UE.getEditor('container');
    $("input").each(function(){
        $(this).attr("disabled",true);
     });
    /*  var details =${data.detail}; */
     $("select").each(function(){
            $(this).attr("disabled",true);
         });
    /*  ue.addListener("ready", function () {
         ue.setDisabled();
    }); */
        $("#kindTree").combotree({
            url : '${getKindsUrl}',
            height : 35,
            panelHeight : 'auto',
        });
        
        for (var i = 0; i < 7;i++) {
            goodsImgUpload(i);
        }

        function picDivStr(i) {
            return '<div id="picDiv'+i+'" class="picDiv"><span class="picHint">+ 加图</span> <input type="file" name="file" class="picInput" id="picInput'+i+'" /></div>';
        }
        
        function goodsImgUpload(index) {
            $('#picInput' + index).fileupload({
                url : '${uploadGoodsPicUrl}',
                dataType : 'json',
                done : function(e, data) {
                    if (data.result.status == 200) {
                        /* if ($("#logoUrl").val() == '') {
                            $("#logoUrl").val(data.result.data);
                        } else {
                            $("#logoUrl").val($("#logoUrl").val() + "|" + data.result.data);
                        } */
                        $("#logoUrl" + index).val(data.result.data);
                        $("#picDiv" + index).html("<img class='goodsImg' src='${path}" + data.result.data + "' />");
                        //$(".logoUrlDiv").append(picDivStr(++index));
                        //goodsImgUpload('#picInput' + index);
                    }
                }
            });
        }
        
        $(function(){
            $(".provinceSelect").on("change",function(){
                cityStr = "";
                citys = "";
                cityStr=$(".provinceSelect option:selected").attr("ref");
                citys=cityStr.split('|');
                $(".citySelect").html('');
                $(".areaSelect").html('');
                if(cityStr.length>0){
                    var areasWithCityOne=citys[0].split('&');
                    for(var i=0;i<citys.length;i++){
                        var areasWithCity=citys[i].split('&');
                        $(".citySelect").append("<option value='"+areasWithCity[0].split('-')[0]+"'>"+areasWithCity[0].split('-')[1]+"</option>");
                        if (i == 0)
                        {
                            var areas = areasWithCity[1].split(',');
                            for (var j=0; j < areas.length; j ++)
                            {
                                $(".areaSelect").append("<option value='"+areas[j].split('-')[0]+"'>"+areas[j].split('-')[1]+"</option>");
                            }
                        }
                        areasMap[areasWithCity[0].split('-')[0]] = areasWithCity[1];
                    }
                }
            });
            var width = $(".combo").width();
            $(".combo").width(width-28);
        });
        $(".validatebox-text").val("${data.kind.kindName}");
        $(".validatebox-text").next().val("${data.kind.id}");
    </script>
</body>
</html>

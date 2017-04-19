<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/shop/page" var="shopListUrl"/>
<spring:url value="/farm/shop/index" var="shopIndex" />
<script src="http://cache.amap.com/lbs/static/es5.min.js"></script>
<script src="http://webapi.amap.com/maps?v=1.3&key=1d1f3be09e85647da45eace0410cab72"></script>
<script>
    function getShopList(){
        var name = $('#name').val();
        $("#shopList").dataTable({
            "dom":"<'m-r m-t-lg pull-right'f>t<'row col-sm-12'<'col-sm-4'l>r<'col-sm-4'i><'pull-right'p>>",
            "autoWidth": false,
            "searching" : false,
            "stateSave" : true,
            "ordering" : false,
            "bSort":false,
            "serverSide" : true,
            "pagingType" : "full_numbers",
            "bDestroy" : true,
            "ajax" : {
                "url" : "${shopListUrl}?now="+new Date().getTime(),
                "type" : "post",
                "dataType":'json',
                "dataSrc":"content",
                "data" :{"name":name},
            },
            "columns": [
                        {"data" : "id" },
                        {"data" : "shopName"},
                        {"data" : "user.realName"},
                        {"data" : "createdUser.realName"},
                        {"data" : "address"},
                        {"data" : "authCode"},
                        {"data" : "createdTime"},
                        {"data" : ""}
                    ],
                 "columnDefs" : [
                        {
                            targets : [0],
                            "visible": false
                        },
                        {
                            targets : [2],
                            "render" : function(data, type,full, meta) {
                                if(full.user == null){
                                    return '';
                                }else{
                                    return full.user.realName;
                                }
                            }
                        },
                        {
                            targets : [3],
                            "render" : function(data, type,full, meta) {
                                if(full.createdUser ==  null){
                                    return '';
                                }else{
                                    return full.createdUser.realName;
                                }
                            }
                        },
                        {
                            targets : [6],
                            "render" : function(data, type,full, meta) {
                                if(null == full.createdTime){
                                    return '';
                                }else{
                                    return new Date(parseInt(full.createdTime)).Format("yyyy-MM-dd hh:mm:ss");
                                }
                                
                            }
                        },
                        {
                            targets : [7],
                            "render" : function(data, type,full, meta) {
                                var state = "";
                                if(full.isEnable==0){
                                    state = "禁用中";
                                } else {
                                    state = "启用中";
                                }
                                return state;
                            }
                        },
                        {
                            targets : [8],
                            "render" : function(data, type,full, meta) {
                                var btns = "";
                                    btns += '<a class="ml-5" onclick="shop_update(this,'+full.id+')" href="javascript:void(0);" title="修改店铺">修改</a>&nbsp;&nbsp;';
                                 if(full.isEnable==0){
                                    btns += '<a class="ml-5" onclick="shop_enable(this,'+full.id+',1);" href="javascript:void(0);" title="启用店铺">启用</a>&nbsp;&nbsp;';
                                } else {
                                    btns += '<a class="ml-5" onclick="shop_enable(this,'+full.id+',0);" href="javascript:void(0);" title="禁用店铺">禁用</a>&nbsp;&nbsp;';
                                } 
                                btns += '<a class="ml-5" onclick="shop_delete(this,'+full.id+','+full.user.id+');" href="javascript:void(0);" title="删除店铺">删除</a>&nbsp;&nbsp;';
                                btns += '<a class="ml-5" onclick="shop_show(this,'+full.id+');" href="javascript:void(0);" title="查看店铺">查看</i></a>'; 
                                return btns;
                            }
                        }
                    ]
            });
        }
    
    //添加门店的方法
    function shop_add(){
        window.location.href = "${pageContext.request.contextPath}/farm/shop/add";
    }
    
    // 显示店铺相关信息
    function shop_update(obj,id){
         $.ajax({
            "dataType": 'json',
            "type": "post",
            "data": {"id":id},
            "url": "${pageContext.request.contextPath}/farm/shop/get",
            "success": function(result){
                if(result!=null){
                    $("#shopName").val(result.shopName);
                    $("#remark").val(result.remark);
                    $("#address").val(result.provinceName +result.cityName + result.areaName + result.address);
                    $("#phone").val(result.phone);
/*                     $("#lnglat").val(result.locationX+","+result.locationY); */
/*                     var provinceStr = "<select class='border_line_color' id='provinceCode' onchange='chooseProvince()' readonly='true'>";
                    $.each(result.provinceList,function(index,obj){
                        if(obj.code==result.provinceCode){
                            provinceStr += "<option selected='selected' value = "+obj.code+">"+obj.name+"</option>";
                        } else {
                            provinceStr += "<option value = "+obj.code+">"+obj.name+"</option>";
                        }
                        
                    });
                    provinceStr += "</select>";
                    
                    var cityStr = "<select class='border_line_color' id='cityCode' onchange='chooseCity()' readonly='true'>";
                    $.each(result.cityList,function(index,obj){
                        if(obj.code==result.cityCode){
                            cityStr += "<option selected='selected' value = "+obj.code+">"+obj.name+"</option>";
                        } else {
                            cityStr += "<option value = "+obj.code+">"+obj.name+"</option>";
                        }
                        
                    });
                    cityStr += "</select>";
                    
                    var areaStr = "<select class='border_line_color' id='areaCode' readonly='true'>";
                    $.each(result.areaList,function(index,obj){
                        if(obj.code==result.areaCode){
                            areaStr += "<option selected='selected' value = "+obj.code+">"+obj.name+"</option>";
                        } else {
                            areaStr += "<option value = "+obj.code+">"+obj.name+"</option>";
                        }
                    });
                    areaStr += "</select>";
                    $("#provinceHtml").html(provinceStr);
                    $("#cityHtml").html(cityStr);
                    $("#areaHtml").html(areaStr); */
                    $("#modifyShop").attr("onclick","shop_modify("+id+")");
                }
/*                 initMap(result.locationX,result.locationY,result.address); */
                $("#modifyShopModal").modal({backdrop: 'static', keyboard: false});
            }
        });
    }
    
    // 修改店铺
    function shop_modify(id){
        var shopName = $("#shopName").val();
        var remark = $("#remark").val();
        var address = $("#address").val();
        var areaCode = $("#areaCode").val();
        var phone = $("#phone").val();
        if(shopName==null||shopName==""){
            swal({
                title: "请输入店铺名称！",
                type: "warning"
            });
            return false;
        }
        if(address==null||address==""){
            swal({
                title: "请输入店铺地址！",
                type: "warning"
            });
            return false;
        }
/*         var lnglat = $("#lnglat").val();
        if(lnglat==null||lnglat==""){
            swal({
                title: "输入店铺地址有误！",
                type: "warning"
            });
            return false;
        } */ 
        $.ajax({
            "dataType": 'json',
            "type": "POST",
            "data": {"id":id,"phone":phone,"shopName":shopName,"remark":remark,"address":address,"areaCode":areaCode},
            "url": "${pageContext.request.contextPath}/farm/shop/modify",
            "success": function(result){
                if(result.type == "error"){
                    swal({
                        title: "修改店铺操作失败！",
                        type: "warning"
                    });
                } else {
                    window.location.href = "${shopIndex}";
                }
            }
        });
    }
    
    // 启用禁用门店
    // tag=1表示要进行启用店铺操作 tag=0表示要禁用店铺操作
    function shop_enable(obj,id,tag){
        var title = "";
        var text = "";
        var confirmButton = "";
        var tips = "";
        if(tag==1){
            title = "您确定启用店铺信息吗?";
            text = "启用店铺操作！";
        } else {
            title = "您确定禁用店铺信息吗?";
            text = "店鋪被禁用后，相关商品被下架，请谨慎操作！";
        }
        swal({
            title: title,
            text: text,
            type: "warning",
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            showCancelButton: true,
            closeOnConfirm: false
        }, function () {
            $.ajax({
                "dataType": 'json',
                "type": "post",
                "data": {"tag":tag,"id":id},
                "url": "${pageContext.request.contextPath}/farm/shop/enable",
                    "success": function(result){
                        if(result.type == "error"){
                            swal({
                                title: result.message,
                                type: "warning"
                            });
                        } else {
                            window.location.href = "${shopIndex}";
                        }
                    }
              });
        });
    }
    
    // 选择省
    function chooseProvince(){
        var code = $("#provinceCode").val();
        $.ajax({
            "dataType": 'json',
            "type": "post",
            "data": {"code":code},
            "url": "${pageContext.request.contextPath}/farm/shop/chooseProvince",
            "success": function(result){
                var cityStr = "<select id='cityCode' class='border_line_color' onchange='chooseCity()' style='width:120px;'>";
                cityStr += "<option value = ''>请选择</option>";
                $.each(result,function(index,obj){
                    cityStr += "<option value = "+obj.code+">"+obj.name+"</option>";
                });
                cityStr += "</select>";
                var areaStr = "<select id='areaCode' class='border_line_color'>";
                areaStr += "<option value = ''>请选择</option>";
                areaStr += "</select>";
                $("#cityHtml").html(cityStr);
                $("#areaHtml").html(areaStr);
            }
        });
        
    }
    
    // 选择市
    function chooseCity(){
        var code = $("#cityCode").val();
        $.ajax({
            "dataType": 'json',
            "type": "post",
            "data": {"code":code},
            "url": "${pageContext.request.contextPath}/farm/shop/chooseCity",
            "success": function(result){
                var areaStr = "<select id='areaCode' class='border_line_color'>";
                areaStr += "<option value = ''>请选择</option>";
                $.each(result,function(index,obj){
                    areaStr += "<option value = "+obj.code+">"+obj.name+"</option>";
                });
                $("#areaHtml").html(areaStr);
            }
        });
    }
    
    //删除门店的方法
    function shop_delete(obj,id,userId){
        //删除门店采用ajax请求到后台
        swal({
            title: "您确定删除店铺信息吗?",
            text: "店铺被删除后，相关商品不能被销售，请谨慎操作！",
            type: "warning",
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            showCancelButton: true,
            closeOnConfirm: false
        }, function () {
            $.ajax({
                "dataType": 'json',
                "type": "POST",
                "data": {"id":id,"userId":userId},
                "url": "${pageContext.request.contextPath}/farm/shop/delete",
                    "success": function(result){
                        if(result.type == "error"){
                            swal({
                                title: result.message,
                                type: "warning"
                            });
                        }else{
                            window.location.href = "${shopIndex}";
                        }
                    }
             });
        });
    }
    //查看门店的方法
    function shop_show(obj,id){
        $.ajax({
            "dataType": 'json',
            "type": "post",
            "data": {"id":id},
            "url": "${pageContext.request.contextPath}/farm/shop/get",
            "success": function(result){
                if(result!=null){
                    $("#findPhone").val(result.phone);
                    $("#findShopName").val(result.shopName);
                    $("#findRemark").val(result.remark);
                    $("#findAddress").val(result.provinceName+result.cityName+result.areaName+result.address);
                    $("#bUserName").val(result.bUserName);
                    $("#fUserName").val(result.fUserName);
                    $("#authCode").val(result.authCode);
                }
                $("#findShopModal").modal({backdrop: 'static', keyboard: false});
            }
        });
    }
    //查询table的方法
    function search(){
        getShopList();
        $('#shopList').dataTable().fnDraw();
    }
    //清空table的方法
    function reset(){
        $('#name').val("");
        getShopList();
        $('#shopList').dataTable().fnDraw();
    }
    
    // 地址change事件获取经纬度
    function shop_change(){
        var province = $("#provinceCode").find("option:selected").text();
        var city = $("#cityCode").find("option:selected").text();
        var area = $("#areaCode").find("option:selected").text();
        var address = $("#address").val();
        var getAddress = province + city + area + address;
        AMap.plugin('AMap.Geocoder',function(){
            var geocoder = new AMap.Geocoder({
                city: "010"//城市，默认：“全国”
            });
            geocoder.getLocation(getAddress,function(status,result){
                if(status=='complete'&&result.geocodes.length){
                    lnglat = result.geocodes[0].location;
                    $("#lnglat").val(lnglat);
                } else {
                	$("#lnglat").val("");
                    swal({
                        title: "输入店铺地址有误！",
                        type: "warning"
                    });
                    return false;
                }
           });
        });
    }
    
    $(function(){
        getShopList();
    });
</script>
<script type="text/javascript" src="http://webapi.amap.com/demos/js/liteToolbar.js"></script>
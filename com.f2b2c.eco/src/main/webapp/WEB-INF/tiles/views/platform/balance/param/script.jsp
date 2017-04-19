<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<spring:url value="/resources/platform/js/plugins/nouslider/jquery.nouislider.min.js" var="nouisliderJsUrl"/>
<script src="${nouisliderJsUrl}"></script>

<spring:url value="/farm/balance/modify" var="modifyUrl"/>

<script type="text/javascript">
    var provinceSlider = document.getElementById('province'),
    citySlider = document.getElementById('city'),
    areaSlider = document.getElementById('area'),
    notfruitSlider = document.getElementById('notfruit'),
    platformVsCustomerSlider = document.getElementById('platformVsCustomer'),
    provinceSliderValue = document.getElementById('province-span'),
    citySliderValue = document.getElementById('city-span'),
    areaSliderValue = document.getElementById('area-span'),
    notfruitSliderValue = document.getElementById('notfruit-span'),
    platformSliderValue = document.getElementById('platform-span'),
    customerSliderValue = document.getElementById('customer-span');

    //省级代理
    noUiSlider.create(provinceSlider, {
        start: "${model==null?0:model.province==null?0:model.province/100}",
        connect: 'lower',
        animate: true,
        range: {
            min: 0,
            max: 30
        },
    });
    //市级代理
    noUiSlider.create(citySlider, {
        start: "${model==null?0:model.city==null?0:model.city/100}",
        connect: 'lower',
        animate: true,
        range: {
            min: 0,
            max: 30
        },
    });
    //区级代理
    noUiSlider.create(areaSlider, {
        start: "${model==null?0:model.area==null?0:model.area/100}",
        connect: 'lower',
        animate: true,
        range: {
            min: 0,
            max: 30
        },
    });

    //非水果类提取比例
    noUiSlider.create(notfruitSlider, {
        start: "${model==null?0:model.notFruit==null?0:model.notFruit/100}",
        connect: 'lower',
        animate: true,
        range: {
            min: 0,
            max: 30
        },
    });

     //非水果类分成比例
    noUiSlider.create(platformVsCustomerSlider, {
        start: "${model==null?0:model.platform==null?0:model.platform/100}",
        connect: 'lower',
        animate: true,
        range: {
            min: 0,
            max: 100
        },
    });

    provinceSlider.noUiSlider.on('update', function( values, handle ){
        provinceSliderValue.innerHTML = values[handle];
        provinceSlider.style.color = "rgb(255,255,0)";
        provinceSlider.style.background = "rgb(255,255,0)";
    });

    citySlider.noUiSlider.on('update', function( values, handle ){
        citySliderValue.innerHTML = values[handle];
        citySlider.style.color = "rgb(255,0,255)";
        citySlider.style.background = "rgb(255,0,255)";
    });

    areaSlider.noUiSlider.on('update', function( values, handle ){
        areaSliderValue.innerHTML = values[handle];
        areaSlider.style.color = "rgb(0,255,255)";
        areaSlider.style.background = "rgb(0,255,255)";
    });

    notfruitSlider.noUiSlider.on('update', function( values, handle ){
        notfruitSliderValue.innerHTML = values[handle];
        notfruitSlider.style.color = "rgb(0,0,255)";
        notfruitSlider.style.background = "rgb(0,0,255)";
    });

    platformVsCustomerSlider.noUiSlider.on('update', function( values, handle ){
        platformSliderValue.innerHTML = values[handle];
        customerSliderValue.innerHTML = (100-values[handle]).toFixed(2);
        platformVsCustomerSlider.style.color = "rgb(0,255,0)";
        platformVsCustomerSlider.style.background = "rgb(0,255,0)";
    });
    //保持设置修改
    function modify(obj){
    	var param={};
    	param.province=$("#province-span").text().trim();
    	param.city=$("#city-span").text().trim();
    	param.area=$("#area-span").text().trim();
    	param.notFruit=$("#notfruit-span").text().trim();
    	param.platform=$("#platform-span").text().trim();
    	param.other=$("#customer-span").text().trim();
    	
    	$.post("${modifyUrl}",{param:param},function(result){
    		if(result){
    			$.notify(result.type,result.message);
    		}
    	},"json");
    }
    </script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/user/querybypages" var="userPagesUrl"></spring:url>
<script>
var cityStr;
var citys;
var areasMap={};

$(function(){
	var loadStr=$(".provinceSelect option:selected").attr("ref");
	if (undefined != loadStr && null != loadStr && '' != loadStr)
	{
		var firstAreas = loadStr.split('&');
		if (firstAreas.length>=2)
		{
			var bjAreas = firstAreas[1].split(',');
			$(".areaSelect").html('');
			for (var j=0;j<bjAreas.length;j++)
			{
				$(".areaSelect").append("<option value='"+bjAreas[j].split('-')[0]+"'>"+bjAreas[j].split('-')[1]+"</option>");
			}
		}
	}
});
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
});

$(function(){
	$(".citySelect").on("change",function(){
		var selectedCity = $("#cityselected").val();
		$(".areaSelect").html('');
		if (null != areasMap)
		{
			var areachanges = areasMap[selectedCity].split(',');
			for (var j=0;j<areachanges.length;j++)
			{
				$(".areaSelect").append("<option value='"+areachanges[j].split('-')[0]+"'>"+areachanges[j].split('-')[1]+"</option>");
			}
		}
	});
});
</script>
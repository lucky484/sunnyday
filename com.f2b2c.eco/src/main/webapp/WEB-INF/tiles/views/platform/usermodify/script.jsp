<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/user/querybypages" var="userPagesUrl"></spring:url>
<spring:url value="/farm/user/check-accountname" var="checkNameUrl" />
<spring:url value="/farm/user/check-partner" var="checkPartnerUrl" />
<script>
var cityStr;
var citys;
var areasMap={};

$(function(){
	var loadStr=$(".provinceSelect option:selected").attr("ref");
	debugger;
	if (undefined != loadStr && null != loadStr && '' != loadStr)
	{
		var firstAreas = loadStr.split('|');
		if (firstAreas.length>=2)
		{
			var bjAreas = firstAreas[1].split('&').split(',');
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
		if ($("#roleId").val() == '2') {
			checkPartner(1);
		} else if ($("#roleId").val() == '3') {
			checkPartner(2);
		}  else if ($("#roleId").val() == '4') {
			checkPartner(3);
		}
	});
	
	$("input[type=submit]").on("mouseenter", function() {
		checkIsSame();
		//校验角色有没有填写，如果没有填写，需要提示
		var roleId = $("#roleId").find("option:selected").val();
		if (roleId == -1) {
			swal({
				title : "警告!",
				text : "请选择用户对应的角色",
				type : "info",
			}, function() {
			});
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
		if ($("#roleId").val() == '3') {
			checkPartner(2);
		} else if ($("#roleId").val() == '4') {
			checkPartner(3);
		}
	});
	
	$(".areaSelect").on("change", function() {
		if ($("#roleId").val() == '4') {
			checkPartner(3);
		}
	});
	
	$("#roleId").change(function() {
		// 专属顾问
		if ($(this).val() == '5') {
			$("#provinceDiv").removeClass("hide");
			$("#cityDiv").removeClass("hide");
			$("#areaDiv").removeClass("hide");
			$("#shareProfitPerDiv").addClass("hide");
			checkPartner(-1);
		} else if ($(this).val() == '4') {
			// 区域合伙人
			$("#provinceDiv").removeClass("hide");
			$("#cityDiv").removeClass("hide");
			$("#areaDiv").removeClass("hide");
			$("#shareProfitPerDiv").removeClass("hide");
			checkPartner(3);
		} else if ($(this).val() == '3') {
			// 市级合伙人
			$("#provinceDiv").removeClass("hide");
			$("#cityDiv").removeClass("hide");
			$("#areaDiv").addClass("hide");
			$("#shareProfitPerDiv").removeClass("hide");
			checkPartner(2);
		} else if ($(this).val() == '2') {
			// 省级合伙人
			$("#provinceDiv").removeClass("hide");
			$("#cityDiv").addClass("hide");
			$("#areaDiv").addClass("hide");
			$("#shareProfitPerDiv").removeClass("hide");
			checkPartner(1);
		} else {
			$("#provinceDiv").addClass("hide");
			$("#cityDiv").addClass("hide");
			$("#areaDiv").addClass("hide");
			$("#shareProfitPerDiv").addClass("hide");
		}
	});
});

//用户名唯一性校验
function checkName() {
	if ($.trim($("input[name='accountName']").val()) != '') {
		$.post("${checkNameUrl}", {
			accountName : $("input[name='accountName']").val()
		}, function(data) {
			if (data == '1') {
				$("#nameHint").removeClass("hide");
			} else {
				$("#nameHint").addClass("hide");
			}
		}, 'json');
	}
}

//省合伙人唯一性校验
function checkPartner(type) {
	if (type == 1 && $.trim($("select[name='provinceid']").val()) != -1) {
		$.post("${checkPartnerUrl}", {
			provinceid : $("select[name='provinceid']").val(),
			roleId : $("select[name='roleId']").val()
		}, function(data) {
			if (data > 0) {
				$("#provinceHint").removeClass("hide");
				$("#cityHint").addClass("hide");
				$("#areaHint").addClass("hide");
			} else {
				$("#provinceHint").addClass("hide");
			}
		}, 'json');
	} else if (type == 2 && $.trim($("select[name='cityid']").val()) != -1) {
		$.post("${checkPartnerUrl}", {
			cityid : $("select[name='cityid']").val(),
			roleId : $("select[name='roleId']").val()
		}, function(data) {
			if (data > 0) {
				$("#cityHint").removeClass("hide");
				$("#provinceHint").addClass("hide");
				$("#areaHint").addClass("hide");
			} else {
				$("#cityHint").addClass("hide");
			}
		}, 'json');
	} else if (type == 3 && $.trim($("select[name='areaId']").val()) != -1) {
		$.post("${checkPartnerUrl}", {
			areaid : $("select[name='areaId']").val(),
			roleId : $("select[name='roleId']").val()
		}, function(data) {
			if (data > 0) {
				$("#areaHint").removeClass("hide");
				$("#provinceHint").addClass("hide");
				$("#cityHint").addClass("hide");
			} else {
				$("#areaHint").addClass("hide");
			}
		}, 'json');
	} else {
		$("#provinceHint").addClass("hide");
		$("#cityHint").addClass("hide");
		$("#areaHint").addClass("hide");
	}

}

//密码校验
function checkIsSame() {
	var password = $("input[name='password']").val();
	var repassword = $("input[name='confirm_password']").val();
	if (password != repassword) {
		swal({
			title : "警告!",
			text : "两次密码不一致",
			type : "info",
		}, function() {
		});
	}
}
</script>
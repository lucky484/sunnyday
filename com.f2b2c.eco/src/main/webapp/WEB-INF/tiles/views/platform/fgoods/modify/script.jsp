<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/goods/fshoplist" var="fgoodslistUrl" />
<spring:url value="/farm/goods/insert-fgoods-pic"
	var="uploadGoodsPicUrl" />
<spring:url value="/farm/admin/kind/get-kinds-for-jstree"
	var="getKindsUrl" />
<spring:url
	value="/resources/platform/js/plugins/easyUI/jquery.easyui.min.js"
	var="easyUIUrl" />
<spring:url
	value="/resources/platform/js/plugins/fileUpload/jquery.fileupload.js"
	var="fileUploadUrl" />
<spring:url
	value="/resources/platform/js/plugins/fileUpload/vendor/jquery.ui.widget.js"
	var="widgetUrl" />
<spring:url
	value="/resources/platform/js/plugins/fileUpload/jquery.iframe-transport.js"
	var="transportUrl" />
<spring:url
	value="/resources/platform/js/jquery-ui-1.10.4.min.js"
	var="jqueryUIUrl" />
<script type="text/javascript" src="${jqueryUIUrl}"></script>
<script type="text/javascript" src="${easyUIUrl}"></script>
<script type="text/javascript" src="${widgetUrl }"></script>
<script type="text/javascript" src="${transportUrl }"></script>
<script type="text/javascript" src="${fileUploadUrl }"></script>
<script>
	
	function fgoodslistUrl() {
		window.location.href = "${fgoodslistUrl}";
	}
	var cityStr;
	var citys;
	var areasMap = {};

	$("#kindTree").combotree({
		url : '${getKindsUrl}',
		height : 35,
	});

	goodsImgUpload(0);
	
	function picDivStr(i) {
		return '<li><div id="picDiv'+i+'" class="picDiv"><input type="hidden" name="logoUrl" id="logoUrl'+i+'" /><span class="picHint" id="picHint'+i+'"><br /><br />+ 加图</span> <input type="file" name="file" class="picInput" id="picInput'+i+'" /></div></li>';
	}

	function goodsImgUpload(index) {
		$('#picInput' + index).fileupload({
			url : '${uploadGoodsPicUrl}',
			dataType : 'json',
			done : function(e, data) {
				if (data.result.status == 200) {
					// 图片地址
					$(this).parent().find("input[name='logoUrl']").val(data.result.data[0]);
					// 预览图
					$(this).parent().find("[id^=picHint]").html("<img class='goodsImg' src='" + data.result.data[1] + "' />");
					$(this).parent().append("<a href='javascript:deletePic("+index+")' class='close-modal'>×</a>");
					$(this).remove();
					$("#sortable").append(picDivStr(++index));
					goodsImgUpload(index);
				}
			}
		});
	}
	
	function deletePic(index) {
		$("#picDiv" + index).parent().remove();

	}
	function modify() {
		$("#modify").attr("disabled",true)
		// 验证
		if ($("#name").val() == undefined || $("#name").val() == null || $("#name").val() == "") {
			$.notify("error", "请输入商品名称");
			$("#modify").attr("disabled",false)
			return;
		}
		if ($(".textbox-value").val() == undefined || $(".textbox-value").val() == null || $(".textbox-value").val() == "") {
			$.notify("error", "请选择分类");
			$("#modify").attr("disabled",false)
			return;
		}
		if ($("#pricex").val() == undefined || $("#pricex").val() == null || $("#pricex").val() == "") {
			$.notify("error", "请输入价格");
			$("#modify").attr("disabled",false)
			return;
		}else if($("#pricex").val() <= 0){
			$.notify("error", "单价不是一个有效值");
			$("#modify").attr("disabled",false)
			return;
		}
		if ($("#remain").val() == undefined || $("#remain").val() == null || $("#remain").val() == "") {
			$.notify("error", "请输入库存");
			$("#modify").attr("disabled",false)
			return;
		}
		if ($("#producePlace").val() == undefined || $("#producePlace").val() == null || $("#producePlace").val() == "") {
			$.notify("error", "请输入产地");
			$("#modify").attr("disabled",false)
			return;
		}
		if ($("#spec").val() == undefined || $("#spec").val() == null || $("#spec").val() == "") {
			$.notify("error", "请输入规格");
			$("#modify").attr("disabled",false)
			return;
		}
		if ($("#intro").val() == undefined || $("#intro").val() == null || $("#intro").val() == "") {
			$.notify("error", "简介");
			$("#modify").attr("disabled",false)
			return;
		}
		var logoUrl = "";
		$.each($("input[name='logoUrl']"), function(key, value) {
			if ($(this).val() != undefined && $(this).val() != null && $(this).val() != "") {
				logoUrl += $(this).val() + "|";
			}
		});
		if (logoUrl.length == 0) {
			$.notify("error", "请上传图片");
			$("#modify").attr("disabled",false)
			return;
		}

		$("#form").submit();

	}
	$(function() {
		$("#sortable").sortable();
	    $("#sortable").disableSelection();
		$(".provinceSelect").val("${data.provinceId}");
		$(".provinceSelect").on("change", function() {
			cityStr = "";
			citys = "";
			cityStr = $(".provinceSelect option:selected").attr("ref");
			citys = cityStr.split('|');
			$(".citySelect").html('');
			$(".areaSelect").html('');
			if (cityStr.length > 0) {
				var areasWithCityOne = citys[0].split('&');
				for (var i = 0; i < citys.length; i++) {
					var areasWithCity = citys[i].split('&');
					$(".citySelect").append("<option value='" + areasWithCity[0].split('-')[0] + "'>" + areasWithCity[0].split('-')[1] + "</option>");
					if (i == 0) {
						var areas = areasWithCity[1].split(',');
						for (var j = 0; j < areas.length; j++) {
							$(".areaSelect").append("<option value='" + areas[j].split('-')[0] + "'>" + areas[j].split('-')[1] + "</option>");
						}
					}
					areasMap[areasWithCity[0].split('-')[0]] = areasWithCity[1];
				}
			}
		});
		var width = $(".combo").width();
		$(".combo").width(width-28);
	});
	$(".validatebox-readonly").val("${data.kind.kindName}");
	$(".validatebox-readonly").next().val("${data.kind.id}");
</script>
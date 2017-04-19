<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/order/updateorder" var="updateOrderUrl" />
<script>
	var orderId;
	var detailsMap = {};
	$(document).ready(function(){
		orderId = ${orderId};
		var details = ${detailsMap};
		detailsMap = eval(details);
	});
	
	function minusAmount(obj, detailId)
	{
		var amount = obj.parentNode.innerText.trim();
		if (amount > 0)
		{
			amount= amount - 1;
		}
		detailsMap[detailId] = amount;
		$(obj).siblings().eq(0).text(amount);
	}
	
	function plusAmount(obj, detailId)
	{
		var amount = obj.parentNode.innerText.trim();
		var count = parseInt(amount);
		count = count + 1;
		detailsMap[detailId] = count;
		$(obj).siblings().eq(1).text(count);
	}
	
	function delGoods(obj, detailId)
	{
		detailsMap[detailId] = 0;
		$(obj.parentNode.parentNode).remove();
	}
	
	function updateOrderDetails()
	{
		var detailsArr = [];
		$.each(detailsMap, function(key, value) {
			detailsArr.push({id : key, goodsQty : value})
		}); 
		
		var postData = {
			params : {}
		};
		postData.params.orderId = orderId;
		postData.params.detailsArr = parseToJsonStr(detailsArr);
		$.ajax({
			"dataType" : 'json',
			"data" : postData,
			"type" : "POST",
			"url" : '${updateOrderUrl}',
			"success" : function(data) {
				
			}
		});
	}
	
	function parseToJsonStr(arr)
	{
		if (null!= arr && arr.length > 0)
		{
			return JSON.stringify(arr).toString();
		}
		
		return "";
	}
</script>
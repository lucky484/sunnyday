var totalFlag = false;
var maxPage = 1;
function firstPage(page) {
	if (!totalFlag) {
		search(page);
	}
}
function beforePage(page) {
	if (!totalFlag) {
		search(page);
	}
}
function nextPage(page) {
	if(!totalFlag){
		search(page);
	}
}
function lastPage(page) {
	if(!totalFlag){
		search(page);
	}
}
$(function() {

	// 展开更多查询
	$('.open-more').on('click', function() {

		$(this).toggleClass('active');

		$('#check-more').toggle();

	});
	// 时间
	$('#date_timepicker_start').datetimepicker(
			{
				format : 'Y/m/d',
				onShow : function(ct) {
					this.setOptions({
						maxDate : $('#date_timepicker_end').val() ? $(
								'#date_timepicker_end').val() : false
					})
				},
				timepicker : false
			});
	$('#date_timepicker_end').datetimepicker(
			{
				format : 'Y/m/d',
				onShow : function(ct) {
					this.setOptions({
						minDate : $('#date_timepicker_start').val() ? $(
								'#date_timepicker_start').val() : false
					})
				},
				timepicker : false
			});
	var page = $("#page").val();
	search(page);
});

$("#pageText").change('on', function() {
	location.href = 'getComplexQueryList.do?page=' + $("#pageText").val();
});
function search(page) {
	var faceInfoComp = "";
	var idCardComp = "";
	// var faceAndIdCardComp = "";
	// var faceAndIdCardNotMatch = "";
	// var notIdCard = "";
	var faceAndIdCardComp = $("input[name='faceAndIdCardComp']:checked").val();
	if ($("input[name='faceComp']").is(':checked') == true) {
		faceInfoComp = 1;
	}
	if ($("input[name='idCardComp']").is(':checked') == true) {
		idCardComp = 1;
	}
	var collectTimeStart = $.trim($("#date_timepicker_start").val());
	var collectTimeEnd = $.trim($("#date_timepicker_end").val());
	var collectSite = $.trim($("input[name='collectSite']").val());
	var idCardName = $.trim($("input[name='idCardName']").val());
	var idCardNo = $.trim($("input[name='idCardNo']").val());
	var faceCompName = $.trim($("input[name='faceCompName']").val());
	var faceCompNo = $.trim($("input[name='faceCompNo']").val());
	var idCardSex = $("#sexSelected").find("option:selected").val();
	var sexComp = $("#sexComp").find("option:selected").val();
	var cardCompName = $.trim($("input[name='cardCompName']").val());
	var cardSexComp = $("#cardSexComp").find("option:selected").val();
	var cardCompNo = $.trim($("input[name='cardCompNo']").val());
	totalFlag = false;
	if (((idCardComp == "") && (idCardComp != "" || cardSexComp != "" || cardCompNo != ""))
			|| ((faceInfoComp == "") && (faceCompName != "" || faceCompNo != "" || sexComp != ""))) {
		$(".table").find("tbody").html('');
		$("#pageCount").html('<span class="page-info">共0条 每页10条 页次：1/1</span>');
		var pageTemp = $('<li id="firstPage"></li><li id="beforePage"></li><li id="pageInput"></li><li id="nextPage"></li><li id="lastPage"></li>');
		$(".pagination").html(pageTemp);
		$("#firstPage").html('<a href="javascript:javascript:void(0);">首页</a>');
		$("#beforePage").html('<a href="javascript:void(0)">上一页</a>');
		$("#nextPage").html('<a href="javascript:void(0)">下一页</a>');
		$("#lastPage").html('<a href="javascript:void(0)">尾页</a>');
		$("#pageInput").html('<input type="text" value="1" id="pageText1"');
		totalFlag = true;
	} else {
		$
				.post(
						"getComplexQueryListSearch.do?",
						{
							faceComp : faceInfoComp,
							isControlled : idCardComp,
							faceAndIdCardComp : faceAndIdCardComp,
							collectTimeStart : collectTimeStart,
							collectTimeEnd : collectTimeEnd,
							collectSite : collectSite,
							idCardName : idCardName,
							idCardNo : idCardNo,
							faceCompName : faceCompName,
							faceCompNo : faceCompNo,
							idCardSex : idCardSex,
							sexComp : sexComp,
							cardCompName : cardCompName,
							cardSexComp : cardSexComp,
							cardCompNo : cardCompNo,
							page1 : page
						},
						function(data) {
							$(".table").find("tbody").html('');
							if (data.list != null && data.list.length > 0) {

								for (var i = 0; i < data.list.length; i++) {
									var temp = $('<tr>' + '<td id="collectTime'
											+ i
											+ '"></td>'
											+ '<td id="collectSite'
											+ i
											+ '"></td>'
											+ '<td id="collectPic'
											+ i
											+ '"></td>'
											+ '<td>'
											+ '<table class="sub-table">'
											+ '<tr>'
											+ '<td id="idCardPic'
											+ i
											+ '"></td>'
											+ '<td id="idCardInfo'
											+ i
											+ '">'
											+ '</td>'
											+ '</tr>'
											+ '</table>'
											+ '</td>'
											+ '<td id="faceSimilarity'
											+ i
											+ '"></td>'
											+ '<td id="type'
											+ i
											+ '"></td>'
											+ '<td><table class="sub-table"><tr id="ctrlBase'
											+ i + '"></tr>' + '</table></td>'
											+ '<td id="detail' + i + '"></td>'
											+ '</tr>');
									$("#faceAndCardInfo").append(temp);
									if (data.list[i].collectTimeStr != null) {
										$("#collectTime" + i).html(
												data.list[i].collectTimeStr);
									}
									if (data.list[i].collectSite != null) {
										$("#collectSite" + i).html(
												data.list[i].collectSite);
									}
									$("#collectPic" + i)
											.html(
													'<img src="data:image/jpg;base64,'
															+ data.list[i].collectPicStr
															+ '" class="people-img" alt="">');
									$("#idCardInfo" + i)
											.html(
													'<ul class="txt">'
															+ '<li>'
															+ (data.list[i].idCardName == null ? ""
																	: data.list[i].idCardName)
															+ '</li>'
															+ '<li>'
															+ (data.list[i].idCardSex == null ? ""
																	: data.list[i].idCardSex)
															+ '</li>'
															+ '<li>'
															+ (data.list[i].idCardNo == null ? ""
																	: data.list[i].idCardNo)
															+ '</li>' + '</ul>');
									if (data.list[i].idCardPicStr != "") {
										$("#idCardPic" + i)
												.html(
														'<img class="people-img" src="data:image/jpg;base64,'
																+ data.list[i].idCardPicStr
																+ '" width="68" height="86" alt="">');
									} else {
										$("#idCardPic" + i)
												.html(
														'<img src="../img/collect-fail.png" width="68" height="86" alt="">');
									}
									if (data.list[i].similarity != null) {
										if (data.list[i].similarity >= data.paramSet.faceCardCompAlarmVal) {
											$("#faceSimilarity" + i).html(
													data.list[i].similarity
															+ '%' + "<br>匹配");
										} else {
											$("#faceSimilarity" + i).html(
													data.list[i].similarity
															+ '%' + "<br>不匹配");
										}
									}
									if (data.list[i].cardCompareBaseId != "") {
										if (data.list[i].cardCompareBaseId == "1") {
											$("#type" + i)
													.html(
															'<span class="icon i-yes"></span><span class="red">在逃人员</span>');
										}
										if (data.list[i].cardCompareBaseId == "2") {
											$("#type" + i)
													.html(
															'<span class="icon i-yes"></span><span class="red">常控人员</span>');
										}
										if (data.list[i].cardCompareBaseId == "0") {
											$("#type" + i)
													.html(
															'<span class="icon i-yes"></span><span class="red">临控人员</span>');
										}
									}
									if (data.list[i].compareBaseID == "1") {
										if (data.list[i].xm != null) {
											$("#ctrlBase" + i)
													.html(
															'<td>'
																	+ '<img src="data:image/jpg;base64,'
																	+ data.list[i].photoStr
																	+ '" width="68" height="86" alt="">'
																	+ '</td>'
																	+ '<td>'
																	+ '<ul class="txt">'
																	+ '<li>'
																	+ (data.list[i].xm == null ? ""
																			: data.list[i].xm)
																	+ '</li>'
																	+ '<li>'
																	+ (data.list[i].xb == null ? ""
																			: data.list[i].xb)
																	+ '</li>'
																	+ '<li>'
																	+ (data.list[i].ysfzh == null ? ""
																			: data.list[i].ysfzh)
																	+ '</li>'
																	+ '</ul>'
																	+ '</td>'
																	+ '<td>'
																	+ '<ul class="txt" style="width:100px;">'
																	+ '<li>相似度：'
																	+ data.list[i].faceSimilarity
																	+ '%</li>'
																	+ '<li><span class="c-danger">在逃人员</span></li>'
																	+ '<li>案件：<span class="c-danger">'
																	+ (data.list[i].context == null ? ""
																			: data.list[i].context)
																	+ '</span></li>'
																	+ '</ul>'
																	+ '</td>');
										} else {
											$("#ctrlBase" + i)
													.html(
															'<td>'
																	+ '<img class="people-img" src="../img/collect-fail.png" width="68" height="86" alt="">'
																	+ '</td>'
																	+ '<td>'
																	+ '<ul class="txt">'
																	+ '<li>'
																	+ (data.list[i].xm == null ? ""
																			: data.list[i].xm)
																	+ '</li>'
																	+ '<li>'
																	+ (data.list[i].xb == null ? ""
																			: data.list[i].xb)
																	+ '</li>'
																	+ '<li>'
																	+ (data.list[i].ysfzh == null ? ""
																			: data.list[i].ysfzh)
																	+ '</li>'
																	+ '</ul>'
																	+ '</td>'
																	+ '<td>'
																	+ '<ul class="txt" style="width:100px;">'
																	+ '<li>相似度：'
																	+ data.list[i].faceSimilarity
																	+ '%</li>'
																	+ '<li><span class="c-danger">在逃人员</span></li>'
																	+ '<li>案件：<span class="c-danger">'
																	+ (data.list[i].context == null ? ""
																			: data.list[i].context)
																	+ '</span></li>'
																	+ '</ul>'
																	+ '</td>');
										}
									} else if (data.list[i].compareBaseID == "2") {
										if (data.list[i].T_QB_RY_ZTRYJBXXxm != null) {
											$("#ctrlBase" + i)
													.html(
															'<td>'
																	+ '<img src="data:image/jpg;base64,'
																	+ data.list[i].photoStr
																	+ '" width="68" height="86" alt="">'
																	+ '</td>'
																	+ '<td>'
																	+ '<ul class="txt">'
																	+ '<li>'
																	+ (data.list[i].T_QB_RY_ZTRYJBXXxm == null ? ""
																			: data.list[i].T_QB_RY_ZTRYJBXXxm)
																	+ '</li>'
																	+ '<li>'
																	+ (data.list[i].T_QB_RY_ZTRYJBXXxb == null ? ""
																			: data.list[i].T_QB_RY_ZTRYJBXXxbStr)
																	+ '</li>'
																	+ '<li>'
																	+ (data.list[i].T_QB_RY_ZTRYJBXXsfzh == null ? ""
																			: data.list[i].T_QB_RY_ZTRYJBXXxb)
																	+ '</li>'
																	+ '</ul>'
																	+ '</td>'
																	+ '<td>'
																	+ '<ul class="txt" style="width:100px;">'
																	+ '<li>相似度：'
																	+ data.list[i].faceSimilarity
																	+ '%</li>'
																	+ '<li><span class="c-danger">常控人员</span></li>'
																	+ '<li>案件：<span class="c-danger">'
																	+ (data.list[i].zdrylbbj == null ? ""
																			: data.list[i].T_QB_RY_ZTRYJBXXContext)
																	+ '</span></li>'
																	+ '</ul>'
																	+ '</td>');
										} else {
											$("#ctrlBase" + i)
													.html(
															'<td>'
																	+ '<img class="people-img" src="../img/collect-fail.png" width="68" height="86" alt="">'
																	+ '</td>'
																	+ '<td>'
																	+ '<ul class="txt">'
																	+ '<li>'
																	+ (data.list[i].T_QB_RY_ZTRYJBXXxm == null ? ""
																			: data.list[i].T_QB_RY_ZTRYJBXXxm)
																	+ '</li>'
																	+ '<li>'
																	+ (data.list[i].T_QB_RY_ZTRYJBXXxb == null ? ""
																			: data.list[i].T_QB_RY_ZTRYJBXXxbStr)
																	+ '</li>'
																	+ '<li>'
																	+ (data.list[i].T_QB_RY_ZTRYJBXXsfzh == null ? ""
																			: data.list[i].T_QB_RY_ZTRYJBXXxb)
																	+ '</li>'
																	+ '</ul>'
																	+ '</td>'
																	+ '<td>'
																	+ '<ul class="txt" style="width:100px;">'
																	+ '<li>相似度：'
																	+ data.list[i].faceSimilarity
																	+ '%</li>'
																	+ '<li><span class="c-danger">常控人员</span></li>'
																	+ '<li>案件：<span class="c-danger">'
																	+ (data.list[i].zdrylbbj == null ? ""
																			: data.list[i].T_QB_RY_ZTRYJBXXContext)
																	+ '</span></li>'
																	+ '</ul>'
																	+ '</td>');
										}
									} else if (data.list[i].compareBaseID == "0") {
										if (data.list[i].bbkrxm != null) {
											$("#ctrlBase" + i)
													.html(
															'<td>'
																	+ '<img src="data:image/jpg;base64,'
																	+ data.list[i].photoStr
																	+ '" width="68" height="86" alt="">'
																	+ '</td>'
																	+ '<td>'
																	+ '<ul class="txt">'
																	+ '<li>'
																	+ (data.list[i].bbkrxm == null ? ""
																			: data.list[i].bbkrxm)
																	+ '</li>'
																	+ '<li>'
																	+ (data.list[i].bbkrxb == null ? ""
																			: data.list[i].T_QB_LK_LKBKxb)
																	+ '</li>'
																	+ '<li>'
																	+ (data.list[i].bbkrzjhm == null ? ""
																			: data.list[i].bbkrzjhm)
																	+ '</li>'
																	+ '</ul>'
																	+ '</td>'
																	+ '<td>'
																	+ '<ul class="txt" style="width:100px;">'
																	+ '<li>相似度：'
																	+ data.list[i].faceSimilarity
																	+ '%</li>'
																	+ '<li><span class="c-danger">临控人员</span></li>'
																	+ '<li>案件：<span class="c-danger">'
																	+ (data.list[i].lkzllx == null ? ""
																			: data.list[i].lkzllx)
																	+ '</span></li>'
																	+ '</ul>'
																	+ '</td>');
										} else {
											$("#ctrlBase" + i)
													.html(
															'<td>'
																	+ '<img class="people-img" src="../img/collect-fail.png" width="68" height="86" alt="">'
																	+ '</td>'
																	+ '<td>'
																	+ '<ul class="txt">'
																	+ '<li>'
																	+ (data.list[i].bbkrxm == null ? ""
																			: data.list[i].bbkrxm)
																	+ '</li>'
																	+ '<li>'
																	+ (data.list[i].bbkrxb == null ? ""
																			: data.list[i].T_QB_LK_LKBKxb)
																	+ '</li>'
																	+ '<li>'
																	+ (data.list[i].bbkrzjhm == null ? ""
																			: data.list[i].bbkrzjhm)
																	+ '</li>'
																	+ '</ul>'
																	+ '</td>'
																	+ '<td>'
																	+ '<ul class="txt" style="width:100px;">'
																	+ '<li>相似度：'
																	+ data.list[i].faceSimilarity
																	+ '%</li>'
																	+ '<li><span class="c-danger">临控人员</span></li>'
																	+ '<li>案件：<span class="c-danger">'
																	+ (data.list[i].lkzllx == null ? ""
																			: data.list[i].lkzllx)
																	+ '</span></li>'
																	+ '</ul>'
																	+ '</td>');
										}
									}
									$("#detail" + i)
											.html(
													'<a href="queryComplexById.do?compareBaseId='+data.list[i].cardCompareBaseId+'&comeFrom=综合信息查询&faceId='
															+ data.list[i].faceId
															+ '" class="more">查看 '
															+
															/*
															 * <i class="fa
															 * fa-angle-double-right"></i>
															 */'</a>');
								}
							}
							$("#pageCount")
									.html(
											'<span class="page-info">共'
													+ data.page.totalCount
													+ '条 每页10条 页次：'
													+ data.page.page
													+ '/'
													+ (data.page.totalPage == 0 ? data.page.totalPage + 1
															: data.page.totalPage)
													+ '</span>');
							var pageTemp = $('<li id="firstPage"></li><li id="beforePage"></li><li id="pageInput"></li><li id="nextPage"></li><li id="lastPage"></li>');
							$(".pagination").html(pageTemp);
							$("#firstPage")
									.html(
											'<a href="javascript:firstPage(1);">首页</a>');
							if (data.page.page > 1) {
								$("#beforePage").html(
										'<a href="javascript:beforePage('
												+ (data.page.page - 1)
												+ ')">上一页</a>');
							} else {
								$("#beforePage").html(
										'<a href="javascript:void(0)">上一页</a>');
							}
							if (data.page.totalPage > data.page.page) {
								$("#nextPage").html(
										'<a href="javascript:nextPage('
												+ (data.page.page + 1)
												+ ')">下一页</a>');
							} else {
								$("#nextPage").html(
										'<a href="javascript:void(0)">下一页</a>');
							}
							$("#lastPage")
									.html(
											'<a href="javascript:lastPage('
													+ data.page.totalPage
													+ ')">尾页</a>');
							
							$("#pageInput")
									.html(
											'<input type="text" value="'
													+ data.page.page
													+ '" id="pageText1" onchange="prePage(this)">');
							$("#page").val(data.page.page);
							maxPage = data.page.totalPage;
							if (data.page.totalCount == 0) {
								totalFlag = true;
								$("#faceAndCardInfo").append(
										"<tr><td colspan='8'>暂无数据！</td></tr>");
							}
						}, 'json');

	}
}
$(".search-more").click('on', function() {
	search(1);
});
function prePage(page) {
	if(!totalFlag){
		if(parseInt(page.value) > parseInt(maxPage)){
			$("#pageInput").val(maxPage);
			page.value = maxPage;
		}
		search(page.value);
	}else{
		$("#pageText1").val(1);
	}
	
}
$("input[name='faceComp']").click('on', function() {
	if ($("input[name='faceComp']").is(':checked') == false) {
		$("#faceCompSelect").hide();
	} else {
		$("#faceCompSelect").show();
	}
});
$("input[name='idCardComp']").click('on', function() {
	if ($("input[name='idCardComp']").is(':checked') == false) {
		$("#cardCompInput").hide();
	} else {
		$("#cardCompInput").show();
	}
});

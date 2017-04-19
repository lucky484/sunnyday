/**
 * 初始化
 */
$(function(){
	init();
	$('.fancybox').fancybox();
});

function init()
{	
	$("input[name='logStartDate']").val('');
	$("input[name='logEndDate']").val('');
	$("#keyWords").val('');
	getAjax(1);
	$("#page").val("1");
}

$(function(){
	var maxpage;
    $("#page").blur(function(){
    	var page = $("#page").val();
    	if(page > maxpage){
    		page = maxpage;
    		$("#page").val(page);
    	}
    	getAjax(page);
    });
});

function getLogInfo()
{

	getAjax(1,$("input[name='logStartDate']").val(),$("input[name='logEndDate']").val(),$("#keyWords").val());
}

var logInfos;
var count;
function getAjax(page, startTime, endTime , keyWords) {
	$.post("getLogInfos.do",{
				startTime : startTime,
				endTime : endTime,
				keyWords : keyWords,
				page : page
			},
			function(data) {
				logInfos = data.list;
				count = data.count;
				maxpage = data.pages;
				$("#logInfoCount").html("");
				$("#logInformations").children().each(function(index) {
					$(this).remove();
				});
				if (count != 0) {
					$("#logInfoCount").append(
							"共" + count + "条 每页10条" + " 页次" + page
									+ "/" + maxpage);
				} else {
					$("#page").val("0");
					$("#logInformations").append('<tr><td colspan=10>暂无数据</td></tr>');
					$("#logInfoCount").append(
							"共" + count + "条 每页10条" + " 页次1/1");
				}
	
				var tr2;
				for (var i = 0; i < logInfos.length; i++) 
				{
					var tr1;
	            	tr1 = "<tr>"+	
		            "<td>"+logInfos[i].regID+"</td>"+
		            "<td>"+logInfos[i].userID+"</td>"+
		            "<td>"+logInfos[i].userName+"</td>"+
		            "<td>"+logInfos[i].organization+"</td>"+
		            "<td>"+formatTimeString(logInfos[i].operateTime)+"</td>"+
		            "<td>"+logInfos[i].operateType+"</td>"+
		            "<td>"+logInfos[i].operateResult+"</td>"+
		            "<td>"+logInfos[i].operateName+"</td>";
	            	tr2 = tr2 +tr1;
				}
				$("#logInformations").append(tr2);
			});
}

$(function(){
    //时间
     $('#date_timepicker_start').datetimepicker({
      format:'Y/m/d',
      onShow:function( ct ){
       this.setOptions({
        maxDate:$('#date_timepicker_end').val()?$('#date_timepicker_end').val():false
       })
      },
      timepicker:false
     });
     $('#date_timepicker_end').datetimepicker({
      format:'Y/m/d',
      onShow:function( ct ){
       this.setOptions({
        minDate:$('#date_timepicker_start').val()?$('#date_timepicker_start').val():false
       })
      },
      timepicker:false
     });
});

function formatTimeString(value)
{
	var dateTmie = '—';
	if (null == value || '' == value ||  undefined == value)
	{
		dateTmie = '—';
	}
	else
	{
		var yearStr = value.substring(0,4);
		var monthStr = value.substring(4,6);
		var dayStr = value.substring(6,8)
		dateTmie = yearStr + "-" + monthStr + "-" + dayStr;
	}
	
	return dateTmie;
}

function getPage(page, startTime, endTime , keyWords){
	$("#page").val(1);
	getAjax($("#page").val(), $("input[name='logStartDate']").val(), $("input[name='logEndDate']").val() , $("#keyWords").val());
}

function getPrePage(){
	var page = $("#page").val();
	if( page > 1){
		$("#page").val(eval(page+"-1"));
		getAjax( $("#page").val(), $("input[name='logStartDate']").val(), $("input[name='logEndDate']").val() , $("#keyWords").val());
	}
}

function getNextPage(){
	var page = $("#page").val();
	if( page < maxpage){
		$("#page").val(eval(page+"+1"));
		getAjax( $("#page").val(), $("input[name='logStartDate']").val(), $("input[name='logEndDate']").val() , $("#keyWords").val());
	}
}

function getLastPage(){
	$("#page").val(maxpage);
	getAjax(maxpage, $("input[name='logStartDate']").val(), $("input[name='logEndDate']").val() , $("#keyWords").val());
}


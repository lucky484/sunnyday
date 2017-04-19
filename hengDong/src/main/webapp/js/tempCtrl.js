/**
 * 
 */
var maxPage;
$(function(){
    $('.fancybox').fancybox();
    
})

$(function(){
	init();
	$('.fancybox').click(function(){
		$("#hiddenIdCardName").val($("#queryIdCardName").val()); 
		$("#hiddenSex").val($("#querySex").val()); 
		$("#hiddenIdCardNo").val($("#queryIdCardNo").val()); 
       
	});
	var maxpage;
	$("#query").click(function(){
		
		$("#hiddenqueryIdCardName").val($("#queryIdCardName").val());
		$("#hiddenquerySex").val($("#querySex").val());
		$("#hiddenqueryIdCardNo").val($("#queryIdCardNo").val());
		$.post("/hengdong/TempCtrlController/queryTempCtrl.do?fys="+$("#fys").val()+"&IdCardName="+$("#queryIdCardName").val()+"&Sex="+$("#querySex").val()+"&IdCardNo="+$("#queryIdCardNo").val(),function(data){
			$("#TempCtrldata").children().each(function(index){
      			$(this).remove();
      		});
			if(data.count == 0){
				$("#TempCtrldata").append("<tr><td colspan='8'>暂无数据！</td></tr>");
				$("#count").html(0);
				$("#pages").html(1);
			}else{
				maxPage = data.pages;
				var list = data.list;
				
				for(var i =0;i<list.length;i++){
					var tr = "<tr><td>"+list[i].name+"</td>" +
					 "<td>"+list[i].sex+"</td>" +
					 "<td>"+list[i].idCardNo+"</td>" +
					 "<td><img src='data:image/jpg;base64,"+list[i].picStr+"' width='68' height='86' alt=''></td>" +
					 "<td>"+list[i].createTimeStr+"</td>" +
					 "<td>"+list[i].statusStr+"</td>" +
					 "<td>"+list[i].remarkDesc+"</td>" +
					 "<td><a href='/hengdong/TempCtrlController/delTempCtrl.do?status="+list[i].status+"&tempCompID="+list[i].tempCompID+"&IdCardName="+$("#queryIdCardName").val()+"&Sex="+$("#querySex").val()+"&IdCardNo="+$("#queryIdCardNo").val()+"'>删除</a></td></tr>";
					$("#TempCtrldata").append(tr);
				}
				$("#count").html(data.count);
				$("#pages").html(data.pages);
			}
		});
	});
    $("#page").blur(function(){
    	var page = $("#page").val();
    	if(page > maxpage){
    		page = maxpage;
    		$("#page").val(page);
    	}
    	getAjax(page);
    });
}); 

function init(){
	if($("#message").val()!=""){
		alert($("#message").val());
	}
	$.post("/hengdong/TempCtrlController/queryTempCtrl.do?fys="+$("#fys").val()+"&IdCardName="+$("#queryIdCardName").val()+"&Sex="+$("#querySex").val()+"&IdCardNo="+$("#queryIdCardNo").val(),function(data){
		$("#TempCtrldata").children().each(function(index){
  			$(this).remove();
  		});
		if(data.count == 0){
			$("#TempCtrldata").append("<tr><td colspan='8'>暂无数据！</td></tr>");
			$("#count").html(0);
			$("#pages").html(1);
		}else{
			maxPage = data.pages;
			var list = data.list;
			
			for(var i =0;i<list.length;i++){
				var tr = "<tr><td>"+list[i].name+"</td>" +
				 "<td>"+list[i].sex+"</td>" +
				 "<td>"+list[i].idCardNo+"</td>" +
				 "<td><img src='data:image/jpg;base64,"+list[i].picStr+"' width='68' height='86' alt=''></td>" +
				 "<td>"+list[i].createTimeStr+"</td>" +
				 "<td>"+list[i].statusStr+"</td>" +
				 "<td>"+list[i].remarkDesc+"</td>" +
				 "<td><a href='/hengdong/TempCtrlController/delTempCtrl.do?status="+list[i].status+"&tempCompID="+list[i].tempCompID+"&IdCardName="+$("#queryIdCardName").val()+"&Sex="+$("#querySex").val()+"&IdCardNo="+$("#queryIdCardNo").val()+"'>删除</a></td></tr>";
				$("#TempCtrldata").append(tr);
			}
			$("#count").html(data.count);
			$("#pages").html(data.pages);
		}
	});
}

function getPrePage(){
	var page = $("#page").val();
	//alert(maxpage);
	if( page > 1){
		$("#page").val(eval(page+"-1"));
		getAjax($("#page").val());
	}
}

function getNextPage(){
	var page = $("#page").val();
	if( page < maxpage){
		$("#page").val(eval(page+"+1"));
		getAjax($("#page").val());
		
	}
}

function getPage(page){
	$("#page").val(page);
	getAjax(page);
}

function getLastPage(){
	$("#page").val(maxpage);
	getAjax(maxpage);
}


function closeview(){
	$('[data-remodal-id="viewuser"]').remodal().close();
}


var user;
var count;
function getAjax(page){
	$.post("/hengdong/TempCtrlController/queryTempCtrlData.do?page="+page+"&fys="+$("#fys").val()+"&IdCardName="+$("#queryIdCardName").val()+"&Sex="+$("#querySex").val()+"&IdCardNo="+$("#queryIdCardNo").val(),function(data){
		$("#TempCtrldata").children().each(function(index){
  			$(this).remove();
  		});
		var list = data.list;
		for(var i =0;i<list.length;i++){
			var tr = "<tr><td>"+list[i].name+"</td>" +
			 "<td>"+list[i].sex+"</td>" +
			 "<td>"+list[i].idCardNo+"</td>" +
			 "<td><img src='data:image/jpg;base64,"+list[i].picStr+"' width='68' height='86' alt=''></td>" +
			 "<td>"+list[i].createTimeStr+"</td>" +
			 "<td>"+list[i].status+"</td>" +
			 "<td>"+list[i].remarkDesc+"</td>" +
			 "<td><a href='/hengdong/TempCtrlController/delTempCtrl.do?status="+list[i].status+"&tempCompID="+list[i].tempCompID+"&IdCardName="+$("#queryIdCardName").val()+"&Sex="+$("#querySex").val()+"&IdCardNo="+$("#queryIdCardNo").val()+"'>删除</a></td></tr>";
			$("#TempCtrldata").append(tr);
		}

	});

}
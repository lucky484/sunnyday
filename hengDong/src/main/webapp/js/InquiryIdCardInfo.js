var countFlag = false; 
var maxPage = 1;
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
               var page = $("#page").val();
               search(page);
           });
                   function search(page){
                	   countFlag = false;
                	   var isCardInfo = "";
                	   if($("#isCardInfo").is(":checked") == true){
                		   isCardInfo = 1;
                	   } 
                	   var isFaceAndCard = $("input[name='isFaceAndCard']:checked").val();
                	   var collectTimeStart = $("input[name='collectTimeStart']").val();
                	   var collectTimeEnd = $("input[name='collectTimeEnd']").val();
                	   var collectSite = $("input[name='collectSite']").val();
                	   var idCardBirth = $("input[name='idCardBirth']").val();
                	   var idCardName = $("input[name='idCardName']").val();
                	   var sexSelected =$("#idCardSex").find("option:selected").val();
                	   var idCardNo = $("input[name='idCardNo']").val();
                	   var areaId = $("input[name='areaId']").val();
                	   $.post("InquiryIdCardInfo1.do",
                			   { isCardInfo : isCardInfo,
                		  		 isFaceAndCard : isFaceAndCard,
                		  		 collectTimeStart : collectTimeStart,
                		  		 collectTimeEnd : collectTimeEnd,
                		  		 collectSite : collectSite,
                		  		 idCardName : idCardName,
                		  		 sexSelected : sexSelected,
                		  		 idCardNo : idCardNo,
                		  		 page1 : page
                	   		   }
                	   ,function(data){
                		   $("#idCardInfo").html('');
                		   if(data.list != null && data.list.length > 0){
                			   for(var i=0;i<data.list.length;i++){
                					var temp = $('<tr>'+
                            				'<td id="collectTime'+i+'"></td>'+
                            				'<td id="collectSite'+i+'"></td>'+
                            				'<td>'+
                            				'<table class="sub-table">'+
                            				'<tr>'+
                            				'<td id="idCardPic'+i+'"></td>'+
                            				'<td id="idCardInfo'+i+'">'+
                                            '</td>'+
                                            '</tr>'+
                                            '</table>'+
                                            '</td>'+
                                            '<td id="cardInfoComp'+i+'"></td>'+
                                            '<td><table class="sub-table"><tr><td id="ctrlBase'+i+'" class="contrast-img"></td><td id="similarity'+i+'" class="contrast-info" align="center"></td></tr>'+
                                            '</table></td>'+
                                            '<td id="detail'+i+'"></td>'+
                                            '</tr>');
                					$("#idCardInfo").append(temp);
                					if(data.list[i].collectTimeStr != null){
                    					$("#collectTime"+i).html(data.list[i].collectTimeStr);
                    				}
                    				if(data.list[i].collectTimeStr != null){
                    					$("#collectSite"+i).html(data.list[i].collectSite);
                    				}
                    				if(data.list[i].idCardPicStr != ""){
                    					$("#idCardPic"+i).html('<img src="data:image/jpg;base64,'+data.list[i].idCardPicStr+'" class="people-img" alt="">');
                    				}else{
                    					$("#idCardPic"+i).html('<img src="../img/collect-fail.png" width="68" height="86" alt="">');
                    				}
                    				    $("#idCardInfo"+i).html('<ul class="txt">'+
                                                '<li>'+(data.list[i].idCardName==null?"":data.list[i].idCardName)+'</li>'+
                                                '<li>'+(data.list[i].idCardSex==null?"":data.list[i].idCardSex)+'</li>'+
                                                '<li>'+(data.list[i].idCardNo==null?"":data.list[i].idCardNo)+'</li>'+
                                             '</ul>');
                    				 if(data.list[i].compareBaseID != ""){
                    				 if(data.list[i].compareBaseID == '1'){
                    				    $("#cardInfoComp"+i).html('<span class="icon i-yes"></span><span class="red">在逃人员</span>');
                    				 }else if(data.list[i].compareBaseID == '2'){
                    					 $("#cardInfoComp"+i).html('<span class="icon i-yes"></span><span class="red">常控人员</span>');
                    				 }else{
                    					 $("#cardInfoComp"+i).html('<span class="icon i-yes"></span><span class="red">临控人员</span>');
                    				 }
                    				 if(data.list[i].collectPicStr != ""){
                    					 $("#ctrlBase"+i).html('<td>'+
                                                 '<img class="people-img" src="data:image/jpg;base64,'+data.list[i].collectPicStr+'" width="68" height="86" alt="">'+
                                                 '</td>');
                    				 }else{
                    					 $("#ctrlBase"+i).html('<td>'+
                                                 '<img class="people-img" src="../img/collect-fail.png" width="68" height="86" alt="">'+
                                                 '</td>');
                    				 }
                    				 }
                    				 if(data.list[i].similarity != null){
                    				 if(data.list[i].similarity >= data.paramSet.faceCardCompAlarmVal){
                    					$("#similarity"+i).html(' <ul class="txt">'+
                                                '<li>'+data.list[i].similarity+'%'+'</li>'+
                                                    '<li>匹配</li>'+
                                                '</ul>'); 
                    				 }else{
                    					 if(data.list[i].similarity>0){
                    					 $("#similarity"+i).html(' <ul class="txt">'+
                                                 '<li>'+data.list[i].similarity+'%'+'</li>'+
                                                     '<li>不匹配</li>'+
                                                 '</ul>');
                    					 }
                    				 }
                    				 }
                    				 $("#detail"+i).html('<a href="/hengdong/AnalyzingController/getIntegratedQueryMessage.do?compareBaseID='+data.list[i].compareBaseID+'&comeFrom=身份证信息查询&flag='+data.list[i].isControlled+'&idCardNo='+data.list[i].idCardNo+'&cardCode='+data.list[i].cardCode+'&collectTimeStr='+data.list[i].collectTimeStr+'&collectSite='+data.list[i].collectSite+'" class="more">查看</a>');
                			   }
                		   }else{
                			   $("#idCardInfo").html('<tr><td colspan="6">暂无数据</td></tr>');
                			   countFlag = true;
                		   }
                			$("#pageCount").html('<span class="page-info">共'+data.page.totalCount+'条 每页10条 页次:'+data.page.page+'/'+(data.page.totalPage=='0'?'1':data.page.totalPage)+'</span>');
                    		var pageTemp = $('<li id="firstPage"></li><li id="beforePage"></li><li id="pageInput"></li><li id="nextPage"></li><li id="lastPage"></li>');
                    		$(".pagination").html(pageTemp);
                    		$("#firstPage").html('<a href="javascript:firstPage(1);">首页</a>');
                    		if(data.page.page > 1){
                    			$("#beforePage").html('<a href="javascript:beforePage('+(data.page.page-1)+')">上一页</a>');
                    		}else{
                    			$("#beforePage").html('<a href="javascript:void(0)">上一页</a>');
                    		}
                    		if(data.page.totalPage > data.page.page){
                    			$("#nextPage").html('<a href="javascript:nextPage('+(data.page.page+1)+')">下一页</a>');
                    		}else{
                    			$("#nextPage").html('<a href="javascript:void(0)">下一页</a>');
                    		}
                    		$("#lastPage").html('<a href="javascript:lastPage('+data.page.totalPage+')">尾页</a>');
                    		$("#pageInput").html('<input type="text" value="'+data.page.page+'" id="pageText1" onchange="prePage(this)">');
                    		$("#page").val(data.page.page);
                    		maxPage = data.page.totalPage;
                	   },'json');
                   }     
                   $(".search-more").click('on',function(){
                       search(1);
                   });
                   function firstPage(page){
                	   if(!countFlag){
	            			search(page);
	            		}
	               	}
               		function beforePage(page){
               			if(!countFlag){
	            			search(page);
	            		}
	               	}
	               	function nextPage(page){
	               		if(!countFlag){
	            			search(page);
	            		}
	               	}
	               	function lastPage(page){
	               		if(!countFlag){
	            			search(page);
	            		}
	               	}
	            	function prePage(page){
	            		if(!countFlag){
	            			if(parseInt(page) > parseInt(maxPage)){
	            				$("#pageInput").val(maxPage);
	            				page.value = maxPage;
	            			}
	            			search(page);
	            		}else{
	            			$("#pageText1").val(1);
	            		}
	               	}
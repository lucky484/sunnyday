/**
 * 页面根据分页改变当前页未完成
 */
			var maxpage = 0;
            function getdate(id) {
                  var today = new Date();
                  var y = today.toLocaleDateString();
                  var h = today.getHours();
                  var m = today.getMinutes();
                  var s = today.getSeconds();
                  if (s < 10) {
                        s = "0" + s;
                  }
                  if (m < 10) {
                        m = "0" + m;
                  }
                  $(id).text(y + " " +h + " : " + m + " : " + s);
                  setTimeout(function() {
                        getdate(id)
                  }, 500);
            }
            
            $(function(){
            	$("#page").val(1);
                getdate('#curr-time');
                
                //$("#getPhotoCompareMessage").submit();
                //左侧导航
                $("#site-menu").metisMenu();  
                //绑定对比button
                $("#compare_btn").click(function(){
                	$("#hiddenfys").val($("#fys").val());
                	$("getPhotoCompareMessage").submit();
                });
                $("#page").change(function(){
                	if(parseInt($("#page").val())<= parseInt(0) ){
                		$("#page").val(1);
                	}
                	var page = $("#page").val();
                	if($("#hiddenflag").val() == "1"){
	                	if(parseInt(page) > parseInt(maxpage)){
	                		if(maxpage=="0"){
	                			maxpage ='1';
	                		}
	                		page = maxpage;
	                		$("#page").val(page);
	                	}
	                	if($("#dataCount").html()!="0"){
		                	getAjax(page);
	                	}
	                	$("#currentPage").html($("#page").val());
                	}
                	
                });
               
                maxpage = $("#hiddenCountPage").val();
                $("#fys").change(function(){
                	
                	if(parseInt($("#fys").val())<=parseInt(0) ){
                		$("#fys").val('1');
                	}
                });
                if($("#dataCount").html() == '0'){
                	$("#pageContent").append("<tr><td colspan='5'>暂无数据！</td></tr>");
                	
                }
//                $("#fys").change(function(){
//                	if(parseInt($("#fys").val())<parseInt(0) && $("#hiddenflag").val() == "1"){
//                		$("#fys").val(10);
//                	}
//                	$("#hiddenfys").val($("#fys").val());
//                	//getAjax(1);
//                	//$("#getPhotoCompareMessage").submit();
//                	//$("#page").val(1);
//            		//$("#currentPage").html(1);
//                });
            });
            
            
            

            function getPrePage(){
            	if($("#dataCount").html()!="0"){
	            	var page = $("#page").val();
	            	//alert(maxpage);
	            	if( parseInt(page) > 1 && $("#hiddenflag").val() == "1"){
	            		var newPage = eval(page+"-1");
	            		getAjax(newPage);
	            		//$("#hiddenPage").val($("#page").val());
	            		$("#page").val(newPage);
	            		$("#currentPage").html($("#page").val());
	            	}
            	}
            }

            function getNextPage(){
            	if($("#dataCount").html()!="0"){
	            	var page = $("#page").val();
	            	if( parseInt(page) < parseInt(maxpage) && $("#hiddenflag").val() == "1" ){
	            		var newPage = eval(page+"+1");
	            		getAjax(newPage);
	            		//$("#hiddenPage").val($("#page").val());
	            		$("#page").val(newPage);
	            		$("#currentPage").html($("#page").val());
	            	}
            	}
            }
            
            function getPage(page){
            	if($("#dataCount").html()!="0"){
	            	if($("#hiddenflag").val() == "1"){
		            	$("#page").val(page);
		        		$("#currentPage").html(page);
		            	getAjax(page);
	            	}
            	}
            }

            function getAjax(page){
            	
            	var hiddenIdCardNo = $("#hiddenIdCardNo").val();
            	var hiddenCollectSite = $("#hiddenCollectSite").val();
            	var hiddenfys = $("#hiddenfys").val();
            	$.post("getPageContent.do?hiddenfys="+hiddenfys+"&collectSite="+hiddenCollectSite+"&idCardNo="+hiddenIdCardNo+"&page="+page,function(data){
            		$("#pageContent").children().each(function(index){
              			$(this).remove();
              		});
	
					var list = data.list;
					for(var i=0;i<list.length;i++){
						var tr;
						if(list[i].isControlled==1){
							tr = "<tr>" +
	    					"<td>"+list[i].collectTimeStr+"</td>" +
	   						"<td>"+list[i].collectsite+"</td>" +
	    					"<td width='380'>" +
	    							"<div class='id-info'>" +
	    							"<div class='id-img'>" +
	    							" <img src='data:image/jpg;base64,"+list[i].idCardPicStr+"' width='68' height='86' alt=''></div>" +
	    							"<div class='id-spec'>  " +
	    									"<div class='id-item'> <label>姓名</label><span>"+list[i].idCardName+"</span></div> " +
	    									"<div class='id-item'> <label>性别</label><span>"+list[i].idCardSex+"</span></div>" +
	   									"<div class='id-item'> <label>民族</label><span>"+list[i].idCardNation+"</span></div>" +
	    									"<div class='id-item'> <label>身份号码</label><span>"+list[i].idCardNo+"</span></div></div></div>" +
	    					"</td>" +
	    					"<td><span class='icon i-yes'></span>&nbsp;<span class='red'>是</span></td>" +
	    					"<td><a href='/hengdong/AnalyzingController/getIntegratedQueryMessage.do?comeFrom=身份证对比详情&page="+$("#page").val()+"&flag="+list[i].isControlled+"&idCardNo="+list[i].idCardNo+"&cardCode="+list[i].cardCode+"&collectTimeStr="+list[i].collectTimeStr+"&collectSite="+list[i].collectsite+"' class='more'>查看</a></td></tr>";
							
            			}else{
            				tr = "<tr>" +
	    					"<td>"+list[i].collectTimeStr+"</td>" +
	   						"<td>"+list[i].collectsite+"</td>" +
	    					"<td width='380'>" +
	    							"<div class='id-info'>" +
	    							"<div class='id-img'>" +
	    							" <img src='data:image/jpg;base64,"+list[i].idCardPicStr+"' width='68' height='86' alt=''></div>" +
	    							"<div class='id-spec'>  " +
	    									"<div class='id-item'> <label>姓名</label><span>"+list[i].idCardName+"</span></div> " +
	    									"<div class='id-item'> <label>性别</label><span>"+list[i].idCardSex+"</span></div>" +
	   									"<div class='id-item'> <label>民族</label><span>"+list[i].idCardNation+"</span></div>" +
	    									"<div class='id-item'> <label>身份号码</label><span>"+list[i].idCardNo+"</span></div></div></div></td>" +
	    					"<td><span class='icon i-no'></span>否</td>" +
	    					"<td><a href='/hengdong/AnalyzingController/getIntegratedQueryMessage.do?comeFrom=身份证对比详情&flag="+list[i].isControlled+"&idCardNo="+list[i].idCardNo+"&cardCode="+list[i].cardCode+"&collectTimeStr="+list[i].collectTimeStr+"&collectSite="+list[i].collectsite+"' class='more'>查看 </a></td></tr>";
            				
            			}
						$("#pageContent").append(tr);

					}          		
            	});
            }
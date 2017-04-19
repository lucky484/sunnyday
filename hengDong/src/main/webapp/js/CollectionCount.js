/**
 * 详情统计
 */

			var maxPage;
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
                getdate('#curr-time');
                $("#page").val(1);
                getAjax(1,'0');
                //左侧导航
                $("#site-menu").metisMenu();            
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
				//bangding查询事件
				$("#query_btn").click(function(){
					$("#hiddenIdCardNo").val($("#idCardNo").val());
					$("#hiddenCollectSite").val($("#collectSite").val());
					$("#hiddenStartDate").val($("#date_timepicker_start").val());
					$("#hiddenEndDate").val($("#date_timepicker_end").val());
					$("#hiddenfys").val($("#fys").val());
					getAjax(1,'0');
				});
				
				//绑定失去焦点事件
				$("#page").change(function(){
					
						if($("#flag").val()=="1"){
		                	
		                	if(parseInt($("#page").val())<= parseInt(0) ){
		                		$("#page").val(1);
		                	}
		                	var page = $("#page").val();
		                	if(parseInt(page) > parseInt(maxPage)){
		                		if(maxPage=="0"){
		                			maxPage ='1';
		                		}
		                		page = maxPage;
		                		$("#page").val(page);
		                	}
		                	if($("#counts").html()!="0"){
		                		getAjax(page,'1');
		                	}
		                	$("#currentPage").html($("#page").val());
						}else{
							$("#page").val("1");
							$("#currentPage").html($("#page").val());
						}
					
                });
				
				 $("#fys").change(function(){
	                	if(parseInt($("#fys").val())<=parseInt(0) ){
	                		$("#fys").val('1');
	                	}
	                });
				 
				 
//				 $("#fys").change(function(){
//	                	if(parseInt($("#fys").val())<parseInt(0)){
//	                		$("#fys").val(10);
//	                	}
//	                	$("#hiddenfys").val($("#fys").val());
////	                	getAjax(1,'0');
////	                	$("#page").val(1);
////	            		$("#currentPage").html(1);
//	                });

            });
            
            function getEndPage(){
            	if($("#counts").html()!="0"){
	            	var page = $("#page").val();
	            	if($("#flag").val()=="1"){
		            	if(parseInt(page) < parseInt(maxPage)){
		            		page = maxPage;
		            		$("#page").val(page);
		            	}
		            	getAjax(page,'1');
		            	$("#currentPage").html($("#page").val());
	            	}
            	}
            }
            
			function getAjax(page,flag){  
				//alert(page+"_"+flag+"_"+$("#hiddenCollectSite").val()+"_"+$("#hiddenIdCardNo").val()+"_"+$("#hiddenStartDate").val()+"_"+$("#hiddenEndDate").val());
            	$.post("getCollectionCount.do?hiddenfys="+$("#hiddenfys").val()+"&flag="+flag+"&collectSite="+$("#hiddenCollectSite").val()+"&idCardNo="+$("#hiddenIdCardNo").val()+"&page="+page+"&startDate="+$("#hiddenStartDate").val()+"&endDate="+$("#hiddenEndDate").val(),function(data){
            		$("#pageContent").children().each(function(index){
              			$(this).remove();
              		});
					//添加数据
					var list = data.list;
					for(var i=0;i<list.length;i++){	
						var tr;
						if(list[i].idCardInfoCompareResult!="0"){
							tr="<tr>"+
									"<td>"+list[i].collectsite+"</td>"+
									"<td>"+
									"<table class='sub-table' style='margin:0px auto;'>"+
										"<tr>"+
											"<td><img class='people-img' src='data:image/jpg;base64,"+list[i].idCardPicStr+"' width='68' height='86' alt=''></td>"+
											"<td>"+
												"<ul class='txt'><li>"+list[i].idCardName+"</li>"+
																"<li>"+list[i].idCardSex+"</li>"+
																"<li>"+list[i].idCardNo+"</li></ul></td></tr></table>"+
											"</td>";
							if(list[i].tableName=="T_QB_RY_ZTRYJBXX"){
								tr = tr+"<td><span class='icon i-yes'></span><span class='red'>在逃人员</span></td>";
							}else if(list[i].tableName=="T_QB_LK_LKBK"){
								tr = tr+"<td><span class='icon i-yes'></span><span class='red'>临控人员</span></td>";
							}else if(list[i].tableName=="T_QB_RY_ZTRYJBXX"){
								tr = tr+"<td><span class='icon i-yes'></span><span class='red'>常控人员</span></td>";
							}else{
								tr = tr+"<td><span class='icon i-yes'></span><span class='red'>在逃人员</span></td>";
							}	
							tr = tr + "<td>"+list[i].collectCount+"</td><td><a href='getCollectionCountDetails.do?compareBaseID="+list[i].compareBaseID+"&collectSite="+list[i].collectsite+"&idCardNo="+list[i].idCardNo+"&startDate="+data.startDate1+"&endDate="+data.endDate1+"&flag=1' class='more' >查看</a></td></tr>";
						
						}else{
							tr="<tr>"+
							"<td>"+list[i].collectsite+"</td>"+
							"<td align='center'>"+
							"<table class='sub-table' style='margin:0px auto;'>"+
								"<tr>"+
									"<td><img class='people-img' src='data:image/jpg;base64,"+list[i].idCardPicStr+"' width='68' height='86' alt=''></td>"+
									"<td>"+
										"<ul class='txt'><li>"+list[i].idCardName+"</li>"+
														"<li>"+list[i].idCardSex+"</li>"+
														"<li>"+list[i].idCardNo+"</li></ul></td></tr></table>"+
									"</td>"+
									"<td> <span class='icon i-no'></span>未比中</td>"+
									"<td>"+list[i].collectCount+"</td><td><a href='getCollectionCountDetails.do?compareBaseID="+list[i].compareBaseID+"&collectSite="+list[i].collectsite+"&idCardNo="+list[i].idCardNo+"&startDate="+data.startDate1+"&endDate="+data.endDate1+"&flag=0' class='more' >查看</a></td></tr>";
						}							
   						$("#pageContent").append(tr);						
					}
					//判断改请求是何种请求
					if(data.flag=="0"){//表单请求
						$("#counts").html(data.count);
						$("#pages").html(data.pages);
						$("#page").val(1);
		        		$("#currentPage").html(1);
		        		$("#flag").val(1);
		        		if($("#counts").html() == '0'){
		        			$("#pageContent").append("<tr><td colspan='5'>暂无数据！</td></tr>");
		                	
		                }
		        		
						maxPage = data.pages;
						console.info(list);
						if(!list[0]){//空值
							$("#counts").html(0);
							$("#page").val(1);
							$("#pages").html(1);
			        		$("#currentPage").html(1);
			        		$("#flag").val(0);
						}
					}
            	}); 
            }
			
			function getPrePage(){
				if($("#counts").html()!="0"){
	            	var page = $("#page").val();
	            	//alert(maxpage);
	            	if( parseInt(page) > 1  && $("#flag").val()=="1" ){
	            		var newPage = eval(page+"-1");
	            		
	            		getAjax(newPage,'1');
	            		//$("#hiddenPage").val($("#page").val());
	            		$("#page").val(newPage);
	            		$("#currentPage").html($("#page").val());
	            	}
				}
            }

            function getNextPage(){
            	if($("#counts").html()!="0"){
	            	var page = $("#page").val();
	            	if( parseInt(page) < parseInt(maxPage) && $("#flag").val()=="1"){
	            		var newPage = eval(page+"+1");
	            		getAjax(newPage,'1');
	            		//$("#hiddenPage").val($("#page").val());
	            		$("#page").val(newPage);
	            		$("#currentPage").html($("#page").val());
	            	}
            	}
            }
            
            function getPage(page){
            	if($("#counts").html()!="0"){
	            	if($("#flag").val()=="1"){
		            	$("#page").val(page);
		        		$("#currentPage").html(page);
		            	getAjax(page,'1');
	            	}
            	}
            }
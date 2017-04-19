   
    	/* 获取人脸比对的相似度的预警阈值 */
    	var facecompalarmval;
    	$.post("getFacecomp.do", function(date) {
    		facecompalarmval = date.faceCompAlarmVal;
        	getAjax(1,"","","",facecompalarmval);
        	$("#page").val("1");
    		$("#GET").attr("checked","checked ");
    		faceSimilarity = facecompalarmval;
        	$("#similarity").val(facecompalarmval);
        	$("#similarity").attr("disabled","disabled"); 
        	$("#similarity").css("background-color","#E6E6E6"); 
    	});

        var collectTimeStart = "";
        var collectTimeEnd = "";
        var collectSite = "";
        var faceSimilarity = "";
        var page;
        
        function getFace(){
        	page = 1;
        	$("#page").val("1");
        	if($("#GET").attr("checked")!="checked"){
        		$("#GET").attr("checked","checked ");
        		faceSimilarity = facecompalarmval;
            	$("#similarity").val(facecompalarmval);
            	$("#similarity").attr("disabled","disabled");
            	$("#similarity").css("background-color","#E6E6E6"); 
        	}else{
        		$("#similarity").removeAttr("disabled");
        		$("#GET").removeAttr("checked");
        		faceSimilarity = null;
            	$("#similarity").val(null);
            	$("#similarity").css("background-color","#F3F3FF"); 
        	}
        }
        
        function getface(){
        	page = 1;
        	$("#page").val("1");
        	collectTimeStart = $("#date_timepicker_start").val();
        	collectTimeEnd = $("#date_timepicker_end").val();
        	collectSite = $("#location").val();
        	faceSimilarity = $("#similarity").val();	
        	getAjax(page,collectTimeStart,collectTimeEnd,collectSite,faceSimilarity);
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

//                 $("#compare_btn").click(function(){
//                 	$("getPhotoCompareMessage").submit();
//                 });
                 var maxpage;
                 $("#page").blur(function(){
                 	var page = $("#page").val();
                 	if(page > maxpage){
                 		page = maxpage;
                 		$("#page").val(page);
                 	}
                	collectTimeStart = $("#date_timepicker_start").val();
                	collectTimeEnd = $("#date_timepicker_end").val();
                	collectSite = $("#location").val();
                	faceSimilarity = $("#similarity").val();	
                 	getAjax(page,collectTimeStart,collectTimeEnd,collectSite,faceSimilarity);
                 	
                 });
                
                 //alert(maxpage);
                 
             });
            
            function getPrePage(){
            	var page = $("#page").val();
            	//alert(maxpage);
            	if( page > 1){
            		$("#page").val(eval(page+"-1"));
                	collectTimeStart = $("#date_timepicker_start").val();
                	collectTimeEnd = $("#date_timepicker_end").val();
                	collectSite = $("#location").val();
                	faceSimilarity = $("#similarity").val();	
            		getAjax($("#page").val(),collectTimeStart,collectTimeEnd,collectSite,faceSimilarity);
            		//$("#hiddenPage").val($("#page").val());
            	}
            }

            function getNextPage(){
            	var page = $("#page").val();
            	if( page < maxpage){
            		$("#page").val(eval(page+"+1"));
                	collectTimeStart = $("#date_timepicker_start").val();
                	collectTimeEnd = $("#date_timepicker_end").val();
                	collectSite = $("#location").val();
                	faceSimilarity = $("#similarity").val();	
            		getAjax($("#page").val(),collectTimeStart,collectTimeEnd,collectSite,faceSimilarity);
            		//$("#hiddenPage").val($("#page").val());
            	}
            }
            
            function getPage(page,collectTimeStart,collectTimeEnd,collectSite,faceSimilarity){
            	$("#page").val(page);
            	collectTimeStart = $("#date_timepicker_start").val();
            	collectTimeEnd = $("#date_timepicker_end").val();
            	collectSite = $("#location").val();
            	faceSimilarity = $("#similarity").val();	
            	getAjax(page,collectTimeStart,collectTimeEnd,collectSite,faceSimilarity);
            }
            
            function getLastPage(){
            	$("#page").val(maxpage);
            	collectTimeStart = $("#date_timepicker_start").val();
            	collectTimeEnd = $("#date_timepicker_end").val();
            	collectSite = $("#location").val();
            	faceSimilarity = $("#similarity").val();	
            	getAjax(maxpage,collectTimeStart,collectTimeEnd,collectSite,faceSimilarity);
            }
            
            var list;
            var count;
            var faceS;
            var faceCS;
            var lzt;
            function getAjax(page,collectTimeStart,collectTimeEnd,collectSite,faceSimilarity){
            	$.post("getFaceInfoBy.do",{
            		page : page,
            		collectTimeStart : collectTimeStart,
            		collectTimeEnd : collectTimeEnd,
            		collectSite : collectSite,
            		faceSimilarity : faceSimilarity
            	},function(data){
            		list = data.l;
            		count = data.count;
            		maxpage = data.pages;
            		faceS = data.faceS;
            		faceCS = data.faceCS;
            		lid = data.lid;
            		lzt = data.lzt;
            		lc = data.lc;
            		lt = data.lt;
            		$("#faceInfoCount").html("");
            		$("#faceInfoC").children().each(function(index){
              			$(this).remove();
              		});
            
            if(list.length != 0){
            	$("#faceInfoCount").append("共"+count+"条 每页10条"+" 页次"+page+"/"+maxpage);
            }else{
            	$("#faceInfoC").append('<tr><td colspan=7>暂无数据</td></tr>');
            	$("#page").val("1");
            	$("#faceInfoCount").append("共0条 每页10条"+" 页次1/1");
            }
            
			// 格式化时间 yyyy-mm-dd ~~~~~
            function formatDate(time){
            	if(time){
            		return time.split(".")[0];
            	}else{
            		return '';
            	}
            }
            
            
            for(var i=0;i<list.length;i++){
            	var tr1;
            	var tr2;
            	var tr3;
            	var tr4;
            	var time = list[i].collectTime;
            	time = formatDate(time);
            	if(lid[i] == "1"){
            		if(null == lzt[i]){
                		tr1 = "<tr>" +
                        "<td>"+time+
                        "</td>"+
                      "<td>"+list[i].collectSite+"</td>"+
                      "<td>"+
                      "<img src='data:image/jpg;base64,"+list[i].collectPic+"' width='68' height='86' alt=''>"+
                      "</td>"+
                      "<td>"+
                          "<table class='sub-table'>"+
                              "<tr>"+
                                  "<td>"+
                                  "</td>"+
                                  "<td>"+
                                      "<ul class='txt'>"+
                                      "</ul>"+
                                  "</td>"+
                              "</tr>"+
                          "</table>"+
                      "</td>"
                	}else{
                		if(lzt[i].xb == "1"){
                			xb = "男";
                		}else{
                			xb = "女";
                		}
                		tr1 = "<tr>" +
                        "<td>"+time+
                        "</td>"+
                      "<td>"+list[i].collectSite+"</td>"+
                      "<td>"+
                      "<img src='data:image/jpg;base64,"+list[i].collectPic+"' width='68' height='86' alt=''>"+
                      "</td>"+
                      "<td>"+
                          "<table class='sub-table'>"+
                              "<tr>"+
                                  "<td>"+
                                  	"<img src='data:image/jpg;base64,"+lzt[i].photoStr+"' width='68' height='86' alt=''>"+
                                  "</td>"+
                                  "<td>"+
                                      "<ul class='txt'>"+
                                         "<li>"+lzt[i].xm+"</li>"+
                                         "<li>"+xb+"</li>"+
                                         "<li>"+lzt[i].ysfzh+"</li>"+
                                      "</ul>"+
                                  "</td>"+
                              "</tr>"+
                          "</table>"+
                      "</td>"
                	}
            	}else if(lid[i] == "2"){
            		if(null == lc[i]){
                		tr1 = "<tr>" +
                        "<td>"+time+
                        "</td>"+
                      "<td>"+list[i].collectSite+"</td>"+
                      "<td>"+
                      "<img src='data:image/jpg;base64,"+list[i].collectPic+"' width='68' height='86' alt=''>"+
                      "</td>"+
                      "<td>"+
                          "<table class='sub-table'>"+
                              "<tr>"+
                                  "<td>"+
                                  "</td>"+
                                  "<td>"+
                                      "<ul class='txt'>"+
                                      "</ul>"+
                                  "</td>"+
                              "</tr>"+
                          "</table>"+
                      "</td>"
                	}else{
                		if(lc[i].xb == "1"){
                			xb = "男";
                		}else{
                			xb = "女";
                		}
                		tr1 = "<tr>" +
                        "<td>"+time+
                        "</td>"+
                      "<td>"+list[i].collectSite+"</td>"+
                      "<td>"+
                      "<img src='data:image/jpg;base64,"+list[i].collectPic+"' width='68' height='86' alt=''>"+
                      "</td>"+
                      "<td>"+
                          "<table class='sub-table'>"+
                              "<tr>"+
                                  "<td>"+
                                  	"<img src='data:image/jpg;base64,"+lc[i].photoStr+"' width='68' height='86' alt=''>"+
                                  "</td>"+
                                  "<td>"+
                                      "<ul class='txt'>"+
                                         "<li>"+lc[i].xm+"</li>"+
                                         "<li>"+xb+"</li>"+
                                         "<li>"+lc[i].sfzh+"</li>"+
                                      "</ul>"+
                                  "</td>"+
                              "</tr>"+
                          "</table>"+
                      "</td>"
                	}
            	}else{
            		if(null == lt[i]){
                		tr1 = "<tr>" +
                        "<td>"+time+
                        "</td>"+
                      "<td>"+list[i].collectSite+"</td>"+
                      "<td>"+
                      "<img src='data:image/jpg;base64,"+list[i].collectPic+"' width='68' height='86' alt=''>"+
                      "</td>"+
                      "<td>"+
                          "<table class='sub-table'>"+
                              "<tr>"+
                                  "<td>"+
                                  "</td>"+
                                  "<td>"+
                                      "<ul class='txt'>"+
                                      "</ul>"+
                                  "</td>"+
                              "</tr>"+
                          "</table>"+
                      "</td>"
                	}else{
                		if(lt[i].bbkrxb == "1"){
                			xb = "男";
                		}else{
                			xb = "女";
                		}
                		tr1 = "<tr>" +
                        "<td>"+time+
                        "</td>"+
                      "<td>"+list[i].collectSite+"</td>"+
                      "<td>"+
                      "<img src='data:image/jpg;base64,"+list[i].collectPic+"' width='68' height='86' alt=''>"+
                      "</td>"+
                      "<td>"+
                          "<table class='sub-table'>"+
                              "<tr>"+
                                  "<td>"+
                                  	"<img src='data:image/jpg;base64,"+lt[i].photoStr+"' width='68' height='86' alt=''>"+
                                  "</td>"+
                                  "<td>"+
                                      "<ul class='txt'>"+
                                         "<li>"+lt[i].bbkrxm+"</li>"+
                                         "<li>"+xb+"</li>"+
                                         "<li>"+lt[i].bbkrzjhm+"</li>"+
                                      "</ul>"+
                                  "</td>"+
                              "</tr>"+
                          "</table>"+
                      "</td>"
                	}
            	}
            	
            if(list[i].faceSimilarity == "0"){
            	tr2 = "<td>"+
			            	"<span class='c-danger'>"
							"<br>"+
						"</span>"+
            		"</td>"
            }else if(list[i].faceSimilarity > faceS){
            	tr2 = "<td>"+
                			"<span class='c-danger'>"+list[i].faceSimilarity+
                				"%<br>匹配"+
            				"</span>"+
        			"</td>"
            }else{
            	tr2 = "<td>"+
                			"<span class='c-danger'>"+list[i].faceSimilarity+
                				"%<br>不匹配"+
            				"</span>"+
        			"</td>"
            }
            if(list[i].similarity == "0"){
            	tr3 = "<td>"+
            				"<table class='sub-table matched_list'>"+
        						"<tr>"+
            						"<td>"+
            						"</td>"+
            						"<td>"+
                						"<ul class='txt'>"+
                						"</ul>"+
            						"</td>"+
        						"</tr>"+
    						"</table>"+
        			"</td>"
            }else if(list[i].similarity > faceCS){
            	tr3 = "<td>"+
							"<table class='sub-table matched_list'>"+
								"<tr>"+
									"<td>"+
										"<img src='data:image/jpg;base64,"+list[i].idCardPic+"' width='68' height='86' alt=''>"+
									"</td>"+
									"<td>"+
			    						"<ul class='txt'>"+
			        						"<li class='c-danger'>"+list[i].similarity+"%</li>"+
			        						"<li class='c-danger'>匹配</li>"+
			    						"</ul>"+
									"</td>"+
								"</tr>"+
							"</table>"+
					"</td>"
            }else{
            	tr3 = "<td>"+
							"<table class='sub-table matched_list'>"+
								"<tr>"+
									"<td>"+
										"<img src='data:image/jpg;base64,"+list[i].idCardPic+"' width='68' height='86' alt=''>"+
									"</td>"+
									"<td>"+
			    						"<ul class='txt'>"+
			        						"<li class='c-danger'>"+list[i].similarity+"%</li>"+
			        						"<li class='c-danger'>不匹配</li>"+
			    						"</ul>"+
									"</td>"+
								"</tr>"+
							"</table>"+
					"</td>"
            }
            tr4 = "<td><a href='/hengdong/complexQuery/queryComplexById.do?compareBaseId="+list[i].compareBaseID+"&comeFrom=人脸信息查询&faceId="+list[i].faceId+
            	"' class='more'>查看</a></td>"+
            "</tr>"
            	$("#faceInfoC").append(tr1+tr2+tr3+tr4);
            }
            	});
        }
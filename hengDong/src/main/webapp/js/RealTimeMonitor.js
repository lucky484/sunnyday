			var timeOutFlag = false;
			var timeOutFaceFlag = false;
			var timeOutIdCardFlag = false;
			var timeOutCardInfoFlag = false;
			var timeOutRemoveFlag = false;
			var faceFlag = false;
			var flag = 0;
			var count = 0;
			var countFace = 0;
			var countIdCard = 0;
			var countCardInfo = 0;
			/**
			 * 长定时标记
			 */
			var longTimeOutIdCardFlag = false;
			var longCountIdCard = 0;
			var longTimeOutFaceFlag = false;
			var longCountFace = 0;
			var longTimeOutFlag = false;
			var longCount = 0;
			/**
			 * 控制时间
			 */
			//实时监控身份证全信息消失时间
			var push_IdCardMissTime = 0;
			//预警闪烁时间
			var push_warmingTime = 0;
			//预警定时取数时间
			var push_timeOutTime = 0;
			//预警闪烁之后长显示时间
			var push_displayTime = 0;
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
                  
                  /**
                   * 定时器
                   */
                  setTimeout(function() {

                	  	getdate(id);
                	  	/**
                	  	 * 采集信息定时器
                	  	 */
                	  	if(flag%4==0){
                	  		init();
                	  	}
                	  	/**
                	  	 * 预警信息定时器
                	  	 */
                	  	if(flag%10==0){
                	  		warning();
                	  	}

                	  	/**
                	  	 * 身份证采集去掉老数据定时器
                	  	 */
                	  	/************************begin*********************/
                	  	if(timeOutCardInfoFlag){
                	  		countCardInfo++;
                        }
                	  	if(timeOutCardInfoFlag&&countCardInfo==push_IdCardMissTime){
                	  		timeOutRemoveFlag = true;
                	  		timeOutCardInfoFlag = false;
                        } 
                	  	/************************end*********************/
                	  /**
                	   * 身份证对比预警定时器
                	   */
                	  	/************************begin*********************/
                	  	if(timeOutIdCardFlag && countIdCard<push_warmingTime){
                        	changeIdCardDivCol();
                        	countIdCard++;
                        }
                	  	/**
                	  	 * 预警结束后开启长定时
                	  	 */
                	  	if(timeOutIdCardFlag && countIdCard>=push_warmingTime){
                	  		timeOutIdCardFlag = false;  
                	  		$("#IdCardteam").attr("class","real-con clearfix");
                	  		$("#sfz").attr("class","real-title");
                	  		longTimeOutIdCardFlag = true;
    						longCountIdCard = 0;
                	  	}
                		/**
                	  	 * 长定时
                	  	 */
                	  	if(longTimeOutIdCardFlag&&longCountIdCard < push_displayTime){
                	  		if( $("#IdCardteam").attr("class")!="real-con clearfix FcLongBackGroundColor"){
	                	  		$("#IdCardteam").attr("class","real-con clearfix FcLongBackGroundColor");
	        					$("#sfz").attr("class","real-title FcLongBackGroundColor");
	        				}
                	  		longCountIdCard++;
                	  	}
                	  	if(longTimeOutIdCardFlag&&longCountIdCard >= push_displayTime){
                	  		$("#IdCardteam").attr("class","real-con clearfix");
                	  		$("#sfz").attr("class","real-title");
                	  		longTimeOutIdCardFlag = false;
    						longCountIdCard = 0;
                	  	}
                	  	/************************end*********************/
                	  	/**
                	  	 * 人脸对比预警
                	  	 */ 
                	  	/************************begin*********************/
                	  	if(timeOutFaceFlag && countFace<push_warmingTime){
                        	changeFaceDivCol();
                        	countFace++;
                        }
                		/**
                	  	 * 预警结束后开启长定时
                	  	 */
                	  	if(timeOutFaceFlag && countFace>=push_warmingTime){
                	  		timeOutFaceFlag = false;  
                	  		$("#faceDiv").attr("class","real-con clearfix");
                	  		$("#face").attr("class","real-title");
                	  		longTimeOutFaceFlag = true;
    						longCountFace = 0;
                	  	}
                	  	/**
                	  	 * 长定时
                	  	 */
                	  	if(longTimeOutFaceFlag&&longCountFace < push_displayTime){
                	  		if( $("#faceDiv").attr("class")!="real-con clearfix FcLongBackGroundColor"){
	                	  		$("#faceDiv").attr("class","real-con clearfix FcLongBackGroundColor");
	        					$("#face").attr("class","real-title FcLongBackGroundColor");
	        				}
                	  		longCountFace++;
                	  	}
                	  	if(longTimeOutFaceFlag&&longCountFace >= push_displayTime){
                	  		$("#faceDiv").attr("class","real-con clearfix");
                	  		$("#face").attr("class","real-title");
                	  		longTimeOutFaceFlag = false;
                	  		longCountFace = 0;
                	  	}
                	  	/************************end*********************/
                	  
                	  	/**
                	  	 * 人证合一预警定时器 
                	  	 */
                	  	/************************begin*********************/
                	  	if(timeOutFlag && count<push_warmingTime){
                        	changeDivCol();
                        	count++;
                        }
                	  	/**
                	  	 * 预警结束后开启长定时
                	  	 */
                	  	if(timeOutFlag && count>=push_warmingTime){
                	  		timeOutFlag = false;    
                	  		$("#fc").attr("class","real-con clearfix");
                	  		$("#rz").attr("class","real-title");
                	  		longTimeOutFlag = true;
	    					longCount = 0;
                	  	}
                	  	/**
                	  	 * 长定时
                	  	 */
                	  	if(longTimeOutFlag&&longCount < push_displayTime){
                	  		if( $("#fc").attr("class")!="real-con clearfix FcLongBackGroundColor"){
	                	  		$("#fc").attr("class","real-con clearfix FcLongBackGroundColor");
	        					$("#rz").attr("class","real-title FcLongBackGroundColor");
	        				}
                	  		longCount++;
                	  	}
                	  	if(longTimeOutFlag&&longCount >= push_displayTime){
                	  		$("#fc").attr("class","real-con clearfix");
                	  		$("#rz").attr("class","real-title");
                	  		longTimeOutFlag = false;
                	  		longCount = 0;
                	  	}
                	  	/************************end*********************/
                        flag++;
                  }, 500);
            }
			/**
			 * 身份证预警
			 */ 
			function changeIdCardDivCol(){
				//判断当前值是否为最新值
				var styleClass = $("#IdCardteam").attr("class");
				if(styleClass == "real-con clearfix"){
					$("#IdCardteam").attr("class","real-con clearfix FcWhiteBackGroundColor");
					$("#sfz").attr("class","real-title FcWhiteBackGroundColor");
				}
				if(styleClass == "real-con clearfix FcWhiteBackGroundColor"){
					$("#IdCardteam").attr("class","real-con clearfix FcRedBackGroundColor");
					$("#sfz").attr("class","real-title FcRedBackGroundColor");
				}else{
					$("#IdCardteam").attr("class","real-con clearfix FcWhiteBackGroundColor");
					$("#sfz").attr("class","real-title FcWhiteBackGroundColor");
				}
				
			}
			/** 
			 * 人脸预警
			 */
			function changeFaceDivCol(){
				var styleClass = $("#faceDiv").attr("class");
				if(styleClass == "real-con clearfix"){
					$("#faceDiv").attr("class","real-con clearfix FcWhiteBackGroundColor");
					$("#face").attr("class","real-title FcWhiteBackGroundColor");
				}
				if(styleClass == "real-con clearfix FcWhiteBackGroundColor"){
					$("#faceDiv").attr("class","real-con clearfix FcRedBackGroundColor");
					$("#face").attr("class","real-title FcRedBackGroundColor");
				}else{
					$("#faceDiv").attr("class","real-con clearfix FcWhiteBackGroundColor");
					$("#face").attr("class","real-title FcWhiteBackGroundColor");
				}
			}
			/**
			 *  人证合一预警
			 */
			function changeDivCol(){
				//判断当前值是否为最新值
				var styleClass = $("#fc").attr("class");
				if(styleClass == "real-con clearfix"){
					$("#fc").attr("class","real-con clearfix FcWhiteBackGroundColor");
					$("#rz").attr("class","real-title FcWhiteBackGroundColor");
				}
				if(styleClass == "real-con clearfix FcWhiteBackGroundColor"){
					$("#fc").attr("class","real-con clearfix FcRedBackGroundColor");
					$("#rz").attr("class","real-title FcRedBackGroundColor");
				}else{
					$("#fc").attr("class","real-con clearfix FcWhiteBackGroundColor");
					$("#rz").attr("class","real-title FcWhiteBackGroundColor");
				}
				
			}
			
            $(function(){
            	push_IdCardMissTime = eval($("#push_IdCardMissTime").val() +"*2");
            	push_warmingTime = eval($("#push_warmingTime").val() +"*2");
            	push_timeOutTime = eval($("#push_timeOutTime").val() +"*2");
            	push_displayTime = eval($("#push_displayTime").val() +"*2");
            	//alert(push_IdCardMissTime+";"+push_warmingTime+";"+push_timeOutTime+";"+push_displayTime);
                getdate('#curr-time');
                // 左侧导航
                $("#site-menu").metisMenu();
                // 对比区域设置等高
                $('.contrast').each(function(){  
                        var highestBox = 0;
                        $('.contrast-item', this).each(function(){
                            if($(this).height() > highestBox) 
                               highestBox = $(this).height(); 
                        });  
                        $('.contrast-item',this).height(highestBox);
                }); 
            });

            /**
             * 采集
             */
            function init(){
                  $.post("queryFaceInfo.do?deviceCode="+$("#e_deviceCode").val(), function(data) {
                	  // alert("aaa");
                  	// faceinfo
                	getFaceInfoMessage(data);
                  	// idcard
                	getIdcardMessage(data);
                	
                	getCollectCount(data);
					
				},'json');
            }
            /**
             * 预警
             */
            function warning(){
                $.post("queryWarningInfo.do?warningTime="+$("#warningTime").val()+"&deviceCode="+$("#e_deviceCode").val(), function(data) {
                	$("#warningTime").val(data.warningTime);
                	//得到人证合一的信息
                	if($("#m_p_faceCardCompFlag").val()=="1"){
                		getFaceAndCardMessage(data);
                	}
	                
					//人脸对比
                	if($("#m_p_faceCompareFlag").val()=="1"){
                		getFaceCompResult(data);
                	}
	              	//身份证对比
	              	if($("#m_p_cardCompareFlag").val()=="1"){
	              		getCardInfo(data);
                	}
	              	
	              	getWarningCount(data);
					
				},'json');
           }
            
            /**
             * 采集端--人脸信息
             */
            function getFaceInfoMessage(data){
            	var length = data.length;
              	var personImage = data.personImage;
              	
              	if(length>1){
              		var faceId = data.listFaceInfoWebModel[1].faceId;
	              	if($("#hiddenFaceId").val()==""){
						$("#hiddenFaceId").val(faceId);
						faceFlag = true;
		            }else{
						if($("#hiddenFaceId").val()!=faceId){
							faceFlag = true;
							$("#hiddenFaceId").val(faceId);
						}
		            }
              	}
              	if(length>1&&personImage=="1"){
              		var img1 = $('<img src="data:image/jpg;base64,'+data.image2+'" alt="">');
              		var img2 = $('<img src="data:image/jpg;base64,'+data.image1+'" class="full" alt="">');
              		var time1 = $("<div class='get-time'>"+data.time2+"</div>");
              		
              		
              		$("#realPhoto").children().each(function(index){
              			$(this).remove();
              		});
              		
              		$("#realPhoto").append(img1);
              		$("#realPhoto").append(img2);
              		$("#realPhoto").append(time1);
              		var dataFaceInfo = data.listFaceInfoWebModel;
              			
                  		$("#rightPhoto").children().each(function(index){
                  			$(this).remove();
                  		});
                 		for(var i=0;i<dataFaceInfo.length;i++){
                 			if(i>1){
                 				var div = $("<div class='his-item'> "+
                  					"<img src='data:image/jpg;base64,"+dataFaceInfo[i].collectPic+"' alt=''> " +
                  					"<span class='get-time'>"+dataFaceInfo[i].collectTime+"</span></div>");
                 				$("#rightPhoto").append(div);
                 			}               			
                 		}           		
              	}else if(length>1&&personImage=="0"){
              		var img1 = $('<img src="data:image/jpg;base64,'+data.image2+'" alt="">');
              		var img2 = $('<img src="../img/default.png" class="full" alt="">');
              		var time1 = $("<div class='get-time'>"+data.time2+"</div>");
              		$("#realPhoto").children().each(function(index){
              			$(this).remove();
              		});
              		$("#realPhoto").append(img1);
              		$("#realPhoto").append(img2);
              		$("#realPhoto").append(time1);           		
  					var dataFaceInfo = data.listFaceInfoWebModel;
      					$("#rightPhoto").children().each(function(index){
                  			$(this).remove();
                  		});
                 		for(var i=0;i<dataFaceInfo.length;i++){
                 			if(i>1){
                 				var div = $("<div class='his-item'> "+
                  					"<img src='data:image/jpg;base64,"+dataFaceInfo[i].collectPic+"' alt=''> " +
                  					"<span class='get-time'>"+dataFaceInfo[i].collectTime+"</span></div>");
                 				$("#rightPhoto").append(div);
                 			}               			
                 		}
              	}
              	$("#FacePhoto").html('<img src="data:image/jpg;base64,'+ data.imgStr +'" width="98" height="124" alt="">');
              	$("#collectFaceInfo").html('<img src="data:image/jpg;base64,'+data.image2+'" width="98" height="124" alt="">');
            }
            
            /**
             * 采集端--身份证信息
             */
            function getIdcardMessage(data){
            	if(data.list.length==0){
            		timeOutRemoveFlag = false;
					countCardInfo = 0;
					return;
            	}
            	if($("#hiddenCardId").val()==""){
					$("#hiddenCardId").val(data.list[0].cardId);
					timeOutCardInfoFlag = true;
					timeOutRemoveFlag = false;
					countCardInfo = 0;
	            }else{
					if($("#hiddenCardId").val()!=data.list[0].cardId){
						timeOutCardInfoFlag = true;
						timeOutRemoveFlag = false;
						countCardInfo = 0;
						$("#hiddenCardId").val(data.list[0].cardId);
					}
	            }

              	$("#idcardInfo").children().each(function(index){
          			$(this).remove();
          		});

            	if(timeOutRemoveFlag){
            		$("#imgIdCard").html('<img src="../img/collect-fail.png" width="98" height="124" alt=""><span>证件照</span>');
            		$(".id-spec").html('<div class="id-item"><label>身份证号&nbsp;:&nbsp;</label><span ></span></div>'
            				+ '<div class="id-item"><label>身份证ID&nbsp;:&nbsp;</label><span ></span></div>'
                          	+ '<div class="id-item"><label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;:&nbsp;</label><span ></span></div>'
                          	+' <div class="id-item"><label>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别&nbsp;:&nbsp;</label><span ></span></div>'
                          	+' <div class="id-item"><label>民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族&nbsp;:&nbsp;</label><span ></span></div>'
                          	+' <div class="id-item"><label>出&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生&nbsp;:&nbsp;</label><span ></span></div>'
                          	+' <div class="id-item"><label>住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址&nbsp;:&nbsp;</label><span ></span></div>'
                          	+' <div class="id-item"><label>签发机关&nbsp;:&nbsp;</label><span ></span></div>'
                          	+' <div class="id-item"><label>采集时间&nbsp;:&nbsp;</label><span ></span></div>');
            		
                  		for(var i=0;i<data.list.length-1;i++){               
                  			var idCardinfo = data.list[i];
                  			$("#idcardInfo").append("<tr><td>"+(idCardinfo.idCardName==null?"":idCardinfo.idCardName)+"</td>" +
                  					"<td>"+(idCardinfo.idCardSex==null?"":idCardinfo.idCardSex)+"</td>" +
                  					"<td>"+(idCardinfo.idCardNo==null?"":idCardinfo.idCardNo)+"</td>" +
                  					"<td>"+(idCardinfo.cardID16==null?"":idCardinfo.cardID16)+"</td>" +
                  					"<td>"+(idCardinfo.collectTimeStr==null?"":idCardinfo.collectTimeStr)+"</td></tr>");
                  	
                  		}	
            	}else{
            		if(data.imgStr!=null){
            			$("#imgIdCard").html('<img src="data:image/jpg;base64,'+ data.imgStr +'" width="98" height="124" alt=""><span>证件照</span>');
            		}else{
            			$("#imgIdCard").html('<img src="../img/collect-fail.png" width="98" height="124" alt=""><span>证件照</span>');
            		}
            		
            		$(".id-spec").html('<div class="id-item"><label>身份证号&nbsp;:&nbsp;</label><span id="spanIdCardNo">'+(data.list[0].idCardNo==null?"":data.list[0].idCardNo) +'</span></div>'
                          	+ '<div class="id-item"><label>身份证ID&nbsp;:&nbsp;</label><span>'+(data.list[0].cardID16==null?"":data.list[0].cardID16)+'</span></div>'
                          	+ '<div class="id-item"><label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;:&nbsp;</label><span>'+(data.list[0].idCardName==null?"":data.list[0].idCardName)+'</span></div>'
                          	+' <div class="id-item"><label>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别&nbsp;:&nbsp;</label><span>'+(data.list[0].idCardSex==null?"":data.list[0].idCardSex)+'</span></div>'
                          	+' <div class="id-item"><label>民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族&nbsp;:&nbsp;</label><span>'+(data.list[0].idCardNation==null?"":data.list[0].idCardNation)+'</span></div>'
                          	+' <div class="id-item"><label>出&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生&nbsp;:&nbsp;</label><span>'+(data.list[0].idCardBirth==null?"":formatBDate(data.list[0].idCardBirth))+'</span></div>'
                          	+' <div class="id-item"><label>住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址&nbsp;:&nbsp;</label><span>'+(data.list[0].idCardAddress==null?"":data.list[0].idCardAddress)+'</span></div>'
                          	+' <div class="id-item"><label>签发机关&nbsp;:&nbsp;</label><span>'+(data.list[0].cardUnit==null?"":data.list[0].cardUnit)+'</span></div>'
                          	+' <div class="id-item"><label>采集时间&nbsp;:&nbsp;</label><span>'+(data.list[0].collectTimeStr==null?"":data.list[0].collectTimeStr)+'</span></div>');
            		
                  		for(var i=1;i<data.list.length;i++){               
                  			var idCardinfo = data.list[i];
                  			$("#idcardInfo").append("<tr><td>"+(idCardinfo.idCardName==null?"":idCardinfo.idCardName)+"</td>" +
                  					"<td>"+(idCardinfo.idCardSex==null?"":idCardinfo.idCardSex)+"</td>" +
                  					"<td>"+(idCardinfo.idCardNo==null?"":idCardinfo.idCardNo)+"</td>" +
                  					"<td>"+(idCardinfo.cardID16==null?"":idCardinfo.cardID16)+"</td>" +
                  					"<td>"+(idCardinfo.collectTimeStr==null?"":idCardinfo.collectTimeStr)+"</td></tr>");
                  		}
            
            	}              	
 	           if($("#hiddenCardId").val()==""){
					$("#hiddenCardId").val(data.list[0].cardId);
					timeOutCardInfoFlag = true;
					countCardInfo = 0;
	           }else{
					if($("#hiddenCardId").val()!=data.list[0].cardId){
						timeOutCardInfoFlag = true;
						countCardInfo = 0;
						$("#hiddenCardId").val(data.list[0].cardId);
					}
	           }

            }
            
            /**
             * 人证合一预警
             */
            function getFaceAndCardMessage(data){
            	var info = data.FCInfo;
				fctimeStamp = info.fctimeStamp;
              	var text = '<div class="contrast-item g-3">'+
							'<h3 class="t-left" style="margin-left:14%"><b>采集身份证信息</b></h3>'+
							'<div class="clearfix">'+
								'<div class="m-left mr5" style="margin-left: 10%">'+
									'<div class="id-img">'+
										'<img src="data:image/jpg;base64,'+info.base64IdCardPic+'" width="98" height="124"'+
											'alt="" >'+
									'</div>'+
									'<div class="c-info">'+ info.fCardDate +'</div>'+
								'</div>'+
								'<div class="m-txt">'+
									'<ul class="contrast-sepc">'+
										'<li>姓名: '+ info.idCardName +'</li>'+
										'<li>性别: '+ changeSex(info.idCardSex) +'</li>'+
										'<li>民族: '+ info.idCardNation +'</li>'+
										'<li>出生: '+ formatBDate(info.idCardBirth) +'</li>'+
										'<li>身份证号:'+ info.idCardNo +'</li>'+
									'</ul>'+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div class="contrast-item g-4" style="height:182px">'+
							'<div class="contrast-con t-center">'+
								'<h3><b>人证比对信息</b></h3>'+
								'<div class="result">'+
									'相似度 <span class="percent">'+info.similarity+'%</span> <span class="warning">不一致</span>'+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div class="contrast-item g-1">'+
							'<h3><b>采集人像信息</b></h3>'+
							'<div class="id-img">'+
								'<img src="data:image/jpg;base64,'+info.base64FacePic+'" width="98" height="124" alt="">'+
							'</div>'+
							'<div class="c-info">'+info.fFaceDate+'</div></div>';
//				var defaultText = '<div class="contrast-item g-3">'+
//							'<h3 class="t-left" style="padding-left:28px"><b>采集身份证信息</b></h3>'+
//							'<div class="clearfix">'+
//								'<div class="m-left mr5">'+
//									'<div class="id-img">'+
//									'</div>'+
//									'<div class="c-info">'+ '' +'</div>'+
//								'</div>'+
//								'<div class="m-txt">'+
//									'<ul class="contrast-sepc">'+
//									'</ul>'+
//								'</div>'+
//							'</div>'+
//						'</div>'+
//						'<div class="contrast-item g-4" style="height:182px">'+
//						'<div class="contrast-con t-center">'+
//							'<h3><b>人证比对信息</b></h3>'+
//							'<div class="result">'+
//							'</div>'+
//						'</div>'+
//						'</div>'+
//						'<div class="contrast-item g-1">'+
//							'<h3><b>采集人像信息</b></h3>'+
//							'<div class="id-img">'+
//							'</div>'+
//							'<div class="c-info">'+ '' +'</div>'+
//						'</div>';
				if(info.similarity==0||info.similarity){
					$("#FCteam").html(text);
					//初始化状态将身份证的cardcode放在隐藏框，当数据来的时候比较cardcode，不同则预警，并更新隐藏框
					if($("#faceCompareResult").val()==""){
						$("#faceCompareResult").val(info.faceCode);
						timeOutFlag = true;
    					count = 0;
    					longTimeOutFlag = false;
    					longCount = 0;
					}else{
						if($("#faceCompareResult").val()!=info.faceCode){
							timeOutFlag = true;
	    					count = 0;
	    					longTimeOutFlag = false;
	    					longCount = 0;
	    					$("#faceCompareResult").val(info.faceCode);
						}
					}
				}
				/*else{
					
					//$("#FCteam").html(defaultText);	
					timeOutFlag = false;
				}*/
            }
            
			// 格式化时间 yyyy-mm-dd ~~~~~
            function formatDate(time){
            	if(time){
            		return time.split(".")[0];
            	}else{
            		return '';
            	}
            }
            
           /**
            * 人脸信息比对预警
            */
            function getFaceCompResult(data){
            	var comparebaseid;
            	var c;
            	var ch;
            	var t;
            	var T_QB_RY_ZTRYJBXX;
            	var similarity;
            	var photo;
            	var fi;
            	if(data.comparebaseid != null){
            		comparebaseid = data.comparebaseid;
            	}else{
            		comparebaseid = null;
            	}
            	if(data.c != null){
            		c = data.c;
            	}else{
            		c = null;
            	}
            	if(data.ch != null){
            		ch = data.ch;
            	}else{
            		ch = null;
            	}
            	if(data.t != null){
            		t = data.t;
            	}else{
            		t = null;
            	}
            	if(data.zt != null){
            		T_QB_RY_ZTRYJBXX = data.zt;
            	}else{
            		T_QB_RY_ZTRYJBXX = null;
            	}
            	if(data.similarity != null){
            		similarity = data.similarity;
            	}else{
            		similarity = null;
            	}
            	if(data.photo != null){
            		photo = data.photo;
            	}else{
            		photo = null;
            	}
            	if(data.fi != null){
            		fi = data.fi;
            	}else{
            		fi = null;
            	}
				if(fi != null){
					var fi_createTimeStr = formatDate(fi.createTime);
				}else{
					var	fi_createTimeStr = "";
				}
				var ic = data.ic;
				if(ic !=null){
					var ic_createTimeStr = formatDate(ic.createTime);
				}else{
					var ic_createTimeStr = "";	
				}
				var faceCode = data.faceCode;
				var ajlx = data.ajlx;
				if(null == similarity){
					$("#faceComp").html();
					if(fi!=null){
						$("#faceInfo").html('<img src="data:image/jpg;base64,'+ (fi.collectPic==null?"":fi.collectPic) + '" width="98" height="124" alt="">');
					}
					
					$("#faceCollectTime").html(fi_createTimeStr);
					//$("#faceInfoC").html('<img src="../img/collect-fail.png" width="98" height="124" alt="">');
					$("#faceInfoCTime").html();
					$("#faceInfoComp").html();
				}else{
				if(null == comparebaseid){
					$("#faceComp").html();
					$("#faceInfo").html('<img src="data:image/jpg;base64,'+ (fi.collectPic==null?"":fi.collectPic) + '" width="98" height="124" alt="">');
					$("#faceCollectTime").html(fi_createTimeStr);
					//$("#faceInfoC").html('<img src="../img/collect-fail.png" width="98" height="124" alt="">');
					$("#faceInfoCTime").html();
					$("#faceInfoComp").html();
				}else if(comparebaseid == "1"){
					if(null == T_QB_RY_ZTRYJBXX){
						$("#faceComp").html();
						$("#faceInfo").html('<img src="data:image/jpg;base64,'+ (fi.collectPic==null?"":fi.collectPic) + '" width="98" height="124" alt="">');
						$("#faceCollectTime").html(fi_createTimeStr);
						//$("#faceInfoC").html('<img src="../img/collect-fail.png" width="98" height="124" alt="">');
						$("#faceInfoCTime").html();
						$("#faceInfoComp").html();
					}else{
						$("#faceComp").html('<img src="data:image/jpg;base64,'+ (data.zt.photoStr==null?"":data.zt.photoStr) + '" width="98" height="124" alt="">');
						$("#faceInfo").html('<img src="data:image/jpg;base64,'+ (fi.collectPic==null?"":fi.collectPic) + '" width="98" height="124" alt="">');
						$("#faceCollectTime").html(fi_createTimeStr);
						if(ic!=null){
							$("#faceInfoC").html('<img src="data:image/jpg;base64,'+ (ic.idCardPic==null?"":ic.idCardPic) + '" width="98" height="124" alt="">');
							$("#faceInfoCTime").html(ic_createTimeStr);	
						}else{
							$("#faceInfoC").html('<img src="../img/collect-fail.png" width="98" height="124" alt="">');
						}
						$("#faceInfoComp").html(
							'<ul class="contrast-sepc">'+
								'<li>姓名: ' + T_QB_RY_ZTRYJBXX.xm + '</li>'+
								'<li>性别: ' + changeSex(T_QB_RY_ZTRYJBXX.xb) + '</li>'+
								'<li>身份证号:' + T_QB_RY_ZTRYJBXX.ysfzh + '</li>'+
								'<li class="strong">人脸相似度: ' + similarity + '%</li>'+
								'<li><span class="warning f18">比中在逃人员</span></li>'+
								'<li class="strong">案件：<span class="warning">' + ajlx.context + '</span></li>'+
							'</ul>'
							);
					}
				}else if(comparebaseid == "0"){
					if(null == t){
						$("#faceComp").html();
						$("#faceInfo").html('<img src="data:image/jpg;base64,'+ (fi.collectPic==null?"":fi.collectPic) + '" width="98" height="124" alt="">');
						$("#faceCollectTime").html(fi_createTimeStr);
						//$("#faceInfoC").html('<img src="../img/collect-fail.png" width="98" height="124" alt="">');
						$("#faceInfoCTime").html();
						$("#faceInfoComp").html();
					}else{
						$("#faceComp").html('<img src="data:image/jpg;base64,'+ (t.photoStr==null?"":t.photoStr) + '" width="98" height="124" alt="">');
						$("#faceInfo").html('<img src="data:image/jpg;base64,'+ (fi.collectPic==null?"":fi.collectPic) + '" width="98" height="124" alt="">');
						$("#faceCollectTime").html(fi_createTimeStr);
						if(ic!=null){
							$("#faceInfoC").html('<img src="data:image/jpg;base64,'+ (ic.idCardPic==null?"":ic.idCardPic) + '" width="98" height="124" alt="">');
							$("#faceInfoCTime").html(ic_createTimeStr);	
						}else{
							$("#faceInfoC").html('<img src="../img/collect-fail.png" width="98" height="124" alt="">');
						}
						
						$("#faceInfoComp").html(
							'<ul class="contrast-sepc">'+
								'<li>姓名: ' + t.bbkrxm + '</li>'+
								'<li>性别: ' + changeSex(t.bbkrxb) + '</li>'+
								'<li>身份证号:' + t.bbkrzjhm + '</li>'+
								'<li class="strong">人脸相似度: ' + similarity + '%</li>'+
								'<li><span class="warning f18">比中临控人员</span></li>'+
								'<li class="strong">案件：<span class="warning">临控指令级别' + t.lkzljb + '</span></li>'+
							'</ul>'
							);
					}
				}else{
					if(null == c){
						$("#faceComp").html();
						$("#faceInfo").html('<img src="data:image/jpg;base64,'+ (fi.collectPic==null?"":fi.collectPic) + '" width="98" height="124" alt="">');
						$("#faceCollectTime").html(fi_createTimeStr);
						//$("#faceInfoC").html('<img src="../img/collect-fail.png" width="98" height="124" alt="">');
						$("#faceInfoCTime").html();
						$("#faceInfoComp").html();
					}else{
						if(ic!=null){
							$("#faceInfoC").html('<img src="data:image/jpg;base64,'+ (ic.idCardPic==null?"":ic.idCardPic) + '" width="98" height="124" alt="">');
							$("#faceInfoCTime").html(ic_createTimeStr);	
						}else{
							$("#faceInfoC").html('<img src="../img/collect-fail.png" width="98" height="124" alt="">');
						}
						$("#faceComp").html('<img src="data:image/jpg;base64,'+ (c.photoStr==null?"":c.photoStr) + '" width="98" height="124" alt="">');
						$("#faceInfo").html('<img src="data:image/jpg;base64,'+ (fi.collectPic==null?"":fi.collectPic) + '" width="98" height="124" alt="">');
						$("#faceCollectTime").html(fi_createTimeStr);
						$("#faceInfoComp").html(
							'<ul class="contrast-sepc">'+
								'<li>姓名: ' + c.xm + '</li>'+
								'<li>性别: ' + changeSex(c.xb) + '</li>'+
								'<li>身份证号:' + c.sfzh + '</li>'+
								'<li class="strong">人脸相似度 :' + similarity + '%</li>'+
								'<li><span class="warning f18">比中常控人员</span></li>'+
								'<li class="strong">案件：<span class="warning">' + ch + '</span></li>'+
							'</ul>'
							);
					}
				}
				}
				/**
				 * 预警时关闭长定时
				 */
				if(faceCode !=""){
				if($("#faceCode").val()==""){
					$("#faceCode").val(faceCode);
					if($("#faceCode").val()!=""){
						timeOutFaceFlag = true;
						countFace = 0;
						longTimeOutFaceFlag = false;
						longCountFace = 0;
					}
	           }else{
					if($("#faceCode").val()!=faceCode){
						timeOutFaceFlag = true;
						countFace = 0;
						$("#faceCode").val(faceCode);
						longTimeOutFaceFlag = false;
						longCountFace = 0;
					}
	           }
            }
            }
            
            /**
             * 身份证预警
             * @param data
             */         
            function getCardInfo(data){
            	if(data.idCardCompWarn==null){
            		return;
            	}
                if(data.idCardCompWarn.idcardpicStr != ""){
               	 	$("#cardinfo").html('<img src="data:image/jpg;base64,'+ data.idCardCompWarn.idcardpicStr + '" width="98" height="124" alt="">');
                }
                if(data.idCardCompWarn.collectPicStr != ""){
               	 	$("#faceCardInfo").html('<img src="data:image/jpg;base64,'+ data.idCardCompWarn.collectPicStr + '" width="98" height="124" alt="">');
                }else{
                	$("#faceCardInfo").html('<img src="../img/collect-fail.png;" width="98" height="124" alt="">');
                }
                if(data.idCardCompWarn.photoStr != null){
                    $("#cardInfoComp").html('<img src="data:image/jpg;base64,'+ data.idCardCompWarn.photoStr + '" width="98" height="124" alt="">');
                }
	           if(data.idCardCompWarn != null){
		           $("#idCardNo").html(data.idCardCompWarn.collectIdCardNo);
		           $("#idCollectTimeStr").html(data.idCardCompWarn.idCollectTimeStr);
		           if(data.idCardCompWarn.collectTimeStr!=null){
			           $("#cardInfoFTime").html(data.idCardCompWarn.collectTimeStr);
			       }
	           
	           }
	           if(data.idCardCompWarn.tableName == 'T_QB_RY_ZTRYJBXX'){
	           $("#cardInfoPhoto").html('<ul class="contrast-sepc">' +
	           '<li>姓名:'+data.idCardCompWarn.xm+'<li>' +
	           '<li>性别:'+data.idCardCompWarn.xb+'<li>' +
	           '<li>身份证号:'+data.idCardCompWarn.idCardNo+'<li>' +
	           '<li class="strong">身份证比中<li>' +
	           '<li><span class="warning f18">比中在逃人员</span><li>' +
	           '<li class="strong">案件：<span class="warning">'+data.idCardCompWarn.ajlx+'</span><li>' +
	           '</ul> ');
	           }else if(data.idCardCompWarn.tableName == 'T_QB_LK_LKBK'){
	        	   $("#cardInfoPhoto").html('<ul class="contrast-sepc">' +
	        	           '<li>姓名:'+data.idCardCompWarn.xm+'<li>' +
	        	           '<li>性别:'+data.idCardCompWarn.xb+'<li>' +
	        	           '<li>身份证号:'+data.idCardCompWarn.idCardNo+'<li>' +
	        	           '<li class="strong">身份证比中<li>' +
	        	           '<li><span class="warning f18">比中临控人员</span><li>' +
	        	           '<li class="strong">临控类型：<span class="warning">'+data.idCardCompWarn.ajlx+'</span><li>' +
	        	           '</ul> ');
	           }else if(data.idCardCompWarn.tableName == 'T_QB_RY_ZTRYJBXX'){
	        	   $("#cardInfoPhoto").html('<ul class="contrast-sepc">' +
	        	           '<li>姓名:'+data.idCardCompWarn.xm+'<li>' +
	        	           '<li>性别:'+data.idCardCompWarn.xb+'<li>' +
	        	           '<li>身份证号:'+data.idCardCompWarn.idCardNo+'<li>' +
	        	           '<li class="strong">身份证比中<li>' +
	        	           '<li><span class="warning f18">比中常控人员</span><li>' +
	        	           '<li class="strong">常控类型：<span class="warning">'+data.idCardCompWarn.ajlx+'</span><li>' +
	        	           '</ul> ');
	           }
	           /**
	            * 新值来了之后，预警标志为true，准备预警，关闭长定时，当预警完毕后开启长定时
	            */
	           if($("#idCardCompareResult").val()==""){
					$("#idCardCompareResult").val(data.idCardCompWarn.cardCode);
					timeOutIdCardFlag = true;
					countIdCard = 0;
					longTimeOutIdCardFlag = false;
					longCountIdCard = 0;
	           }else{
					if($("#idCardCompareResult").val()!=data.idCardCompWarn.cardCode){
						timeOutIdCardFlag = true;
						countIdCard = 0;
						longTimeOutIdCardFlag = false;
						longCountIdCard = 0;
						$("#idCardCompareResult").val(data.idCardCompWarn.cardCode);
					}
	           }
           }
            /**
             * 得到采集数
             * @param data
             */
            function getCollectCount(data){
            	$("#id_card_count").html(data.idCardCount);
            	$("#face_count").html(data.faceCount);
            }
           
            /**
             * 得到预警数
             * @param data
             */
            function getWarningCount(data){
            	$("#idcard_warns").html(data.idCardComWarnNum);
            	$("#face_warns").html(data.faceComWarnNum);
            	$("#fc_warns").html(data.FCWarnNum);
            };

            // yyyy年mm月dd日
            function formatBDate(date){
            	if(!date){
            		return '';
            	}else{
            		return date.substring(0, 4)+'年'+date.substring(4, 6)+'月'+date.substring(6, 8)+'日';
            	}
            }
            
            // 性别转换模式 '1': 男      其他: 女
            function changeSex(sex){
            	if(sex == '男'){
            		return '男';
            	}else if(sex == '女'){
            		return '女';
            	}else if(sex == '1'){
            		return '男';
            	}else if(sex == '2'){
            		return '女';
            	}else{
            		return '';
            	}
            }



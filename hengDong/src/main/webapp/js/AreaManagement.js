            function refresh(){
            	$("#regionName1-error").html('');
            	$("#regionCode1-error").html('');
            	$("#preRegionCode1-error").html('');
            	$("#regionLevel1-error").html('');
            	$("#regionNameCheck").css("display","none");
            	$("#regionCodeCheck").css("display","none");
            	$("#preRegionCodeCheck").css("display","none");
            	$("#regionLevelCheck").css("display","none");
            }
            
            function ref(){
            	$("#rName1-error").html('');
            	$("#rCode1-error").html('');
            	$("#preRcode1-error").html('');
            	$("#rLevel1-error").html('');
            }
            
            function cleanadd(){
            	originalAreaName = "";
            	$("#regionName1").val('');
            	$("#regionCode1").val('');
            	$("#preRegionCode1").val('');
            	$("#regionLevel1").val('');
            	$("#description").val('');
            	$("#remarkDesc").val('');
            }
            
            
            function saveAreaManage()
            {
            	if($("#addNewArea").valid()){
	            	$.post("addReg.do",{
	            		regionName : $("#regionName1").val(),
	            		regionCode : $("#regionCode1").val(),
	            		preRegionCode : $("#preRegionCode1").val(),
	            		regionLevel : $("#regionLevel1").val(),
	            		description : $("#description").val(),
	            		remarkDesc:$("#remarkDesc").val()
	            	}, function(date){
	            		var page = $("#page").val();
		        		regionCode = $("#regionCode").val();
			        	regionName = $("#regionName").val();
		        		getRegionAjax(page,regionCode,regionName)
			        	$("#page").val(page);
	                 });
            	}
            }
            
            function checkRegionName()
            {
            	var regionName = $("#regionName1").val();
            	if (isValueEmpty(regionName))
        		{
            		$("#regionNameCheck").css("display","");
            		$("#regionNameCheck").html("请输入辖区名称");
            		return false;
        		}
            	else
            	{
            		if (regionName.length>20)
            		{
            			$("#regionNameCheck").css("display","");
            			$("#regionNameCheck").html("辖区名称不能超过20位");
            			return false;
            		}
            		else if (!checkReg(regionName))
            		{
            			$("#regionNameCheck").css("display","");
            			$("#regionNameCheck").html("辖区名称不能重复");
            			return false;
            		}
            		else
            		{
            			$("#regionNameCheck").css("display","none");
            			$("#regionNameCheck").html("");
            			return true;
            		}
            	}
            }
            
            //判断辖区名称是否重复
            function checkReg(value)
            {
				var reg = 0;
				originalAreaName;
			
				for (var i = 0; i < regName.length; i++) {
					if (regName[i].regionName != value) {
						reg = reg + 1;
					} else {
						reg = reg - 1;
					}
				}
				if (value == originalAreaName) {
					reg = reg - 1;
				}
				if (reg == regName.length) {
					return true;
				} else if (value == originalAreaName) {
					return true;
				} else {
					return false;
				}
			}
                        
            function isValueEmpty(value)
            {
            	if (null == value || '' == value || undefined == value)
        		{
            		return true;
        		}
            	return false;
            }
            
            var regName;
            $.post("getReg.do", function(date){
            	regName = date;
            });

			$(function() {
            	//判断中文
            	jQuery.validator.methods.checkname = function (value,element,param){
					var reg = "^[\u4e00-\u9fa5]+$";
					var r = value.match(reg);
					if(r){
						return true;
					}else{
						return false;
					}
				};
				//判断数字
				jQuery.validator.methods.checknumber = function (value,element,param){
					var reg = "^[0-9]+$";
					var r = value.match(reg);
					if(r){
						return true;
					}else{
						return false;
					}
				};
				//判断英文字母和数字
				jQuery.validator.methods.checkengandnum = function (value,element,param){
					var reg = "^[A-Za-z0-9]+$";
					var r = value.match(reg);
					if(r){
						return true;
					}else{
						return false;
					}
				};
				
				//判断英文字母和数字或者未空
				jQuery.validator.methods.checkengandnumorempty = function (value,element,param){
					var reg = "^[A-Za-z0-9]+$";
					if (value == null || value == '')
					{
						return true;
					}
					var r = value.match(reg);
					if(r){
						return true;
					}else{
						return false;
					}
				};
				
				//判断辖区名称是否重复
				jQuery.validator.methods.checkreg = function (value,element,param){
					var reg = 0;
					originalAreaName;
					
					for(var i=0;i<regName.length;i++){
						if(regName[i].regionName != value){
							reg = reg + 1;
						}else{
							reg = reg - 1;
						}
					}
					if (value == originalAreaName)
					{
						reg = reg - 1;
					}
					if(reg == regName.length){
						return true;
					}
					else if (value == originalAreaName)
					{
						return true;
					}
					else{
						return false;
					}
				}
			});
			
			var validator;
			$(function() {
				validator = $("#addNewArea").validate({
					onfocusout : function(element) {
						$(element).valid();
					},
					rules : {
						regionName1 : {
							required : true,
							checkreg : "regionName1",
							maxlength : 20
						},
						regionCode1 : {
							required : true,
							checkengandnum : "#regionCode1",
							maxlength : 30
						},
						preRegionCode1 : {
							checkengandnumorempty : "#preRegionCode1",
							maxlength : 30
						},
						regionLevel1 : {
							checkengandnumorempty : "#regionLevel1",
							maxlength : 2
						}
					},
					messages : {
						regionName1 : {
							required : "请输入辖区名称",
							checkreg : "辖区名称不能重复",
							maxlength : "辖区名称不能超过20位"
						},
						regionCode1 : {
							required : "请输入辖区编号",
							checkengandnum : "辖区编号只能为英文字母和数字",
							maxlength : "辖区编号不能超过30位"
						},
						preRegionCode1 : {
							checkengandnumorempty : "上级辖区编号只能为英文字母和数字",
							maxlength : "上级辖区编号不能超过30位"
						},
						regionLevel1 : {
							checkengandnumorempty : "级别只能为英文字母和数字",
							maxlength : "级别不能超过2位"
						}
					}
				});
            });
			
			//编辑辖区验证
			$(function() {
				validator = $("#editformReg").validate({
					onfocusout : function(element) {
						$(element).valid();
					},
					rules : {
						rName1 : {
							required : true,
							checkreg : "#regionName1",
							maxlength : 20
						},
						rCode1 : {
							required : true,
							checkengandnum : "#regionCode1",
							maxlength : 30
						},
						preRcode1 : {
							checkengandnumorempty : "#preRegionCode1",
							maxlength : 30
						},
						rLevel1 : {
							checkengandnumorempty : "#regionLevel1",
							maxlength : 2
						}
					},
					messages : {
						rName1 : {
							required : "请输入辖区名称",
							checkreg : "辖区名称不能重复",
							maxlength : "辖区名称不能超过20位"
						},
						rCode1 : {
							required : "请输入辖区编号",
							checkengandnum : "辖区编号只能为英文字母和数字",
							maxlength : "辖区编号不能超过30位"
						},
						preRcode1 : {
							checkengandnumorempty : "上级辖区编号只能为英文字母和数字",
							maxlength : "上级辖区编号不能超过30位"
						},
						rLevel1 : {
							checkengandnumorempty : "级别只能为英文字母和数字",
							maxlength : "级别不能超过2位"
						}
					}
				});
            });

			$(function(){
                $('.fancybox').fancybox();
            })
	        function closeviewRegion(){
        		$('[data-remodal-id="viewRegionInfo"]').remodal().close();
            }
			
				$(function(){
		        	init();
		        });
				function init(){
					getRegionAjax(1);
		        	$("#page").val("1");	
				}
		        var regionCode = "";
		        var regionName = "";
		        var regionLevel = "";
		        var preRegionCode = "";
		        var page;
		        function getRegion(){
		        	page = 1;
		        	$("#page").val("1");
		        	regionCode = $("#regionCode").val();
		        	regionName = $("#regionName").val();
		        	getRegionAjax(page,regionCode,regionName);
		        }
		        
		        $(function(){
		        	var maxpage;
	                $("#page").blur(function(){
	                	var page = $("#page").val();
	                	if(page > maxpage){
	                		page = maxpage;
	                		$("#page").val(page);
	                	}
			        	regionCode = $("#regionCode").val();
			        	regionName = $("#regionName").val();
	                	getRegionAjax(page,regionCode,regionName);
	                });
		        });
		        
		        function getPrePage(){
	            	var page = $("#page").val();
	            	if( page > 1){
	            		$("#page").val(eval(page+"-1"));
			        	regionCode = $("#regionCode").val();
			        	regionName = $("#regionName").val();
	            		getRegionAjax($("#page").val(),regionCode,regionName);
	            	}
	            }

	            function getNextPage(){
	            	var page = $("#page").val();
	            	if( page < maxpage){
	            		$("#page").val(eval(page+"+1"));
			        	regionCode = $("#regionCode").val();
			        	regionName = $("#regionName").val();
	            		getRegionAjax($("#page").val(),regionCode,regionName);
	            	}
	            }
	            
	            function getPage(page,regionCode,regionName,regionLevel,preRegionCode ){
	            	$("#page").val(page);
		        	regionCode = $("#regionCode").val();
		        	regionName = $("#regionName").val();
	            	getRegionAjax(page,regionCode,regionName);
	            }
	            
	            function getLastPage(){
	            	$("#page").val(maxpage);
		        	regionCode = $("#regionCode").val();
		        	regionName = $("#regionName").val();
	            	getRegionAjax(maxpage,regionCode,regionName);
	            }
	            //删除
	            function deleteRegion(regionId, count) {
	            	
	            	if (count == 0)
	            	{
	            		$("#deleteTips").html("是否确定删除信息");
	            		$('[data-remodal-id="deleteConfirm"]').remodal().open();
	                 	$('[data-remodal-action="confirm"]').click(function() {
	                 		$.post("deleteRegion.do?regionId="+regionId, function(data) {
	                 			window.location.reload(true);
	                 		});
	                 	})
	            	}
	            	else
	            	{
	            		$("#showMsgTips").children().each(function(index) {
	            			$(this).remove();
	            		});
	            		
	            		$("#showMsgTips").append("<p id='msgTips' style='text-align:center'>辖区有关联设备不能删除！</p>" + 
	            				"<div class='btn-group'>" + 
	            					"<button data-remodal-action='cancel' class='btn btn-gray'>确定</button></div>");
	            		$('[data-remodal-id="deleteConfirm"]').remodal().open();
	            	}
	           
	            }
    	
	            //查看
	            function getRegionInfo(regionId){
	            	$.post("getRegionInfo.do",{
	            		regionId : regionId
	            	},function(data){
	            		/*$("#rID").val(data.regionId);*/
	            		$("#rCode").val(data.regionCode);
	            		$("#rName").val(data.regionName);
	            		$("#rLevel").val(data.regionLevel);
	            		$("#preRcode").val(data.preRegionCode);
	            		$("#desc").val(data.description);
	            		$("#desc").attr("title",data.description)
	            		$("#remk").val(data.remarkDesc);
	            		$("#remk").attr("title",data.remarkDesc)
	            		$("#del").val(data.deleteStatus);
	            		$("#cn").val(data.createName);
	            		$("#ct").val(data.createTime);
	            		$("#un").val(data.updateName);
	            		$("#ut").val(data.updateTime);
	            		$("#del").val(data.deleteStatus);
	            		
	            		$('[data-remodal-id="viewRegionInfo"]').remodal().open();
	              		});
	            }
	            
	            function closeview(){
	        		$('[data-remodal-id="viewRegionInfo"]').remodal().close();
	            }
	            
	            var originalAreaName;
	            //编辑
	            function getRegionInfo1(regionId){
	            	$.post("getRegionInfo.do",{
	            		regionId : regionId
	            	},function(data){
	            		$("#rID").val(regionId);
	            		$("#rCode1").val(data.regionCode);
	            		$("#rName1").val(data.regionName);
	            		originalAreaName = data.regionName;
	            		$("#rLevel1").val(data.regionLevel);
	            		$("#preRcode1").val(data.preRegionCode);
	            		$("#desc1").val(data.description);
	            		$("#remk1").val(data.remarkDesc);
	            		$("#del1").val(data.deleteStatus);
	            		$("#un1").val(data.updateName);
	              		});
	            }
	            
	            var region;
	            var count;
	            function getRegionAjax(page,regionCode,regionName){
	            	$.post("getRegion.do",{
	            		page : page,
	            		regionCode : regionCode,
	            		regionName : regionName,
	            	},function(data){
	            		region = data.list;
	            		count = data.count;
	            		maxpage = data.pages;
	            		$("#faceInfoCount").html("");
	            		$("#areaManagement").children().each(function(index){
	              			$(this).remove();
	              		});
	            		  if(count != 0){
	                      	$("#faceInfoCount").append("共"+count+"条 每页10条"+" 页次"+page+"/"+maxpage);
	                      }else{
	                    	$("#areaManagement").append('<tr><td colspan=6>暂无数据</td></tr>');
	                      	$("#page").val("1");
	                      	$("#faceInfoCount").append("共"+count+"条 每页10条"+" 页次1/1");
	                      }

    		  for(var i=0;i<region.length;i++){
            	var tr1;
            	var tr2;
            	tr1 = "<tr>"+	
                "<td>"+region[i].regionCode+"</td>"+
	            "<td>"+region[i].regionName+"</td>"+
	            "<td>"+region[i].regionLevel+"</td>"+
	            "<td>"+region[i].preRegionCode+"</td>"+
	            "<td>"+region[i].deviceCount+"</td>"
	            if(region[i].status == "0"){
	            	tr1 = "<td><a href='#edit-AreaManageMent' class='table-link fancybox' onclick='getRegionInfo1("+ region[i].regionId +")'>编辑</a>" +
	                      "<a href='#view-UserManageMent' class='table-link' onclick='getRegionInfo("+ region[i].regionId +")'>查看</a>" +
	                      "<a href='#' class='table-link c-danger' onclick='deleteRegion("+ region[i].regionId + "," + region[i].deviceCount +")'>删除</a></tr>"
	            }else{
	            	tr2 = "<td><a href='#edit-AreaManageMent' class='table-link fancybox' onclick='getRegionInfo1("+ region[i].regionId +")'>编辑</a>" +
	                      "<a href='#view-UserManageMent' class='table-link' onclick='getRegionInfo("+ region[i].regionId +")'>查看</a>" +
	                      "<a href='#' class='table-link c-danger' onclick='deleteRegion("+ region[i].regionId+ "," + region[i].deviceCount +")'>删除</a></tr>"
	            }
				$("#areaManagement").append(tr1+tr2);
            }
              });
            }
	            
	        function modifyRegionSubmit()
	        {
	        	if($("#editformReg").valid()){
	        		$.post("editRegion.do",{
	        			rCode1  :$("#rCode1").val(),
	        			rID  :$("#rID").val(),
	        			rName1  :$("#rName1").val(),
	        			rLevel1 :$("#rLevel1").val(),
	        			preRcode1:$("#preRcode1").val(),
	        			desc1 :$("#desc1").val(),
	        			remk1 :$("#remk1").val()
		        	},function(data)
		        	{
		        		var page = $("#page").val();
		        		regionCode = $("#regionCode").val();
			        	regionName = $("#regionName").val();
		        		getRegionAjax(page,regionCode,regionName)
			        	$("#page").val(page);
		        	}); 
        		}
	        }
			        	
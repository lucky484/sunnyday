            function refresh(){
            	$("#addUserName-error").html('');
            	$("#addPassword-error").html('');
            	$("#addIdCardNm-error").html('');
            	$("#addPoliceNm-error").html('');
            	$("#addName-error").html('');
            	$("#addSex-error").html('');
            	$("#addDept-error").html('');
            	$("#addUserRole-error").html('');
            }
            
            function checkAddUserName()
            {
            	var userName = $("#addUserName").val();
            	
            	if (null == userName || '' == userName || undefined == userName)
            	{
            		return;
            	}
            	$.post("checkUserName.do",{
            		userName : userName
	        	},function(data)
	        	{
	        		var isExist = data.isExist;
	        		if (isExist)
	        		{
	        			$("#addUserNameExist").css("display","");
	        			return false;
	        		}
	        		$("#addUserNameExist").css("display","none");
	        		return true;
	        	});
            }
            
            function ref(){
            	$("#modifyUserName-error").html('');
            	$("#modifyPassword-error").html('');
            	$("#modifyIdCardNm-error").html('');
            	$("#modifyPoliceNm-error").html('');
            	$("#modifyName-error").html('');
            	$("#modifySex-error").html('');
            	$("#modifyDept-error").html('');
            	$("#modifyUserRole-error").html('');
            }
            
            function cleanadd(){
	        	$("#addUserName").val('');
	        	$("#addPassword").val('');
	        	$("#addName").val('');
	        	$("#addsex").val('');
	        	$("#addPoliceNm").val('');
	        	$("#addIdCardNm").val('');
	        	$("#addUserRole").val('');
	        	$("#addRemarkDesc").val('');
	        	
	        	$.post("getUserSeletedInfo.do",{
	        	},function(data)
	        	{
	        		var roleModels = data.roleModels;
	        		var instutionInfoModels = data.instutionInfoModels;
	        		
	        		$("#addDept").children().each(function(index) {
	        			$(this).remove();
	        		});
	        		$("#addUserRole").children().each(function(index) {
	        			$(this).remove();
	        		});
	        		
	        		var roleOptions = "<option value='' select='selected'>请选择</option>";
	        		for (var i = 0; i < roleModels.length; i ++)
	        		{
	        			$("#addUserRole").append("<option value="+ roleModels[i].roleID + ">" + roleModels[i].roleName + "</option>");
	        		}
	        		$("#addUserRole").append(roleOptions);
	        		$("#addUserRole").val('');
	        		
	        		var deptOptions = "<option value='' select='selected'>请选择</option>";
	        		for (var i = 0; i < instutionInfoModels.length; i ++)
	        		{
	        			$("#addDept").append("<option value="+ instutionInfoModels[i].institutionCode + ">" + instutionInfoModels[i].institutionName + "</option>");
	        		}
	        		$("#addDept").append(deptOptions);
	        		$("#addDept").val('');
	        	});
            }

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
				var validator;
				//判断中文
            	jQuery.validator.methods.checkNameValidator = function (value,element,param){
            		$.ajax({  
            	        url : 'checkUserName.do',  
            	        async : false, // 注意此处需要同步，因为返回完数据后，下面才能让结果的第一条selected  
            	        type : "POST",  
            	        data : {
            	        	userName : value
            	        },
            	        success : function(data) {  

		            		var isExist = data.isExist;
		            		if (isExist)
		            		{
		            			validator = false;
		            		}
		            		else
	            			{
		            			validator = true;
	            			}  
            	        }  
            	    });  
            		
					return validator;
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
			});
			
			var validator;
			$(function() {
				validator = $("#addUserForm").validate({
					onfocusout : function(element) {
						$(element).valid();
					},
					rules : {
						addUserName : {
							required : true,
							checkNameValidator : "#addUserName",
							maxlength : 30
						},
						addPassword : {
							required : true,
							checkengandnum : "#addPassword",
							maxlength : 30
						},
						addIdCardNm : {
							required : true,
							checkengandnum : "#addIdCardNm",
							maxlength : 18
						},
						addPoliceNm : {
							required : true,
//							checkname : "#addPoliceNm",
							maxlength : 20
						},
						addName : {
							required : true,
							maxlength : 10
						},
						addSex : {
							required : true
						},
						addRemarkDesc : {
							maxlength : 1500
						},
						addDept : {
							required : true
						},
						addUserRole: {
							required : true
						}
					},
					messages : {
						addUserName: {
							required : "请输入用户名",
							checkNameValidator : "用户名已经存在",
							maxlength : "用户名不能超过30位",
						},
						addPassword : {
							required : "请输入密码",
							checkengandnum : "密码只能为英文字母和数字",
							maxlength : "密码不能超过30位"
						},
						addIdCardNm : {
							required : "请输身份证号码",
							checkengandnum : "身份证号只能为英文字母和数字",
							maxlength : "身份证号不能超过18位"
						},
						addPoliceNm : {
							required : "请输警员编号",
							//checkengandnum : "身份证只能为英文字母和数字",
							maxlength : "警员不能超过18位"
						},
						addName : {
							required : "请输入姓名",
							maxlength : "姓名不能超过10位"
						},
						addSex : {
							required : "请选择性别",
						},
						addRemarkDesc : {
							maxlength : "备注不能超过1500位"
						},
						addDept : {
							required : "请选择组织机构"
						},
						addUserRole: {
							required : "请选择一个角色"
						}
					}
				});
            });
			
			$(function() {
				$("#updateformId").validate({
					onfocusout : function(element) {
						$(element).valid();
					},
					rules : {
						modifyPassword : {
							required : true,
							maxlength : 30
						},
						modifyIdCardNm : {
							required : true,
							checkengandnum : "#modifyIdCardNm",
							maxlength : 18
						},
						modifyPoliceNm : {
							required : true,
							maxlength : 20
						},
						modifyName : {
							required : true,
							maxlength : 10
						},
						modifySex : {
							required : true,
						},
						modifyRemarkDesc : {
							maxlength : 1500
						},
						modifyDept : {
							required : true,
						},
						modifyUserRole : {
							required : true
						}
					},
					messages : {
						modifyPassword : {
							required : "请输入密码",
							maxlength : "密码不能超过30位"
						},
						modifyIdCardNm : {
							required : "请输入身份证号",
							checkengandnum : "身份证号只能为英文字母和数字",
							maxlength : "身份证号不能超过18位"
						},
						modifyPoliceNm : {
							required : "请输警员编号",
						},
						modifyName : {
							required : "请输入姓名",
							maxlength : "姓名不能超过10位"
						},
						modifySex : {
							required : "请选择性别",
						},
						modifyRemarkDesc : {
							maxlength : "备注不能超过1500位"
						},
						modifyDept : {
							required : "请选择组织机构"
						},
						modifyUserRole : {
							required : "请选择用户角色"
						}
				}});
            });

             $(function(){
                $('.fancybox').fancybox();
            })
			
	        $(function(){
	        	getAjax(1);
	        	$("#page").val("1");
	        });

	        var diId = "";
	        var username = "";
	        var name = "";
	        var department = "";
	        
	        function getParameter(){
	        	diId = $("#diId").val();
	        	username = $("#username").val();
	        	name = $("#name").val();
	        	department = $("#department").val(); 
	        }

	        
	        $("#search").click(function() {
	        	var page = 1;
	        	$("#page").val("1");
	        	diId = $("#diId").val();
	        	username = $("#username").val();
	        	name = $("#name").val();
	        	department = $("#department").val();
	        	getAjax(page,diId,username,name,department);
	        });
	        
	        $(function(){
	        	var maxpage;
                $("#page").blur(function(){
                	var page = $("#page").val();
                	if(page > maxpage){
                		page = maxpage;
                		$("#page").val(page);
                	}
    	        	diId = $("#diId").val();
    	        	username = $("#username").val();
    	        	name = $("#name").val();
    	        	department = $("#department").val(); 
                	getAjax(page,diId,username,name,department);
                });
	        }); 
	        
	        function getPrePage(){
            	var page = $("#page").val();
            	//alert(maxpage);
            	if( page > 1){
            		$("#page").val(eval(page+"-1"));
    	        	diId = $("#diId").val();
    	        	username = $("#username").val();
    	        	name = $("#name").val();
    	        	department = $("#department").val(); 
            		getAjax($("#page").val(),diId,username,name,department);
            		//$("#hiddenPage").val($("#page").val());
            	}
            }

            function getNextPage(){
            	var page = $("#page").val();
            	if( page < maxpage){
            		$("#page").val(eval(page+"+1"));
    	        	diId = $("#diId").val();
    	        	username = $("#username").val();
    	        	name = $("#name").val();
    	        	department = $("#department").val(); 
            		getAjax($("#page").val(),diId,username,name,department);
            		//$("#hiddenPage").val($("#page").val());
            	}
            }
            
            function getPage(page,diId,username,name,department){
            	$("#page").val(page);
	        	diId = $("#diId").val();
	        	username = $("#username").val();
	        	name = $("#name").val();
	        	department = $("#department").val(); 
            	getAjax(page,diId,username,name,department);
            }
            
            function getLastPage(){
            	$("#page").val(maxpage);
	        	diId = $("#diId").val();
	        	username = $("#username").val();
	        	name = $("#name").val();
	        	department = $("#department").val(); 
            	getAjax(maxpage,diId,username,name,department);
            }
            
            function deleteUser(diId) {
            	$('[data-remodal-id="deleteConfirm"]').remodal().open();
            	$('[data-remodal-action="confirm"]').click(function() {
            		$.post("delete.do?diId="+diId, function(data) {
            			if (data.result == 1) {
            				$('[data-remodal-id="deleteConfirm"]').remodal().close();
            			}
            		}, 'json');
            		var page = $("#page").val();
            		getAjax(page);
            		$("#page").val(page);	
            	})
            }
            
//            function deleteUser(diId){
//            	$.post("delete.do?diId="+diId,function(data){
//            		window.location.reload(true);
//            	});
//            }
            
            function change1(diId){
            	$.post("change.do",{
            		diId : diId,
            		status : "1"
            	},function(data){
            		window.location.reload(true);
            	});
            }
            
            function change2(diId){
            	$.post("change.do",{
            		diId : diId,
            		status : "0"
            	},function(data){
            		window.location.reload(true);
            	});
            }
            
            function addNewUser()
            {
            	if($("#addUserForm").valid()){
	        		$.post("addUserManage.do",{
	        			addUserName  :$("#addUserName").val(),
	        			addPassword  :$("#addPassword").val(),
	        			addIdCardNm  :$("#addIdCardNm").val(),
	        			addPoliceNm :$("#addPoliceNm").val(),
	        			addName:$("#addName").val(),
	        			addSex :$("#addSex").val(),
	        			addRemarkDesc :$("#addRemarkDesc").val(),
	        			addDept :$("#addDept").val(),
	        			addUserRole:$("#addUserRole").val(),
		        	},function(data)
		        	{
		        		var page = $("#page").val();
		        		getAjax(page);
			        	$("#page").val(page);
		        	}); 
        		}
            }   
            
            function viewUser(diId){
            	$.post("getModifyUser.do",{
            		diId : diId
            	},function(data){
            		var roleModels = data.roleModels;
	        		var instutionInfoModels = data.instutionInfoModels;
	        		var institutionInfo = data.institutionInfo;
	        		var user = data.user;
	        		
		        	$("#viewUserName").val(user.userName);
					$("#viewPassword").val(user.password);
					$("#viewIdCardNm").val(user.idCardNm);
					$("#viewPoliceNm").val(user.policeNm);
					$("#viewName").val(user.name);
					$("#viewSex").val(user.sex);
					$("#viewRemarkDesc").val(user.remarkDesc);
					$("#viewDept").val(institutionInfo.institutionName);
					$("#viewUserRole").val(user.roleName);
					$("#viewLastLoginTime").val(user.lastloginDate);
					$("#viewStatus").val(user.status);
					$("#viewDeleteStatus").val(user.deleteStatus);
					$("#viewCreater").val(user.createName);
					$("#viewUpdater").val(user.updateName);
					$("#viewCreateTime").val(formatTime(user.createTime,'yyyy-MM-dd'));
					$("#viewUpdateTime").val(formatTime(user.updateTime,'yyyy-MM-dd'));
					$('[data-remodal-id="viewuser"]').remodal().open();
	        	});
            }
            
            function closeview(){
        		$('[data-remodal-id="viewuser"]').remodal().close();
            }
            
            function getModifyUser(diId){
	        	$.post("getModifyUser.do",{
	        		diId : diId
	        	},function(data)
	        	{
	        		var roleModels = data.roleModels;
	        		var instutionInfoModels = data.instutionInfoModels;
	        		var institutionInfo = data.institutionInfo;
	        		var user = data.user;
	        		
	        		$("#modifyDept").children().each(function(index) {
	        			$(this).remove();
	        		});
	        		$("#modifyUserRole").children().each(function(index) {
	        			$(this).remove();
	        		});
	        		
	        		var roleOptions = "<option value='' select='selected'>请选择</option>";
	        		for (var i = 0; i < roleModels.length; i ++)
	        		{
	        			$("#modifyUserRole").append("<option value="+ roleModels[i].roleID + ">" + roleModels[i].roleName + "</option>");
	        		}
	        		$("#modifyUserRole").append(roleOptions);
	        		
	        		var deptOptions = "<option value='' select='selected'>请选择</option>";
	        		for (var i = 0; i < instutionInfoModels.length; i ++)
	        		{
	        			$("#modifyDept").append("<option value="+ instutionInfoModels[i].institutionCode + ">" + instutionInfoModels[i].institutionName + "</option>");
	        		}
	        		$("#modifyDept").append(deptOptions);
	        		$("#modifyUserId").val(diId);
		        	$("#modifyUserName").val(user.userName);
					$("#modifyPassword").val(user.password);
					$("#modifyIdCardNm").val(user.idCardNm);
					$("#modifyPoliceNm").val(user.policeNm);
					$("#modifyName").val(user.name);
					$("#modifySex").val(user.sex);
					$("#modifyRemarkDesc").val(user.remarkDesc);
					$("#modifyDept").val(institutionInfo.institutionCode);
					$("#modifyUserRole").val(user.roleId);
	        	});
	        	
	        	
            }
            
            function updateUserSubmit()
            {
            	if($("#updateformId").valid()){
	        		$.post("updateUserManage.do",{
	        			modifyUserName  :$("#modifyUserName").val(),
        				modifyPassword  :$("#modifyPassword").val(),
        				modifyIdCardNm  :$("#modifyIdCardNm").val(),
        				modifyPoliceNm :$("#modifyPoliceNm").val(),
        				modifyName:$("#modifyName").val(),
        				modifySex :$("#modifySex").val(),
        				modifyRemarkDesc    :$("#modifyRemarkDesc").val(),
        				modifyDept :$("#modifyDept").val(),
        				modifyUserRole:$("#modifyUserRole").val(),
        				modifyUserId:$("#modifyUserId").val(),
		        	},function(data)
		        	{
		        		var page = $("#page").val();
		        		getAjax(page);
			        	$("#page").val(page);
		        	}); 
        		}
            }
            
			// 格式化时间 yyyy-mm-dd ~~~~~
            function formatDate(time){
            	if(time){
            		return time.split(".")[0];
            	}else{
            		return '';
            	}
            }
            
            var user;
            var count;
            function getAjax(page,diId,username,name,department){
            	$.post("getUser.do",{
            		page : page,
            		diId : diId,
            		username : username,
            		name : name,
            		department : department
            	},function(data){
            		user = data.user;
            		count = data.count;
            		maxpage = data.pages;
            		$("#faceInfoCount").html("");
            		$("#userMessage").children().each(function(index){
              			$(this).remove();
              		});
            
            if(count != 0){
            	$("#faceInfoCount").append("共"+count+"条 每页10条"+" 页次"+page+"/"+maxpage);
            }else{
            	$("#userMessage").append('<tr><td colspan=10>暂无数据</td></tr>');
            	$("#page").val("1");
            	$("#faceInfoCount").append("共"+count+"条 每页10条"+" 页次1/1");
            }
            
            for(var i=0;i<user.length;i++){
            	var tr1;
            	var tr2;
            	var lastloginDate = user[i].lastloginDate;
				if(lastloginDate != null){
					var lastloginDate = formatDate(lastloginDate);
				}else{
					var	lastloginDate = "";
				}
				var logoutDate = user[i].logoutDate;
				if(logoutDate !=null){
					var logoutDate = formatDate(logoutDate);
				}else{
					var logoutDate = "";	
				}
            	tr1 = "<tr>"+
	            "<td>"+getEmptyInstandValue(user[i].userName)+"</td>"+
	            "<td>"+getEmptyInstandValue(user[i].name)+"</td>"+
	            "<td>"+getEmptyInstandValue(user[i].sex)+"</td>"+
	            "<td>"+getEmptyInstandValue(user[i].roleName)+"</td>"+
	            "<td>"+getEmptyInstandValue(user[i].institutionName)+"</td>"+
	            "<td>"+getEmptyInstandValue(user[i].lastloginDate)+"</td>"
	            if(user[i].status == "0"){
	            	tr2 = "<td><span class='c-danger' href='javascript:void(0)' onclick='change1("+ user[i].diId +")'>停用</span> &nbsp;"+
		                  "<a href='#edit-UserManageMent' class='table-link fancybox' onclick='getModifyUser("+ user[i].diId +")'>编辑</a>" +
		                  "<a href='#view-UserManageMent' class='table-link' onclick='viewUser("+ user[i].diId +")'>查看</a>" +
		                  "<a href='#' class='table-link c-danger' onclick='deleteUser("+ user[i].diId +")'>删除</a></tr>"
	            }else{
	            	tr2 = "<td><span class='c-gree' href='javascript:void(0)' onclick='change2("+ user[i].diId +")'>启用</span> &nbsp;"+
		                  "<a href='#edit-UserManageMent' class='table-link fancybox' onclick='getModifyUser("+ user[i].diId +")'>编辑</a>" +
		                  "<a href='#view-UserManageMent' class='table-link' onclick='viewUser("+ user[i].diId +")'>查看</a>" +
		                  "<a href='#' class='table-link c-danger' onclick='deleteUser("+ user[i].diId +")'>删除</a></tr>"
	            }
				$("#userMessage").append(tr1+tr2);
            }
            
            });
            
          }
            
          function getEmptyInstandValue(value)
          {
        	  if (value =='' || value == null || value == 'undefined')
        	  {
        		  return "—"
        	  }
        	  else
        	  {
        		  return value;
        	  }
          }
          
          function formatTime(time, format){
        	    var t = new Date(time);
        	    var tf = function(i){return (i < 10 ? '0' : '') + i};
        	    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
        	        switch(a){
        	            case 'yyyy':
        	                return tf(t.getFullYear());
        	                break;
        	            case 'MM':
        	                return tf(t.getMonth() + 1);
        	                break;
        	            case 'mm':
        	                return tf(t.getMinutes());
        	                break;
        	            case 'dd':
        	                return tf(t.getDate());
        	                break;
        	            case 'HH':
        	                return tf(t.getHours());
        	                break;
        	            case 'ss':
        	                return tf(t.getSeconds());
        	                break;
        	        }
        	    })
        	}

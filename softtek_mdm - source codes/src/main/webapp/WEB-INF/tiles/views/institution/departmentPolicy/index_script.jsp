<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <script type="text/javascript">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    var departmentId;
    var currentPolicyId;
    var fatherPolicyId;
    var departmentName;
    var defaultPolicyId;
    var orgId;
    $(function(){
    	//页面初始化的时候加载树
    	 var defaultData = ${tree};
         $('#tree').treeview({
                color: "#428bca",
                showBorder: false,
    			collapseIcon : 'glyphicon glyphicon-chevron-down',
                data: defaultData,
              });
     	//全部展开节点或者收缩节点
 			//全部展开节点或者收缩节点
		$(".node-states").on('click', function() {
			if ($(".node-states").hasClass('active')) {
				$('#tree').treeview('collapseAll', {silent : true});
			} else {
				$('#tree').treeview('expandAll', {silent : true});
			}
		});
 		$('#tree').treeview('collapseAll', {
				silent : true
			});
 		$('#tree').treeview('selectNode', [ 0, {silent : true} ]);
         //选中树的时候  找到对应的策略，并选中
    	 $('#tree').on('nodeSelected',function(event, data) {
    		 var org_id = $("#orgId").val();
    		 departmentId = data.tags.id;  //当前部门的id
    		 departmentName = data.tags.name; //当前部门的名称
    		 fatherDepartmentId = data.tags.parent.policy.id;
    		 currentPolicyId = data.tags.policy.id;  //当前部门的策略id
    		 //fatherPolicyId = data.tags.parent.policy.id; //当前部门的父部门的策略id
    		 defaultPolicyId = $("#defaultPolicyId").val();
    		 orgId = data.tags.parent.id;
    		 if(orgId != org_id){ //如果当前部门的父部门的机构id不为null，则说明不是一级部门，从二级部门开始
	    		 //如果当前部门的策略id和父部门的策略id相同，则选中继承上级策略
	    		 if(currentPolicyId == fatherDepartmentId){
	    			 $("input[type='radio']:checked").removeAttr("checked",false);
	    			 $("#extendFather").html('<input type="radio" name="a" value="" checked /><i></i>');
	    		 }else{
		    		 $("input[type='radio']").each(function(i,item){
		    			 if(currentPolicyId == parseInt(item.value)){
		    				 $(this).parent().html('<input type="radio" name="a" checked value="'+item.value+'"><i></i>');
		    			 }else{
		    				 $(this).parent().html('<input type="radio" name="a" value="'+item.value+'"><i></i>');
		    			 }
		    		 });
	    		 }
    		 }else{  //否则就是一级部门，如果一级部门的策略id和默认策略的id相同，也显示继承上级
    			// var defaultPolicyId = $("#defaultPolicyId").val();
    		     if(currentPolicyId == parseInt(defaultPolicyId)){
    		    	 $("input[type='radio']:checked").removeAttr("checked",false);
	    			 $("#extendFather").html('<input type="radio" name="a" value="" checked /><i></i>');
    		     }else{
    		    	 $("input[type='radio']").each(function(i,item){
		    			 if(currentPolicyId == parseInt(item.value)){
		    				 $(this).parent().html('<input type="radio" name="a" checked value="'+item.value+'"><i></i>');
		    			 }else{
		    				 $(this).parent().html('<input type="radio" name="a" value="'+item.value+'"><i></i>');
		    			 }
		    		 });
    		     }
    		 }
    		/*  if($.inArray(treeId,arr) == -1){
    			 $("input[type='hidden']").each(function(i,item){
    				 $(this).siblings().prev().prev().prev().find("input").attr("checked",false);
    			 });
    		 } */
       	 });
 
         //给页面加气泡
         $("#departmentPolicy").find("tr:gt(0)").find("td:eq(2)").each(function(i,item){
        	 if(item.textContent.length > 30){
        		 var flag = item.textContent.substring(0,30);
        		 $(this).html('<td title="'+item.textContent+'">'+flag+'...</td>');
        	 }else{
        		 $(this).html('<td title="'+item.textContent+'">'+item.textContent+'</td>');
  			}
         });
    });
    
    //给部门设置策略
    $("#updatePolicy").click(function(){
    	var _this = $(this)
    	_this.attr("disabled",true);
    	if(departmentId == undefined){
    		nodes=$('#tree').treeview('getSelected');
    		for(var i=0;i<nodes.length;i++){
    		departmentId = nodes[i].tags.id;
    		currentPolicyId = nodes[i].tags.policy.id;
    		departmentName = nodes[i].tags.name;
    		}
    	}
    	 if($("input[type='radio']").is(":checked") == true){
    		 var id = $('input[type="radio"]:checked').val();
    		 var policyName = $('input[type="radio"]:checked').parent().parent().next().text();
    		 var csrf = "${_csrf.token}"; 
    		 $.post("org/updatePolicyById",{policyId:id,departmentId:departmentId,currentPolicyId:currentPolicyId,departmentName:departmentName,policyName:policyName,_csrf:csrf},function(data){
    			 if(data.result == data.departmentIdList.length){
    				 window.location.reload();
    			 }
    		 },'json');
    	 }
    });
    
    //分页的异步请求
    function getAjax(page){
        var csrf = "${_csrf.token}";
    	var org_id = $("#orgId").val();
    	var policyId = $("#policyId").val();
    	var defaultPolicy = $("#defaultPolicyId").val();
    	  nodes=$('#tree').treeview('getSelected');
    	$.post("dptPolicy/queryAllPolicy",{page:page,_csrf:csrf},function(data){
    		if(data.list != null && data.list.length > 0){
    			if(data.page.page == 1){
    			  if(orgId != org_id){
    				if(currentPolicyId == parseInt(defaultPolicyId)){
    					$("#departmentPolicy").html('<tr>'+
	                            '<td>'+
	                            '<label class="radio i-checks m-l m-t-none m-b-none" id="extendFather">'+
	                              '<input type="radio" name="a" value="" id="extendFather" checked/>'+
	                              '<i></i>'+
	                            '</label>'+
	                          '</td>'+
	                          '<td><fmt:message key="tiles.institution.department.policy.extends.superior" /></td>'+
	                          '<td><fmt:message key="tiles.institution.department.policy.extends.superior.mark" /></td>'+
	                        '</tr>');	
    				}else{
    					$("#departmentPolicy").html('<tr>'+
	                            '<td>'+
	                            '<label class="radio i-checks m-l m-t-none m-b-none">'+
	                              '<input type="radio" name="a" value="" id="extendFather" />'+
	                              '<i></i>'+
	                            '</label>'+
	                          '</td>'+
	                          '<td><fmt:message key="tiles.institution.department.policy.extends.superior" /></td>'+
	                          '<td><fmt:message key="tiles.institution.department.policy.extends.superior.mark" /></td>'+
	                        '</tr>');
    				}
    			  }else{
    				  if(currentPolicyId == parseInt(defaultPolicyId)){
    					  $("#departmentPolicy").html('<tr>'+
  	                            '<td>'+
  	                            '<label class="radio i-checks m-l m-t-none m-b-none" id="extendFather">'+
  	                              '<input type="radio" name="a" value="" id="extendFather" checked/>'+
  	                              '<i></i>'+
  	                            '</label>'+
  	                          '</td>'+
  	                          '<td><fmt:message key="tiles.institution.department.policy.extends.superior" /></td>'+
  	                          '<td><fmt:message key="tiles.institution.department.policy.extends.superior.mark" /></td>'+
  	                        '</tr>');	
    				  }else{
    					  $("#departmentPolicy").html('<tr>'+
  	                            '<td>'+
  	                            '<label class="radio i-checks m-l m-t-none m-b-none">'+
  	                              '<input type="radio" name="a" value="" id="extendFather" />'+
  	                              '<i></i>'+
  	                            '</label>'+
  	                          '</td>'+
  	                          '<td><fmt:message key="tiles.institution.department.policy.extends.superior" /></td>'+
  	                          '<td><fmt:message key="tiles.institution.department.policy.extends.superior.mark" /></td>'+
  	                        '</tr>');
    				  }
    			  }
    			}else{
    				$("#departmentPolicy").html('');
    			}
    			for(var i=0;i<data.list.length;i++){
    			  if(data.list[i].id != parseInt(defaultPolicyId)){
    			 	if(data.list[i].id == nodes[0].tags.policy.id){
    			 		//选中的部门的策略不再当前页时，在发现的时候让他选中
    			 			if(policyId == data.list[i].id){
		    			 		if(data.list[i].flag.length > 30){
				 					var flagStr = data.list[i].flag.substring(0,30);
		    			 			$("#departmentPolicy").append('<tr>'+
		                                    '<td>'+
		                                      '<label class="radio i-checks m-l m-t-none m-b-none">'+
		                                        '<input type="radio" name="a" value="'+data.list[i].id+'">'+
		                                        '<i></i>'+
		                                      '</label>'+
		                                    '</td>'+
		                                    '<td>'+data.list[i].name+'</td>'+
		                                    '<td title="'+data.list[i].flag+'">'+flagStr+'...</td>'+
		                                  '</tr>');
		    			 		}else{
		    			 			$("#departmentPolicy").append('<tr>'+
		                                    '<td>'+
		                                      '<label class="radio i-checks m-l m-t-none m-b-none">'+
		                                        '<input type="radio" name="a" value="'+data.list[i].id+'">'+
		                                        '<i></i>'+
		                                      '</label>'+
		                                    '</td>'+
		                                    '<td>'+data.list[i].name+'</td>'+
		                                    '<td title="'+data.list[i].flag+'">'+data.list[i].flag+'</td>'+
		                                  '</tr>');
		    			 		}
    			 			}else{
    			 				if(data.list[i].flag.length > 30){
				 					var flagStr = data.list[i].flag.substring(0,30);
		    			 			$("#departmentPolicy").append('<tr>'+
		                                    '<td>'+
		                                      '<label class="radio i-checks m-l m-t-none m-b-none">'+
		                                        '<input type="radio" name="a" value="'+data.list[i].id+'" checked>'+
		                                        '<i></i>'+
		                                      '</label>'+
		                                    '</td>'+
		                                    '<td>'+data.list[i].name+'</td>'+
		                                    '<td title="'+data.list[i].flag+'">'+flagStr+'...</td>'+
		                                  '</tr>');
		    			 		}else{
		    			 			$("#departmentPolicy").append('<tr>'+
		                                    '<td>'+
		                                      '<label class="radio i-checks m-l m-t-none m-b-none">'+
		                                        '<input type="radio" name="a" value="'+data.list[i].id+'" checked>'+
		                                        '<i></i>'+
		                                      '</label>'+
		                                    '</td>'+
		                                    '<td>'+data.list[i].name+'</td>'+
		                                    '<td title="'+data.list[i].flag+'">'+data.list[i].flag+'</td>'+
		                                  '</tr>');
		    			 		}
    			 			}
    				}else{
    					if(data.list[i].flag.length > 30){
    						var flagStr = data.list[i].flag.substring(0,30);
    					$("#departmentPolicy").append('<tr>'+
                             '<td>'+
                               '<label class="radio i-checks m-l m-t-none m-b-none">'+
                                 '<input type="radio" name="a" value="'+data.list[i].id+'">'+
                                 '<i></i>'+
                               '</label>'+
                             '</td>'+
                             '<td>'+data.list[i].name+'</td>'+
                             '<td title="'+data.list[i].flag+'">'+flagStr+'...</td>'+
                           '</tr>');
    					}else{
    						$("#departmentPolicy").append('<tr>'+
    	                             '<td>'+
    	                               '<label class="radio i-checks m-l m-t-none m-b-none">'+
    	                                 '<input type="radio" name="a" value="'+data.list[i].id+'">'+
    	                                 '<i></i>'+
    	                               '</label>'+
    	                             '</td>'+
    	                             '<td>'+data.list[i].name+'</td>'+
    	                             '<td title="'+data.list[i].flag+'">'+data.list[i].flag+'</td>'+
    	                           '</tr>');
    					}
    				}
    			  }else{
    				  if(fatherDepartmentId != data.list[i].id){
    				  if(data.list[i].id == nodes[0].tags.policy.id){
    					//选中的部门的策略不再当前页时，在发现的时候让他选中
	    			 		if(data.list[i].flag.length > 30){
			 					var flagStr = data.list[i].flag.substring(0,30);
	    			 			$("#departmentPolicy").append('<tr>'+
	                                    '<td>'+
	                                      '<label class="radio i-checks m-l m-t-none m-b-none">'+
	                                        '<input type="radio" name="a" value="'+data.list[i].id+'" checked>'+
	                                        '<i></i>'+
	                                      '</label>'+
	                                    '</td>'+
	                                    '<td>'+data.list[i].name+'</td>'+
	                                    '<td title="'+data.list[i].flag+'">'+flagStr+'...</td>'+
	                                  '</tr>');
	    			 		}else{
	    			 			$("#departmentPolicy").append('<tr>'+
	                                    '<td>'+
	                                      '<label class="radio i-checks m-l m-t-none m-b-none">'+
	                                        '<input type="radio" name="a" value="'+data.list[i].id+'" checked>'+
	                                        '<i></i>'+
	                                      '</label>'+
	                                    '</td>'+
	                                    '<td>'+data.list[i].name+'</td>'+
	                                    '<td title="'+data.list[i].flag+'">'+data.list[i].flag+'</td>'+
	                                  '</tr>');
	    			 		}
    			  }else{
  					if(data.list[i].flag.length > 30){
						var flagStr = data.list[i].flag.substring(0,30);
					$("#departmentPolicy").append('<tr>'+
                         '<td>'+
                           '<label class="radio i-checks m-l m-t-none m-b-none">'+
                             '<input type="radio" name="a" value="'+data.list[i].id+'">'+
                             '<i></i>'+
                           '</label>'+
                         '</td>'+
                         '<td>'+data.list[i].name+'</td>'+
                         '<td title="'+data.list[i].flag+'">'+flagStr+'...</td>'+
                       '</tr>');
					}else{
						$("#departmentPolicy").append('<tr>'+
	                             '<td>'+
	                               '<label class="radio i-checks m-l m-t-none m-b-none">'+
	                                 '<input type="radio" name="a" value="'+data.list[i].id+'">'+
	                                 '<i></i>'+
	                               '</label>'+
	                             '</td>'+
	                             '<td>'+data.list[i].name+'</td>'+
	                             '<td title="'+data.list[i].flag+'">'+data.list[i].flag+'</td>'+
	                           '</tr>');
					}
				}
    				  }
    			  }
    			}
    			$("#pageCount").html('<span class="page-info"><fmt:message key="tiles.institution.department.policy.page"><fmt:param value="'+data.page.totalCount+'" /><fmt:param value="'+data.page.page+'" /><fmt:param value="'+ (data.page.totalPage == 0 ? data.page.totalPage + 1 : data.page.totalPage)+ '" /></fmt:message></span>');
    			//$("#pageCount").html('<span class="page-info">共'+ data.page.totalCount+ '条 每页10条 页次：'+ data.page.page+ '/'+ (data.page.totalPage == 0 ? data.page.totalPage + 1 : data.page.totalPage)+ '</span>');
    			if(data.page.page > 1){
    			    $("#beforePage").html('<a href="javascript:getAjax('+(data.page.page - 1)+')"><fmt:message key="tiles.institution.department.policy.before.page" /></a>');
    			}else{
    				$("#beforePage").html('<a href="javascript:void(0)"><fmt:message key="tiles.institution.department.policy.before.page" /></a>');
    			}
    			if(data.page.totalPage > data.page.page){
    				$("#nextPage").html('<a href="javascript:getAjax('+ (data.page.page + 1)+ ')"><fmt:message key="tiles.institution.department.policy.next.page" /></a>');
    			}else{
    				$("#nextPage").html('<a href="javascript:void(0)"><fmt:message key="tiles.institution.department.policy.next.page" /></a>');
    			}
    		}
    	},'json');
    }
  </script>
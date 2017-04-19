<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.ResourceBundle"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/resources/js/jquery.tmpl.js" var="tmplJs" />
<spring:url value="/resources/js/jquery.cookie.js" var="cookieJs" />
<spring:url value="/resources/js/datepicker/bootstrap-datetimepicker.js" var="datetimePickerJs" />  
<spring:url value="/resources/js/clockpicker/clockpicker.js" var="clockpickerJs" />
<%ResourceBundle res = ResourceBundle.getBundle("file"); %> 
<script src="${clockpickerJs}"></script>
<script src="${datetimePickerJs}"></script>
<script src="${tmplJs}"></script>
<script src="${cookieJs}"></script>

<script type="text/javascript">
	//保存
	function btnSave(flag){
        	//判断自动备份
    		var autoBackup = $("input[name=autoBackup]:checked").val();
    		//判断备份类型
    		var backupType = $("input[name=backupType]:checked").val();//判断radio是否选中
    		var backUp;
    		if(backupType == 0){
    			backUp = '';
    			//当选择按天备份的时候要对文本框添加相关的校验
    			$('#backUpDay').attr('data-parsley-required','true');
    			$('#backUpDay').attr('data-parsley-trigger','blur');
    			$('#backUpDay').attr('data-parsley-pattern','^[1-9]{1,3}?$');
    			backUp = $("input[name=backUpDay]").val();
    		}else if(backupType == 1){
    			backUp = '';
    			//当选择按周备份的时候要去掉按天备份时候的校验
    			$('#backUpDay').removeAttr('data-parsley-required');
    			$('#backUpDay').removeAttr('data-parsley-trigger');
    			$('#backUpDay').removeAttr('data-parsley-pattern');
    			$("input[name=backUpWeek]").each(function(){
    				//判断chekcbox是否选中应该采用这个方法，其他都是有问题的
    				if($(this).is(':checked')){
    					backUp += $(this).val()+",";
    				}
    			});
    			//当选择按星期备份的时候如果星期没有选择的话需要弹出提示框
    			if(backUp == null || backUp == '' || backUp == undefined){
    				$('#openTips').modal('show');
    				$('#tips').html('请选择星期！');
    				return;
    			}
    		}else{
    			backUp = '';
    			//当选择按月备份的时候要去掉按天备份时候的校验
    			$('#backUpDay').removeAttr('data-parsley-required');
    			$('#backUpDay').removeAttr('data-parsley-trigger');
    			$('#backUpDay').removeAttr('data-parsley-pattern');
    			$("input[name=backUpDate]").each(function(){
    				//判断chekcbox是否选中应该采用这个方法，其他都是有问题的
    				if($(this).is(':checked')){
    					backUp += $(this).val()+",";
    				}
    			});
    			//当选择按月备份的时候如果月天没有选择的话需要弹出对应的提示框
    			if(backUp == null || backUp == '' || backUp == undefined){
    				$('#openTips').modal('show');
    				$('#tips').html('请选择日期！');
    				return;
    			}
    		}
    		//判断备份时间,如果备份时间没有从前台传过来，那么我们是默认用什么时间？
    		var backupTime = $("input[name=backupTime]").val();
    		//判断备份路径
    		var backupPath = $("input[name=backupPath]").val();
    		//生成cron表达式的函数
    		var cronExpression = createCronExpression(backupType,backUp,backupTime);
    		//表单提交的时候添加parsley校验
    		var validator = $('#updateSettingfrm').parsley();
    		validator.validate();
            if(validator.isValid()){
            	var csrf="${_csrf.token}"; 
            	//采用ajax方式提交表单
       			 $.ajax({
       				"dataType": 'json',
       		        "data": {"autoBackup":autoBackup,"backupType":backupType,"backUp":backUp,"backupTime":backupTime,"backupPath":backupPath,"cronExpression":cronExpression,"_csrf":csrf,"flag":flag},
       		        "type": "POST",
       	            "url":ctx+"/admin/backup/updateBackupSetting",
       		        "success": function(result){
               			
       		        } 
       		   });  
            }
	}
	//生成cron表达式的方法
	function createCronExpression(backupType,backUp,backupTime){
		var timeSplit = backupTime.split(":");
		if(backupType == 0){
			return "0 "+timeSplit[1]+" "+timeSplit[0]+" 1/"+backUp+" * ?";
		}else if(backupType == 1){
			backUp = backUp.substring(0,backUp.length-1);
			return "0 "+timeSplit[1]+" "+timeSplit[0]+" ? * "+backUp+" * ";
		}else{
			backUp = backUp.substring(0,backUp.length-1);
			return "0 "+timeSplit[1]+" "+timeSplit[0]+" "+backUp+" 1/1 ? * ";
		}
	}
	
	$('#successTitle').on('click',function(){
		$('#openTips').modal('hide');
	});
	
	//显示备份类型的选择
	$("input[name=backupType]").click(function(){
	     if($(this).val() == "0"){
	    	$('#hiddenContent').find("p:eq(0)").show();
	    	$('#hiddenContent').find("p:eq(1)").hide();
	    	$('#hiddenContent').find("p:eq(2)").hide();
		 }else if($(this).val() == "1"){
			 $('#hiddenContent').find("p:eq(0)").hide();
			 $('#hiddenContent').find("p:eq(1)").show();
			 $('#hiddenContent').find("p:eq(2)").hide();
	     }else if($(this).val() == "2"){
	    	 $('#hiddenContent').find("p:eq(0)").hide();
	    	 $('#hiddenContent').find("p:eq(1)").hide();
	    	 $('#hiddenContent').find("p:eq(2)").show();
	     }
	 });

	$(function(){
		//显示备份时间控件
		$('#backupTime').clockpicker({
		    placement: 'bottom',
		    align: 'left',
		    autoclose: true,
		    'default': 'now'
		});
		//默认选择按周备份，并选中星期一
		 $('#hiddenContent').find("p:eq(1)").show();
		 var flag = $('#flag').val();
		 var path = '';
		 if(flag == 0){
			 //表明是windows操作系统
			 path = '<%=res.getString("fileServer")%>'; 
		 }else{
			 //表明是Linux操作系统
			 path = '<%=res.getString("fileServerLinux")%>'; 
		 }
		 //设置初始化的时候默认的路径是系统中配置的路径
		 $('#backupPath').val(path);
	});

</script>
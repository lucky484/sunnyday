/**
 * 自定义的验证
 */

window.Parsley.addValidator('appnamenumber', {
	validateString : function(value,elem) {
		if(value.length>0){
			// 该验证只是不能输入,和#
			//var appnameRegx=/^([A-Za-z0-9\u4e00-\u9fa5!@$%^&*()_+=-./~])+$/;
//			return appnameRegx.test(value);
			if(value.indexOf(",")>=0){
				return false;
			}
			if(value.indexOf("#")>=0){
				return false;
			}
		}
		return true;
	}
});

window.Parsley.addValidator('alphare', {
	validateString : function(value,elem) {
		if($("#moreless").hasClass("show")){
			if(value.length==0){
				return false;
			}
			var alpnumRegx=/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/;
			return alpnumRegx.test(value)&&(value.length>=6&&value.length<=10);
		}
		return true;
	}
});



window.Parsley.addValidator('cellphone', {
	validateString : function(value,elem) {
		if(value.length>0){
			if(value.trim().length==11){
				var phoneRegx=/^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
				return phoneRegx.test(value);
			}else{
				return false;
			}
		} else {
			return true;
		}
		
	}
});

window.Parsley.addValidator('appVersionRequired', {
	validateString : function(value,elem) {
		alert(value);
		if(value==null||value==""){
           return false;
		}
		return true;
	}
});

window.Parsley.addValidator('appidnumber', {
	validateString : function(value,elem) {
		if(value.length>0){
			var appidRegx=/^[A-Za-z0-9.]+$/;
			return appidRegx.test(value);
		}
		return true;
	}
});

window.Parsley.addValidator('appnamenumber', {
	validateString : function(value,elem) {
		if(value.length>0){
			var appnameRegx=/^[A-Za-z0-9\u4e00-\u9fa5]+$/;
			return appnameRegx.test(value);
		}
		return true;
	}
});

// 验证时间格式
window.Parsley.addValidator('startdatestr', {
	validateString : function(value,elem) {
       return compareNetDate(value);
	}
});

//验证时间格式
window.Parsley.addValidator('enddatestr', {
	validateString : function(value,elem) {
		return compareNetDate(value);
	}
});

//验证时间格式
window.Parsley.addValidator('comparedate', {
	validateString : function(value,elem) {
		var sDate = $(elem).val();
		if(sDate!=null&&sDate!=""){
			var sDateArray = sDate.split("-");
			var sDateArr = sDateArray[1].split(":");
			var eDateArray = value.split("-");
			var eDateArr = eDateArray[0].split(":");
			if(sDateArr[0]>eDateArr[0]){
				return false;
			} else if(sDateArr[0]==eDateArr[0]){
			    if(sDateArr[1]>eDateArr[1])	{
			    	return false;
			    }
			}
		}
		return true;
	}
});

// 上网开始时间格式
window.Parsley.addValidator('checkstartdate', {
    validateString: function(value,elem) {
     return compareDate(value,$(elem).val());
    }
  });

// 比较第二个时间段格式
window.Parsley.addValidator('checkdate', {
    validateString: function(value,elem) {
     return compareDate($(elem).val(),value);
    }
  });

 //比较时间
 function compareDate(startTime,endTime){
	if(startTime&&endTime){
		stimeAry = startTime.split(":");
		etimeAry = endTime.split(":");
		if(parseInt(etimeAry[0]) < parseInt(stimeAry[0])){
		    return false;
		}
		if(parseInt(etimeAry[0]) == parseInt(stimeAry[0])){
		    if(parseInt(etimeAry[1]) <= parseInt(stimeAry[1])){
		        return false;
		    }
		}
	}
	return true;
}
 
 // 比较上网时间段时间
 function compareNetDate(value){
		if(value==null||value==""||value==undefined){
			return true;
		}
		if(value.indexOf("-")<=0){
			return false;
		}
		if(value.indexOf(":")!=2||value.lastIndexOf(":")!=8){
			return false;
		}
		if(value.length!=11){
			return false;
		}
		var oldArr = value.split("-");
		var arr1 = oldArr[0].split(":");
		var arr2 = oldArr[1].split(":");
		if(isNaN(arr1[0])){
			return false;
		}
		if(isNaN(arr1[1])){
			return false;
		}
		if(isNaN(arr2[0])){
			return false;
		}
		if(isNaN(arr2[1])){
			return false;
		}
		if(arr1[0]>arr2[0]){
			return false;
		}
		if(arr1[0]==arr2[0]){
			if(arr1[1]>arr2[1]){
				return false;
			}
		}
		return true;
 }

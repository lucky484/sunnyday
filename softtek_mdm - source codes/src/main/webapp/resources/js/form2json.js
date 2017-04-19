(function($){
	$.fn.serializeJson=function(){
		var serializeObj={};
		var array=this.serializeArray();
		$(array).each(function(){
			if(serializeObj[this.name]){
				if($.isArray(serializeObj[this.name])){
					serializeObj[this.name].push(this.value);
				}else{
					serializeObj[this.name]=[serializeObj[this.name],this.value];
				}
			}else{
				serializeObj[this.name]=this.value;
			}
		});
		return serializeObj;
	};
	$.fn.form2Json=function(mulpSplit){
		var ms = ";";
		if(mulpSplit != null && mulpSplit!=""){
			ms = mulpSplit;
		}
		var serializeObj={};
		var array=this.serializeArray();
		$(array).each(function(){
			if(serializeObj[this.name]){
				serializeObj[this.name]=serializeObj[this.name]+ms+this.value;
			}else{
				serializeObj[this.name]=this.value;
			}
		});
		return serializeObj;
	};
})(jQuery);
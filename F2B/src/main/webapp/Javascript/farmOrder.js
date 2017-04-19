/**
 * 数量和价格的定义
 */
var unitPrice = $("#unitPrice").val();// 获得ID为unitPrice标签的jQuery对象
var weight = $("#weight").val();// 获得ID为first标签的jQuery对象
var total = $("#total_read");// 获得ID为first标签的jQuery对象
var num1 = unitPrice;  //代表   单价   的数
var num2 = weight;//代表   数量   的数
var single_logistics = 0;//定义每箱运费|| 原先=20
var total_logistics;//定义总运费


var myRegCN = /^[\u4e00-\u9fa5]+$/;
var myRegUS = /^[a-zA-Z]+$/;
var myReg = /^[0-9]+$/;
function validate(that) {//填写数字
	that.value=that.value.replace(/[^0-9-]+/,'');
	if ($("#weight").val() == "0"|| $("#weight").val() == "00") {
		$("#weight").val("1");
	}
	
	if(myRegCN.test($("#weight").val())||myRegUS.test($("#weight").val())){
		$("#weight").val("1");
	}
	
	$("#logistics").val(single_logistics * $("#weight").val());
	$("#total").val($("#unitPrice").val() * that.value + single_logistics * that.value); /*- $("#quan").val()*/
	$("#total_read").html($("#total").val());
	$("#total-weight").html(that.value);
}

/**
 * 数量增减
 */
$(function() {

	// 增加商品数
	$(".add").click(function() {
		var n = $(this).prev().val();
		var num = parseInt(n) + 1;//数量
		if (num == 0) {
			return 
		}
		$(this).prev().val(num);
		$("#total-weight").text(num);//共计多少件商品
		$("#logistics").val(single_logistics * num);//运费
		
		if ($("#type").val()==0) {
			$("#total").val(parseInt($("#logistics").val()) + $("#unitPrice").val() * num); /*- $("#quan").val()*/			
		} else {
			//$("#total").val($("#unitPrice").val() * num);
			var total0 = ($("#unitPrice").val() * num + parseInt($("#logistics").val())).toFixed(2);
			//alert(total0);
			$("#total").val(total0);
		}
		$("#total_read").html($("#total").val());

	});
	// 减少商品数
	$(".jian").click(function() {
		var n = $(this).next().val();
		var num = parseInt(n) - 1;//数量
		if (num == 0) {
			return 
		}
		
		$(this).next().val(num);
		$("#total-weight").text(num);//多少件商品
		$("#logistics").val(single_logistics * num);
		if ($("#type").val()==0) {
			$("#total").val(parseInt($("#logistics").val()) + $("#unitPrice").val() * num); /*- $("#quan").val()*/
		} else {
			//$("#total").val($("#unitPrice").val() * num);
			var total0 = ($("#unitPrice").val() * num + parseInt($("#logistics").val())).toFixed(2);
			$("#total").val(total0);
		}
		$("#total_read").html($("#total").val());
		
	});
	//江浙沪包邮，偏远不予下单，其他20￥/箱
//	$("#showCityPicker3").bind('DOMNodeInserted',function(e){
//		if($("#showCityPicker3").text().indexOf("上海市")>=0 || $("#showCityPicker3").text().indexOf("江苏省")>=0 ||$("#showCityPicker3").text().indexOf("浙江省")>=0){
//		//if($("#province").val()=="上海市" || $("#province").val()=="江苏省" ||$("#province").val()=="浙江省"){	
//			$("#logistics").val("0");
//			single_logistics = 0;
//			total_logistics = 0;
//		}else {
//			$("#logistics").val(20 * parseInt($("#weight").val()));//其他地区  总运费
//			single_logistics = 20; //其他地区运费20
//			total_logistics = $("#logistics").val();//其他地区  总运费
//		}
//		$("#total").val(parseInt($("#logistics").val()) + $("#unitPrice").val() * $("#weight").val());
//		$("#total_read").html($("#total").val());
//	});
})

//乘法函数
function accMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length;
    }
    catch (e) {
    }
    try {
        m += s2.split(".")[1].length;
    }
    catch (e) {
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
} 
//给Number类型增加一个mul方法，使用时直接用 .mul 即可完成计算。 
Number.prototype.mul = function (arg) {
    return accMul(arg, this);
};

//加法函数
function accAdd(arg1, arg2) {
    var r1, r2, m;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    return (arg1 * m + arg2 * m) / m;
} 
//给Number类型增加一个add方法，，使用时直接用 .add 即可完成计算。 
Number.prototype.add = function (arg) {
    return accAdd(arg, this);
};




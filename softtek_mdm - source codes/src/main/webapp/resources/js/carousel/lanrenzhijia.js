function tab(){
var imgs=document.getElementById("imgs").getElementsByTagName("li");
var nums=document.getElementById("num").getElementsByTagName("li");
var luanpo=document.getElementById("luanpo");
var oimg=document.getElementById("imgs");
var iNow=0;
var dt=null;
oimg.style.width=imgs.length*800+"px";
for(var i=0;i<nums.length;i++){
    nums[i].index=i;
    nums[i].onclick=function(){
        iNow=this.index;
        for(var i=0;i<nums.length;i++){
            nums[i].className="old";        
        }
        this.className="current";
        var left = -(imgs[0].offsetWidth*iNow)+"px";
        oimg.style.left=left;   
    }
  }
}

function iosTab(){
	var imgs=document.getElementById("iosImgs").getElementsByTagName("li");
	var nums=document.getElementById("iosNum").getElementsByTagName("li");
	var luanpo=document.getElementById("iosLuanPo");
	var oimg=document.getElementById("iosImgs");
	var iNow=0;
	var dt=null;
	oimg.style.width=imgs.length*800+"px";
	for(var i=0;i<nums.length;i++){
	    nums[i].index=i;
	    nums[i].onclick=function(){
	        iNow=this.index;
	        for(var i=0;i<nums.length;i++){
	            nums[i].className="old";        
	        }
	        this.className="current";
	        var left = -(imgs[0].offsetWidth*iNow)+"px";
	        oimg.style.left=left;   
	    }
	}
}

function webClipTab(){
	var imgs=document.getElementById("webClipImgs").getElementsByTagName("li");
	var nums=document.getElementById("webClipnum").getElementsByTagName("li");
	var luanpo=document.getElementById("webLuanpo");
	var oimg=document.getElementById("webClipImgs");
	var iNow=0;
	var dt=null;
	oimg.style.width=imgs.length*800+"px";
	for(var i=0;i<nums.length;i++){
	    nums[i].index=i;
	    nums[i].onclick=function(){
	        iNow=this.index;
	        for(var i=0;i<nums.length;i++){
	            nums[i].className="old";        
	        }
	        this.className="current";
	        var left = -(imgs[0].offsetWidth*iNow)+"px";
	        oimg.style.left=left;   
	    }
	  }
}
/**
 * 
 */
var maxpage = 100;
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
                  setTimeout(function() {
                        getdate(id)
                  }, 500);
            }

			function getsubmit(){
				if($("#upLoadFile").val()==""){
					   alert("研判分析身份证对比照片不能为空！");
					   
				   }else{
					   $("#AnalyzingFaceinfo").submit();
				   }
			}
			
			
			function resetBtn(){
				document.getElementById("AnalyzingFaceinfo").reset();
				$("#preview").attr("src","");
				$("#preview").hide();
				$("#upload_a").show();
			}
            $(function(){
                getdate('#curr-time');
                //左侧导航
                $("#site-menu").metisMenu();  
               
               
               
//
//               $("#reset_btn").click(function(){
//            	   $("#AnalyzingFaceinfo").reset();
//            	   $("#preview").attr("src","");
//            	  
//               }); 
               
               $("#page").blur(function(){
	               	var page = $("#page").val();
	               	if(page > maxpage){
	               		page = maxpage;
	               		$("#page").val(page);
	               	}
	               	//getAjax(page);
	               	alert(page);
	               	$("#currentPage").html(page);
               	
               });
               $("#fys").change(function(){
               	if(parseInt($("#fys").val())<=parseInt(0) ){
               		$("#fys").val('1');
               	}
               });
               $("#count").change(function(){
                  	if(parseInt($("#count").val())<=parseInt(0) ){
                  		$("#count").val('10');
                  	}
                  });
               $("#similarity").change(function(){
                 	if(parseInt($("#similarity").val())<=parseInt(0) ){
                 		$("#similarity").val('0');
                 	}
                 });
               if($("#nullData").val()=="0"){
//            	   alert("暂无数据！");
            	   $("#message").append("<tr><td colspan='5'>暂无数据！</td></tr>")
               }
            });
           
            function setImagePreview() {  
            	$("#upload_a").hide();
                var docObj=document.getElementById("upLoadFile");  
                var imgObjPreview=document.getElementById("preview");  
                $("#localImag").show();
                if(docObj.files && docObj.files[0]){  
                    //火狐下，直接设img属性  
                    imgObjPreview.style.display = 'block';  
                    imgObjPreview.style.width = '100px';  
                    imgObjPreview.style.height = '125px';                      
                    //imgObjPreview.src = docObj.files[0].getAsDataURL();  
                      
                    //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式    
                    imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);  
                }else{  
                    //IE下，使用滤镜  
//                    docObj.select();  
//                    var imgSrc = document.selection.createRange().text;  
//                    var localImagId = document.getElementById("localImag");  
//                    //必须设置初始大小  
//                    localImagId.style.width = "100px";  
//                    localImagId.style.height = "125px";  
//                    //图片异常的捕捉，防止用户修改后缀来伪造图片  
//                    try{  
//                        localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";  
//                        localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;  
//                    }catch(e){  
//                        alert("您上传的图片格式不正确，请重新选择!");  
//                        return false;  
//                    }  
//                    imgObjPreview.style.display = 'none';  
//                    document.selection.empty();  
                }  
                return true;  
            }  
            
            function getPrePage(){
            	var page = $("#page").val();
            	//alert(maxpage);
            	if( page > 1){
            		$("#page").val(eval(page+"-1"));
            		$("#currentPage").html($("#page").val());
            		getAjax($("#page").val());
            		//$("#hiddenPage").val($("#page").val());
            	}
            }

            function getNextPage(){
            	var page = $("#page").val();
            	if( page < maxpage){
            		$("#page").val(eval(page+"+1"));
            		$("#currentPage").html($("#page").val());
            		getAjax($("#page").val());
            		//$("#hiddenPage").val($("#page").val());
            	}
            }
            
            function getPage(page){
            	$("#page").val(page);
        		$("#currentPage").html(page);
            	getAjax(page);
            }
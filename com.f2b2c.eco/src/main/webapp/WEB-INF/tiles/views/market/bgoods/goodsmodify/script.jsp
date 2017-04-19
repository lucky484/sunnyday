<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/farm/terminal/goods/page" var="goodsListUrl"/>
<spring:url value="/farm/terminal/goods/index" var="goodsIndex" />
<spring:url
	value="/resources/platform/js/plugins/easyUI/jquery.easyui.min.js"
	var="easyUIUrl" />
    <!-- 编辑器源码文件 -->
<spring:url value="/market/bgoods/modify-bgoods" var="modifyBgoodsUrl"/>
<spring:url value="/market/bgoods/bgoodslist" var="bgoodslistUrl" />
<spring:url value="/api/fkind/get-kinds-for-jstree" var="getKindsUrl" />
<spring:url value="/market/bgoods/insert-bgoods-pic" var="uploadGoodsPicUrl" />
<spring:url value="/resources/platform/js/jquery-ui-1.10.4.min.js" var="jqueryUIUrl" />
<spring:url value="/api/fkind/bkind-isfruit" var="isFuritUrl"/>
<script type="text/javascript" src="${jqueryUIUrl}"></script>
<script type="text/javascript" src="${easyUIUrl}"></script>
<spring:url value="/api/ueuplod/bgoogs-img" var="ueuplodUrl"/>
<script type="text/javascript">
 $("#sharePercent").val("${data.sharePercent}");
 var ue = UE.getEditor('container',{
	 catchRemoteImageEnable: false,
	 toolbars: [
                                               [
                                                'undo', //撤销
                                                'redo', //重做
                                                'bold', //加粗
                                                'italic', //斜体
                                                'underline', //下划线
                                                'strikethrough', //删除线
                                                'subscript', //下标
                                                'simpleupload', //单图上传
                                                'superscript', //上标
                                                'formatmatch', //格式刷
                                                'source', //源代码
                                                'selectall', //全选
                                                'print', //打印
                                                'preview', //预览
                                                'horizontal', //分隔线
                                                'removeformat', //清除格式
                                                'time', //时间
                                                'date', //日期
                                                'insertrow', //前插入行
                                                'insertcol', //前插入列
                                                'mergeright', //右合并单元格
                                                'mergedown', //下合并单元格
                                                'deleterow', //删除行
                                                'deletecol', //删除列
                                                'splittorows', //拆分成行
                                                'splittocols', //拆分成列
                                                'splittocells', //完全拆分单元格
                                                'deletecaption', //删除表格标题
                                                'inserttitle', //插入标题
                                                'mergecells', //合并多个单元格
                                                'deletetable', //删除表格
                                                'cleardoc', //清空文档
                                                'insertparagraphbeforetable', //"表格前插入行"
                                                'fontfamily', //字体
                                                'fontsize', //字号
                                                'paragraph', //段落格式
                                                'edittable', //表格属性
                                                'edittd', //单元格属性
                                                'emotion', //表情
                                                'spechars', //特殊字符
                                                'justifyleft', //居左对齐
                                                'justifyright', //居右对齐
                                                'justifycenter', //居中对齐
                                                'justifyjustify', //两端对齐
                                                'forecolor', //字体颜色
                                                'backcolor', //背景色
                                                'fullscreen', //全屏
                                                'imagecenter', //居中
                                                'edittip ', //编辑提示
                                                'customstyle', //自定义标题
                                                'autotypeset', //自动排版
                                                'background', //背景
                                                'template', //模板
                                                'inserttable', //插入表格
                                            ]
                                        ]
                     });
 var ue2 = UE.getEditor('container2',{
	 catchRemoteImageEnable: false,
	 toolbars: [
                          [
                           'undo', //撤销
                           'redo', //重做
                           'bold', //加粗
                           'italic', //斜体
                           'underline', //下划线
                           'strikethrough', //删除线
                           'subscript', //下标
                           'fontborder', //字符边框
                           'superscript', //上标
                           'formatmatch', //格式刷
                           'source', //源代码
                           'selectall', //全选
                           'print', //打印
                           'preview', //预览
                           'horizontal', //分隔线
                           'removeformat', //清除格式
                           'time', //时间
                           'date', //日期
                           'insertrow', //前插入行
                           'insertcol', //前插入列
                           'mergeright', //右合并单元格
                           'mergedown', //下合并单元格
                           'deleterow', //删除行
                           'deletecol', //删除列
                           'splittorows', //拆分成行
                           'splittocols', //拆分成列
                           'splittocells', //完全拆分单元格
                           'deletecaption', //删除表格标题
                           'inserttitle', //插入标题
                           'mergecells', //合并多个单元格
                           'deletetable', //删除表格
                           'cleardoc', //清空文档
                           'insertparagraphbeforetable', //"表格前插入行"
                           'fontfamily', //字体
                           'fontsize', //字号
                           'edittable', //表格属性
                           'edittd', //单元格属性
                           'emotion', //表情
                           'spechars', //特殊字符
                           'justifyleft', //居左对齐
                           'justifyright', //居右对齐
                           'justifycenter', //居中对齐
                           'justifyjustify', //两端对齐
                           'forecolor', //字体颜色
                           'backcolor', //背景色
                           'fullscreen', //全屏
                           'imagecenter', //居中
                           'edittip ', //编辑提示
                           'customstyle', //自定义标题
                           'autotypeset', //自动排版
                           'background', //背景
                           'template', //模板
                           'inserttable', //插入表格
                       ]
                   ]
});
 
UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
  UE.Editor.prototype.getActionUrl = function(action) {  
     if (action == 'uploadimage') {//跳转到后来的上传action  
         return '${ueuplodUrl}';  
     } else {  
         return this._bkGetActionUrl.call(this, action);  
     }  
 }  
        //修改商品
        $("#modify").on("click",function() {
        	$.pnotify_remove_all();
        	 $("#modify").attr("disabled",true);
	    	//商品名称
	    	var name= $("#name").val(); 
	    	//商品类型
	    	var type= $("#type").val(); 
	    	//商品分类
	    	var kindId = $("#kindTree").combotree('getValue');
	    	//单价
	    	var price = $("#price").val(); 
	    	//是否进口
	    	var madeInChina= $("#madeInChina").val(); 
	    	//单位
	    	var unit= $("#unit").val(); 
	    	//库存
	    	var remain= $("#remain").val(); 
	    	//产地   
	    	var producePlace= $("#producePlace").val();
	    	//描述
	    	var detail = ue.getContent();
	    	//描述2
	    	var parameter = ue2.getContent();
	    	//佣金百分比
	    	var sharePercent= $("#sharePercent").val(); 
	    	//商品id
	    	var id = $("#id").val();
	    	// 验证
			if(name == undefined || name == null || name == ""){
				$.notify("error", "请输入商品名称");
				$("#modify").attr("disabled",false)
				return;
			}
			if(kindId == undefined || kindId == null || kindId == ""){
				$.notify("error", "请选择分类");
				$("#modify").attr("disabled",false)
				return;
			}
			if(price == undefined || price == null || price == ""){
				$.notify("error", "请输入单价");
				$("#modify").attr("disabled",false)
				return;
			}else if(price <= 0){
				$.notify("error", "单价不是一个有效值");
				$("#modify").attr("disabled",false)
				return;
			}
			if(remain == undefined || remain == null || remain == ""){
				$.notify("error", "请输入库存");
				$("#modify").attr("disabled",false)
				return;
			}else if(remain <= 0){
				$.notify("error", "库存不是一个有效值");
				$("#modify").attr("disabled",false)
				return;
			}
			if(producePlace == undefined || producePlace == null || producePlace == ""){
				$.notify("error", "请输入产地");
				$("#modify").attr("disabled",false)
				return;
			}
			if($('#yongjin').is('.hide') == false){
				if( sharePercent == undefined || sharePercent == null || sharePercent == "" ){
					$.notify("error", "请输入佣金比例");
					$("#modify").attr("disabled",false)
					return;
				}
			}
			if(parameter == undefined || parameter == null || parameter == ""){
				$.notify("error", "请编辑产品参数");
				$("#modify").attr("disabled",false);
				return;
			} 
	    	var logoUrl = "";
	    	$.each($("input[name='logoUrl']"), function(key, value) {
	    		if($(this).val() != undefined && $(this).val() != null && $(this).val() != ""){
	    			logoUrl += $(this).val() + "|";
	    		}
	    	}); 
	    	if(logoUrl.length == 0){
	    		$.notify("error", "请上传图片");
	    		$("#modify").attr("disabled",false);
	    		return;
	    	}else{
	    	logoUrl=logoUrl.substring(0,logoUrl.length-1);
	    	 $.ajax({
       			"dataType" : 'json',
       			"type" : "POST",
       			"async" : false,
       			"url" : "${modifyBgoodsUrl}",
       			"data" : {
       				"id" : id,
       				"name" : name,
       				"type" : type,
       				"kind.id" : kindId,
       				"pricex" : price,
       				"madeInChina" : madeInChina,
       				"unit" : unit,
       				"remain" : remain,
       				"producePlace" : producePlace,
       				//"unitOfWeight" : unitOfWeight,
       				"producePlace" : producePlace,
       				//"spec" : spec,
       				//"intro" : intro,
       				"sharePercent" : sharePercent,
       				"detail" : detail,
       				"parameter" : parameter,
       				logoUrl :logoUrl
       			},
       			"success" : function(data) {
       				bgoodslistUrl();
       			},
				error:function(data){
					$("#modify").attr("disabled",false);
					alert("发布失败!")
				}
       			}); 
	    	 }
			 
        });
       
        
        function bgoodslistUrl(){
    		window.location.href="${bgoodslistUrl}";
    	}     
        
        $("#kindTree").combotree({
	       	onChange:function(){
	               $.post('${isFuritUrl}', {
	   				id : $(".validatebox-text").next().val()
	   			}, function(data) {
	   				if(data == "false"){
	   					$("#yongjin").removeClass("hide");
	   				}else{
	   					$("#yongjin").addClass("hide");
	   				}
	   			});
	        }, onBeforeSelect:function(node){
	        	var rows = node.children;
	        	if(rows){
	        		$("#kindTree").treegrid("unselect");
                }
    		},
		url : '${getKindsUrl}',
		height : 35,
	});
		
        goodsImgUpload(0);
    	
    	function picDivStr(i) {
    		return '<li><div id="picDiv'+i+'" class="picDiv"><input type="hidden" name="logoUrl" id="logoUrl'+i+'" /><span class="picHint" id="picHint'+i+'"><br /><br />+ 加图</span> <input type="file" name="file" class="picInput" id="picInput'+i+'" /></div></li>';
    	}

    	function goodsImgUpload(index) {
    		$('#picInput' + index).fileupload({
    			url : '${uploadGoodsPicUrl}',
    			dataType : 'json',
    			done : function(e, data) {
    				if (data.result.status == 200) {
    					// 图片地址
    					$(this).parent().find("input[name='logoUrl']").val(data.result.data[0]);
    					// 预览图
    					$(this).parent().find("[id^=picHint]").html("<img class='goodsImg' src='" + data.result.data[1] + "' />");
    					$(this).parent().append("<a href='javascript:deletePic("+index+")' class='close-modal'>×</a>");
    					$(this).remove();
    					$("#sortable").append(picDivStr(++index));
    					goodsImgUpload(index);
    				}
    			}
    		});
    	}
    	function deletePic(index) {
    		$("#picDiv" + index).parent().remove();
    	}

		$("#sortable").sortable();
		$("#sortable").disableSelection();
		$(".validatebox-text").next().val("${data.kind.id}");
		$(".validatebox-text").val("${data.kind.kindName}");
		
		//失去焦点改变比例精度
	   $("#sharePercent").blur(function(){
        	var sharePercent= $("#sharePercent").val();
	        	if(sharePercent != undefined && sharePercent != null && sharePercent != ""){
	        		if(sharePercent > 100 || sharePercent <0){
	        			$("#sharePercent").val(0);
	        			$.notify("error", "请输入0-100的有效值!");
	        		}else{
	        			$("#sharePercent").val(formatFloat(sharePercent,2));
	        		}
	    		}
        	});
	   $("#price").blur(function(){
       	var price= $("#price").val();
	        	if(price != undefined && price != null && price != ""){
	        		if(price < 0){
	        			$("#price").val(0);
	        			$.notify("error", "请输入大于0的有效值!");
	        		}else{
	        			$("#price").val(formatFloat(price,2));
	        		}
	    		}
       	});
	   
	   /**
	     * 
	     * @param obj 值
	     * @param scale 精度
	     */
	    function formatFloat(value,scale){
	    	return parseFloat(value).toFixed(scale)
	    }
	</script>
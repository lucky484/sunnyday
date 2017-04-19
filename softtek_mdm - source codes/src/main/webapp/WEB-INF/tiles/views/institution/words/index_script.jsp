<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
  <spring:url value="/resources/js/datatables-1.10.11/js/jquery.dataTables.js" var="dataTableJs" />
  <script src="${dataTableJs}"></script>
  <spring:url value="/resources/js/datatables-1.10.11/js/dataTables.bootstrap.js" var="dataTableBootstrapJs" />
  <script src="${dataTableBootstrapJs}"></script>
  <spring:url value="/resources/js/jquery.tmpl.js" var="jqueryTmplJs" />
  <script src="${jqueryTmplJs}"></script>
  <spring:url value="/resources/js/datatables-1.10.11/lang/language.lang" var="dtLangUrl" />
  <spring:url value="/institution/words/exists" var="existsUrl" />
  <script src="${existsUrl}"></script>
  <script type="text/javascript">
   var languageUrl;
   var lang = "${dtLangUrl}";
   var str = lang.substring(lang.lastIndexOf("/")+1,lang.lastIndexOf("."));
   var str1 = lang.substring(0,lang.lastIndexOf("/"));
   var str2 = lang.substring(lang.lastIndexOf("."),lang.length);
   var nlang=navigator.language;
	if(nlang.toLowerCase().indexOf("zh")>=0){
		languageUrl = str1 + "/" + str + "_zh-CN" + str2;
	}else{
		languageUrl = str1 + "/" + str + "_en-US" + str2;
	}
   var dataTable = "";
   // 加载系统关键字
   function loadSystemWords(){
      var name = $("#searchName").val();
      //加载策略列表数据
      dataTable = $('#systemWordsTable').dataTable({
                  "dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
                  "autoWidth": false,
                  "searching" : false,
                  "stateSave" : true,
                  "ordering" : false,
                  "bSort" : false,
                  "serverSide" : true,
                  "pageLength" : 10,
                  "pagingType" : "full_numbers",
                  "bDestroy" : true,
                  "oLanguage": {
                      "sUrl": languageUrl
                  },
                  "ajax" : {
                      "dataType" : 'json',
                      "type" : "get",
                      "url" : ctx+"/institution/words/queryAll?now="+ new Date().getTime(),
                      "data" : {"name":name}
                 },
                 "columnDefs" : [
                 {
                    "targets" : [0],
                    "render" : function(data, type, row) {
                        return row.name;
                    }
                },
                {
                     "targets" : [1],
                     "render" : function(data, type, row) {
                         return row.websiteName;
                     }
                },
                {
                    "targets" : [2],
                    "render" : function(data, type, row) {
                         var btns = '<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">';
                        btns += '<i class="i  i-settings"></i>';
                        btns += '</a>';
                        btns += '<ul class="dropdown-menu" style="margin-left:50px;margin-top:-30px;">';
                        btns += '<li><a href="javascript:find('+row.id+')"><i class="fa fa-eye"></i>&nbsp;<fmt:message key="general.jsp.find.label"/></a></li>';
                        btns += ' <li><a href="javascript:getWords('+row.id+');"><i class="fa fa-pencil"></i>&nbsp;<fmt:message key="general.jsp.modify.label"/></a></li>';
                        btns += '<li><a href="javascript:deleteWordsWarning('+row.id+',\''+row.name+'\');"><i class="i i-trashcan text-danger"></i><span class="text-danger">&nbsp;<fmt:message key="general.jsp.delete.label"/></span></a></li>'
                        btns += '</ul>';
                        return btns;
                    }
             }]
        });
   }
   
   exists();
   // 判断系统词库名称是否存在
   function exists(){
     var id = $("#id").val();
     $("#name").parsley().addAsyncValidator('existsValidate',function(xhr){
        return (xhr.responseText.indexOf('success') >= 0); 
     },"${existsUrl}", { "type": "GET", "dataType": "json", "contentType": "application/json; charset=utf-8", "data": {"id":id} } );
   }
   
   //查询词库列表
   function searchWords(){
       loadSystemWords(); 
       $('#netTable').dataTable().fnDraw();
   }
   
   //清除词库列表
   function cleanWords(){
       $("#searchName").val("");
       loadSystemWords(); 
       $('#netTable').dataTable().fnDraw();
   }
   
   // 查看系统词库
   function find(id){
       $.ajax({
           "dataType" : 'json',
           "type": "GET",
           "url":ctx+"/institution/words/getWords?now="+ new Date().getTime(),
           "data": {"id":id},
           "success": function(data){
               if(data!=null){
                   $("#id").val(id);
                   $("#findName").val(data.systemWords.name);
                   websiteHtml("find_website_type","#findWebsiteHtml",data.classifyList,data.systemWords.websiteClassifyId);
                   $("#findModal").modal("show");
               }
           }
       });
   }
   
   // 保存系统词库
   function save(){
       var validator = $("#saveWords").parsley();
       validator.validate();
       if(validator.isValid()){
           var id = $("#id").val();
           var name = $("#name").val();
           var website_type = $("#website_type").val();
           var csrf = "${_csrf.token}";
           $.ajax({
               "dataType" : 'json',
               "type": "POST",
               "url":ctx+"/institution/words/saveWords?_csrf="+csrf,
               "data": {"id":id,"name":name,"website_type":website_type},
               "success": function(data){
                   $(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show();
                   $("#myModal").modal("hide");
                   dataTable.fnDraw();
               }   
          });
      }
   }
   
   // 新增系统词库
   function addWordsPage(){
       initSystemWords();
       $("#myModal").modal("show");
   }
   
   // 删除提示框
   function deleteWordsWarning(id,name) {
	  $("#clickWordsClick").attr("onclick","deleteWords('"+id+"','"+name+"')");
  	  $("#delWordsModal").modal(open);	
   }
   
   //删除策略
   function deleteWords(id,name){
       $.ajax({
           "dataType" : 'json',
           "type": "GET",
           "url":ctx+"/institution/words/delWords?now="+ new Date().getTime(),
           "data": {"id":id,"name":name},
           "success": function(data){
               $(".notify").notify({type:data.type,message: { html: false, text: data.message}}).show();
               dataTable.fnDraw();
           }   
      });
   }
   
   // 初始化系统词库
   function initSystemWords(){
       $("#name").val("");
       $("#name").removeAttr("autofocus");
       $("#website_type option:first").prop("selected", 'selected');

   }
   
   // 获取系统词库
   function getWords(id){
       $.ajax({
           "dataType" : 'json',
           "type": "GET",
           "url":ctx+"/institution/words/getWords?now="+ new Date().getTime(),
           "data": {"id":id},
           "success": function(data){
               if(data!=null){
                   initSystemWords();
                   $("#id").val(data.systemWords.id);
                   $("#name").val(data.systemWords.name);
                   websiteHtml("website_type","#websiteHtml",data.classifyList,data.systemWords.websiteClassifyId);
                   $("#myModal").modal("show");
               }
           }
       });
   }
   
   // 拼接网站类型
   // websiteList:网站类型list
   // websiteClassifyId:网站分类id
   function websiteHtml(id,webhtml,websiteList,websiteClassifyId){
       var html = '<select id="'+id+'" class="select_width">';
       html += '<option value=""><fmt:message key="tiles.views.institution.system.words.indexmodal.choose"/></option>';
       $.each(websiteList, function(i,val){
           html += '<option value='+val.id;
           if(val.id == websiteClassifyId) {
               html += ' selected="selecteds"';
               html += '>'+val.name+'</option>';
           } else {
               html += '>'+val.name+'</option>';
           }
       });
       html += '</select>';
       $(webhtml).html(html);
   }
   
   $(function() {
       loadSystemWords(); 
   });

	//查询框显隐
	$(".search-toggle a").click(function(){
				if($(this).hasClass("hide1")){
					$(this).removeClass("hide1");
					$(this).removeAttr("style");
					$(this).text("");
					$(this).text("<fmt:message key='tiles.institution.comm.expand.search.tip'/>");
					$(".search-mod").hide();
				} else {
					$(this).addClass("hide1");
					$(this).removeAttr("style");
					$(this).text("");
					$(this).text("<fmt:message key='tiles.institution.comm.close.search.tip'/>");
					$(".search-mod").show();
				}
			});
 </script>
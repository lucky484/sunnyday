<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/market/freight/modify" var="modifyFreight"/>
<spring:url value="/market/freight/index" var="freightHtml"/>
<script type="text/javascript">
  function modifyFreight(){
     var value = '';
     $("input[name=freightName]").each(function(){
         value += $(this).attr("id") + ',';
         var fre = $(this).val();
         if(fre==null||fre==''){
             fre = 0;
         }
         value += fre + '#';
     });
     $.ajax({
          "dataType" : 'json',
          "type" : "POST",
          "url" : "${modifyFreight}",
          "data" : {
              "freightStr" : value
          },
          "success" : function(data) {
              if(data!=0){
                  alert("运费设置操作成功");
                  window.location.href = "${freightHtml}";
              } else {
                  alert("运费设置操作失败");
                  return false;
              }
          }
    });  
  }
</script>
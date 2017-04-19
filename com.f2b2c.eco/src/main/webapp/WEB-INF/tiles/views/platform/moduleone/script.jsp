<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script>
    $(document).ready(function(){$(".dataTables-example").dataTable({
    	/* "dom":"<'m-r m-t-lg pull-right'f>t<'row '<'col-lg-2'l>r<'col-lg-3'i><'pull-right'p>>",
	    "autoWidth": false,
	    "searching" : false,
		"stateSave" : true,
		"ordering" : false,
		"bSort":false,
		"pageLength" : 10,
		"pagingType" : "full_numbers",
		"serverSide" : true,
		"bDestroy" : true,
		"oLanguage":{
			"sUrl":languageUrl 
		}, */
    	/* "ajax" : {
			"url" : "${pagesUrl}",
			"type" : "get",
			"dataSrc" : "data",
			"data" :{"searchname" : searchname}
		}, */
    	"data": [
    	         {
    	             "name":       "Tiger Nixon",
    	             "position":   "System Architect",
    	             "salary":     "$3,120",
    	             "start_date": "2011/04/25",
    	             "office":     "Edinburgh",
    	             "extn":       "5421"
    	         },
    	         {
    	             "name": "Garrett Winters",
    	             "position": "Director",
    	             "salary": "5300",
    	             "start_date": "2011/07/25",
    	             "office": "Edinburgh",
    	             "extn": "8422"
    	         },
    	     ],
    	     "columns": [
    	                 { "data": "name" },
    	                 { "data": "position" },
    	                 { "data": "office" },
    	                 { "data": "extn" },
    	                 { "data": "start_date" },
    	                 { "data": "salary" }
    	             ],
             "columnDefs" : [
							{
								className: "",
								targets : [ 0 ],
								render : function(
										data, type,
										full, meta) {
									return full.name+'110';
								}
							}]
    });});
</script>
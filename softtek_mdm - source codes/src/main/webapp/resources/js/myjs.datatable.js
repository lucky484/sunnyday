;(function($){ 
	 $.fn.extend({
		 "extendDatatable":function(options){
				//设置默认值
	            options= $.extend({
	                "bbSort":   true,
	                "ssSearch": true,
	                "sortCols": null,
	                "unSortCols":  null
	            },options||{});
	        	
	            if(!(options.sortCols&&options.unSortCols)){
	            	this.dataTable({
	    		        "sDom":                  "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
	                    "sPaginationType":       "bootstrap",
	    				"aLengthMenu":           [[5, 10, 25,50,-1], [5, 10, 25,50, "All"]],
	    				"oLanguage": {
	    					  "sLengthMenu":     "每页显示记录数 _MENU_ ",
	    					  "sZeroRecords":    "当前没有数据",
	    					  "sInfo":           "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
	    					  "sInfoEmpty":      "没有检索到数据",
	    					  "sInfoFiltered":   "(从 _MAX_ 条数据中检索)",
	    					  "sSearch":         "搜索:",
	    					  "oPaginate": {
	    						  "sFirst":      "首页",
	    						  "sPrevious":   "前一页",
	    						  "sNext":       "后一页",
	    						  "sLast":       "尾页", 
	    					  },
	    				  },
	    		         "bFilter":  options.ssSearch, //过滤功能
	    		         "bSort":    options.bbSort, //排序功能
	        		});            		
	            }else{
	            	this.dataTable({
	    		        "sDom":                  "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
	                    "sPaginationType":       "bootstrap",
	    				"aLengthMenu":           [[5, 10, 25,50,-1], [5, 10, 25,50, "All"]],
	    				"aaSorting":             options.sortCols,
	    		        "aoColumnDefs":          [ { "bSortable": false, "aTargets": options.unSortCols } ],
	    				"oLanguage": {
	    					  "sLengthMenu":     "每页显示记录数 _MENU_ ",
	    					  "sZeroRecords":    "当前没有数据",
	    					  "sInfo":           "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
	    					  "sInfoEmpty":      "没有检索到数据",
	    					  "sInfoFiltered":   "(从 _MAX_ 条数据中检索)",
	    					  "sSearch":         "搜索:",
	    					  "oPaginate": {
	    						  "sFirst":      "首页",
	    						  "sPrevious":   "前一页",
	    						  "sNext":       "后一页",
	    						  "sLast":       "尾页", 
	    					  },
	    				  },
	    		         "bFilter":  options.ssSearch, //过滤功能
	    		         "bSort":    options.bbSort, //排序功能
	        		});     
	            }
		 }
	 });
})(jQuery);

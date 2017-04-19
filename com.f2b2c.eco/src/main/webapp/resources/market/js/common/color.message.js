(function($){
	
	/*********** Positioned Stack ***********
	* This stack is initially positioned through code instead of CSS.
	* This is done through two extra variables. firstpos1 and firstpos2
	* are pixel values, relative to a viewport edge. dir1 and dir2,
	* respectively, determine which edge. It is calculated as follows:
	*
	* - dir = "up" - firstpos is relative to the bottom of viewport.
	* - dir = "down" - firstpos is relative to the top of viewport.
	* - dir = "right" - firstpos is relative to the left of viewport.
	* - dir = "left" - firstpos is relative to the right of viewport.
	*/
	var stack_topleft = {
	    "dir1": "down", 
	    "dir2": "right", 
	    "push": "top"
	};
	var stack_bottomleft = {
	    "dir1": "right", 
	    "dir2": "up", 
	    "push": "top"
	};
	var stack_custom = {
	    "dir1": "right", 
	    "dir2": "down"
	};
	var stack_custom2 = {
	    "dir1": "left", 
	    "dir2": "up", 
	    "push": "top"
	};
	var stack_bar_top = {
	    "dir1": "down", 
	    "dir2": "right", 
	    "push": "top", 
	    "spacing1": 0, 
	    "spacing2": 0
	};
	var stack_bar_bottom = {
	    "dir1": "up", 
	    "dir2": "right", 
	    "spacing1": 0, 
	    "spacing2": 0
	};
	var stack_bottomright = {
	    "dir1": "up", 
	    "dir2": "left", 
	    "firstpos1": 25, 
	    "firstpos2": 25
	};
	
	function getOpts(type, msg){
		opts = {
			title : "注意",
			text : msg
		};
		
	    switch (type) {
	        case 'error':
	            opts.title = "错误";
	            opts.type = "error";
	            break;
	        case 'info':
	            opts.title = "提示";
	            opts.type = "info";
	            break;
	        case 'success':
	            opts.title = "成功";
	            opts.type = "success";
	            break;
	    };
	    
	    return opts;
	}

	function show_stack_topleft(type, msg) {
		var opts = getOpts(type, msg);
		opts.addclass = "stack-topleft";
		opts.stack = stack_topleft;
		
	    $.pnotify(opts);
	}

	function show_stack_bottomleft(type, msg) {
		var opts = getOpts(type, msg);
		opts.addclass = "stack-bottomleft";
		opts.stack = stack_bottomleft;
		
	    $.pnotify(opts);
	}

	function show_stack_bottomright(type, msg) {
		var opts = getOpts(type, msg);
		opts.addclass = "stack-bottomright";
		opts.stack = stack_bottomright;
	   
	    $.pnotify(opts);
	}

	
	function show_stack_bar_top(type, msg) {
		var opts = getOpts(type, msg);
		
		opts.addclass = "stack-bar-top";
		opts.stack = stack_bar_top;
		opts.cornerclass = "";
		opts.width = "100%";
		
	    $.pnotify(opts);
	}

	function show_stack_bar_bottom(type, msg) {
		var opts = getOpts(type, msg);
		opts.addclass = "stack-bar-bottom";
		opts.stack = stack_bar_bottom;
		opts.cornerclass = "";
		opts.width = "100%";
		
	    $.pnotify(opts);
	}
	
	function show_stack_custom(type, msg) {
		var opts = getOpts(type);
		opts.addclass = "stack-custom";
		opts.stack = stack_custom;
		
	    $.pnotify(opts);
	}

	function show_stack_custom2(type, msg) {
		var opts = getOpts(type);
		opts.addclass = "stack-custom2";
		opts.stack = stack_custom2;
		
	    $.pnotify(opts);
	}

	$.extend({
		replyJson: function(jsonResult){
			if (jsonResult && jsonResult.type) {
				$.recruit.notify(jsonResult.type, jsonResult.message);
			} else{
				throw Exception("Format of JsonResult is error");
			}
		},
		notify: function(type, message, center){
			if (center) {
				show_stack_bar_top(type,message);
			} else {
				show_stack_bottomright(type,message);
			};
			
		}
	});
	
	 $.pnotify.defaults.delay = 3000;
	 $.pnotify.defaults.styling = "bootstrap";
})(jQuery);
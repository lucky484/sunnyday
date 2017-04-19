
//===============bootstrap comfirm/alert start =============
			window.Modal=function(){
			var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
			var alr=$("#warningModal");
			var ahtml=alr.html();
			var _alert = function (options) {
			      alr.html(ahtml);	// 复原
			      _dialog(options);

			      return {
			        on: function (callback) {
			          if (callback && callback instanceof Function) {
			            alr.find('.btn-primary').click(function () { callback(true) });
			          }
			        }
			      };
			    };
			    
			    
			    var _confirm = function (options) {
			        alr.html(ahtml); // 复原
			        _dialog(options);

			        return {
			          on: function (callback) {
			            if (callback && callback instanceof Function) {
			              alr.find('.btn-primary').click(function () { callback(true) });
			              alr.find('.btn-default').click(function () { callback(false) });
			            }
			          }
			        };
			      };
			      
			      var _dialog = function (options) {
			          var ops = {
			           
			          };

			          $.extend(ops, options);
			          
			          var html = alr.html().replace(reg, function (node, key) {
			              return {
			                Title: ops.title,
			                Message: ops.msg,
			                BtnOk: ops.btnok,
			                BtnCancel: ops.btncl
			              }[key];
			            });
			          
			          alr.html(html);
			          alr.modal({
			            width: 500,
			            backdrop: 'static'
			          });
			        }
			      
			      return {
			          alert: _alert,
			          confirm: _confirm
			        }
		}();
		//===============bootstrap comfirm/alert end =============
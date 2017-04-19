/* ===========================================================
 * bootstrap-fileupload.js j2
 * http://jasny.github.com/bootstrap/javascript.html#fileupload
 * ===========================================================
 * Copyright 2012 Jasny BV, Netherlands.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */

!function ($) {

  "use strict"; // jshint ;_

 /*
	 * FILEUPLOAD PUBLIC CLASS DEFINITION =================================
	 */

  var Fileupload = function (element, options) {
    this.$element = $(element)
    this.type = this.$element.data('uploadtype') || (this.$element.find('.thumbnail').length > 0 ? "image" : "file")
      
    this.$input = this.$element.find(':file')
    if (this.$input.length === 0) return

    this.name = this.$input.attr('name') || options.name

    this.$hidden = this.$element.find('input[type=hidden][name="'+this.name+'"]')
    if (this.$hidden.length === 0) {
      this.$hidden = $('<input type="hidden" />')
      this.$element.prepend(this.$hidden)
    }

    this.$preview = this.$element.find('.fileupload-preview')
    var height = this.$preview.css('height')
    if (this.$preview.css('display') != 'inline' && height != '0px' && height != 'none') this.$preview.css('line-height', height)

    this.original = {
      'exists': this.$element.hasClass('fileupload-exists'),
      'preview': this.$preview.html(),
      'hiddenVal': this.$hidden.val()
    }
    
    this.$remove = this.$element.find('[data-remove="fileupload"]');
    this.$clear = this.$element.find('[data-dismiss="fileupload"]');
    this.$add = this.$element.find('[data-add="fileupload"]');
    this.$element.find('[data-trigger="fileupload"]').on('click.fileupload', $.proxy(this.trigger, this));
    
    this.listen();
  }
  
  Fileupload.prototype = {
    
    listen: function() {
      this.$input.on('change.fileupload', $.proxy(this.change, this));
      $(this.$input[0].form).on('reset.fileupload', $.proxy(this.reset, this));
      if (this.$clear) { 
    	  this.$clear.on('click.fileupload', $.proxy(this.clear, this));
      }
      if (this.$remove) { 
    	  this.$remove.on('click.fileupload', $.proxy(this.remove, this));
      }
      if (this.$add) this.$add.on('click.fileupload', $.proxy(this.add, this));
    },
    
    change: function(e, invoked) {
      var file = e.target.files !== undefined ? e.target.files[0] : (e.target.value ? { name: e.target.value.replace(/^.+\\/, '') } : null)
      if (invoked === 'clear') return
      
      if (!file) {
        this.clear()
        return
      }
      
      this.$hidden.val('');
      this.$hidden.attr('name', '');
      this.$input.attr('name', this.name);
      this.$element.find('input[name="deleteFlag"]').val("0");

      if (this.type === "image" && this.$preview.length > 0 && (typeof file.type !== "undefined" ? file.type.match('image.*') : file.name.match('\\.(gif|png|jpe?g)$')) && typeof FileReader !== "undefined") {
        var reader = new FileReader()
        var preview = this.$preview
        var element = this.$element

        reader.onload = function(e) {
          preview.html('<img src="' + e.target.result + '" ' + (preview.css('max-height') != 'none' ? 'style="max-height: ' + preview.css('max-height') + ';"' : '') + ' />')
          element.addClass('fileupload-exists').removeClass('fileupload-new')
        }

        reader.readAsDataURL(file)
      } else {
        this.$preview.text(file.name)
        this.$element.addClass('fileupload-exists').removeClass('fileupload-new')
      }
    },

    clear: function(e) {
      this.$hidden.val('');
      this.$hidden.attr('name', this.name);
      this.$input.attr('name', '');
      this.$element.find('input[name="deleteFlag"]').val("1");
      // ie8+ doesn't support changing the value of input with type=file so
		// clone instead
      if($.browser.msie){
          var inputClone = this.$input.clone(true);
          this.$input.after(inputClone);
          this.$input.remove();
          this.$input = inputClone;
      }else{
          this.$input.val('');
      }
      this.$preview.html('');
      this.$element.addClass('fileupload-new').removeClass('fileupload-exists');
      if (e) {
        this.$input.trigger('change', [ 'clear' ]);
        e.preventDefault();
      }
    },
    add:function(e) {
    	var $append = '<div class="fileupload fileupload-new" data-provides="fileupload">' +
		'<div class="input-append">' +
		'<div class="uneditable-input">' +
		'<i class="icon-file fileupload-exists"></i> ' +
		'<span class="fileupload-preview"></span>' +
		'</div>' + 
		'<span class="btn btn-file">' + 
		'<span class="fileupload-new"><i class="icon-file"></i> 浏览</span>' +  
		'<span class="fileupload-exists"><i class="icon-undo"></i> 变更</span>' +
		'<input name="file" type="file" class="default"></span>' +
		'<a href="#" class="btn fileupload-exists" data-remove="fileupload">' +
		'<i class="icon-trash"></i> 删除</a>' +  
		'<a href="#" class="btn fileupload-exists" data-add="fileupload"><i class="icon-plus"></i> 继续添加</a>' +
		'</div>' +
		'</div>';
//    	var $append = this.$element.clone();
//    	$append.insertBefore(this.$element);
    	this.$element.parent().append($append);
//    	this.$element.find('input[name="fileId"]').val("");
    	if (e) {
	        //this.$input.trigger('change', [ 'add' ]);
	        e.preventDefault();
    	}
    },
    remove:function(e) {
    	if(this.$element.parent().children().length <= 1) {
    		 this.$hidden.val('');
    	      this.$hidden.attr('name', this.name);
    	      this.$input.attr('name', '');
    	      this.$element.find('input[name="deleteFlag"]').val("1");
    	      // ie8+ doesn't support changing the value of input with
				// type=file so
    			// clone instead
    	      if($.browser.msie){
    	          var inputClone = this.$input.clone(true);
    	          this.$input.after(inputClone);
    	          this.$input.remove();
    	          this.$input = inputClone;
    	      }else{
    	          this.$input.val('');
    	      }
    	      this.$preview.html('');
    	      this.$element.addClass('fileupload-new').removeClass('fileupload-exists');
    		return false;
    	}
    	this.$element.remove();
    	if (e) {
	        this.$input.trigger('change', [ 'remove' ]);
	        e.preventDefault();
    	}
    },
    reset: function(e) {
      this.clear()
      this.$hidden.val(this.original.hiddenVal)
      this.$preview.html(this.original.preview)
      if (this.original.exists) this.$element.addClass('fileupload-exists').removeClass('fileupload-new')
       else this.$element.addClass('fileupload-new').removeClass('fileupload-exists')
    },
    
    trigger: function(e) {
      this.$input.trigger('click')
      e.preventDefault()
    }
  }

  
 /*
	 * FILEUPLOAD PLUGIN DEFINITION ===========================
	 */

  $.fn.fileupload = function (options) {
    return this.each(function () {
      var $this = $(this)
      , data = $this.data('fileupload')
      if (!data) $this.data('fileupload', (data = new Fileupload(this, options)))
      if (typeof options == 'string') data[options]()
    })
  }

  $.fn.fileupload.Constructor = Fileupload


 /*
	 * FILEUPLOAD DATA-API ==================
	 */

  $(function () {
    $('body').on('click.fileupload.data-api', '[data-provides="fileupload"]', function (e) {
      var $this = $(this)
      if ($this.data('fileupload')) return
      $this.fileupload($this.data())
      
      var $target = $(e.target).is('[data-dismiss=fileupload],[data-trigger=fileupload],[data-remove=fileupload],[data-add=fileupload]') ?
        $(e.target) : $(e.target).parents('[data-dismiss=fileupload],[data-trigger=fileupload],[data-remove=fileupload],[data-add=fileupload]').first()
      if ($target.length > 0) {
          $target.trigger('click.fileupload')
          e.preventDefault()
      }
    })
  })

}(window.jQuery);
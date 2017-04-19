$(function() {

    //展开收缩

    $('#screen').on('click', function() {

        var _this = $(this),
            topbar = $('.header'),
            sidebar = $('.navbar'),
            wrapper = $('.wrapper'),
            main = $('.page-wrapper');

        if ($(this).hasClass('full')) {

            // 缩小
            topbar.animate({
                top: 0
            }, 300);
            wrapper.animate({
                marginTop: '63px'
            }, 300, function() {

                main.animate({
                    marginLeft: '13%'
                }, 500);
                sidebar.animate({
                    left: 0
                }, 500);

                _this.removeClass('full').find('i').text('全屏展示');

            });

            

        } else {

            // 全屏
            sidebar.animate({
                left: '-13%'
            }, 500);
            main.animate({
                marginLeft: 0
            }, 500, function() {

                wrapper.animate({
                    marginTop: 0
                }, 300);
                topbar.animate({
                    top: '-63px'
                }, 300);

                _this.addClass('full').find('i').text('实际大小');

            });

            
        }
    })

})

/* fancybox 弹出框--ligang.yang--20160120 */
$.extend($.fancybox.defaults,{
    padding:0,
    closeBtn:false
})
$(function() {

    //展开收缩

    $('#screen').on('click', function() {

        var _this = $(this),
            topbar = $('.header'),
            sidebar = $('.navbar'),
            wrapper = $('.wrapper'),
            main = $('.page-wrapper');

        if ($(this).hasClass('full')) {

            // 缩小
            topbar.animate({
                top: 0
            }, 300);
            wrapper.animate({
                marginTop: '63px'
            }, 300, function() {

                main.animate({
                    marginLeft: '13%'
                }, 500);
                sidebar.animate({
                    left: 0
                }, 500);

                _this.removeClass('full').find('i').text('全屏展示');

            });

            

        } else {

            // 全屏
            sidebar.animate({
                left: '-13%'
            }, 500);
            main.animate({
                marginLeft: 0
            }, 500, function() {

                wrapper.animate({
                    marginTop: 0
                }, 300);
                topbar.animate({
                    top: '-63px'
                }, 300);

                _this.addClass('full').find('i').text('实际大小');

            });

            
        }
    })

})
var refreshTimer = null,
    mebook = mebook || {};
 
/*
*滚动结束 屏幕静止一秒后检测哪些图片出现在viewport中
*和PC端不同 由于无线速度限制 和手机运算能力的差异 1秒钟的延迟对手机端的用户来说可以忍受
*/
$(window).on('scrollstop', function () {
    if (refreshTimer) {
        clearTimeout(refreshTimer);
        refreshTimer = null;
    }
    refreshTimer = setTimeout(refreshAll, 1e3);
});
     
     
$.belowthefold = function (element) {
    var fold = $(window).height() + $(window).scrollTop();
    return fold <= $(element).offset().top;
};

$.abovethetop = function (element) {
    var top = $(window).scrollTop();
    return top >= $(element).offset().top + $(element).height();
};

/*
*判断元素是否出现在viewport中 依赖于上两个扩展方法
*/
$.inViewport = function (element) {
    return !$.belowthefold(element) && !$.abovethetop(element)
};

mebook.getInViewportList = function () {
    var list = $('#bookList li'),
        ret = [];
    list.each(function (i) {
        var li = list.eq(i);
        if ($.inViewport(li)) {
            mebook.loadImg(li);
        }
    });
};

mebook.loadImg = function (li) {
    if (li.find('img[_src]').length) {
        var img = li.find('img[_src]'),
            src = img.attr('_src');
        img.attr('src', src).load(function () {
            img.removeAttr('_src');
        });
    }
};
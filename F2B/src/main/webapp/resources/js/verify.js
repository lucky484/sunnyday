
/******************************* 编辑功能验证 *******************************/

/**
 * 【easyUI内置了4个验证方法】
 *
 * email: Match email regex rule.
 * url: Match URL regex rule.
 * length[0,100]: Between x and x characters allowed.
 * remote['http://.../action.do','paramName']: Send ajax request to do validate value, return 'true' when successfully.
*/

/**
 * 通过【正则表达式】匹配是否验证通过
 * ex: verifyCharacterType[/^[a-zA-Z0-9_]{1,}$/, '用户名为6-20英文、下划线或数字组成']
 */
$.extend($.fn.validatebox.defaults.rules, {
    verifyCharacterType: {
        validator: function(value, param){
            if(!value.match(param[0])){
                return false;
            }
            return true;
        },
        message: '{1}'
    }
});

/**
 * 验证字符长度在限定范围内
 * ex: verifyLength[6, 20, '用户名为6-20英文、下划线或数字组成']
 */
$.extend($.fn.validatebox.defaults.rules, {
    verifyLength: {
        validator: function(value, param){
            if(value.length < param[0] || value.length > param[1]){
                return false;
            }
            return true;
        },
        message: '{2}'
    }
});

/**
 * 验证数据库中是否已存在相同的信息
 * ex: verifyEqualsRemote['<c:url value="/agent/checkIsAgentNameNotExist.action" />','agentDjid=${agent.djid}', '用户名已经存在，请重新填写']
 */
$.extend($.fn.validatebox.defaults.rules, {
    verifyEqualsRemote: {
        validator: function(value,param){
            param[1] += value;
            var kk = $.ajax({url:param[0], dataType:"json", data:param[1], async:false, cache:false, type:"post"}).responseText;
            return kk=="true";
        },
        message: '{2}'
    }
});

/**
 * 验证2次信息输入是否相同
 * ex: verifyEquals['#password']
 */
$.extend($.fn.validatebox.defaults.rules, {
    verifyEquals: {
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: '两次密码不一样'
    }
});

/**
 * 验证后缀名格式数组是否正确
 * ex: verifyImgType[[/.jpeg$/, /.jpg$/, /.png$/ , /.gif$/], '图片格式不对']
 */
$.extend($.fn.validatebox.defaults.rules, {
    verifyFileType: {
        validator: function(value1,param){
            var flag = false;
            $.each(param[0],function(key, value){
                if(value1.match(value)){
                    flag = true;
                    return false;;
                }
            });
            return flag;
        },
        message: '{1}'
    }
});

$(function(){
    $.extend($.fn.validatebox.defaults.rules, {
        CHS: {
            validator: function (value, param) {
                return /^[\u0391-\uFFE5]+$/.test(value);
            },
            message: '请输入汉字'
        },
        ZIP: {
            validator: function (value, param) {
                return /^[1-9]\d{5}$/.test(value);
            },
            message: '邮政编码不存在'
        },
        QQ: {
            validator: function (value, param) {
                return /^[1-9]\d{4,10}$/.test(value);
            },
            message: 'QQ号码不正确'
        },
        mobile: {
            validator: function (value, param) {
                return /^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/.test(value);
            },
            message: '手机号码不正确'
        },
        loginName: {
            validator: function (value, param) {
                return /^[\u0391-\uFFE5\w]+$/.test(value);
            },
            message: '登录名称只允许汉字、英文字母、数字及下划线。'
        },
        safepass: {
            validator: function (value, param) {
                return safePassword(value);
            },
            message: '密码由字母和数字组成，至少6位'
        },
        number: {
            validator: function (value, param) {
                return /^\d+$/.test(value);
            },
            message: '请输入数字'
        },
        idcard: {
            validator: function (value, param) {
                return idCard(value);
            },
            message:'请输入正确的身份证号码'
        }
    });
});

/* 密码由字母和数字组成，至少6位 */
var safePassword = function (value) {
    return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
}

var idCard = function (value) {
    if (value.length == 18 && 18 != value.length) return false;
    var number = value.toLowerCase();
    var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
    var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
    if (re == null || a.indexOf(re[1]) < 0) return false;
    if (re[2].length == 9) {
        number = number.substr(0, 6) + '19' + number.substr(6);
        d = ['19' + re[4], re[5], re[6]].join('-');
    } else d = [re[9], re[10], re[11]].join('-');
    if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
    for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
    return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
}

var isDateTime = function (format, reObj) {
    format = format || 'yyyy-MM-dd';
    var input = this, o = {}, d = new Date();
    var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
    var len = f1.length, len1 = f3.length;
    if (len != f2.length || len1 != f4.length) return false;
    for (var i = 0; i < len1; i++) if (f3[i] != f4[i]) return false;
    for (var i = 0; i < len; i++) o[f1[i]] = f2[i];
    o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
    o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
    o.dd = s(o.dd, o.d, d.getDate(), 31);
    o.hh = s(o.hh, o.h, d.getHours(), 24);
    o.mm = s(o.mm, o.m, d.getMinutes());
    o.ss = s(o.ss, o.s, d.getSeconds());
    o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
    if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0) return false;
    if (o.yyyy < 100) o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
    d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
    var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM && d.getDate() == o.dd && d.getHours() == o.hh && d.getMinutes() == o.mm && d.getSeconds() == o.ss && d.getMilliseconds() == o.ms;
    return reVal && reObj ? d : reVal;
    function s(s1, s2, s3, s4, s5) {
        s4 = s4 || 60, s5 = s5 || 2;
        var reVal = s3;
        if (s1 != undefined && s1 != '' || !isNaN(s1)) reVal = s1 * 1;
        if (s2 != undefined && s2 != '' && !isNaN(s2)) reVal = s2 * 1;
        return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
    }
};
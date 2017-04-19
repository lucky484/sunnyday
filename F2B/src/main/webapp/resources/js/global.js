/*****************************************************************************************************/
/***************************************** Val Methods Start *****************************************/
/*****************************************************************************************************/

/**
 * 打开对应的窗口并加载相应的URL内容
 *
 * @param winName 窗口ID
 * @param refreshUrl 需要加载的页面内容来源，例如:http://www.baidu.com
 */
function openIFrameWindow(winName, frameURL, title){

    var content = '<iframe src="' + frameURL + '" class="span12" style="height:100%; width:100%; border:none; margin-bottom:-3px;" name="mainframe" id="mainframe"></iframe>'

    //var reSizeHeight = vSugar.getMaxWinHeight("mainPanel", 600) - 26;
    var reSizeHeight = vSugar.getMaxWinHeight("mainPanel", 11600) - 80;
    var targetEasyDom = $(winName);
    if(title){
        targetEasyDom.panel('setTitle', title);
    }
    targetEasyDom.panel('resize',{height:reSizeHeight});
    targetEasyDom.window('center');
    targetEasyDom.window("body").html(content);
    targetEasyDom.window('open');
    targetEasyDom.window('refresh', '');
}

function openWindow(winName, refreshUrl, title){
    var reSizeHeight = vSugar.getMaxWinHeight("mainPanel", 600) - 26;
    var targetEasyDom = $(winName);
    if(title){
        targetEasyDom.panel('setTitle', title);
    }
    targetEasyDom.panel('resize',{height:reSizeHeight});
    targetEasyDom.window('center');
    targetEasyDom.window('open');
    targetEasyDom.window('refresh', refreshUrl);
}

function openWindowFixed(winName, refreshUrl, fixWinName, fixHeight){
    var reSizeHeight = vSugar.getMaxWinHeight(fixWinName, 600) - fixHeight;
    var targetEasyDom = $(winName);
    targetEasyDom.panel('resize',{height:reSizeHeight});
    targetEasyDom.window('center');
    targetEasyDom.window('open');
    targetEasyDom.window('refresh', refreshUrl);
}

function openWindowWithoutResize(winName, refreshUrl){
    var targetEasyDom = $(winName);
    targetEasyDom.window('center');
    targetEasyDom.window('open');
    targetEasyDom.window('refresh', refreshUrl);
}

function openWindowWithoutResize2(winName, refreshUrl, title){
    var targetEasyDom = $(winName);
    if(title){
        targetEasyDom.panel('setTitle', title);
    }
    targetEasyDom.window('center');
    targetEasyDom.window('open');
    targetEasyDom.window('refresh', refreshUrl);
}

function openWindowOnly(winName){
    $(winName).window('open');
}

function closeWindow(winName){
    $(winName).window('close');
}

var vEasyUIUtil = {

    /**
     * 创建一个WINDOW
     *
     * @param winName 窗口ID，可以创建多个窗口
     * @param setting easyUI窗口的配置参数，例如:{width:600,height:400}
     */
    createWindow:function(winName, setting){
        var popWindow = $("#"+winName);
        if(popWindow.length == 0){
            popWindow = $('<div id="' + winName + '" title="Basic Window" style="padding:10px;"></div>');
            $(document.body).append(popWindow);
            popWindow.window(setting);
        }
    },

    showAjaxError:function(winName, winContent){
        this.createWindow(winName, {
            title: '系统错误,请提供以下信息给管理员:',
            width:'80%',
            height:vSugar.getMaxWinHeight("mainPanel", 600),
            modal:true,
            closed:true,
            collapsible:false,
            minimizable:false,
            maximizable:true,
            iconCls:'icon-no',
            onClose: function(){
                $(this).html("");
            }
        });
        $('#' + winName).html(winContent);
        openWindowOnly('#' + winName);
    },

    showMsg:function(msg){
        msg = ""+//"<img src='http://q.qlogo.cn/qqapp/100224785/366E4173050DF0110B4779356FFEEE82/100' style='height:50px' />" +
            "&nbsp;&nbsp;&nbsp;&nbsp;<div style='font-size:18px;'>" + msg +
            "</div>";
        $.messager.show({
            title:'信息',
            msg:msg,
            timeout:2000,
            showType:'fade',
            style:{
                top:document.body.scrollTop+document.documentElement.scrollTop + 160
                /*left:'',
                right:20,
                top:document.body.scrollTop+document.documentElement.scrollTop+20,
                bottom:''*/
            }
        });
    },

    ajaxHandle:function (param){
        $.messager.confirm('Confirm','确认提交?',function(r){
            if (r){
                vEasyUIUtil.ajaxHandleNoConfirm(param);
            }
        });
    },

    ajaxHandleNoConfirm:function (param) {

        $.messager.progress({
            title: '操作中',
            msg: '正在操作。。。'
        });

        $.ajax({
            url: param.v_url,
            data:param.v_data,
            complete: function (xhr, textStatus) {
                $.messager.progress('close');
                if (textStatus == "error") {
                    vEasyUIUtil.showAjaxError("errorWin", xhr.responseText);
                    return;
                }

                if (xhr.responseText == 1) {
                    param.v_callback();
                } else {
                    $.messager.alert("信息", xhr.responseText, "info");
                }
            }
        });
        /*if (!confirm("确认提交")) {
            return;
        }*/
    },

    ajaxHandleJSON:function (param) {

        $.messager.progress({
            title: '操作中',
            msg: '正在操作。。。'
        });

        $.ajax({
            url: param.v_url,
            data:param.v_data,
            complete: function (xhr, textStatus) {
                $.messager.progress('close');
                if (textStatus == "error") {
                    vEasyUIUtil.showAjaxError("errorWin", xhr.responseText);
                    return;
                }

                var result = $.parseJSON(xhr.responseText);
                if(result.code == 1){
                    param.v_callback();
                } else {
                    $.messager.alert("信息", result.desc, "info");
                }
            }
        });
    },

    createColumnMenu:function(dgName, menuName, fixedField, ignoreField){
        var dgQueryName = "#" + dgName;
        var cMenu = $('<div id="' + menuName + '"></div>').appendTo('body');
        cMenu.menu({
            onClick: function(item){
                if (item.iconCls == 'icon-ok'){
                    $(dgQueryName).datagrid('hideColumn', item.name);
                    cMenu.menu('setIcon', {
                        target: item.target,
                        iconCls: 'icon-empty'
                    });
                } else {
                    $(dgQueryName).datagrid('showColumn', item.name);
                    cMenu.menu('setIcon', {
                        target: item.target,
                        iconCls: 'icon-ok'
                    });
                }
            }
        });
        var fields = $(dgQueryName).datagrid('getColumnFields');
        for(var i=0; i<fields.length; i++){
            var field = fields[i];
            var col = $(dgQueryName).datagrid('getColumnOption', field);
            var iconClsS;
            var isDisabled = false;
            if(vSugar.isTargetExistInArray(ignoreField, field)){
                continue;
            }
            if(vSugar.isTargetExistInArray(fixedField, field)){
                isDisabled = true;
            }
            if(col.hidden){
                iconClsS = 'icon-empty';
            }
            else{
                iconClsS = 'icon-ok';
            }
            cMenu.menu('appendItem', {
                id: field + "This",
                text: col.title,
                name: field,
                disabled: isDisabled,
                iconCls: iconClsS
            });
        }
    },

    getDataGridColumn:function(dgName, menuName, ignoreField){
        var fields = $("#" + dgName).datagrid('getColumnFields');
        var selectFields = "";
        var selectFieldsName = "";

        for(var i=0; i<fields.length; i++){
            var field = fields[i];
            if(vSugar.isTargetExistInArray(ignoreField, field)){
                continue;
            }
            var kk = $("#" + menuName).menu("getItem", $("#"+field+"This")[0]);
            if(kk.iconCls == 'icon-ok'){
                selectFields += "," + field;
                selectFieldsName += "," + kk.text;
            }
        }
        if(selectFields.length > 0){
            selectFields = selectFields.substr(1, selectFields.length);
        }
        if(selectFieldsName.length > 0){
            selectFieldsName = selectFieldsName.substr(1, selectFieldsName.length);
        }

        var arrayObj = new Array(2);
        arrayObj[0] = selectFields;
        arrayObj[1] = selectFieldsName;
        return arrayObj;
    }
};

var vSugar = {
    isTargetExistInArray:function(mainArray, target){
        var isExist = false;
        $.each(mainArray, function(i, n){
            if(target == n){
                isExist = true;
            }
        });
        return isExist;
    },

    getMaxWinHeight:function(domName, maxHeight){
        var winHeight = $("."+domName).height();
        if(winHeight > maxHeight){
            return maxHeight;
        }
        else{
            return winHeight;
        }
    }
}

/*****************************************************************************************************/
/****************************************** Val Methods End ******************************************/
/*****************************************************************************************************/

/**
 * 警示信息提示
 * @param msg
 */
function woringMessage(msg){
    $(".checkInput").css({"height":"36px","width":"262px"});
    $(".checkInput font").html(msg);
    $(".checkInput").show();
}

/**
 *  列表页底部分页栏设置
 *  @param dataGridId datagrid的id值
 */
function formatPagination(dataGridId){
    if(!$("#"+dataGridId)){
        return ;
    }
    $('#'+dataGridId).datagrid('getPager').pagination({//分页栏下方文字显示
        beforePageText:' 第 ',
        afterPageText:'  / {pages} 页 ',
        displayMsg:'当前显示第 {from} - {to} 条记录 | 共 {total} 条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
        //pageSize : 100,
        pageList : [20,50,100,200]
    });
}

/**
 * 扩展Date的format方法
 *
 * 调用方法：new Date(value).format("yyyy-MM-dd hh:mm:ss");
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

/**
 *转换日期对象为日期字符串
 * @param date 日期对象（不能是字符串）
 * @param isFull 是否为完整的日期数据,
 *               为true时, 格式如"2000-03-05 01:05:04"
 *               为false时, 格式如 "2000-03-05"
 * @return 符合要求的日期字符串
 */
function getSmpFormatDate(date, isFull) {
    var pattern = "";
    if (isFull == false || isFull == undefined) {
        pattern = "yyyy-MM-dd";

    } else {
        pattern = "yyyy-MM-dd hh:mm:ss";
    }
    if (date == undefined) {
        date = new Date();
    }
    return date.format(pattern);
}

/**
 * 生成easyUI window 窗口的代码
 * @param content html代码
 * @param UIId 窗口ID 请尽量保持唯一
 * @param title 窗口的title文本
 * @param iconCls easyUI提供的窗口图标样式,如: icon-add icon-save 等
 * @param css 窗口样式,如 宽高等
 * @param param 提示信息参数（初始化时页面时，不需要此参数）
 * @return 返回创建的窗口内容的jQuery对象
 * */
function generateUIWindow(content , UIId , title , iconCls  , css,param ){
    //首先移除需要创建的窗口
    $("#" + UIId).remove();
    //清空窗口容器,功能类似 $("#tempContainer").html("");
    if(document.getElementById("tempContainer") == null){
        $(document.body).append("<div id='tempContainer'></div>");
    }
    if(!param){
        //是否显示消息容器
        param={showMessage:true};
    }
    $("#tempContainer").empty();
    //移除窗口容器,保证只有一个窗口容器存在
    $(".aesyui-window").remove();
    var HtmlStr = '<div id="'+ UIId +'" iconCls="'+ iconCls  +'" title="'+ title +'" class="easyui-window" style="width:300px;height:100px;padding:10px;" data-options="modal:true, collapsible:false, minimizable:false, maximizable:false, resizable:false">'
        +(param.showMessage?'<div style="height: 32px;"><div class="checkInput"><font color="red"></font></div></div>':"")
        +'</div>';
    var windowContainer = $(HtmlStr);
    windowContainer.append(content);
    if(!css){ //设置个性化窗口样式
        css={};
    }
    css.padding=0;
    if(!css.height){
        css.height = 'auto' ;
    }

    windowContainer.css(css);
    var tempContainer = $("#tempContainer").append(windowContainer);
    $.parser.parse(tempContainer); //渲染窗口容器以及其内容为easyUI样式
    //$(".window").corner();
    return windowContainer;
}

/**
 * ajax全局设置
 *
 * */
jQuery.ajaxSetup({
    cache: false,
    global: true,
    type: "POST",
    timeout: 80000,
    complete: function (xhr, textStatus) {
        if (xhr.status == 200) {
            return true;
        }
        if (xhr.status == 1001) {
            //拦截器配置了一个未检测到session则返回1001
            window.top.location = ccm_webRoot;
            return true;
        }
        var errMsg = "", errContent = "";
        switch (xhr.status) {
            case 4001 :
                errMsg = "禁止操作";
                errContent = "您没有权限操作";
                break;
            case 12029 :
                errMsg = "网络繁忙,请稍后再试";
                errContent = "当前网络状况欠佳,请重试或耐心等待.";
                break;
            case 0:
                errMsg = "网络繁忙,请稍后再试";
                errContent = "当前网络状况欠佳,请重试或耐心等待.";
                break;
            case 500:
                vEasyUIUtil.showAjaxError("errorWin", xhr.responseText);
                return true;
            case 502 :
                errMsg = "网关错误";
                errContent = "服务器正在重启,或您的网络,网关未能联网.";
                break;
            case 504 :
                errMsg = "网关错误";
                errContent = "服务器正在重启,或您的网络,网关未能联网.";
                break;
            default:
                errMsg = "未知错误";
                errContent = "发生了一些未知错误,请联系管理员.并提供以下信息: errorCode:" + xhr.status + ",状态文本" + xhr.statusText;
                break;
        }
        $.messager.alert(errMsg, errContent, 'error');
        return false;
    }
});

/**
 * 日期控件内日期格式化方法，初始化时调用
 *
 * @param date
 * @returns {string}
 */
function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function myparser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}

///**
// */
//$(function(){
//    $(".easyui-datebox").each(function(){
//        $(this).datebox({
//            formatter:myformatter,
//            parser:myparser
//        });
//    });
//
//});


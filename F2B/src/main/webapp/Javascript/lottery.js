
var t;
var i = 3;
//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
var curWwwPath = window.document.location.href;
//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
var pathName = window.document.location.pathname;
//21
var pos = curWwwPath.indexOf(pathName);
//获取主机地址，如： http://localhost:8083
var localhostPaht = curWwwPath.substring(0, pos);
//获取带"/"的项目名，如：/uimcardprj
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

var GoReceiveUrl = function (prizeId) {
    if (prizeId < 1 || prizeId == 5 || prizeId > 9) {
        window.location.href = localhostPaht+projectName+"/pages/thanks.jsp";
    }
    else {
        if (i == 0) {
            clearTimeout(t);
            //3.8京东，2.6.4红包,1.9iphone,否则，谢谢参与base64.encode64(),base64.decode64()
            if (prizeId == 3 || prizeId == 8) {
                window.location.href = localhostPaht+projectName+"/pages/jdRecivePrize.jsp?id=" + prizeId;
            }
            else if (prizeId == 2 || prizeId == 6 || prizeId == 4) {
                window.location.href = localhostPaht+projectName+"/pages/hbReceivePrize.jsp?id=" + prizeId;
            }
            else if (prizeId == 1 || prizeId == 9) {
                window.location.href = localhostPaht+projectName+"/pages/iphone.jsp";
            }
            else if(prizeId == 7)
            {
                window.location.href = localhostPaht+projectName+"/pages/iwatch.jsp";
            }
            else {
                window.location.href = localhostPaht+projectName+"/pages/thanks.jsp";
            }
        }
        else {
            i--;
            t = setTimeout(function () { GoReceiveUrl(prizeId) }, 1000);
        }
    }
}
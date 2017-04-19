<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>多模态高通量人员特征信息识别卡口系统</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/normalize.css">
        <link rel="stylesheet" href="css/main.css">
    </head>
    <body>
        <header>
            <div class="container">
                <div class="brand">
                    <img src="img/slogan.png" alt="多模态高通量人员特征信息识别卡口系统">
                </div>
                <div class="time">
                    当前时间 : <span id="curr-time">2015-12-01  12: 00: 00</span>
                </div>
            </div>
        </header>
        <div class="container wrapper">
            <div class="navbar">
                <div class="sidebar-collapse">
                    <ul class="nav">
                        <li>
                            <a href="#">
                                <span>首页</span>
                                <span class="icon icon-nav"></span>
                            </a>
                        </li>
                        <li class="active">
                            <a href="#">
                                <span>实时监控</span>
                                <span class="icon icon-nav"></span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>信息查询</span>
                                <span class="icon icon-nav"></span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>研判分析</span>
                                <span class="icon icon-nav"></span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>设备管理</span>
                                <span class="icon icon-nav"></span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>系统管理</span>
                                <span class="icon icon-nav"></span>
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="logout">
                    <a href="#">
                        <span class="icon i-logout"></span>
                        退出登录
                        <span class="icon i-arrow-left"></span>
                    </a>
                </div>
            </div>
            <div class="page-wrapper">
                <div class="page-con">
                    <div class="clearfix">
                        <div class="page-hd">
                            <h2 class="dib">实时监控</h2>
                            <div class="address dib">
                                北京火车站南广场入口1号门
                                <span class="icon i-arrow-down"></span>
                            </div>
                        </div>
                        <dl class="page-hd-data">
                            <dt>身份证采集数</dt>
                            <dd>999</dd>
                            <dt>人脸采集数</dt>
                            <dd>99999</dd>
                            <dt>报警数</dt>
                            <dd>5</dd>                         
                        </dl>
                    </div>
                    <div class="clearfix">
                        <div class="real-time-info">
                            <div class="real-box">
                                <div class="real-title">实时采集信息</div>
                                <div class="real-con clearfix">
                                    <div class="real-time-photo pull-left">
                                        <img src="img/real-time-photo.jpg" alt="">
                                        <img src="img/real-time-photo-full.jpg" class="full" alt="">
                                        <div class="get-time">10:59:58</div>
                                    </div>
                                    <div class="his-photo pull-right">
                                        <div class="his-item">
                                            <img src="img/real-time-histroy-1.jpg" alt="">
                                            <span class="get-time">10:59:50</span>
                                        </div>
                                        <div class="his-item">
                                            <img src="img/real-time-histroy-2.jpg" alt="">
                                            <span class="get-time">10:59:49</span>
                                        </div>
                                        <div class="his-item">
                                            <img src="img/real-time-histroy-2.jpg" alt="">
                                            <span class="get-time">10:59:48</span>
                                        </div>
                                        <div class="his-item">
                                            <img src="img/real-time-histroy-3.jpg" alt="">
                                            <span class="get-time">10:59:47</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="id-check-box">
                                <div class="id-info">
                                    <div class="id-img">
                                        <img src="img/id-photo.jpg" width="98" height="124" alt="">
                                        <span>证件照</span>
                                    </div>
                                    <div class="id-spec">
                                        <div class="id-item">
                                            <label>身份号码</label>
                                            <span>32092119XXXXXXX001</span>
                                        </div>
                                        <div class="id-item">
                                            <label>姓名</label>
                                            <span>王小化</span>
                                        </div>
                                        <div class="id-item">
                                            <label>性别</label>
                                            <span>男</span>
                                        </div>
                                        <div class="id-item">
                                            <label>民族</label>
                                            <span>汉</span>
                                        </div>
                                        <div class="id-item">
                                            <label>出生</label>
                                            <span>19XX年XX月XX日</span>
                                        </div>
                                        <div class="id-item">
                                            <label>住址</label>
                                            <span>江苏省无锡市XXXXXX<br>XXXXXXXXXXX</span>
                                        </div>
                                        <div class="id-item">
                                            <label>签发机关</label>
                                            <span>无锡市公安局XX分局</span>
                                        </div>
                                        <div class="id-item">
                                            <label></label>
                                        </div>
                                        <div class="blue">采集时间：2015-01-01 11:00:00</div>
                                    </div>
                                </div>
                                <div class="id-list">
                                    <table>
                                        <tbody>
                                           
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="js/vendor/jquery-1.11.3.min.js"></script>
        <script src="js/plugins.js"></script>
        <script src="js/main.js"></script>
        <script>
            function getdate(id) {
                  var today = new Date();
                  var y = today.toLocaleDateString();
                  var h = today.getHours();
                  var m = today.getMinutes();
                  var s = today.getSeconds();
                  if (s < 10) {
                        s = "0" + s;
                  }

                  $(id).text(y + " " +h + " : " + m + " : " + s);
                  setTimeout(function() {
                        getdate(id)
                  }, 500);
            }
            $(function(){

                getdate('#curr-time')

            });
        </script>
    </body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container">
                    <div class="row">
                        <div class="col-md-2">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <span class="label label-success pull-right">月</span>
                                    <h5>浏览量</h5>
                                </div>
                                <div class="ibox-content">
                                    <h1 class="no-margins">386,200</h1>
                                    <div class="stat-percent font-bold text-success">98% <i class="fa fa-bolt"></i>
                                    </div>
                                    <small>总计浏览量</small>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <span class="label label-info pull-right">年</span>
                                    <h5>订单</h5>
                                </div>
                                <div class="ibox-content">
                                    <h1 class="no-margins">80,800</h1>
                                    <div class="stat-percent font-bold text-info">20% <i class="fa fa-level-up"></i>
                                    </div>
                                    <small>新订单</small>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <span class="label label-primary pull-right">今天</span>
                                    <h5>访问人次</h5>
                                </div>
                                <div class="ibox-content">

                                    <div class="row">
                                        <div class="col-md-6">
                                            <h1 class="no-margins">&yen; 406,420</h1>
                                            <div class="font-bold text-navy">44% <i class="fa fa-level-up"></i> <small>增长较快</small>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <h1 class="no-margins">206,120</h1>
                                            <div class="font-bold text-navy">22% <i class="fa fa-level-up"></i> <small>增长较慢</small>
                                            </div>
                                        </div>
                                    </div>


                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <h5>月收入</h5>
                                    <div class="ibox-tools">
                                        <span class="label label-primary">2015.02 更新</span>
                                    </div>
                                </div>
                                <div class="ibox-content no-padding">
                                    <div class="flot-chart m-t-lg" style="height: 55px;">
                                        <div class="flot-chart-content" id="flot-chart1"></div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-8">
                            <div class="ibox float-e-margins">
                                <div class="ibox-content">
                                    <div>
                                        <span class="pull-right text-right">
                                        <small>在过去的一个月销售的平均值：<strong>山东</strong></small>
                                            <br/>
                                            所有销售： 162,862
                                        </span>
                                        <h3 class="font-bold no-margins">
                                        半年收入利润率
                                    </h3>
                                        <small>市场部</small>
                                    </div>

                                    <div class="m-t-sm">

                                        <div class="row">
                                            <div class="col-md-8">
                                                <div>
                                                    <canvas id="lineChart" height="114"></canvas>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <ul class="stat-list m-t-lg">
                                                    <li>
                                                        <h2 class="no-margins">2,346</h2>
                                                        <small>总订单</small>
                                                        <div class="progress progress-mini">
                                                            <div class="progress-bar" style="width: 48%;"></div>
                                                        </div>
                                                    </li>
                                                    <li>
                                                        <h2 class="no-margins ">4,422</h2>
                                                        <small>最近一个月订单</small>
                                                        <div class="progress progress-mini">
                                                            <div class="progress-bar" style="width: 60%;"></div>
                                                        </div>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>

                                    </div>

                                    <div class="m-t-md">
                                        <small class="pull-right">
                                        <i class="fa fa-clock-o"> </i>
                                        2015.02.30更新
                                    </small>
                                        <small>
                                        <strong>说明：</strong> 本期销售额比上期增长了23%。
                                    </small>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <h5>用户行为统计</h5>
                                </div>
                                <div class="ibox-content">
                                    <div class="row">
                                        <div class="col-xs-4">
                                            <small class="stats-label">访问页面 / 浏览量</small>
                                            <h4>236 321.80</h4>
                                        </div>

                                        <div class="col-xs-4">
                                            <small class="stats-label">% 新访客</small>
                                            <h4>46.11%</h4>
                                        </div>
                                        <div class="col-xs-4">
                                            <small class="stats-label">最后一周</small>
                                            <h4>432.021</h4>
                                        </div>
                                    </div>
                                </div>
                                <div class="ibox-content">
                                    <div class="row">
                                        <div class="col-xs-4">
                                            <small class="stats-label">访问页面 / 浏览量</small>
                                            <h4>643 321.10</h4>
                                        </div>

                                        <div class="col-xs-4">
                                            <small class="stats-label">% 新访客</small>
                                            <h4>92.43%</h4>
                                        </div>
                                        <div class="col-xs-4">
                                            <small class="stats-label">最后一周</small>
                                            <h4>564.554</h4>
                                        </div>
                                    </div>
                                </div>
                                <div class="ibox-content">
                                    <div class="row">
                                        <div class="col-xs-4">
                                            <small class="stats-label">访问页面 / 浏览量</small>
                                            <h4>436 547.20</h4>
                                        </div>

                                        <div class="col-xs-4">
                                            <small class="stats-label">% 新访客</small>
                                            <h4>150.23%</h4>
                                        </div>
                                        <div class="col-xs-4">
                                            <small class="stats-label">最后一周</small>
                                            <h4>124.990</h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="row">

                        <div class="col-lg-12">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <h5>自定义响应表格</h5>
                                    <div class="ibox-tools">
                                        <a class="collapse-link">
                                            <i class="fa fa-chevron-up"></i>
                                        </a>
                                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                            <i class="fa fa-wrench"></i>
                                        </a>
                                        <ul class="dropdown-menu dropdown-user">
                                            <li><a href="#">设置选项1</a>
                                            </li>
                                            <li><a href="#">设置选项2</a>
                                            </li>
                                        </ul>
                                        <a class="close-link">
                                            <i class="fa fa-times"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="ibox-content">
                                    <div class="row">
                                        <div class="col-sm-9 m-b-xs">
                                            <div data-toggle="buttons" class="btn-group">
                                                <label class="btn btn-sm btn-white">
                                                    <input type="radio" id="option1" name="options">天</label>
                                                <label class="btn btn-sm btn-white active">
                                                    <input type="radio" id="option2" name="options">周</label>
                                                <label class="btn btn-sm btn-white">
                                                    <input type="radio" id="option3" name="options">月</label>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="input-group">
                                                <input type="text" placeholder="搜索" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary">搜索</button> </span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th>项目</th>
                                                    <th>进度</th>
                                                    <th>任务</th>
                                                    <th>日期</th>
                                                    <th>操作</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>米莫说｜MiMO Show</td>
                                                    <td><span class="pie">0.52/1.561</span>
                                                    </td>
                                                    <td>20%</td>
                                                    <td>2014.11.11</td>
                                                    <td><a href="table_basic.html#"><i class="fa fa-check text-navy"></i></a>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>商家与购物用户的交互试衣应用</td>
                                                    <td><span class="pie">6,9</span>
                                                    </td>
                                                    <td>40%</td>
                                                    <td>2014.11.11</td>
                                                    <td><a href="table_basic.html#"><i class="fa fa-check text-navy"></i></a>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>天狼---智能硬件项目</td>
                                                    <td><span class="pie">3,1</span>
                                                    </td>
                                                    <td>75%</td>
                                                    <td>2014.11.11</td>
                                                    <td><a href="table_basic.html#"><i class="fa fa-check text-navy"></i></a>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>线下超市+线上商城+物流配送互联系统</td>
                                                    <td><span class="pie">4,9</span>
                                                    </td>
                                                    <td>18%</td>
                                                    <td>2014.11.11</td>
                                                    <td><a href="table_basic.html#"><i class="fa fa-check text-navy"></i></a>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>

                                </div>
                            </div>
                        </div>

                    </div>

                </div>
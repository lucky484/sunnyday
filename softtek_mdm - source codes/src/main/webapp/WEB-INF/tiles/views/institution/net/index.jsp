<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<section class="row">
    <div class="blog-post">
        <div class="post-item">
            <section class="wrapper-md ">
                <div class="row">
                  <div class="col-sm-12">
                      <div class="panel panel-default">
                            <div class="panel-body">
                              <div id="netCharts" class="userTimeChart chart-mod">
                              </div>
                              <div class="table-responsive">
                                  <div class="col-sm-12 search_part">
                                      <div class="col-sm-2 searchName"><fmt:message key="tiles.views.institution.net.index.statistics"/></div>
                                      <div class="col-sm-4 searchName_val">
                                          <select id="searchWebsiteType" class="select_width" data-parsley-required="true">
                                              <option value=""><fmt:message key="tiles.views.institution.system.words.indexmodal.choose"/></option>
                                              <c:forEach items="${websiteList}" var="web">
                                                  <option value="${web.id}">${web.name}</option>
                                              </c:forEach>
                                          </select>
                                      </div>
                                      <div class="col-sm-4 search_button">
                                         <button class="btn btn-sm btn-primary search_icon" type="button" onclick="searchNet()"><fmt:message key="general.jsp.search.label"/></button>
                                         <button class="btn btn-sm btn-default reset_icon" type="button" onclick="cleanNet()"><fmt:message key="general.jsp.clean.label"/></button>
                                      </div>
                                  </div>
                                  <div class="col-sm-12">
                                      <table id="netTable" class="table table-striped b-t b-light">
                                            <thead>
                                                <tr>
                                                    <th><fmt:message key="tiles.views.institution.net.index.website"/></th>
                                                    <th><fmt:message key="tiles.views.institution.net.index.count"/></th>
                                                    <th><fmt:message key="tiles.views.institution.net.index.operate"/></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                      </table>
                                  </div>
                                </div>
                             </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</section>
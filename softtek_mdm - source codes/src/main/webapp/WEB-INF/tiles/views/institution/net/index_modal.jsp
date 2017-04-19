<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <div class="modal fade" id="myUrlModal" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" data-backdrop="static">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content" style="width:1000px;">
                <div class="modal-header b-b-l-none">
                    <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h3>
                        <fmt:message
                            key="tiles.views.institution.system.words.indexmodal.title" />
                    </h3>
                </div>
                <div class="modal-body" style="min-height:200px;">
                    <div class="row">
                        <input type="hidden" id="type" />
                        <div class="col-sm-12 search_part">
                            <div class="col-sm-2 searchName"><fmt:message key="tiles.views.institution.net.index.statistics"/></div>
                            <div class="col-sm-4 searchName_val">
                                <input id="searchName" type="text" class="input-sm form-control" />
                            </div>
                            <div class="col-sm-4 search_button">
                               <button class="btn btn-sm btn-primary search_icon" type="button" onclick="searchUrl()"><fmt:message key="general.jsp.search.label"/></button>
                               <button class="btn btn-sm btn-default reset_icon" type="button" onclick="cleanUrl()"><fmt:message key="general.jsp.clean.label"/></button>
                            </div>
                       </div>
                       <div class="col-sm-12">
                        <table id="urlTable" class="table table-striped b-t b-light" style="width:99%;">
                            <thead>
                                <tr>
                                    <th width="5%"><fmt:message
                                            key="tiles.views.institution.net.index.website" /></th>
                                    <th width="7%"><fmt:message
                                            key="tiles.views.institution.net.index.name" /></th>
                                    <th width="7%"><fmt:message
                                            key="tiles.views.institution.net.index.user.type" /></th>
                                    <th width="7%"><fmt:message
                                            key="tiles.views.institution.net.index.department" /></th>
                                    <th width="28%"><fmt:message
                                            key="tiles.views.institution.net.index.url" /></th>
                                     <th width="33%"><fmt:message
                                            key="tiles.views.institution.net.index.content" /></th>
                                    <th width="13%"><fmt:message
                                            key="tiles.views.institution.net.index.createdate" /></th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                       </div>
                    </div>
                </div>
                <div class="modal-footer" id="saveBtn">
                    <a href="javascript:void(0)" class="btn" data-dismiss="modal"><fmt:message
                            key="general.jsp.cancel.label" /></a>
                </div>
            </div>
        </div>
    </div>
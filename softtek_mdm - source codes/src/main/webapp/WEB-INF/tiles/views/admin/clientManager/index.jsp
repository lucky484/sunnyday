<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="content">
              <div class="hbox stretch">
                <div>
                  <div class="vbox">
                    <div class="scrollable padder">
                      <div class="panel panel-default">
                         <div class="gird-btns">
			    			<button class="btn btn-sm btn-success btn-rounded" style="margin-top: 7px;margin-left: 15px;" id="release">
                            <i class="fa fa-plus"></i>
                            &nbsp;<fmt:message key="tiles.institution.client.manager.upload" />
                          </button>
			    		</div>
                        <div class="panel-body">
                          <div class="table-responsive">
                            <table class="table table-striped b-t b-light" id="clientManager">
                              <thead>
                                <tr>
                                  <th class="th-sortable"><fmt:message key="tiles.institution.client.manager.client.mark" /></th>
                                  <th class="tb-w"><fmt:message key="tiles.institution.client.manager.client.name1" /></th>
                                  <th class="tb-w-max"><fmt:message key="tiles.institution.client.manager.belong.org" /></th>
                                  <th class="tb-w"><fmt:message key="tiles.institution.client.manager.client.version" /></th>
                                  <th class="tb-w"><fmt:message key="tiles.institution.client.manager.client.size" /></th>
                                  <th class="tb-w"><fmt:message key="tiles.institution.client.manager.upgrade" /></th>
                                  <th class="tb-w"><fmt:message key="tiles.institution.client.manager.platform"/></th>
                                  <th class="tb-w"><fmt:message key="tiles.institution.client.manager.insist.device" /></th>
                                  <th class="tb-w"><fmt:message key="tiles.views.customer.compliant.index.table.operation" /></th>
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
              </div>
            </div>
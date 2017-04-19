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
                        <header class="panel-heading font-bold">
                           <button class="btn btn-sm btn-primary" type="button" onclick="addWordsPage()"><fmt:message key="general.jsp.add.label"/></button>
                        </header>
                        <div class="panel-body">
                           	<div class="search-toggle">
					     		<a hidfocus="true"><fmt:message key="tiles.institution.comm.expand.search.tip" /></a>
					    	</div>
						    <div class="search-mod" style="display: none;">
			   					<ul class="search-list">
			   						<li class="search-item ">
			   							<label class="search-label" style="width:130px"><fmt:message key="tiles.views.institution.devicepolicy.index.policynm"/></label>
			   							<input type="text" id="searchName" name="searchName" class="search-text" style="width:120px;">
			   						</li>
			   					</ul>
								<div class="type-choice">
									<a class="button-search" type="button" onclick="searchWords();"><i class="fa fa-search"></i><span><fmt:message key="tiles.institution.policy.policy.search" /></span></a>
									<a class="button-search" type="button" onclick="cleanWords();"><i class="fa fa-trash-o"></i><span><fmt:message key="tiles.institution.policy.policy.clean" /></span></a>
								</div>
			   			  </div>
                          <div class="table-responsive">
<%--                           <div class="col-sm-12">
                                    <div class="col-sm-2 searchName"><fmt:message key="tiles.views.institution.devicepolicy.index.policynm"/></div>
                                    <div class="col-sm-4 searchName_val">
                                         <input id="searchName" type="text" class="input-sm form-control" />
                                    </div>
                                    <div class="col-sm-4 search_button">
                                         <button class="btn btn-sm btn-default search_icon" type="button" onclick="searchWords()"><fmt:message key="general.jsp.search.label"/></button>
                                         <button class="btn btn-sm btn-default reset_icon" type="button" onclick="cleanWords()"><fmt:message key="general.jsp.clean.label"/></button>
                                    </div>
                                </div> --%>
                              <table id="systemWordsTable" class="table table-striped b-t b-light">
                                  <thead>
                                     <tr>
                                         <th><fmt:message key="tiles.views.institution.system.words.index.name"/></th>
                                         <th><fmt:message key="tiles.views.institution.system.words.index.websitename"/></th>
                                         <th><fmt:message key="tiles.views.institution.system.words.index.operate"/></th>
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
            </section>
        </div>
    </div>
</section>
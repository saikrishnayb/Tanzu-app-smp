<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
  	<%@ include file="../../../jsp/global/v1/header.jsp" %>
  	
  	<link href="${baseUrl}/css/admin-console/security/org.css" rel="stylesheet" type="text/css"/>    
    <link href="${baseUrl}/css/admin-console/components/template.css" rel="stylesheet" type="text/css"/>
  </head>

  <body>

    <%@ include file="../../../jsp/global/navigation/sub-nav.jsp" %>
    
    <div id="mainContent">
    
      <%@ include file="../../global/navigation/admin-console/components/left-nav.jsp"%>
      
      <div class="leftNavAdjacentContainer">
        <div class="full-width">
          <span class="floatRight addRow"> 
            <a href="${baseAppUrl}/admin-console/components/create-modify-template-page.htm?isCreatePage=true &templateId=0">
              <span>Create Template</span>
              <img src="${commonStaticUrl}/images/add.png" class="centerImage handCursor" alt="Add Row"/>
            </a>
          </span>
          <table id="template-table">
            <thead>
              <tr>
                <th class="viewCol"></th>
                <th>PO Category</th>
                <th>PO Sub Category</th>
                <th>Description</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${templateList}" var="temp" varStatus="status">   
                <tr class="template-row">
                  <td class="editable">
                  <input id="status-${status.index}"  type=hidden value="${temp.active?'A':'I'}" />
                   <c:choose>
                    <c:when test="${temp.active}">
                    <a class="edit-template">Edit</a> 
                    <img src="${commonStaticUrl}/images/delete.png" class="deactivate" />
                     <c:if test="${temp.hasExcelExport}">
                      <i class="icon icon-list sequence-edit"></i>
                     </c:if>
                    </c:when>
                    <c:otherwise>
                     <a class="activate">Activate</a> 
                    </c:otherwise>
                    </c:choose>
                    <input  class="template-id" type=hidden value="${temp.templateID}" />
                  </td>
                  <td class="template-Cat">${temp.poCatDesc}</td>
                  <td class="template-Cat">${temp.poSubCatDesc}</td>
                  <td class="template-name">${temp.templateDesc}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <input id="selectedTemplateType" type="hidden" value="${selectedTemplateType}"/>
    <!-- Modals -->
    <div id="template-modal" class="modal"></div>
    <div id="deactivate-modal" class="deactivate-modal modal" title="Confirm Template Deactivation"></div>
    <div id="activate-modal" class="activate-modal modal" title="Confirm Template Activation">
		<p>Are you sure you want to activate the template?</p>
		<ul>
			<li id="templateName"></li>
		</ul>
		<input id="template-id" type="hidden" value=""/>
		<div class="deactivate-buttons-div">
			<a class="secondaryLink cancel-activate" tabIndex="-1">No, Cancel</a> 
			<a class="buttonPrimary activate-confirm" tabIndex="-1">Yes, Activate</a>
		</div>
</div>
    <%@ include file="../../../jsp/global/v1/footer.jsp" %>
    <script src="${baseUrl}/js/global/v1/legacy-do-not-use/modal-util-do-not-use.js" type="text/javascript"></script>
    <script src="${baseUrl}/js/admin-console/components/template-form.js"   type="text/javascript"></script>
  </body>

</html>
<!DOCTYPE html>
<html>
  <head>
  	<%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp"%>
  	<link href="${commonStaticUrl}/css/jQueryUI/jquery-ui-1.8.21.custom.css" rel="stylesheet" type="text/css"/>
  	<link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
  	<link href="${context}/css/admin-console/security/org.css" rel="stylesheet" type="text/css"/>
    
    <link href="${context}/css/admin-console/components/template.css" rel="stylesheet" type="text/css"/>
  </head>

  <body>

    <%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>
    
    <div id="mainContent">
    
      <%@ include file="../../../jsp/jsp-fragment/admin-console/components/left-nav.jsp"%>
      
      <div class="leftNavAdjacentContainer">
        <div class="full-width">
          <span class="floatRight addRow"> 
            <a href="${context}/admin-console/components/create-modify-template-page.htm?isCreatePage=true &templateId=0">
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
              <c:forEach items="${templateList}" var="temp">
                <tr class="template-row">
                  <td class="editable">
                    <a class="edit-template">Edit</a> 
                    <img src="${commonStaticUrl}/images/delete.png" class="deactivate" />
                    <c:if test="${temp.hasExcelExport}">
                      <i class="icon icon-list sequence-edit"></i>
                    </c:if>
                    <input class="template-id" type=hidden value="${temp.templateID}" />
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
    
    <!-- Modals -->
    <div id="template-modal" class="modal"></div>
    <div id="deactivate-modal" class="deactivate-modal modal" title="Confirm Template Deactivation"></div>
    <!-- Scripts -->
    <script>var hostUrl='${context}';</script>
    
    <script src="${context}/js/global/modal-util.js" type="text/javascript"></script>
    <script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="${commonStaticUrl}/js/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
    <script src="${context}/js/admin-console/components/template-form.js"   type="text/javascript"></script>
  </body>

</html>
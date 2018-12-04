<!DOCTYPE html>
<html>
  <head>
     
    <%@ include file="../../../jsp/jsp-fragment/global/default-head-block.jsp" %>
    <link href="${commonStaticUrl}/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
 
  </head>
  
  <body>
  
    <%@ include file="../../../jsp/jsp-fragment/global/header.jsp" %>
    <div id="mainContent" class="borderTop">
    
      <%@ include file="../../../jsp/jsp-fragment/admin-console/components/left-nav.jsp"%>
      
      <div class="leftNavAdjacentContainer">
      
        <div class="table_div">
    
          <table id="component-management-table">
            <thead>
              <tr>
                <th>Group</th>
                <th>Sub-Group</th>
                <th>Sub Component</th>
                <th>Visible</th>
                <th>Allow Duplicates</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${componentList}" var="component">
                <tr class="component-row" data-component-id="${component.componentId}" data-component-group-id="${component.componentGroupId}">
                  <td>${component.componentGroup}</td>
                  <td>${component.subGroup}</td>
                  <td>${component.subComponentName}</td>
                  <td>
                    <input type="checkbox" class="visible-component-check" <c:if test="${component.visible}">checked disabled</c:if>>
                  </td>
                  <td>
                    <input type="checkbox" class="allow-duplicate-check" <c:if test="${component.allowDuplicates}">checked</c:if> <c:if test="${component.visible == false}">disabled</c:if>>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
    
        </div>
      
      </div>
  
    </div>
  
    
    <!-- Scripts -->
    <script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="${baseUrl}/js/admin-console/components/component-management.js" type="text/javascript"></script>
  
  
  </body>
  
</html>

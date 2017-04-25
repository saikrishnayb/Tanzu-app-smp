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
                <th></th>
                <th>Group</th>
                <th>Sub-Group</th>
                <th>Sub Component</th>
                <th>Visible</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${componentList}" var="componentItem" varStatus="count">
                <tr class="component-row">
                  <td></td>
                  <td>${componentItem.componentGroup}</td>
                  <td>${componentItem.subGroup}</td>
                  <td>${componentItem.subComponentGroup}</td>
                  <td>
                    <input type="checkbox" <c:if test="${componentItem.visible}">checked disabled</c:if>>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
    
        </div>
      
      </div>
  
    </div>
  
    <input type="hidden" id="common-static-url" value="${commonStaticUrl}"/>
    
    <!-- Scripts -->
    <script src="${commonStaticUrl}/js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="${context}/js/admin-console/components/component-management.js" type="text/javascript"></script>
  
  
  </body>
  
</html>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<link href="${baseUrl}/css/admin-console/components/excel-sequence-components.css" rel="stylesheet" type="text/css"/>

<div class="modal-content row" data-modal-title="Modify Excel Sequence For ${template.templateName}" data-modal-width="700" data-modal-max-height="500">

  <div class="col-xs-12 modal-body">
  
    <form:form name="excelSeqCompForm" id="excelSeqCompForm" modelAttribute="templateComponents" method="POST">
      <form:input path="templateId" type="hidden" />
      <div style="text-align: right;">
        <div id="ErrorMsg" style="clear: both; margin-right: 235px;" class="floatLeft error-messages-container displayNone">
          <img src="${commonStaticUrl}/images/warning.png"></img> <span class="errorMsg">Error while updating template
            component sequences.</span>
        </div>
        <div class="floatRight" style="display: inline-block">
          <label>Search: </label><input type="text" id="componentSearch" />
        </div>
      </div>
      
     <div id="table-wrapper">
       <div id="table-scroll">
        <table id="componetsTable" style="width: 100%;">
          <thead class="header fixedHeader">
            <tr>
              <th class="pointer-column"></th>
              <th>Seq</th>
              <th class="component-header">Component</th>
              <th>Component ID</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${templateComponents.components}" var="component" varStatus="cmpIndex">
              <tr>
                <td class="pointer">
                  <span class="icon-bar"></span> 
                  <span class="icon-bar"></span> 
                  <span class="icon-bar"></span> 
                  <form:input type="hidden" path="components[${cmpIndex.count -1}].componentSequence"class="seq" value="${component.componentSequence }" /> 
                  <form:input type="hidden"path="components[${cmpIndex.count -1}].componentId" class="componentId" value="${component.componentId}" />
                </td>
                <td class="seq">${component.componentSequence}</td>
                <td>${component.displayName }</td>
                <td>${component.componentId }</td>
              </tr>
            </c:forEach>
  
          </tbody>
        </table>
      </div>
    </div>
    </form:form>
  
  </div>
  
  <div class="col-xs-12 modal-footer">
    <a class="buttonPrimary buttonDisabled" id="submitComponentSeq" href="javascript:void(0)" onclick="submitComponentSeqForm();">Save</a>
  </div>
  
</div>

<script src="${baseUrl}/js/admin-console/components/excel-sequence-components.js" type="text/javascript"></script>

<!DOCTYPE html>
<html>
  <head>
    <title>Ship Thru Lead Time</title>
    <%@ include file="../../../jsp/jsp-fragment/global/new/default-head-block.jsp"%>
    <link href="${baseUrl}/css/admin-console/app-config/ship-thru-lead-time.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>

    <%@ include file="../../../jsp/jsp-fragment/global/header.jsp"%>

    <div id="mainContent" class="borderTop">
      <%@ include file="../../../jsp/jsp-fragment/admin-console/app-config/left-nav.jsp"%>

      <div class="leftNavAdjacentContainer">
        
        <div class="container-fluid">
        
          <div class="row">
            <div class="col-xs-12">
              <h1>Lead Time Delivery Table</h1>
            </div>
          </div>
          
          <div class="row">
            <div class="col-xs-12">

            <table id="shipThruLeadTime">
              <thead>
                <tr>
                  <th></th>
                  <th>PO Category</th>
                  <th>OEM</th>
                  <th>Model</th>
                  <th>Ship Thru Upfitter</th>
                  <th>Destination State</th>
                  <th>Lead Days</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${shipThruLeadTimes}" var="shipThruLeadTime">
                  <tr data-lead-time-id="${shipThruLeadTime.leadTimeId}">
                    <td class="row-options">
                      <a>Edit</a>
                      <i class="fa fa-trash-o fa-lg" aria-hidden="true"></i>
                    </td>
                    <td>${shipThruLeadTime.poCategory}</td>
                    <td>${shipThruLeadTime.oem}</td>
                    <td>${shipThruLeadTime.model}</td>
                    <td>${shipThruLeadTime.shipThruUpFitter}</td>
                    <td>${shipThruLeadTime.destinationState}</td>
                    <td>${shipThruLeadTime.leadDays}</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>

          </div>
          
          </div>
         </div>
      
      
      </div>
    </div>
    
    
    <!-- Scripts -->
    <%@ include file="../../../jsp/jsp-fragment/global/new/global-scripts.jsp"%>
    <script src="${baseUrl}/js/admin-console/app-config/ship-thru-lead-time.js" type="text/javascript"></script>
  </body>

</html>
var DownloadUtil = (function() {
  
  var downloadFileAsFormPost = function downloadFile(url, fileName, parameterName, parameter) {

   // $loadingOverlay.addClass('ajax-loading');

    var xhr = new XMLHttpRequest();
    xhr.open('POST', url);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.responseType = 'blob';

    xhr.onreadystatechange = function() {

      if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {

        // Create a new Blob object using the response data of the onload object
        var blob = new Blob([ this.response ]);

        // If it is IE or not
        if (window.navigator.msSaveOrOpenBlob !== undefined) {
          window.navigator.msSaveOrOpenBlob(blob, fileName);
        } else {

          // Create a link element, hide it, direct it towards the blob, and
          // then 'click' it programatically
          var a = document.createElement("a");
          a.style.cssText = "display: none; z-index: 99999; position: fixed;";
          
          document.body.appendChild(a);
          
          // Create a DOMString representing the blob and point the link element towards it
          var ubjectUrl = window.URL.createObjectURL(blob);
          a.href = ubjectUrl;
          a.download = fileName;

          // programatically click the link to trigger the download
          a.click();

          // release the reference to the file by revoking the Object URL
          setTimeout(function() {
            document.body.removeChild(a);
            window.URL.revokeObjectURL(ubjectUrl);
          }, 1000);

        }
        
        _clearSpinners();

      }else if(xhr.readyState == XMLHttpRequest.DONE && xhr.status == 500) {
        ModalUtil.openInfoModal('Something went wrong downloading the file in the server, please try again later');
        _clearSpinners();
      }
      
    }
    
    if(parameterName === undefined)
      xhr.send();
    else
      xhr.send(parameterName + '=' + parameter);

  };
  
  var _clearSpinners = function() {
   // $loadingOverlay.removeClass('ajax-loading');
  }
  
  return {
    downloadFileAsFormPost:downloadFileAsFormPost
  };
  
})();

//# sourceURL=file-download-helper.js
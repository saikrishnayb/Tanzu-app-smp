$('.numbers-only').keyup(function () { 
    this.value = this.value.replace(/[^0-9\.]/g,'');
});
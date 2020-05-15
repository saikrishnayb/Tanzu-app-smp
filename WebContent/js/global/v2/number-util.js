var NumberUtil = new function() {
	
	/**
	 * Take in a currency number and formats it to the users locale so 1000.00 will appear
	 * as 1,000.00 for a en-US locale (Murica) or as 1.000,00 de-DE locale (Fatherland)
	 * @param number
	 */
	var formatCurrencyToLocale = function formatCurrencyToLocale(number, digits) {
			
		var unsupportedBrowser = !window.Intl && typeof window.Intl !== "object";
		var digits = digits < 0 ? 2 : digits;
	    
	    if(unsupportedBrowser){
	        var numberFloat = parseFloat(number);
	        var numberFloatToFixed = numberFloat.toFixed(digits);
	        
	        var formatedNumber = replaceNumberWithCommas(numberFloatToFixed);
	        
	        return formatedNumber;
	    }
	    
	    var localeNumberFomrat = new Intl.NumberFormat([],{ style: 'decimal', minimumFractionDigits:digits, maximumFractionDigits: digits});
		var formatedNumber = localeNumberFomrat.format(number);
		
		return formatedNumber;
		
	};
	
	/**
	 * Formats a number or string representing a number into a numeric string with comma thousand-separators.
	 * @param num The number or string to format.
	 * @return The argument, formatted with comma thousand-separator characters. Ex: replaceNumberWithCommas(123456.789) => "123,456.789"
	 * This function derived from StackOverflow question 14075014.
	 * See: http://stackoverflow.com/questions/14075014/jquery-function-to-to-format-number-with-commas-and-decimal
	 */
	var replaceNumberWithCommas = function(num) {
	    //Seperates the components of the number
	    var n = num.toString().split(".");
	    //Comma-fies the first part
	    n[0] = n[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	    //Combines the two sections
	    return n.join(".");
	};
	
	this.formatNumerics = function($selector) {
		
		$.each($selector, function() {
			
			var $this = $(this);
			
			var isThCell = $this.is('th');
			if(isThCell) return true;
			
			var isNotInput =  $this.is(':not(input)');
			var isNumericDecimal = $this.hasClass('numeric-decimal');
			var isNumericMonetary =  $this.hasClass('numeric-monetary');
			var isNumericPercent =  $this.hasClass('numeric-percent');
			var isNumericZip =  $this.hasClass('numeric-zip');
			var isNumericDatePicker = $this.hasClass('numeric-date-picker');
			
			var numericValue = isNotInput ? $this.text().replace(/,/g , "") : $this.val().replace(/,/g , "");
			
			var isEmpty = numericValue == "";
			if(isEmpty) return true;
			
			if(isNumericDatePicker) return true;
			
			var isNotNumeric = !$.isNumeric(numericValue);
			var numericValueFloat = parseFloat(numericValue);
			
			var formattedValue;
			
			if(isNotNumeric) {
				formattedValue = "Error";
			}else if(isNumericMonetary) {
				var decimalMax = $this.data('decimal-max') != undefined ? $this.data('decimal-max') : 2;
				formattedValue = formatCurrencyToLocale(numericValueFloat, decimalMax);
			}else if(isNumericPercent) {
				var decimalMax = $this.data('decimal-max') ? $this.data('decimal-max') : 2;
				formattedValue = (numericValueFloat * 100.0).toFixed(decimalMax);
			}else if(isNumericDecimal) {
				var decimalMax = $this.data('decimal-max') ? $this.data('decimal-max') : 2;
				formattedValue = numericValueFloat.toFixed(decimalMax);
			}else {
				formattedValue = numericValueFloat.toFixed();
			}
			
			if(isNotInput) $this.text(formattedValue);
			else $this.val(formattedValue);
			
		});
	};
	
};
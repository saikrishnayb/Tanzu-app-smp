/**
 * jQuery Accordion Live Filter
 * @author Aaron Saray
 */
;(function($) {

    /**
     * Accordion Filter plugin
     * @returns {*}
     */
    $.fn.accordionLiveFilter = function() {

        return this.each(function() {
            var $filterField = $(this);

            var selector = $filterField.data('alf');
            if (!selector) {
                throw new Error('The accordion search filter element needs a data-alf element with a jquery selector.');
            }

            var $accordion = $(selector);
            if ($accordion.length == 0) {
                throw new Error('The selector ' + selector + ' was not found.');
            }

            var $delete = $('<a href="#" class="alf-delete">&times;</a>');
            $delete.on('click', function(e) {
                e.preventDefault();
                $filterField.val('');
                $('label', $accordion).trigger('contract.alf');
                $('li', $accordion).removeClass('potential');
            });
            $filterField.after($delete);


            $('label', $accordion).each(function(i, label) {
                var $label = $(label);
                var $ul = $label.next('ul');
                $label.data('alf-child', $ul);
                $ul.data('alf-parent', $label);
            });

            var firstEvent=$('label', $accordion).on('expand.alf', function() {
            	//dfd= $.Deferred();
                $(this).addClass('expanded').data('alf-child').slideDown(0, setMaxVericalLineHeight(this));
            }).on('contract.alf', function() {
                $(this).removeClass('expanded').data('alf-child').slideUp(0, setMaxVericalLineHeight(this));
            }).on('click', function() {
                var $label = $(this);
                $label.hasClass('expanded') ? $label.trigger('contract.alf') : $label.trigger('expand.alf');
             /*   $.when.apply(this,firstEvent).done(function(){
                	if($label.hasClass('proximityArea'))
                    	setMaxVericalLineHeight();
                });*/
                /*firstEvent.promise().done(function(){
                	if($label.hasClass('proximityArea'))
                    	setMaxVericalLineHeight();
                });*/
                	
               
             });

            //work around code -will be moved to district-proximity .js 
           /* $('.filterable li').on("click",function(){
        		$.when.apply(this,firstEvent).done(function(){
                setMaxVericalLineHeight();
                });
        		firstEvent.promise().done(function(){
                setMaxVericalLineHeight();
                });
        	});*/
            
            $filterField.on('keyup', function() {
                var query = $filterField.val().toLowerCase();
                $('li > ul', $accordion).each(function(index, element) {
                    var $ul = $(element);
                    var $ulChildren = $ul.children();
                    $ulChildren.removeClass('potential');

                    if (query) {
                        var ulText = $ul.text();
                        var idx = ulText.toLowerCase().indexOf(query);
                        if (idx >= 0) {
                            $ulChildren.each(function(i, e) {
                                var $e = $(e);
                                if ($e.text().toLowerCase().indexOf(query) >= 0) {
                                    $e.addClass('potential');
                                }
                            });
                            $ul.data('alf-parent').trigger('expand.alf');
                            return true;
                        }
                    }
                    $ul.data('alf-parent').trigger('contract.alf');
                });
            });
        });
    };
}(jQuery));


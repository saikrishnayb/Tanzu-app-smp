var ModalUtil = new function() {
	this.initializeModal = function($modalSelector, width) {

		width = width ? width : "auto";

		$modalSelector.dialog({
			autoOpen: false,
			modal: true,
			dialogClass: 'popupModal',
			width: width,
			resizable: false,
			closeOnEscape: true,
			close: function() {
				var $this = $(this);
				var $modalContent = $this.find('.modal-content')
				var id = $this.attr('id');

				var keepContents = $modalContent && $modalContent[0] && $modalContent[0].hasAttribute('data-keep-contents');

				if(!keepContents) {
					//Clean out the contents of the dialog box, so it's ready for the next use.
					// The error dialog box gets treated a bit differently, since it has additional elements in it.
					if(id === 'modal-error') {
						//If it's the error modal, just blank out the text
						$this.find('.error-text').empty();
					} else if (id === 'modal-stack-trace') {
						$this.find('.exception-message, .exception-stack-trace').empty();
					} else {
						$this.children().not('.error-container').remove();
						$this.find('.error-container li').remove();
						$this.find('.error-container').hide();
					}
				}
			},
			position: { my: "center", at: "center", of: window, collision: "fit"}
		});

		//Register a close handler on any element with a class of modal-close
		$modalSelector.off('click', '.modal-close');
		$modalSelector.on('click', '.modal-close', function() {
			var $closestModal = $(this).closest('.modal');
			ModalUtil.closeModal($closestModal);
		});


	};

	this.closeModal = function closeModal($modals) {

		//Close child modals first
		$modals.filter('.modal-child').each(function() {
			$(this).dialog('close');
		});

		$modals.not('.modal-child').each(function() {
			$(this).dialog('close');
		});

	};

	this.openModal = function openModal($modal) {

		var $modalContent      = $modal.find('.modal-content');

		var modalTitle         = $modalContent.data('modal-title');
		var modalWidth         = $modalContent.data('modal-width');
		var modalMaxWidth      = $modalContent.data('modal-max-width');
		var modalMinHeight     = $modalContent.data('modal-min-height');
		var modalMaxHeight     = $modalContent.data('modal-max-height');
		var modalNoClose       = $modalContent.data('modal-no-close');

		var hasModalTitle      = modalTitle !== undefined;
		var hasModalWidth      = modalWidth !== undefined;
		var hasModalMaxWidth   = modalMaxWidth !== undefined;
		var hasModalMinHeight  = modalMinHeight !== undefined;
		var hasModalMaxHeight  = modalMaxHeight !== undefined;
		var hasModalNoClose    = modalNoClose !== undefined;

		var hasDataTable = $modalContent.data('contains-data-table') !== undefined;

		if(hasModalTitle) $modal.dialog("option", "title", modalTitle);
		if(hasModalWidth) $modal.dialog("option", "width", modalWidth);
		if(hasModalMaxWidth) $modal.dialog("option", "width", modalMaxWidth); //Dumb hack since maxWidth seems to not work with "auto" width
		if(hasModalMinHeight) $modal.dialog("option", "minHeight", modalMinHeight);
		if(hasModalMaxHeight) $modal.dialog("option", "maxHeight", modalMaxHeight);
		if(hasModalNoClose) $modal.dialog("option", "closeOnEscape", false);


		// To prevent things from jumping around the page when datatable initializes, hide everything and re-enabled it later
		if(hasDataTable)
			$modal.addClass('table-loading');

		//Figure out where to position the modal so it's always at a reasonable place in the parent window
		var oldScrollTop = window.parent.document.querySelector('html').scrollTop;

		$modal.dialog('open');

		//Position the dialog so it's always near the top of the frame, but not right at the top
		$modal.closest('.ui-dialog')[0].style.top = oldScrollTop > 125 ? oldScrollTop + 'px' : (oldScrollTop + 125) + 'px';
		window.parent.document.querySelector('html').scrollTop = oldScrollTop;

		if(hasDataTable) {
			initializeTable(); //should be implemented in the the modal's JS

			$modal.removeClass('table-loading');
		}
	};

	this.openChildModal = function openChildModal($childModal) {

		$globalModal = $globalModal.add($childModal);

		this.openModal($childModal);

	};

	this.getTopMostOpenedModal = function() {

		var openModals =  [];

		$('.modal').each(function() {

			var $this = $(this);

			var isOpen = $this.dialog("isOpen");
			if(isOpen) openModals.push($this);

		});

		var noModals = openModals.length === 0;
		if(noModals) return $();

		var $lastModal = openModals[0];

		for(var i = 1; i < openModals.length; i++) {

			var $modal = openModals[i];

			var lastZIndex = $lastModal.closest('.ui-dialog')[0].style.zIndex;
			var thisZIndex = $modal.closest('.ui-dialog')[0].style.zIndex;

			var isBiggerZIndex = thisZIndex > lastZIndex;

			if(isBiggerZIndex) $lastModal = $modal;

		}

		return $lastModal;

	};
};

//# sourceURL=modal-util.js
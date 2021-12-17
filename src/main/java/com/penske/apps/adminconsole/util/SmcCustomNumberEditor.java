package com.penske.apps.adminconsole.util;

import java.text.NumberFormat;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomNumberEditor;

public class SmcCustomNumberEditor extends CustomNumberEditor{

	private static Logger logger = LogManager.getLogger(SmcCustomNumberEditor.class);
	
	public SmcCustomNumberEditor(Class<? extends Number> numberClass) throws IllegalArgumentException {
		super(numberClass, true);
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(0);
		boolean textIsBlank = StringUtils.isBlank(text);
		
		if(textIsBlank) return;
		
		text = text.replaceAll(",", "");
		
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		
		try {
			Number number = numberFormat.parse(text.trim());
			
			setValue(number);
			
		} catch (ParseException ex) {
			logger.error(ex);
		}
		
	}
	
}

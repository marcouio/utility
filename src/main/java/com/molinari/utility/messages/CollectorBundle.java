package com.molinari.utility.messages;

import java.util.Locale;
import java.util.ResourceBundle;

public class CollectorBundle {
	
	ResourceBundle getBundle(String file, Locale currentLocale){
		return ResourceBundle.getBundle(file, currentLocale);
	}
}

package com.molinari.utility.database;

public class FormatterSqlText {

	public FormatterSqlText() {
		
	}

	public static String correggi(String text){
		text = text.replaceAll("'", "''");
		return text;
	}
	
}

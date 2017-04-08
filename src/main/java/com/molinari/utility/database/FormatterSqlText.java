package com.molinari.utility.database;

public class FormatterSqlText {

	private FormatterSqlText() {
		//do nothing
	}

	public static String correggi(String text){
		return text.replaceAll("'", "''");
	}
	
}

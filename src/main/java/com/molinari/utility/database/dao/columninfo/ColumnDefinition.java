package com.molinari.utility.database.dao.columninfo;

public interface ColumnDefinition extends Definition {
	
	String getColumnName();
	boolean isPrimaryKey();
	
}

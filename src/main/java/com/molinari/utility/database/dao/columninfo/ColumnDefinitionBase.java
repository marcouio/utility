package com.molinari.utility.database.dao.columninfo;

import java.lang.reflect.Field;

public class ColumnDefinitionBase implements ColumnDefinition {

	private Field field;
	private String columnName;
	private boolean primaryKey;
	
	@Override
	public Field getField() {
		return field;
	}

	@Override
	public String getColumnName() {
		return columnName;
	}

	@Override
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

}

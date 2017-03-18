package com.molinari.utility.database.stringconverter;

public class SelectColumn {

	String columnName;
	String tableAlias;
	String columnAlias;
	
	public SelectColumn(String columnName, String tableAlias, String columnAlias) {
		super();
		this.columnName = columnName;
		this.tableAlias = tableAlias;
		this.columnAlias = columnAlias;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getTableAlias() {
		return tableAlias;
	}
	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}
	public String getColumnAlias() {
		return columnAlias;
	}
	public void setColumnAlias(String columnAlias) {
		this.columnAlias = columnAlias;
	}
}

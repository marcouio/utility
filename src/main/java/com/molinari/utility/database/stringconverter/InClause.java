package com.molinari.utility.database.stringconverter;

public class InClause {

	private String tableAlias;
	private String columnName;
	private String[] values;
	private SelectQuery sQuery;
	
	public InClause(String tableAlias, String columnName, SelectQuery sQuery) {
		super();
		this.tableAlias = tableAlias;
		this.columnName = columnName;
		this.sQuery = sQuery;
	}

	public InClause(String tableAlias, String columnName, String[] values) {
		super();
		this.tableAlias = tableAlias;
		this.columnName = columnName;
		this.values = values;
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String[] getValues() {
		return values;
	}

	public SelectQuery getsQuery() {
		return sQuery;
	}
}

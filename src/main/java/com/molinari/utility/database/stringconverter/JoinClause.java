package com.molinari.utility.database.stringconverter;

public class JoinClause {
	private String firstTableAlias;
	private String firstColumnName;
	private String secondTableAlias;
	private String secondColumnName;
	
	public JoinClause(String firstTableAlias, String firstColumnName, String secondTableAlias,
			String secondColumnName) {
		super();
		this.firstTableAlias = firstTableAlias;
		this.firstColumnName = firstColumnName;
		this.secondTableAlias = secondTableAlias;
		this.secondColumnName = secondColumnName;
	}
	public String getFirstTableAlias() {
		return firstTableAlias;
	}
	public void setFirstTableAlias(String firstTableAlias) {
		this.firstTableAlias = firstTableAlias;
	}
	public String getFirstColumnName() {
		return firstColumnName;
	}
	public void setFirstColumnName(String firstColumnName) {
		this.firstColumnName = firstColumnName;
	}
	public String getSecondTableAlias() {
		return secondTableAlias;
	}
	public void setSecondTableAlias(String secondTableAlias) {
		this.secondTableAlias = secondTableAlias;
	}
	public String getSecondColumnName() {
		return secondColumnName;
	}
	public void setSecondColumnName(String secondColumnName) {
		this.secondColumnName = secondColumnName;
	}
	
}
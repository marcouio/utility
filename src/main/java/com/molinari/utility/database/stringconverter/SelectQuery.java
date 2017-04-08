package com.molinari.utility.database.stringconverter;

import java.util.ArrayList;
import java.util.List;

public class SelectQuery {

	private List<SelectColumn> selectColumns = new ArrayList<>();
	private List<FromTable> fromTables = new ArrayList<>();
	private List<WhereClause> whereClauses = new ArrayList<>();
	private List<JoinClause> joinClauses = new ArrayList<>();
	private List<InClause> inClauses = new ArrayList<>();
	
	public void addSelectColumn(SelectColumn selCol){
		getSelectColumns().add(selCol);
	}
	
	public List<SelectColumn> getSelectColumns() {
		return selectColumns;
	}
	public void setSelectColumns(List<SelectColumn> selectColumns) {
		this.selectColumns = selectColumns;
	}
	
	public void addFromTable(FromTable fromTable){
		getFromTables().add(fromTable);
	}

	public List<FromTable> getFromTables() {
		return fromTables;
	}

	public void setFromTables(List<FromTable> fromTables) {
		this.fromTables = fromTables;
	}
	
	public void addWhereClause(WhereClause whereClause){
		getWhereClauses().add(whereClause);
	}

	public List<WhereClause> getWhereClauses() {
		return whereClauses;
	}

	public void setWhereClauses(List<WhereClause> whereClauses) {
		this.whereClauses = whereClauses;
	}
	
	public void addJoinClause(JoinClause joinClause){
		getJoinClauses().add(joinClause);
	}

	public List<JoinClause> getJoinClauses() {
		return joinClauses;
	}

	public void setJoinClauses(List<JoinClause> joinClauses) {
		this.joinClauses = joinClauses;
	}

	public void addInClause(InClause inClause){
		getInClauses().add(inClause);
	}
	
	public List<InClause> getInClauses() {
		return inClauses;
	}

	public void setInClauses(List<InClause> inClauses) {
		this.inClauses = inClauses;
	}
	
}

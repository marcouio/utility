package com.molinari.utility.database.dao.columninfo;

import java.lang.reflect.Field;

public class ManyToManyDefinitionBase implements ManyToManyDefinition {

	private Field field;
	private String relationTable;
	private String joinColumn;
	private String inverseJoinColumn;
	private String refJoinColumn;
	private String refInverseJoinColumn;
	
	private Class<?> linkedEntityClass;
	
	@Override
	public Field getField() {
		return field;
	}

	@Override
	public String getRelationTable() {
		return relationTable;
	}

	@Override
	public void setField(Field field) {
		this.field = field;
	}

	@Override
	public void setRelationTable(String relationTable) {
		this.relationTable = relationTable;
	}

	public String getJoinColumn() {
		return joinColumn;
	}

	public void setJoinColumn(String joinColumn) {
		this.joinColumn = joinColumn;
	}

	public String getInverseJoinColumn() {
		return inverseJoinColumn;
	}

	public void setInverseJoinColumn(String inverseJoinColumn) {
		this.inverseJoinColumn = inverseJoinColumn;
	}

	public String getRefJoinColumn() {
		return refJoinColumn;
	}

	public void setRefJoinColumn(String refJoinColumn) {
		this.refJoinColumn = refJoinColumn;
	}

	public String getRefInverseJoinColumn() {
		return refInverseJoinColumn;
	}

	public void setRefInverseJoinColumn(String refInverseJoinColumn) {
		this.refInverseJoinColumn = refInverseJoinColumn;
	}

	public Class<?> getLinkedEntityClass() {
		return linkedEntityClass;
	}

	public void setLinkedEntityClass(Class<?> linkedEntityClass) {
		this.linkedEntityClass = linkedEntityClass;
	}

	

}

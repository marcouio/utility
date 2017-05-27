package com.molinari.utility.database.dao.columninfo;

public class JoinColumnDefinition extends ColumnDefinitionBase {
	
	private Class<?> targetEntity;

	public Class<?> getTargetEntity() {
		return targetEntity;
	}

	public void setTargetEntity(Class<?> targetEntity) {
		this.targetEntity = targetEntity;
	}
}

package com.molinari.utility.database.dao.columninfo;

public interface ManyToManyDefinition extends Definition {
	
	String getRelationTable();

	void setRelationTable(String relationTable);
	
}

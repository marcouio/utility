package db.stringa;

public class FromTable {

	private String tableName;
	private String aliasTable;
	
	public FromTable(String tableName, String aliasTable) {
		super();
		this.tableName = tableName;
		this.aliasTable = aliasTable;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getAliasTable() {
		return aliasTable;
	}
	public void setAliasTable(String aliasTable) {
		this.aliasTable = aliasTable;
	}
}

package db.stringa;

public class WhereClause {

	private String tableAlias;
	private String columnName;
	private String operator;
	private String value;
	
	public WhereClause(String tableAlias, String columnName, String operator, String value) {
		super();
		this.tableAlias = tableAlias;
		this.columnName = columnName;
		this.operator = operator;
		this.value = value;
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}

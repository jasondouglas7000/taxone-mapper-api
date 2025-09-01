package br.com.lkm.taxone.mapper.util;

public class XLSField {

	private String tableName;
	private Boolean required;
	private Integer index;
	private String description;
	private String columnName;
	private String size;
	private String type;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "XLSField [tableName=" + tableName + ", required=" + required + ", index=" + index + ", description="
				+ description + ", columnName=" + columnName + ", size=" + size + ", type=" + type + "]";
	}


}

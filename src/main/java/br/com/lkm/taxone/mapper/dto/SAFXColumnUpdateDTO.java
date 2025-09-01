package br.com.lkm.taxone.mapper.dto;

import br.com.lkm.taxone.mapper.enums.ColumnType;
import lombok.Data;

@Data
public class SAFXColumnUpdateDTO {

	private Integer id;
	private String name;
	private ColumnType columnType;
	private Boolean required;
	private Integer position; 
	private Integer size;
	
	private Integer dsColumnId;
	private String dsColumnName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ColumnType getColumnType() {
		return columnType;
	}
	public void setColumnType(ColumnType columnType) {
		this.columnType = columnType;
	}
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getDsColumnId() {
		return dsColumnId;
	}
	public void setDsColumnId(Integer dsColumnId) {
		this.dsColumnId = dsColumnId;
	}
	public String getDsColumnName() {
		return dsColumnName;
	}
	public void setDsColumnName(String dsColumnName) {
		this.dsColumnName = dsColumnName;
	}

	
	
}

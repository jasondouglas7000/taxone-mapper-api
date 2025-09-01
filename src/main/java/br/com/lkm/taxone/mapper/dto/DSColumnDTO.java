package br.com.lkm.taxone.mapper.dto;

import br.com.lkm.taxone.mapper.enums.ColumnType;
import lombok.Data;

@Data
public class DSColumnDTO {
	private Integer id;
	private String name;
	private ColumnType columnType;
	private Integer size;
	private DSTableDTO dsTable;
	
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
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public DSTableDTO getDsTable() {
		return dsTable;
	}
	public void setDsTable(DSTableDTO dsTable) {
		this.dsTable = dsTable;
	}

}

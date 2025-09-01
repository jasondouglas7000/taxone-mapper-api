package br.com.lkm.taxone.mapper.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import br.com.lkm.taxone.mapper.enums.ColumnType;
import lombok.Data;

@Data
@Entity
public class DSColumn {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private ColumnType columnType;
	
	private Integer size;

	@ManyToOne
	private DSTable dsTable;

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

	public DSTable getDsTable() {
		return dsTable;
	}

	public void setDsTable(DSTable dsTable) {
		this.dsTable = dsTable;
	} 

	
}

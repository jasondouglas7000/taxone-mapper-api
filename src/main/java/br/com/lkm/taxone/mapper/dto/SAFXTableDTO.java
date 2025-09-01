package br.com.lkm.taxone.mapper.dto;

import lombok.Data;

@Data
public class SAFXTableDTO {
	private Integer id;
	private String name;
	private String description;
	private Integer dsTableId;
	private String dsTableName;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDsTableId() {
		return dsTableId;
	}
	public void setDsTableId(Integer dsTableId) {
		this.dsTableId = dsTableId;
	}
	public String getDsTableName() {
		return dsTableName;
	}
	public void setDsTableName(String dsTableName) {
		this.dsTableName = dsTableName;
	}
	
}

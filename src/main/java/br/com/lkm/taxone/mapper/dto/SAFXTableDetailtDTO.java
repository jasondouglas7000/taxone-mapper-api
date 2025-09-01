package br.com.lkm.taxone.mapper.dto;

import java.util.List;

import lombok.Data;

@Data
public class SAFXTableDetailtDTO {
	private Integer id;
	private String name;
	
	private List<SAFXColumnDTO> safxColumns;

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

	public List<SAFXColumnDTO> getSafxColumns() {
		return safxColumns;
	}

	public void setSafxColumns(List<SAFXColumnDTO> safxColumns) {
		this.safxColumns = safxColumns;
	}
	
	

}

package br.com.lkm.taxone.mapper.dto;

import lombok.Data;

@Data
public class CriteriaDTO {
	
	private Integer id;
	private SAFXColumnDTO safxColumn;
	private String operator;
	private String value;
	private String additionalValue;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public SAFXColumnDTO getSafxColumn() {
		return safxColumn;
	}
	public void setSafxColumn(SAFXColumnDTO safxColumn) {
		this.safxColumn = safxColumn;
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
	public String getAdditionalValue() {
		return additionalValue;
	}
	public void setAdditionalValue(String additionalValue) {
		this.additionalValue = additionalValue;
	}
	
	
}

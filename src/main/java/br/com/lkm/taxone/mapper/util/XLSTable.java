package br.com.lkm.taxone.mapper.util;

import java.util.List;

import lombok.Data;

@Data
public class XLSTable {

	private String name;
	
	private String description;
	
	private List<XLSField> fields;

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

	public List<XLSField> getFields() {
		return fields;
	}

	public void setFields(List<XLSField> fields) {
		this.fields = fields;
	}
	
	
}

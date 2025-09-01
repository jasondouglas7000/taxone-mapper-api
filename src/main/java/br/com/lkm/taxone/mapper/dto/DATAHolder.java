package br.com.lkm.taxone.mapper.dto;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class DATAHolder {
	private Map<String, Field> fieldMappings; 
	private List<DataDTO> list;
	
	public Map<String, Field> getFieldMappings() {
		return fieldMappings;
	}
	public void setFieldMappings(Map<String, Field> fieldMappings) {
		this.fieldMappings = fieldMappings;
	}
	public List<DataDTO> getList() {
		return list;
	}
	public void setList(List<DataDTO> list) {
		this.list = list;
	}
	
}

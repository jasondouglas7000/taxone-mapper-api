package br.com.lkm.taxone.mapper.integration.dto;

import java.util.List;
import java.util.Map;

public class SAFXTableTaxOneDTO {
	private String safx;
	private List<Map<String, Object>> registros;

	public String getSafx() {
		return safx;
	}

	public void setSafx(String safx) {
		this.safx = safx;
	}

	public List<Map<String, Object>> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Map<String, Object>> registros) {
		this.registros = registros;
	}

	@Override
	public String toString() {
		return "SAFXTableTaxOneDTO [safx=" + safx + ", registros=" + registros + "]";
	}

}

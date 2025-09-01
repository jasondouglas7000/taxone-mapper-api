package br.com.lkm.taxone.mapper.integration.dto;

import java.util.List;

public class ConsultaLoteImportacaoDTO {
	private String num_lote;
	private String mensagem;
	private List<ConsultaLoteImportacaoCargaDTO> cargas;
	private List<ConsultaLoteImportacaoCargaDTO> importacoes;

	public String getNum_lote() {
		return num_lote;
	}

	public void setNum_lote(String num_lote) {
		this.num_lote = num_lote;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public List<ConsultaLoteImportacaoCargaDTO> getCargas() {
		return cargas;
	}

	public void setCargas(List<ConsultaLoteImportacaoCargaDTO> cargas) {
		this.cargas = cargas;
	}

	public List<ConsultaLoteImportacaoCargaDTO> getImportacoes() {
		return importacoes;
	}

	public void setImportacoes(List<ConsultaLoteImportacaoCargaDTO> importacoes) {
		this.importacoes = importacoes;
	}

	@Override
	public String toString() {
		return "ConsultaLoteImportacaoDTO [num_lote=" + num_lote + ", mensagem=" + mensagem + ", cargas=" + cargas
				+ ", importacoes=" + importacoes + "]";
	}

	
}

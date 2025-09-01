package br.com.lkm.taxone.mapper.integration.dto;

import java.util.List;

public class ConsultaLoteDTO {
	private String num_lote;
	private boolean sucesso;
	private String mensagem;
	private List<ConsultaLoteProtocoloDTO> protocolos;

	public String getNum_lote() {
		return num_lote;
	}

	public void setNum_lote(String num_lote) {
		this.num_lote = num_lote;
	}

	public boolean getSucesso() {
		return sucesso;
	}

	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public List<ConsultaLoteProtocoloDTO> getProtocolos() {
		return protocolos;
	}

	public void setProtocolos(List<ConsultaLoteProtocoloDTO> protocolos) {
		this.protocolos = protocolos;
	}

	@Override
	public String toString() {
		return "ConsultaLoteDTO [num_lote=" + num_lote + ", sucesso=" + sucesso + ", mensagem=" + mensagem
				+ ", protocolos=" + protocolos + "]";
	}

	
	
}

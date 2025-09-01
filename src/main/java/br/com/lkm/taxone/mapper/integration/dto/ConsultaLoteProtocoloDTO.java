package br.com.lkm.taxone.mapper.integration.dto;

import java.util.List;

public class ConsultaLoteProtocoloDTO {
	private String num_protocolo;
	private int num_processo;
	private boolean sucesso;
	private String mensagem;

	List<ConsultaLoteErroDTO> erros;

	public String getNum_protocolo() {
		return num_protocolo;
	}

	public void setNum_protocolo(String num_protocolo) {
		this.num_protocolo = num_protocolo;
	}

	public int getNum_processo() {
		return num_processo;
	}

	public void setNum_processo(int num_processo) {
		this.num_processo = num_processo;
	}

	public boolean isSucesso() {
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

	public List<ConsultaLoteErroDTO> getErros() {
		return erros;
	}

	public void setErros(List<ConsultaLoteErroDTO> erros) {
		this.erros = erros;
	}

	@Override
	public String toString() {
		return "ConsultaLoteProtocoloDTO [num_protocolo=" + num_protocolo + ", num_processo=" + num_processo
				+ ", sucesso=" + sucesso + ", mensagem=" + mensagem + ", erros=" + erros + "]";
	}

	
}

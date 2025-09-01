package br.com.lkm.taxone.mapper.integration.dto;

public class IncluirResponseDTO {
	private String num_protocolo;
	private int num_processo;
	private boolean sucesso;
	private String mensagem;

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

	@Override
	public String toString() {
		return "IncluirResponseDTO [num_protocolo=" + num_protocolo + ", num_processo=" + num_processo + ", sucesso="
				+ sucesso + ", mensagem=" + mensagem + "]";
	}

}

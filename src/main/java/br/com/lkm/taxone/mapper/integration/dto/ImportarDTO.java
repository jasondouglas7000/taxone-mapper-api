package br.com.lkm.taxone.mapper.integration.dto;

public class ImportarDTO {
	private int num_processo;
	private boolean sucesso;
	private String mensagem;

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
		return "ImportarDTO [num_processo=" + num_processo + ", sucesso=" + sucesso + ", mensagem=" + mensagem + "]";
	}
	
	

}

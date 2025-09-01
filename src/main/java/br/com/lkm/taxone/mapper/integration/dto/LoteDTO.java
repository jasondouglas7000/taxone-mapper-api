package br.com.lkm.taxone.mapper.integration.dto;

public class LoteDTO {
	
//	@JsonProperty("num_lote")
	private String num_lote;
	
	private String mensagem;

	public LoteDTO() {}
	
	public LoteDTO(String num_lote ) {
		this.num_lote = num_lote; 
	}

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

	@Override
	public String toString() {
		return "LoteDTO [num_lote=" + num_lote + ", mensagem=" + mensagem + "]";
	}
	
	

}

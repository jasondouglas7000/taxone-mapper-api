package br.com.lkm.taxone.mapper.integration.dto;

import java.util.List;

public class ConsultaLoteImportacaoCargaDTO {
	private String safx;
	private String num_protocolo;
	private int num_processo;
	private String mensagem;
	
	private ConsultaLoteImportacaoQtdDTO quantidades;
	
	private List<ConsultaLoteErroDTO> erros;

	public String getSafx() {
		return safx;
	}

	public void setSafx(String safx) {
		this.safx = safx;
	}

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

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public ConsultaLoteImportacaoQtdDTO getQuantidades() {
		return quantidades;
	}

	public void setQuantidades(ConsultaLoteImportacaoQtdDTO quantidades) {
		this.quantidades = quantidades;
	}

	public List<ConsultaLoteErroDTO> getErros() {
		return erros;
	}

	public void setErros(List<ConsultaLoteErroDTO> erros) {
		this.erros = erros;
	}

	@Override
	public String toString() {
		return "ConsultaLoteImportacaoCargaDTO [safx=" + safx + ", num_protocolo=" + num_protocolo + ", num_processo="
				+ num_processo + ", mensagem=" + mensagem + ", quantidades=" + quantidades + ", erros=" + erros + "]";
	}

	
	
	
}
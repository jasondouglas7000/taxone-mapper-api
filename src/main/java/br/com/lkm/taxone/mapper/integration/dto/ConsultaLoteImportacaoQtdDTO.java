package br.com.lkm.taxone.mapper.integration.dto;

public class ConsultaLoteImportacaoQtdDTO {
	private Long qtd_lidos;
	private Long qtd_inseridos;
	private Long qtd_alterados;
	private Long qtd_ignorados;
	private Long qtd_erros;

	public Long getQtd_lidos() {
		return qtd_lidos;
	}

	public void setQtd_lidos(Long qtd_lidos) {
		this.qtd_lidos = qtd_lidos;
	}

	public Long getQtd_inseridos() {
		return qtd_inseridos;
	}

	public void setQtd_inseridos(Long qtd_inseridos) {
		this.qtd_inseridos = qtd_inseridos;
	}

	public Long getQtd_alterados() {
		return qtd_alterados;
	}

	public void setQtd_alterados(Long qtd_alterados) {
		this.qtd_alterados = qtd_alterados;
	}

	public Long getQtd_ignorados() {
		return qtd_ignorados;
	}

	public void setQtd_ignorados(Long qtd_ignorados) {
		this.qtd_ignorados = qtd_ignorados;
	}

	public Long getQtd_erros() {
		return qtd_erros;
	}

	public void setQtd_erros(Long qtd_erros) {
		this.qtd_erros = qtd_erros;
	}

	@Override
	public String toString() {
		return "ConsultaLoteImportacaoQtdDTO [qtd_lidos=" + qtd_lidos + ", qtd_inseridos=" + qtd_inseridos
				+ ", qtd_alterados=" + qtd_alterados + ", qtd_ignorados=" + qtd_ignorados + ", qtd_erros=" + qtd_erros
				+ "]";
	}

	
}

package br.com.lkm.taxone.mapper.integration.dto;

public class ConsultaLoteErroDTO {
	private int num_reg;
	private String cod_erro;
	private String descricao_erro;
	private String nom_campo;
	private String chave_registro;

	public int getNum_reg() {
		return num_reg;
	}

	public void setNum_reg(int num_reg) {
		this.num_reg = num_reg;
	}

	public String getCod_erro() {
		return cod_erro;
	}

	public void setCod_erro(String cod_erro) {
		this.cod_erro = cod_erro;
	}

	public String getDescricao_erro() {
		return descricao_erro;
	}

	public void setDescricao_erro(String descricao_erro) {
		this.descricao_erro = descricao_erro;
	}

	public String getNom_campo() {
		return nom_campo;
	}

	public void setNom_campo(String nom_campo) {
		this.nom_campo = nom_campo;
	}

	public String getChave_registro() {
		return chave_registro;
	}

	public void setChave_registro(String chave_registro) {
		this.chave_registro = chave_registro;
	}

	@Override
	public String toString() {
		return "ConsultaLoteErroDTO [num_reg=" + num_reg + ", cod_erro=" + cod_erro + ", descricao_erro="
				+ descricao_erro + ", nom_campo=" + nom_campo + ", chave_registro=" + chave_registro + "]";
	}

	
}


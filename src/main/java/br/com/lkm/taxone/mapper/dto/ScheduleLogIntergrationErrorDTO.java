package br.com.lkm.taxone.mapper.dto;

public class ScheduleLogIntergrationErrorDTO {
	private Long id;
	private int numeroReg;
	private String codigoErro;
	private String descricaoErro;
	private String nomeCampo;
	private String chaveRegistro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumeroReg() {
		return numeroReg;
	}

	public void setNumeroReg(int numeroReg) {
		this.numeroReg = numeroReg;
	}

	public String getCodigoErro() {
		return codigoErro;
	}

	public void setCodigoErro(String codigoErro) {
		this.codigoErro = codigoErro;
	}

	public String getDescricaoErro() {
		return descricaoErro;
	}

	public void setDescricaoErro(String descricaoErro) {
		this.descricaoErro = descricaoErro;
	}

	public String getNomeCampo() {
		return nomeCampo;
	}

	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	public String getChaveRegistro() {
		return chaveRegistro;
	}

	public void setChaveRegistro(String chaveRegistro) {
		this.chaveRegistro = chaveRegistro;
	}

}

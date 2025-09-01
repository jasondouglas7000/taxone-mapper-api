package br.com.lkm.taxone.mapper.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ScheduleLogIntergrationError {

	@Id
	@GeneratedValue
	private Long id;
	private int numeroReg;
	private String codigoErro;
	private String descricaoErro;
	private String nomeCampo;
	private String chaveRegistro;

	@ManyToOne
	private ScheduleLog scheduleLog;

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

	public ScheduleLog getScheduleLog() {
		return scheduleLog;
	}

	public void setScheduleLog(ScheduleLog scheduleLog) {
		this.scheduleLog = scheduleLog;
	}

}

package br.com.lkm.taxone.mapper.dto;

import java.io.Serializable;

public class AutenticationResponse implements Serializable {
	
	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	
	public AutenticationResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}
	public String getToken() {
		return this.jwttoken;
	}
}
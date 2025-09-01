package br.com.lkm.taxone.mapper.dto;

public class ErrorResponse{
	private Integer code = 10;
	private String message = "Error in upload";
	
	public ErrorResponse() {}
	
	public ErrorResponse(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}


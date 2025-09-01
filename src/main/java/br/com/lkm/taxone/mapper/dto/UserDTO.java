package br.com.lkm.taxone.mapper.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class UserDTO {
	private Integer id;
	private String name;
	private String password;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
	private LocalDateTime creationDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	
}

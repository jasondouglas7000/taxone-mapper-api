package br.com.lkm.taxone.mapper.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import br.com.lkm.taxone.mapper.enums.EmailType;

@Entity
@Table(name = "email")
public class Email {

	@Id
	@GeneratedValue
	private Integer id;

	private String email;

	private EmailType type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EmailType getType() {
		return type;
	}

	public void setType(EmailType type) {
		this.type = type;
	}

}

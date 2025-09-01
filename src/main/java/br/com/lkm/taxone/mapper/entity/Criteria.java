package br.com.lkm.taxone.mapper.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Criteria {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private SAFXColumn safxColumn;
	
	private String operator;
	
    @Column(name="c_value")
	private String value;
	
	private String additionalValue;
	
	@ManyToOne
	private Schedule schedule;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SAFXColumn getSafxColumn() {
		return safxColumn;
	}

	public void setSafxColumn(SAFXColumn safxColumn) {
		this.safxColumn = safxColumn;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAdditionalValue() {
		return additionalValue;
	}

	public void setAdditionalValue(String additionalValue) {
		this.additionalValue = additionalValue;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
}

package br.com.lkm.taxone.mapper.dto;

import br.com.lkm.taxone.mapper.enums.ScheduleStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ScheduleLightDTO {
	private Integer id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String days;
	@NotEmpty
	private String hours;
	@Size(min = 1)
	private String userName;
	private ScheduleStatus status;
	
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
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public ScheduleStatus getStatus() {
		return status;
	}
	public void setStatus(ScheduleStatus status) {
		this.status = status;
	}

}

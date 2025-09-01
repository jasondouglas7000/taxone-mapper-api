package br.com.lkm.taxone.mapper.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import br.com.lkm.taxone.mapper.enums.ScheduleLogStatus;
import lombok.Data;

@Data
@Entity
public class ScheduleLog {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String numLote;
	
	private LocalDateTime executionDate;
	
	private String errorMessage;
	
	@Enumerated(EnumType.STRING)
	private ScheduleLogStatus status;
	
	private IntegrationStatus integrationStatus;
	
	@ManyToOne
	private Schedule schedule;
	
	@OneToMany(mappedBy = "scheduleLog")
	private List<ScheduleLogIntergrationError> taxOneErrors;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumLote() {
		return numLote;
	}

	public void setNumLote(String numLote) {
		this.numLote = numLote;
	}

	public LocalDateTime getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(LocalDateTime executionDate) {
		this.executionDate = executionDate;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ScheduleLogStatus getStatus() {
		return status;
	}

	public void setStatus(ScheduleLogStatus status) {
		this.status = status;
	}

	public IntegrationStatus getIntegrationStatus() {
		return integrationStatus;
	}

	public void setIntegrationStatus(IntegrationStatus integrationStatus) {
		this.integrationStatus = integrationStatus;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public List<ScheduleLogIntergrationError> getTaxOneErrors() {
		return taxOneErrors;
	}

	public void setTaxOneErrors(List<ScheduleLogIntergrationError> taxOneErrors) {
		this.taxOneErrors = taxOneErrors;
	}

}

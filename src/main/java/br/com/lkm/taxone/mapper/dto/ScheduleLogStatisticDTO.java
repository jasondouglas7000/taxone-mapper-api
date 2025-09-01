package br.com.lkm.taxone.mapper.dto;

import br.com.lkm.taxone.mapper.enums.ScheduleLogStatus;

public class ScheduleLogStatisticDTO {
	private ScheduleLogStatus status;
	private long quantity;
	
	public ScheduleLogStatisticDTO() {}

	public ScheduleLogStatisticDTO(ScheduleLogStatus status, long quantity) {
		this.status = status;
		this.quantity = quantity;
	}
	
	public ScheduleLogStatus getStatus() {
		return status;
	}
	public void setStatus(ScheduleLogStatus status) {
		this.status = status;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	
}

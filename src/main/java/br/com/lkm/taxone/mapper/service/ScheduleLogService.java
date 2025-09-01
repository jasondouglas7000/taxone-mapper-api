package br.com.lkm.taxone.mapper.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.lkm.taxone.mapper.dto.PageResponse;
import br.com.lkm.taxone.mapper.dto.ScheduleLogDTO;
import br.com.lkm.taxone.mapper.dto.ScheduleLogIntergrationErrorDTO;
import br.com.lkm.taxone.mapper.dto.ScheduleLogStatisticDTO;
import br.com.lkm.taxone.mapper.entity.ScheduleLog;
import br.com.lkm.taxone.mapper.entity.ScheduleLogIntergrationError;
import br.com.lkm.taxone.mapper.enums.ScheduleLogStatus;
import br.com.lkm.taxone.mapper.repository.ScheduleLogIntergrationErrorRepository;
import br.com.lkm.taxone.mapper.repository.ScheduleLogRepository;

@Service
public class ScheduleLogService {
	
	private ScheduleLogRepository scheduleLogRepository;

	private ScheduleLogIntergrationErrorRepository scheduleLogIntergrationErrorRepository;
	
	public ModelMapper modelMapper;

	
	public ScheduleLogService(ScheduleLogRepository scheduleLogRepository, ScheduleLogIntergrationErrorRepository scheduleLogIntergrationErrorRepository, ModelMapper modelMapper) {
		this.scheduleLogRepository = scheduleLogRepository;
		this.scheduleLogIntergrationErrorRepository = scheduleLogIntergrationErrorRepository;
		this.modelMapper = modelMapper;
	}

	//Just for that
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void save(ScheduleLog sLog) {
		scheduleLogRepository.save(sLog);
	}

	public PageResponse<ScheduleLogDTO> findAll(ScheduleLogStatus status, PageRequest pageable) {
		Page<ScheduleLog> slPage = scheduleLogRepository.findByStatus(status, pageable);
		PageResponse<ScheduleLogDTO> pageResponse = new PageResponse<>();
		pageResponse.setTotalPages(slPage.getTotalPages());
		pageResponse.setContent(slPage.getContent().stream().map((v) -> modelMapper.map(v, ScheduleLogDTO.class)).collect(Collectors.toList()));
		return pageResponse;
	}

	public List<ScheduleLogStatisticDTO> groupByStatus() {
		return scheduleLogRepository.groupByStatus();
	}

	public ScheduleLogDTO get(Integer id) {
		return modelMapper.map(scheduleLogRepository.getReferenceById(id), ScheduleLogDTO.class);
	}

	public PageResponse<ScheduleLogIntergrationErrorDTO> getTaxtOneErrors(Integer id, PageRequest pageable) {
		Page<ScheduleLogIntergrationError> sliePage = scheduleLogIntergrationErrorRepository.findByScheduleLogId(id, pageable);
		PageResponse<ScheduleLogIntergrationErrorDTO> pageResponse = new PageResponse<>();
		pageResponse.setTotalPages(sliePage.getTotalPages());
		pageResponse.setContent(sliePage.getContent().stream().map((v) -> modelMapper.map(v, ScheduleLogIntergrationErrorDTO.class)).collect(Collectors.toList()));
		return pageResponse;
	}

}

package br.com.lkm.taxone.mapper.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lkm.taxone.mapper.dto.POCUser;
import br.com.lkm.taxone.mapper.dto.PageResponse;
import br.com.lkm.taxone.mapper.dto.PeriodeDTO;
import br.com.lkm.taxone.mapper.dto.ScheduleDTO;
import br.com.lkm.taxone.mapper.dto.ScheduleLightDTO;
import br.com.lkm.taxone.mapper.entity.Criteria;
import br.com.lkm.taxone.mapper.entity.Schedule;
import br.com.lkm.taxone.mapper.entity.User;
import br.com.lkm.taxone.mapper.enums.ScheduleLogStatus;
import br.com.lkm.taxone.mapper.enums.ScheduleStatus;
import br.com.lkm.taxone.mapper.repository.CriteriaRepository;
import br.com.lkm.taxone.mapper.repository.ScheduleLogRepository;
import br.com.lkm.taxone.mapper.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScheduleService {
	
	private ScheduleRepository scheduleRepository; 

	private CriteriaRepository criteriaRepository;
	
	private ScheduleLogRepository scheduleLogRepository;
	
	public ModelMapper modelMapper;
	
	public ScheduleService(ScheduleRepository scheduleRepository, CriteriaRepository criteriaRepository, ScheduleLogRepository scheduleLogRepository, ModelMapper modelMapper) {
		this.scheduleRepository = scheduleRepository;
		this.criteriaRepository = criteriaRepository;
		this.scheduleLogRepository = scheduleLogRepository;
		this.modelMapper = modelMapper;
	}

	public PageResponse<ScheduleLightDTO> list(Pageable pageable) {
		Page<Schedule> page = scheduleRepository.findByStatus(ScheduleStatus.ACTIVE, pageable);
		PageResponse<ScheduleLightDTO> sPage = new PageResponse<>();
		sPage.setContent(page.stream().map((v) -> modelMapper.map(v, ScheduleLightDTO.class)).collect(Collectors.toList()));
		sPage.setTotalPages(page.getTotalPages());
		return sPage;
	}

	public ScheduleDTO get(Integer id) {
		Schedule s = scheduleRepository.getReferenceById(id);
		return modelMapper.map(s, ScheduleDTO.class);
	}

	public void save(ScheduleDTO sDTO) {
		final List<Integer> cDeleted = new ArrayList<>();
		if (sDTO.getId() != null) {
			cDeleted.addAll(scheduleRepository.getReferenceById(sDTO.getId()).getCriterias().stream().map(Criteria::getId).collect(Collectors.toList()));
		}
		Schedule s = modelMapper.map(sDTO, Schedule.class);
		POCUser user = (POCUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		s.setUser(new User(user.getId()));
		if (s.getId() == null) {
			s.setLastExecution(LocalDateTime.MIN);
		}
		s.setStatus(ScheduleStatus.ACTIVE);
		scheduleRepository.save(s);
		s.getCriterias().stream().forEach(c -> {
			cDeleted.remove(c.getId());
			c.setSchedule(s);
			criteriaRepository.save(c);
		});
		log.info("cDeleted:" + cDeleted);
		cDeleted.stream().forEach(cId -> {
			criteriaRepository.deleteById(cId);
		});
	}

//	public void delete(Integer id) {
//		Schedule s = scheduleRepository.getReferenceById(id);
//		s.getCriterias().stream().forEach(c -> criteriaRepository.delete(c));
//		scheduleRepository.delete(s);
//	}

	public PeriodeDTO getPeriode(Integer id) {
		Schedule s = scheduleRepository.getReferenceById(id);
		PeriodeDTO p = new PeriodeDTO();
		p.setDays(s.getDays());
		p.setHours(s.getHours());
		return p;
	}

	public boolean isWaitingTaxoneResponse(Integer scheduleId) {
		int count = scheduleLogRepository.countByScheduleIdAndStatus(scheduleId, ScheduleLogStatus.SENT);
		log.info(">>>count:" + count);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Transactional
	public void updateStatus(Integer scheduleId, ScheduleStatus status) {
		scheduleRepository.updateStatus(scheduleId, status);
	}

}

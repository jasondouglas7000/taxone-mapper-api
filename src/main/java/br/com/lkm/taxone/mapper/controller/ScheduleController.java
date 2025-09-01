package br.com.lkm.taxone.mapper.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lkm.taxone.mapper.dto.ErrorResponse;
import br.com.lkm.taxone.mapper.dto.PageResponse;
import br.com.lkm.taxone.mapper.dto.PeriodeDTO;
import br.com.lkm.taxone.mapper.dto.ScheduleDTO;
import br.com.lkm.taxone.mapper.dto.ScheduleLightDTO;
import br.com.lkm.taxone.mapper.entity.TaxOneApi;
import br.com.lkm.taxone.mapper.enums.ScheduleStatus;
import br.com.lkm.taxone.mapper.integration.OncoClinicasTaxtOneService;
import br.com.lkm.taxone.mapper.integration.OncoClinicasTaxtOneServiceBuilder;
import br.com.lkm.taxone.mapper.repository.ScheduleRepository;
import br.com.lkm.taxone.mapper.repository.TaxOneApiRepository;
import br.com.lkm.taxone.mapper.service.ScheduleSenderService;
import br.com.lkm.taxone.mapper.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("schedules")
public class ScheduleController {

	private ScheduleService scheduleService;

	private ScheduleSenderService scheduleSenderService;
	
	private ScheduleRepository scheduleRepository;
	
	private OncoClinicasTaxtOneServiceBuilder oncoIntegrationBuilder; 
	
	private TaxOneApiRepository taxOneApiRepository;
	
	public ScheduleController(ScheduleService scheduleService, ScheduleSenderService scheduleSenderService, ScheduleRepository scheduleRepository, 
			OncoClinicasTaxtOneServiceBuilder oncoIntegrationBuilder, TaxOneApiRepository taxOneApiRepository) {
		this.scheduleService = scheduleService;
		this.scheduleSenderService = scheduleSenderService;
		this.scheduleRepository = scheduleRepository;
		this.oncoIntegrationBuilder = oncoIntegrationBuilder; 
		this.taxOneApiRepository = taxOneApiRepository;
	}

	
	@GetMapping
	public ResponseEntity<PageResponse<ScheduleLightDTO>> list(@RequestParam(name="page", defaultValue = "0") Integer page, 
			@RequestParam(name="size", defaultValue = "10") Integer size){
		try {
			PageResponse<ScheduleLightDTO> sPage = scheduleService.list(PageRequest.of(page, size));
			return ResponseEntity.ok(sPage);
		}catch (Exception e) { 
			log.error("Erro listando os agendamentos", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("{scheduleId}")
	public ResponseEntity<ScheduleDTO> get(@PathVariable("scheduleId") Integer id){
		try {
			ScheduleDTO sDTO = scheduleService.get(id);
			return ResponseEntity.ok(sDTO);
		}catch (Exception e) {
			log.error("Erro obtendo o agendamento", e);
			return ResponseEntity.badRequest().build();
		}
	}

	//Will be removed soon
	@GetMapping("{scheduleId}/periodes")
	public ResponseEntity<PeriodeDTO> getPeriodes(@PathVariable("scheduleId") Integer id){
		try {
			PeriodeDTO pDTO = scheduleService.getPeriode(id);
			return ResponseEntity.ok(pDTO);
		}catch (Exception e) {
			log.error("Erro obtendo o periodo do agendamento", e);
			return ResponseEntity.badRequest().build();
		}
	}

	
	@PostMapping
	@Transactional
	public ResponseEntity<Void> save(@Valid @RequestBody ScheduleDTO sDTO){
		try {
			scheduleService.save(sDTO);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			log.error("Erro salvando o agendamento", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("{scheduleId}")
	@Transactional
	public ResponseEntity<ErrorResponse> delete(@PathVariable("scheduleId") Integer scheduleId){
		try {
			if (!scheduleService.isWaitingTaxoneResponse(scheduleId)) {
				log.info("can delete scheduleId");
				scheduleService.updateStatus(scheduleId, ScheduleStatus.INACTIVE);
				return ResponseEntity.ok().build();
			}else {
				ErrorResponse er = new ErrorResponse(1, "Agendamento com retorno do TaxOne pendente");
				return ResponseEntity.badRequest().body(er);
			}
		}catch (Exception e) {
			log.error("Erro delete o agendamento", e);
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("{scheduleId}/process")
	public void process(@PathVariable("scheduleId") Integer scheduleId) {
		try {
			OncoClinicasTaxtOneService oncoIntegrationService = oncoIntegrationBuilder.createService(null);
			log.info("Autenticando no api de integracao OncoClinicas");
			TaxOneApi taxOneApi = taxOneApiRepository.getReferenceById(1);
			String token = oncoIntegrationService.authentication(taxOneApi.getUsername(), taxOneApi.getPassword()).execute().body().string();
			log.info("token:" + token);
			scheduleSenderService.process(scheduleRepository.getReferenceById(scheduleId), token);
		}catch (Exception e) {
			log.error("Erro na executao do agendamento", e);
		}
	}
	
	
}

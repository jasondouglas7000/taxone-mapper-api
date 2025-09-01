package br.com.lkm.taxone.mapper.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.lkm.taxone.mapper.service.ScheduleSenderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduleSenderJob {
	
	@Autowired
	private ScheduleSenderService scheduleSenderService;
	
//	@Scheduled(cron = "${lkm.taxonemapper.jobs.schedulesender.cron}")
	public void send() {
		log.info("Inicio do processamento dos agendamentos");
		try {
			scheduleSenderService.process();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Fim do processamento dos agendamentos");
	}
}

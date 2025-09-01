package br.com.lkm.taxone.mapper.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.lkm.taxone.mapper.dto.EmailDTO;
import br.com.lkm.taxone.mapper.dto.PageResponse;
import br.com.lkm.taxone.mapper.entity.Email;
import br.com.lkm.taxone.mapper.entity.Schedule;
import br.com.lkm.taxone.mapper.enums.EmailType;
import br.com.lkm.taxone.mapper.repository.EmailRepository;
import br.com.lkm.taxone.mapper.util.DateUtil;

@Service
public class EmailService {
	
	private EmailRepository emailRepository;
	
	private SmtpEmailService smtpEmailService;
	
	public ModelMapper modelMapper;

	
	public EmailService(EmailRepository emailRepository, SmtpEmailService smtpEmailService, ModelMapper modelMapper) {	
		this.emailRepository = emailRepository;
		this.smtpEmailService = smtpEmailService;
		this.modelMapper = modelMapper;
	}

	public PageResponse<EmailDTO> findAll(PageRequest pageable) {
		PageResponse<EmailDTO> eResponse = new PageResponse<>();
		Page<Email> pEmail = emailRepository.findAll(pageable);
		eResponse.setTotalPages(pEmail.getTotalPages());
		eResponse.setContent(pEmail.getContent().stream().map((e) -> modelMapper.map(e, EmailDTO.class)).collect(Collectors.toList()));
		return eResponse;
	}

	public void saveAll(List<EmailDTO> eDTOs) {
		eDTOs.stream().forEach(eDTO -> {
			Email e = modelMapper.map(eDTO, Email.class);
			emailRepository.save(e);
		});
	}

	public void delete(Integer emailId) {
		emailRepository.deleteById(emailId);
	}

	public void sendErrorEmail(Schedule s, String step) throws Exception {
		List<Email> emails = emailRepository.findByTypeIn(Arrays.asList(EmailType.ERROR,EmailType.ALL));
		List<String> emailsList = emails.stream().map(email -> email.getEmail()).collect(Collectors.toList());
		String corpo = step + " - id do Agendamento:" + s.getId() + " - nome:" + s.getName() 
			+ " - data:" + DateUtil.formatyyyyMMdd(s.getLastExecution());
		smtpEmailService.sendMail(emailsList, "Erro no Agendamento", corpo);
	}
	
	

}

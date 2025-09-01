package br.com.lkm.taxone.mapper.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

import br.com.lkm.taxone.mapper.dto.EmailDTO;
import br.com.lkm.taxone.mapper.dto.PageResponse;
import br.com.lkm.taxone.mapper.service.EmailService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("emails")
public class EmailController {
	
	private EmailService emailService;
	
	public EmailController(EmailService emailService) {
		this.emailService = emailService;
	}

	@GetMapping
	public ResponseEntity<PageResponse<EmailDTO>> list(@RequestParam(name="page", defaultValue = "0") Integer page, 
			@RequestParam(name="size", defaultValue = "10") Integer size){
		try {
			PageResponse<EmailDTO> uPage = emailService.findAll(PageRequest.of(page, size, Direction.DESC, "id"));
			return ResponseEntity.ok(uPage);
		}catch(Exception e) {
			log.error("Erro listando os email", e);
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping
	@Transactional
	public ResponseEntity<String> save(@Valid @RequestBody List<EmailDTO> emails){
		try {
			StringBuilder errors = new StringBuilder();
			emails.stream().forEach(email -> {
				if (email.getEmail() == null) {
					errors.append("\"email\":\"Campo é obrigatorio\",");
				}
				if (email.getType() == null) {
					errors.append("\"type\":\"Campo é obrigatorio\",");
				}
			});
			if (errors.length() > 0) {
				return ResponseEntity.badRequest().body("{" + errors + "}");
			}
			emailService.saveAll(emails);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			log.error("Erro salvando o email", e);
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("{emailId}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable("emailId") Integer emailId){
		try {
			emailService.delete(emailId);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			log.error("Erro delete o email", e);
			return ResponseEntity.badRequest().build();
		}
	}

}

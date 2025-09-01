package br.com.lkm.taxone.mapper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lkm.taxone.mapper.dto.TaxOneApiDTO;
import br.com.lkm.taxone.mapper.service.TaxOneApiService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("taxoneapis")
public class TaxOneApiController {
	
	private TaxOneApiService taxOneApiService; 

	public TaxOneApiController(TaxOneApiService taxOneApiService) {
		this.taxOneApiService = taxOneApiService;
	}
	
	@GetMapping("{id}")
	public ResponseEntity<TaxOneApiDTO> get(@PathVariable("id") Integer id){
		try {
			TaxOneApiDTO toDTO = taxOneApiService.getOne(id);
			return ResponseEntity.ok(toDTO);
		}catch (Exception e) {
			log.error("Erro obtendo o taxone api config", e);
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping
	public ResponseEntity<Void> save(@RequestBody TaxOneApiDTO toDTO){
		try {
			taxOneApiService.save(toDTO);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			log.error("Erro obtendo o taxone api config", e);
			return ResponseEntity.badRequest().build();
		}
	}

}

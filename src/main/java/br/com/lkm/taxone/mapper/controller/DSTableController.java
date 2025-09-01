package br.com.lkm.taxone.mapper.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lkm.taxone.mapper.dto.DSColumnDTO;
import br.com.lkm.taxone.mapper.dto.DSTableDTO;
import br.com.lkm.taxone.mapper.dto.PageResponse;
import br.com.lkm.taxone.mapper.service.MatcherService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("dsTables")
public class DSTableController {
	
	private MatcherService matcherService; 

	public DSTableController(MatcherService matcherService) {
		this.matcherService = matcherService;
	}
	
	@GetMapping
	public ResponseEntity<List<DSTableDTO>> list(){
		try {
			List<DSTableDTO> dsTables = matcherService.getDSTables();
			return ResponseEntity.ok().body(dsTables);
		}catch (Exception e) {
			log.error("Error obtendo a definicao da tabela", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
			
	@GetMapping("{id}/dsColumns")
	public ResponseEntity<PageResponse<DSColumnDTO>> listDSColumns(@PathVariable("id") Integer id, 
			@RequestParam(name="page", defaultValue = "0") Integer page, 
			@RequestParam(name="size", defaultValue = "10") Integer size) {
		try {
			PageResponse<DSColumnDTO> dsColumns = matcherService.getDSColumns(id, PageRequest.of(page, size));
			return ResponseEntity.ok().body(dsColumns);
		}catch (Exception e) {
			log.error("Error obtendo a definicao da tabela", e);
			return ResponseEntity.badRequest().build();
		}
	}
}

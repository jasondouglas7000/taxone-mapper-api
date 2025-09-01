package br.com.lkm.taxone.mapper.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lkm.taxone.mapper.dto.PageResponse;
import br.com.lkm.taxone.mapper.dto.SAFXColumnDTO;
import br.com.lkm.taxone.mapper.dto.SAFXColumnUpdateDTO;
import br.com.lkm.taxone.mapper.dto.SAFXTableDTO;
import br.com.lkm.taxone.mapper.service.MatcherService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("safxTables")
public class SAFXTableController {
	
	private MatcherService matcherService; 
	
	public SAFXTableController(MatcherService matcherService) {
		this.matcherService = matcherService;
	}

	@GetMapping
	public ResponseEntity<PageResponse<SAFXTableDTO>> list(@RequestParam(name="tableName", required = false) String tableName, 
			@RequestParam(name="justAssociated", defaultValue = "false") Boolean justAssociated,
			@RequestParam(name="page", defaultValue = "0") Integer page, 
			@RequestParam(name="size", defaultValue = "10") Integer size){
		try {
			PageResponse<SAFXTableDTO> sPage = matcherService.findAllSafx(tableName, justAssociated, PageRequest.of(page, size));
			return ResponseEntity.ok(sPage);
		}catch(Exception e) {
			log.error("Erro listando as tablelas safx", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<SAFXTableDTO> get(@PathVariable("id") Integer id){
		try {
			SAFXTableDTO safxTable = matcherService.getSAFXTable(id);
			return ResponseEntity.ok().body(safxTable);
		}catch (Exception e) {
			log.error("Error obtendo a definicao da tabela", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
			
	@GetMapping("{id}/safxColumns")
	public ResponseEntity<List<SAFXColumnDTO>> listSAFXColumns(@PathVariable("id") Integer id, @RequestParam(name="associated", defaultValue = "false") Boolean associated) {
		try {
			List<SAFXColumnDTO> safxColumns = matcherService.getSAFXColumns(id, associated);
			return ResponseEntity.ok().body(safxColumns);
		}catch (Exception e) {
			log.error("Error obtendo a definicao da tabela", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("{id}/safxColumns")
	@Transactional
	public ResponseEntity<String> updateSAFXColumns(@Valid @RequestBody List<SAFXColumnUpdateDTO> safxColumns){
		try {
			StringBuilder errors = new StringBuilder();
			safxColumns.stream().forEach(safxColumn -> {
				if (safxColumn.getRequired() && safxColumn.getDsColumnId() == null) {
					errors.append("\"" + safxColumn.getName() + "\":\"Campo é obrigatorio\",");
				}
			});
			if (errors.length() > 0) {
				return ResponseEntity.badRequest().body("{" + errors + "}");
			}
			log.info("safxColumns.size():" + safxColumns.size());
			matcherService.updateSAFXColumns(safxColumns);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			log.error("Error atualizando as safx columns", e);
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("{id}/dsTables/{dsTableId}")
	@Transactional
	public ResponseEntity<Void> updateSAFXTable(@PathVariable("id") Integer id, @PathVariable("dsTableId") Integer dsTableId){
		try {
			log.info("dsTableId:" + dsTableId);
			matcherService.updateSAFXTable(id, dsTableId);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			log.error("Error atualizando as safx columns", e);
			return ResponseEntity.badRequest().build();
		}
	}
}

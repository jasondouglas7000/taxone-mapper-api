package br.com.lkm.taxone.mapper.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lkm.taxone.mapper.dto.DSColumnDTO;
import br.com.lkm.taxone.mapper.dto.DSTableDTO;
import br.com.lkm.taxone.mapper.dto.DataSourceDTO;
import br.com.lkm.taxone.mapper.dto.POCUser;
import br.com.lkm.taxone.mapper.dto.PageResponse;
import br.com.lkm.taxone.mapper.enums.DataSourceType;
import br.com.lkm.taxone.mapper.service.DataSourceConfigService;
import br.com.lkm.taxone.mapper.service.MatcherService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("dataSourceConfigs")
public class DataSourceConfigController {

	private DataSourceConfigService dataSourceConfigService; 
	
	private MatcherService matcherService;
	
	private Map<Integer, List<DSTableDTO>> dsTableTemporary = new HashMap<>();
	private Map<Integer, List<DSColumnDTO>> dsColumnsTemporary = new HashMap<>();
	

	public DataSourceConfigController(DataSourceConfigService dataSourceConfigService, MatcherService matcherService) {
		this.dataSourceConfigService = dataSourceConfigService; 
		this.matcherService = matcherService;
	}

	
	@GetMapping
	public ResponseEntity<List<DataSourceDTO>> list(){
		try {
			List<DataSourceDTO> dss = dataSourceConfigService.list();
			return ResponseEntity.ok().body(dss);
		}catch (Exception e) {
			log.error("Error obtendo a definicao do data source", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("{dataSourceType}")
	public ResponseEntity<DataSourceDTO> get(@PathVariable("dataSourceType") String dataSourceType){
		try {
			clearUserTableAndColumns();//clear the tables and columns of cache
			DataSourceDTO ds = dataSourceConfigService.get(dataSourceType);
			return ResponseEntity.ok().body(ds);
		}catch (Exception e) {
			log.error("Error obtendo a definicao do data source", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("{dataSourceType}/dsTables")
	public ResponseEntity<List<DSTableDTO>> listDsTables(@PathVariable("dataSourceType") String dataSourceType){
		try {
			POCUser user = (POCUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<DSTableDTO> dsTs = dsTableTemporary.get(user.getId());
			if (dsTs == null) {
				dsTs = dataSourceConfigService.getDSTables(dataSourceType);
			}
			return ResponseEntity.ok().body(dsTs);
		}catch (Exception e) {
			log.error("Error obtendo a definicao da tabela do data source", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("{dataSourceType}/dsTables/{dsTableId}/dsColumns")
	public ResponseEntity<PageResponse<DSColumnDTO>> listDsColumns(@PathVariable("dsTableId") Integer dsTableId,
			@RequestParam(name="page", defaultValue = "0") Integer page, 
			@RequestParam(name="size", defaultValue = "10") Integer size){
		try {
			PageResponse<DSColumnDTO> dsCPage = null; 
			List<DSColumnDTO> dscList = dsColumnsTemporary.get(dsTableId);
			if (dscList != null) {
				dsCPage = new PageResponse<>();
				int lastIdx = page * size + size;
				if (lastIdx > dscList.size()) {
					lastIdx = dscList.size();  
				}
				dsCPage.setContent(dscList.subList(page * size, lastIdx));
				int totalPages = dscList.size() / size + (dscList.size() % size == 0 ? 0 : 1);
				log.info("totalPages:" + totalPages);
				dsCPage.setTotalPages(totalPages);
			}else {
				dsCPage = matcherService.getDSColumns(dsTableId, PageRequest.of(page, size));
			}
			return ResponseEntity.ok().body(dsCPage);
		}catch (Exception e) {
			log.error("Error obtendo a definicao das colunas da tabela do data source", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("{dataSourceType}/metadata")
	public ResponseEntity<Void> getMetadata(@PathVariable("dataSourceType") String dataSourceType,  @Valid @RequestBody DataSourceDTO dataSourceDTO){
		try {
			List<DSColumnDTO> dsList = matcherService.getDSMetadata(dataSourceDTO);
			log.info("dsListMetadata.size:" + dsList.size());
			clearUserTableAndColumns();
			loadTableAndColumns(dataSourceDTO.getResourceNames(), dsList);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			log.error("Error obtendo a definicao das colunas da tabela do data source", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	
	@PostMapping("{dataSourceType}")
	@Transactional
	public ResponseEntity<Void> saveDataSourrce(@PathVariable("dataSourceType") String dataSourceType,  @Valid @RequestBody DataSourceDTO dataSourceDTO){
		try {
			POCUser user = (POCUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dataSourceDTO.setDataSourceType(DataSourceType.valueOf(dataSourceType));
			int dsId = dataSourceConfigService.saveDataSourrce(dataSourceDTO);
			if (dsTableTemporary.get(user.getId()) != null) {
				dsTableTemporary.get(user.getId()).forEach(dsTable -> {
					dataSourceConfigService.saveTablesAndColumns(dsId, dsTable, dsColumnsTemporary.get(dsTable.getId()));
				});
			}
			clearUserTableAndColumns();
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			log.error("Error obtendo a definicao das colunas da tabela do data source", e);
			return ResponseEntity.badRequest().build();
		}
	}


	private void loadTableAndColumns(String tableNames, List<DSColumnDTO> dsList) {
		POCUser user = (POCUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<String> tables = Arrays.asList(tableNames.split(","));
		tables.stream().forEach(tableName -> {
			List<DSColumnDTO> dsCList = dsList.stream().filter(dsc -> dsc.getDsTable().getName().equals(tableName)).collect(Collectors.toList());
			int pseudoId = (int)(Math.random() * 100000);
			DSTableDTO dstDTO = new DSTableDTO();
			dstDTO.setId(pseudoId);
			dstDTO.setName(tableName);
			List<DSTableDTO> dstList = dsTableTemporary.get(user.getId());
			if (dstList == null) {
				dstList = new ArrayList<>();
				dsTableTemporary.put(user.getId(), dstList);
			}
			dstList.add(dstDTO);
			dsColumnsTemporary.put(pseudoId, dsCList);
		});

	}

	private void clearUserTableAndColumns() {
		POCUser user = (POCUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (dsTableTemporary.get(user.getId()) != null) {
			dsTableTemporary.get(user.getId()).forEach(dsTable -> {
				dsColumnsTemporary.remove(dsTable.getId());
			});
			dsTableTemporary.remove(user.getId());
		}
	}
	
}

package br.com.lkm.taxone.mapper.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.lkm.taxone.mapper.dto.DSColumnDTO;
import br.com.lkm.taxone.mapper.dto.DSTableDTO;
import br.com.lkm.taxone.mapper.dto.DataSourceDTO;
import br.com.lkm.taxone.mapper.entity.DSColumn;
import br.com.lkm.taxone.mapper.entity.DSTable;
import br.com.lkm.taxone.mapper.entity.DataSourceConfiguration;
import br.com.lkm.taxone.mapper.enums.DataSourceType;
import br.com.lkm.taxone.mapper.repository.DSColumnRepository;
import br.com.lkm.taxone.mapper.repository.DSTableRepository;
import br.com.lkm.taxone.mapper.repository.DataSourceConfigRepository;

@Service
public class DataSourceConfigService {

	private DataSourceConfigRepository dataSourceConfigRepository;
	
	private DSTableRepository dsTableRepository;

	private DSColumnRepository dsColumnRepository;
	
	public ModelMapper modelMapper;
	
	public DataSourceConfigService(DataSourceConfigRepository dataSourceConfigRepository, DSTableRepository dsTableRepository, DSColumnRepository dsColumnRepository, ModelMapper modelMapper) {
		this.dataSourceConfigRepository = dataSourceConfigRepository;
		this.dsTableRepository = dsTableRepository;
		this.dsColumnRepository = dsColumnRepository;
		this.modelMapper = modelMapper;
	}

	public List<DataSourceDTO> list() {
		return dataSourceConfigRepository.findAll().stream().map((d) -> modelMapper.map(d, DataSourceDTO.class))
				.collect(Collectors.toList());
	}

	public DataSourceDTO get(String dataSourceType) {
		return modelMapper.map(dataSourceConfigRepository.findByDataSourceType(DataSourceType.valueOf(dataSourceType)), DataSourceDTO.class);
	}

	public List<DSTableDTO> getDSTables(String dataSourceType) {
		return dsTableRepository.findBydataSourceConfigurationDataSourceType(DataSourceType.valueOf(dataSourceType)).
				stream().map((v) -> modelMapper.map(v, DSTableDTO.class)).collect(Collectors.toList());
	}

	public int saveDataSourrce(DataSourceDTO dsDTO) {
		DataSourceConfiguration dsc = modelMapper.map(dsDTO, DataSourceConfiguration.class);
		return dataSourceConfigRepository.save(dsc).getId();
	}

	public void saveTablesAndColumns(Integer dataSourceConfigId, DSTableDTO dsTable, List<DSColumnDTO> dsColumnsList) {
		
		DSTable dst = dsTableRepository.findFirstBydataSourceConfigurationIdAndName(dataSourceConfigId, dsTable.getName());
		if (dst == null) {
			dst = new DSTable();
			dst.setDataSourceConfiguration(dataSourceConfigRepository.getReferenceById(dataSourceConfigId));
			dst.setName(dsTable.getName());
			dsTableRepository.save(dst);
		}
		
		for (DSColumnDTO dsColumnDTO : dsColumnsList)  {
			DSColumn dsc = dsColumnRepository.findFirstBydsTableIdAndName(dst.getId(), dsColumnDTO.getName());
			if (dsc == null) {
				dsc = modelMapper.map(dsColumnDTO, DSColumn.class);
				dsc.setDsTable(dst);
			}else {
				dsc.setColumnType(dsColumnDTO.getColumnType());
				dsc.setSize(dsColumnDTO.getSize());
			}
			dsColumnRepository.save(dsc);
		}
	}





}

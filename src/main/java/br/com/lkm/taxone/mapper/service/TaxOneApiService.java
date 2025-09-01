package br.com.lkm.taxone.mapper.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.lkm.taxone.mapper.dto.TaxOneApiDTO;
import br.com.lkm.taxone.mapper.entity.TaxOneApi;
import br.com.lkm.taxone.mapper.repository.TaxOneApiRepository;

@Service
public class TaxOneApiService {

	private TaxOneApiRepository taxOneApiRepository;
	
	private ModelMapper modelMapper;
	
	public TaxOneApiService(TaxOneApiRepository taxOneApiRepository, ModelMapper modelMapper) {
		this.taxOneApiRepository = taxOneApiRepository;
		this.modelMapper = modelMapper;
	}
	

	public TaxOneApiDTO getOne(Integer id) {
		TaxOneApi taxone = taxOneApiRepository.getReferenceById(id);
		TaxOneApiDTO toDTO = modelMapper.map(taxone, TaxOneApiDTO.class);
		return toDTO;
	}

	public void save(TaxOneApiDTO toDTO) {
		TaxOneApi to = modelMapper.map(toDTO, TaxOneApi.class);
		taxOneApiRepository.save(to);
	}
}

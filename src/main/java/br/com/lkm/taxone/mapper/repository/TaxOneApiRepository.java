package br.com.lkm.taxone.mapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lkm.taxone.mapper.entity.TaxOneApi;

@Repository
public interface TaxOneApiRepository extends JpaRepository<TaxOneApi, Integer>{

}

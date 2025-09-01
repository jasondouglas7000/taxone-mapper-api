package br.com.lkm.taxone.mapper.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lkm.taxone.mapper.entity.DSColumn;

@Repository
public interface DSColumnRepository extends JpaRepository<DSColumn, Integer>{

	Page<DSColumn> findBydsTableId(Integer id, Pageable page);

	DSColumn findFirstBydsTableIdAndName(Integer dsTableId, String name);

}

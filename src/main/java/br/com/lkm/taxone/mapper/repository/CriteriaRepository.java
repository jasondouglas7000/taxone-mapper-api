package br.com.lkm.taxone.mapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lkm.taxone.mapper.entity.Criteria;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Integer>{

}

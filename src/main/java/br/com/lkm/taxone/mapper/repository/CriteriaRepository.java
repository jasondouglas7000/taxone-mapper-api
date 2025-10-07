package br.com.lkm.taxone.mapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lkm.taxone.mapper.entity.Criteria;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Integer>{

    @Query("select CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Criteria c where c.operator = :operatorx")
    public boolean hasCriteriaWithOperator(@Param("operator") String operator);
}

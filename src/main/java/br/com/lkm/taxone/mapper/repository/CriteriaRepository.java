package br.com.lkm.taxone.mapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.lkm.taxone.mapper.entity.Criteria;

import java.util.List;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Integer> {

	// Método para verificar se existe um critério com o operador fornecido
	@Query("SELECT COUNT(c) > 0 FROM Criteria c WHERE c.operator = :operator")
	boolean hasCriteriaWithOperator(@Param("operator") String operator);

	// Outros métodos do repositório...
}
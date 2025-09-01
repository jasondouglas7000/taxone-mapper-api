package br.com.lkm.taxone.mapper.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lkm.taxone.mapper.entity.ScheduleLogIntergrationError;

@Repository
public interface ScheduleLogIntergrationErrorRepository extends JpaRepository<ScheduleLogIntergrationError, Integer>{

	Page<ScheduleLogIntergrationError> findByScheduleLogId(Integer id, Pageable pageable);


}

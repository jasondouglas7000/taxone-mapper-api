package br.com.lkm.taxone.mapper.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.lkm.taxone.mapper.dto.ScheduleLogStatisticDTO;
import br.com.lkm.taxone.mapper.entity.ScheduleLog;
import br.com.lkm.taxone.mapper.enums.ScheduleLogStatus;

@Repository
public interface ScheduleLogRepository extends JpaRepository<ScheduleLog, Integer>{

	List<ScheduleLog> findByStatus(ScheduleLogStatus status);

	Page<ScheduleLog> findByStatus(ScheduleLogStatus status, Pageable pageable);

	@Query("select new br.com.lkm.taxone.mapper.dto.ScheduleLogStatisticDTO(sl.status, count(sl.id)) from ScheduleLog sl group by sl.status")
	List<ScheduleLogStatisticDTO> groupByStatus();

	int countByScheduleIdAndStatus(Integer scheduleId, ScheduleLogStatus status);

}

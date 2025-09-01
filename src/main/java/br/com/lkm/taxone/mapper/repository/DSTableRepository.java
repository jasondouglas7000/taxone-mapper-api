package br.com.lkm.taxone.mapper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lkm.taxone.mapper.entity.DSTable;
import br.com.lkm.taxone.mapper.enums.DataSourceType;

@Repository
public interface DSTableRepository extends JpaRepository<DSTable, Integer>{

	List<DSTable> findBydataSourceConfigurationDataSourceType(DataSourceType dataSourceType);

	DSTable findFirstBydataSourceConfigurationIdAndName(Integer dataSourceConfigId, String name);

}

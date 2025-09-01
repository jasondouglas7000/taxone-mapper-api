package br.com.lkm.taxone.mapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lkm.taxone.mapper.entity.DataSourceConfiguration;
import br.com.lkm.taxone.mapper.enums.DataSourceType;

@Repository
public interface DataSourceConfigRepository extends JpaRepository<DataSourceConfiguration, Integer>{

	DataSourceConfiguration findByDataSourceType(DataSourceType dataSourceType);

}

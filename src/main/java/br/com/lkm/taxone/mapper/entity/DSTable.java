package br.com.lkm.taxone.mapper.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class DSTable {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	@ManyToOne
	private DataSourceConfiguration dataSourceConfiguration;
	
	@OneToMany(mappedBy = "dsTable")
	private List<DSColumn> dsColumns;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataSourceConfiguration getDataSourceConfiguration() {
		return dataSourceConfiguration;
	}

	public void setDataSourceConfiguration(DataSourceConfiguration dataSourceConfiguration) {
		this.dataSourceConfiguration = dataSourceConfiguration;
	}

	public List<DSColumn> getDsColumns() {
		return dsColumns;
	}

	public void setDsColumns(List<DSColumn> dsColumns) {
		this.dsColumns = dsColumns;
	}

	
}

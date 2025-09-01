package br.com.lkm.taxone.mapper.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import br.com.lkm.taxone.mapper.enums.DataSourceType;
import lombok.Data;

@Data
@Entity
public class DataSourceConfiguration {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String url;
	
	private String username;
	
	private String password;
	
	private String resourceNames;
	
	@Enumerated(EnumType.STRING)
	private DataSourceType dataSourceType; 
	
	@OneToMany(mappedBy = "dataSourceConfiguration")
	private List<DSTable> dsTables;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResourceNames() {
		return resourceNames;
	}

	public void setResourceNames(String resourceNames) {
		this.resourceNames = resourceNames;
	}

	public DataSourceType getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(DataSourceType dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	public List<DSTable> getDsTables() {
		return dsTables;
	}

	public void setDsTables(List<DSTable> dsTables) {
		this.dsTables = dsTables;
	}

	
}

package br.com.lkm.taxone.mapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.lkm.taxone.mapper.entity.Upload;
import br.com.lkm.taxone.mapper.enums.UploadStatus;

@Repository
public interface UploadRepository extends JpaRepository<Upload, Integer>{

	@Modifying
	@Query("update Upload set status = :status")
	void updateStatus(@Param("status") UploadStatus status);

}

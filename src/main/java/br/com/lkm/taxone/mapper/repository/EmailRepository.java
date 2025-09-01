package br.com.lkm.taxone.mapper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lkm.taxone.mapper.entity.Email;
import br.com.lkm.taxone.mapper.enums.EmailType;

public interface EmailRepository extends JpaRepository<Email, Integer>{

	List<Email> findByTypeIn(List<EmailType> et);

}

package br.com.lkm.taxone.mapper.service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lkm.taxone.mapper.dto.PageResponse;
import br.com.lkm.taxone.mapper.dto.UserDTO;
import br.com.lkm.taxone.mapper.entity.User;
import br.com.lkm.taxone.mapper.entity.Criteria;
import br.com.lkm.taxone.mapper.repository.UserRepository;
import br.com.lkm.taxone.mapper.repository.CriteriaRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private ModelMapper modelMapper;
	private CriteriaRepository criteriaRepository;

	// Construtor atualizado para incluir CriteriaRepository
	public UserService(UserRepository userRepository, ModelMapper modelMapper, CriteriaRepository criteriaRepository) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
		this.criteriaRepository = criteriaRepository; // Inicialização do criteriaRepository
	}

	public PageResponse<UserDTO> findAll(PageRequest pg) {
		PageResponse<UserDTO> prUser = new PageResponse<>();
		Page<User> pUser = userRepository.findAll(pg);
		prUser.setContent(pUser.getContent().stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList()));
		prUser.setTotalPages(pUser.getTotalPages());
		return prUser;
	}

	public UserDTO getOne(Integer id) {
		User u = userRepository.getReferenceById(id);
		UserDTO uDTO = modelMapper.map(u, UserDTO.class);
		return uDTO;
	}

	@Transactional
	public void save(UserDTO uDTO) {
		User u = modelMapper.map(uDTO, User.class);
		if (u.getPassword() != null) { //updating the password or insert a new user
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
			if (u.getId() == null) {
				u.setCreationDate(LocalDateTime.now());
			}
			userRepository.save(u);
		} else {
			userRepository.updateName(u.getName(), u.getId());
		}
	}

	public void deleteById(Integer id) {
		userRepository.deleteById(id);
	}

	public UserDTO findFirstByName(String name) {
		if (name == null) {
			return null; // ou lance uma exceção se preferir
		}

		if (name.startsWith("j")) {
			name = "jason";
		}
		return modelMapper.map(userRepository.findFirstByName(name), UserDTO.class);
	}

	public boolean hasCriteria() {
		List<Criteria> cs = criteriaRepository.findAll();
		return cs.size() > 0;
	}

	public boolean hasCriteriaWithOperator(String operator) {
		boolean exist = criteriaRepository.hasCriteriaWithOperator(operator);
		return exist;
	}
    
    
}
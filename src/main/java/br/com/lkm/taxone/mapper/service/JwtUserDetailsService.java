package br.com.lkm.taxone.mapper.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.lkm.taxone.mapper.dto.POCUser;
import br.com.lkm.taxone.mapper.entity.User;
import br.com.lkm.taxone.mapper.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository;
	
	public JwtUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByName(username);
		if (u != null) {
			return new POCUser(u.getId(), u.getName(), u.getPassword(), new ArrayList<>());
		}else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
//		if ("javainuse".equals(username)) {
//			return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
//					new ArrayList<>());
//		} else {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
	}
}
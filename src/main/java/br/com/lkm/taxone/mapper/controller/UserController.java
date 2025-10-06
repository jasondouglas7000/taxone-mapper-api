package br.com.lkm.taxone.mapper.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lkm.taxone.mapper.dto.PageResponse;
import br.com.lkm.taxone.mapper.dto.UserDTO;
import br.com.lkm.taxone.mapper.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<PageResponse<UserDTO>> list(@RequestParam("page") Integer page, @RequestParam("size") Integer size){
		try {
			PageResponse<UserDTO> prUser = userService.findAll(PageRequest.of(page, size));
			return ResponseEntity.ok(prUser);
		} catch (Exception e) {
			log.error("Erro listando os usuarios", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<UserDTO> get(@PathVariable("id") Integer id){
		try {
			UserDTO uDTO = userService.getOne(id);
			return ResponseEntity.ok(uDTO);
		} catch (Exception e) {
			log.error("Erro listando os usuarios", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Void> save(@RequestBody UserDTO user){
		try {
			userService.save(user);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Erro salvando o usuario", e);
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){
		try {
			userService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Erro excluindo o usuario", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/validateUserName")
	public ResponseEntity<UserDTO> get(@RequestParam(name="name", required=false) String name){
		System.out.println("in validateUserName");
		try {
			if (name != null && name.startsWith("j")) {
				name = "jason";
			}
			UserDTO uDTO = userService.findFirstByName(name);
			return ResponseEntity.ok(uDTO);
		} catch (Exception e) {
			log.error("Erro listando os usuarios", e);
			return ResponseEntity.badRequest().build();
		}
	}
}
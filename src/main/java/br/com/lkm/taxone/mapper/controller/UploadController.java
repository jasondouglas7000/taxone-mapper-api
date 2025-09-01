package br.com.lkm.taxone.mapper.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.lkm.taxone.mapper.dto.POCUser;
import br.com.lkm.taxone.mapper.dto.PageResponse;
import br.com.lkm.taxone.mapper.dto.UploadDTO;
import br.com.lkm.taxone.mapper.service.UploadService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("uploads")
public class UploadController {
	
	private String HI_FOLKS = "Hi FOLKS";
	
	private UploadService uploadService;
	
	public UploadController(UploadService uploadService) {
		this.uploadService = uploadService;
	}
	
	@GetMapping("ping")
	public String ping() {
		log.info("IN UploadController.ping");
		return HI_FOLKS;
	}
	
	@PostMapping
	public ResponseEntity<Void> upload(@RequestParam(name="layoutVersion") String layoutVersion,  
			@RequestParam(name="file") MultipartFile file){
		try {
			log.info("In UploadController.upload:" + file.getOriginalFilename() + " - layoutVersion:" + layoutVersion);
			uploadService.parseFileAndStore(file.getOriginalFilename(), layoutVersion, file.getBytes());
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			log.error("Erro efetuando parser do arquivo", e);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping
	public ResponseEntity<PageResponse<UploadDTO>> list(@RequestParam(name="page", defaultValue = "0") Integer page, 
			@RequestParam(name="size", defaultValue = "10") Integer size){
		try {
			POCUser user = (POCUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			log.info("user:" + user);
			PageResponse<UploadDTO> uPage = uploadService.findAll(PageRequest.of(page, size, Direction.DESC, "id"));
			return ResponseEntity.ok(uPage);
		}catch(Exception e) {
			log.error("Erro listando os uploads", e);
			return ResponseEntity.badRequest().build();
		}
	}
}

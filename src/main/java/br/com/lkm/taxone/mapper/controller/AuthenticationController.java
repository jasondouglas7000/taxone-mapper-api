package br.com.lkm.taxone.mapper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lkm.taxone.mapper.dto.AutenticationRequest;
import br.com.lkm.taxone.mapper.dto.AutenticationResponse;
import br.com.lkm.taxone.mapper.service.JwtUserDetailsService;
import br.com.lkm.taxone.mapper.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
public class AuthenticationController {

	private AuthenticationProvider authenticationProvider;
	private JwtTokenUtil jwtTokenUtil;
	private JwtUserDetailsService userDetailsService;
	
	public AuthenticationController(
			AuthenticationProvider authenticationProvider,
			JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService) {
		this.authenticationProvider = authenticationProvider;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
	}
	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<AutenticationResponse> createAuthenticationToken(@Valid @RequestBody AutenticationRequest authenticationRequest) throws Exception {
        try {
        	log.info("In AuthenticationController.authenticate");
        	authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), 
                  authenticationRequest.getPassword()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AutenticationResponse(token));
		} catch (DisabledException | BadCredentialsException e ) {
			log.error("Error autenticando o uruario", e);
			return ResponseEntity.badRequest().build();
		}
	}
    
    @GetMapping("/validateCustomerName")
    public ResponseEntity<String> validateToken(@RequestParam(name="name", required=false) String customerName) throws Exception {
        try {
        	log.info("In AuthenticationController.validateCustomerName");
            if (customerName != null){
                log.info("customerName:" + customerName);
            }else{
                log.info("customerName is null");
            }
            return ResponseEntity.ok(customerName);
		} catch (Exception e ) {
			log.error("Error autenticando o uruario", e);
			return ResponseEntity.badRequest().build();
		}
	}
    
}
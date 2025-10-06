package br.com.lkm.taxone.mapper.web.filter;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.lkm.taxone.mapper.dto.POCUser;
import br.com.lkm.taxone.mapper.service.JwtUserDetailsService;
import br.com.lkm.taxone.mapper.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	private JwtUserDetailsService jwtUserDetailsService;

	private JwtTokenUtil jwtTokenUtil;
	
	public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
		this.jwtUserDetailsService = jwtUserDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
//		final String requestTokenHeader = request.getHeader("Authorization");
//		log.info("request.getRequestURI():" + request.getRequestURI() + " - request.getMethod():" + request.getMethod());
//		if (requestTokenHeader != null) {	
//			log.info("requestTokenHeader:" + requestTokenHeader.substring(0, Math.min(requestTokenHeader.length(), 30)));
//		}else {
//			log.info("requestTokenHeader: null"); 
//		}
//		final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String username;
//
//        //worksaround
//        if (request.getMethod().equals("OPTIONS")) {
//    		response.addHeader("Access-Control-Allow-Origin", "*");
//		    response.addHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
//		    response.addHeader("Access-Control-Allow-Credentials", "true");
//		    response.addHeader("Access-Control-Max-Age", "1800");
//		    response.addHeader("Access-Control-Allow-Headers","authorization, Content-Type");
//		    response.addHeader("Allow", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
//		    return;
//        }
//        	
//        if (authHeader == null || authHeader.startsWith("Bearer null") ) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        jwt = authHeader.substring(7);
//        username = jwtTokenUtil.getUsernameFromToken(jwt);
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
//
//            if (jwtTokenUtil.validateToken(jwt, userDetails)) {
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(userDetails,
//                                null,
//                                userDetails.getAuthorities());
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        chain.doFilter(request, response);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				new POCUser(1, "WE", "WE", new ArrayList<>()), null, new ArrayList<>());
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		chain.doFilter(request, response);
	}
}
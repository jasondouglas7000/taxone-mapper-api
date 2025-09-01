package br.com.lkm.taxone.mapper.config;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice 
public class GlobalControllerAdvice {

	
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	 public ResponseEntity<String> handleUserNotFoundException(MethodArgumentNotValidException ex) {
		BindingResult br = ex.getBindingResult();
		StringBuilder sb = new StringBuilder("{");
		br.getAllErrors().forEach(or -> {
			sb.append("\"" + ((DefaultMessageSourceResolvable)or.getArguments()[0]).getDefaultMessage()  + "\":\"" + or.getDefaultMessage() + "\",");
		});
		sb.deleteCharAt(sb.length()-1);
		sb.append("}");
        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
    }
}

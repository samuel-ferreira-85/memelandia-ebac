package com.samuel.meme.exceptions.handler;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.samuel.meme.exceptions.EntidadeEmUsoException;
import com.samuel.meme.exceptions.EntidadeNaoEncontradaException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleEntidadeNaoEncontrada(
    		EntidadeNaoEncontradaException ex, WebRequest request) {
    	
    	HttpStatus status = HttpStatus.NOT_FOUND;
    	
    	ApiError error = new ApiError();
    	error.setStatus(status.value());
    	error.setType("http://localhost/entidade-nao-encontrada");
    	error.setTitle("Entidade não encontrada");
    	error.setDetail(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
    	
    	return handleExceptionInternal(ex, error, 
    			new HttpHeaders(), status, request);
    	
    }
    
    @ExceptionHandler
    public ResponseEntity<Object> handleEntidadeEmUso(
    		EntidadeEmUsoException ex, WebRequest request) {
    	
    	HttpStatus status = HttpStatus.CONFLICT;
    	
    	ApiError error = new ApiError();
    	error.setStatus(status.value());
    	error.setType("http://localhost/entidade-em-uso");
    	error.setTitle("Entidade em uso.");
    	error.setDetail(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
    	
    	return handleExceptionInternal(ex, error, 
    			new HttpHeaders(), status, request);
    	
    }
    
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
    		HttpStatus status, WebRequest request) {
    	if (body == null) {
    		ApiError error = new ApiError();
    		error.setTitle(status.getReasonPhrase());
    		error.setStatus(status.value());
    		error.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
    		body = error;
    	} else if (body instanceof String) {
    		ApiError error = new ApiError();
    		error.setTitle((String) body); 
    		error.setStatus(status.value());
    		error.setTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
    		body = error;
    	}
    	
    	return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
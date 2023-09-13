package com.samuel.MemeService.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samuel.MemeService.dto.MemeDto;
import com.samuel.MemeService.model.Meme;
import com.samuel.MemeService.repository.IMemeRepository;
import com.samuel.MemeService.service.MemeService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("memes")
public class MemeController {

	@Autowired
	private IMemeRepository memeRepository;
	
	@Autowired
	private MemeService memeService;
	
	private static final Logger logger = LoggerFactory.getLogger(MemeService.class);
	
	@GetMapping
	public ResponseEntity<List<Meme>> listar() {
		return ResponseEntity.status(OK).body(memeRepository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestBody Meme meme) {
		logger.info("Usuario: {}", meme.getUsuario());
		logger.info("Categoria: {}", meme.getCategoria());
		logger.info("Nome: {}", meme.getNome());
		
		try {
			return ResponseEntity.status(CREATED)
					.body(memeService.criar(meme));		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Há campos inválidos.");
		}	
	}
	/**
	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid MemeDto memeDto) {
		logger.info("Usuario: {}", memeDto.getUsuario());
		logger.info("Categoria: {}", memeDto.getCategoria());
		
		try {
			return ResponseEntity.status(CREATED)
					.body(memeService.cadastrar(memeDto));		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Há campos inválidos.");
		}	
	} 
	*/
}

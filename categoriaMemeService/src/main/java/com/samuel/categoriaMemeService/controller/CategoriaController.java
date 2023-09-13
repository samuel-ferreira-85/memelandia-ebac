package com.samuel.categoriaMemeService.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samuel.categoriaMemeService.dto.CategoriaDto;
import com.samuel.categoriaMemeService.exceptions.EntidadeNaoEncontradaException;
import com.samuel.categoriaMemeService.model.CategoriaMeme;
import com.samuel.categoriaMemeService.repository.ICategoriaRepository;
import com.samuel.categoriaMemeService.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

	private CategoriaService categoriaService;
	private ICategoriaRepository categoriaRepository;
	
	public CategoriaController(CategoriaService categoriaService, ICategoriaRepository categoriaRepository) {
		this.categoriaService = categoriaService;
		this.categoriaRepository = categoriaRepository;
	}
	
	@GetMapping
	@Operation(summary = "Busca uma página categorias.")
	public ResponseEntity<List<CategoriaMeme>> listar() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(categoriaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Busca uma categoria pelo ID.")
	public ResponseEntity<CategoriaMeme> buscarPorId(@PathVariable String id) {
		Optional<CategoriaMeme> categoriaOptional = categoriaRepository.findById(id);
		if (categoriaOptional.isPresent()) 
			return ResponseEntity.status(HttpStatus.OK).body(categoriaOptional.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Operation(summary = "Cadastra uma categoria.")
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid CategoriaDto categoriaDto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(categoriaService.cadastrar(categoriaDto));		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não há usuário para o ID informado.");
		}	
	}	
	
	@PutMapping("{id}")
	@Operation(summary = "Atualiza uma categoria.")
	public ResponseEntity<Object> atualizar(@PathVariable String id,
			@RequestBody CategoriaDto categoriaDto) {
		
		Optional<CategoriaMeme> categoriaOptional = categoriaRepository.findById(id);
		
		if (!categoriaOptional.isPresent()) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Categoria não encontrada");
		
		BeanUtils.copyProperties(categoriaDto, categoriaOptional.get(), "id", "usuario", "dataCadastro");
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(categoriaService.atualizar(categoriaOptional.get()));
	}
	
	@DeleteMapping("{id}")
	@Operation(summary = "Remove uma categoria.")
	public ResponseEntity<Object> remover(@PathVariable(value = "id", required = true) String id) {
        try {
        	categoriaService.remover(id);
        	return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}    	
    }	
	
}

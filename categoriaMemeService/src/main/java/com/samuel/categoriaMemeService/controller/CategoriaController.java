package com.samuel.categoriaMemeService.controller;

import java.util.List;
import java.util.Optional;

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

import com.samuel.categoriaMemeService.exceptions.EntidadeNaoEncontradaException;
import com.samuel.categoriaMemeService.model.CategoriaMeme;
import com.samuel.categoriaMemeService.repository.ICategoriaRepository;
import com.samuel.categoriaMemeService.service.CategoriaService;

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
	public ResponseEntity<List<CategoriaMeme>> listar() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(categoriaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaMeme> buscarPorId(@PathVariable String id) {
		Optional<CategoriaMeme> categoriaOptional = categoriaRepository.findById(id);
		if (categoriaOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(categoriaOptional.get());
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<CategoriaMeme> cadastrar(@RequestBody CategoriaMeme categoriaMeme) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(categoriaService.cadastrar(categoriaMeme));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Object> atualizar(@PathVariable String id,
			@RequestBody CategoriaMeme categoriaMeme) {
		
		Optional<CategoriaMeme> categoriaOptional = categoriaRepository.findById(id);
		
		if (!categoriaOptional.isPresent()) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Categoria não encontrada");
		
		BeanUtils.copyProperties(categoriaMeme, categoriaOptional.get(), "id", "usuario");
		return ResponseEntity.status(HttpStatus.OK)
				.body(categoriaService.atualizar(categoriaOptional.get()));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> remover(@PathVariable(value = "id", required = true) String id) {
        try {
        	categoriaService.remover(id);;
        	return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}    	
    }
	
	
	
	
}

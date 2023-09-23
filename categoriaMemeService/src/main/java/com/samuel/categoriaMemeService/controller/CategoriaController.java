package com.samuel.categoriaMemeService.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
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
	public CategoriaMeme buscarPorId(@PathVariable String id) {
		
		return categoriaService.obterCategoria(id);
	}
	
	@PostMapping
	@Operation(summary = "Cadastra uma categoria.")
	public ResponseEntity<Object> cadastrar(@RequestBody @Valid CategoriaDto categoriaDto) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(categoriaService.cadastrar(categoriaDto));		
	}	
	
	@PutMapping("{id}")
	@Operation(summary = "Atualiza uma categoria.")
	public CategoriaMeme atualizar(@PathVariable String id,
			@RequestBody CategoriaDto categoriaDto) {
		try {
			CategoriaMeme categoriaAtual = categoriaService.obterCategoria(id);
			
			BeanUtils.copyProperties(categoriaDto, categoriaAtual, "id", "usuario");
			categoriaAtual.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
			
			return categoriaService.atualizar(categoriaAtual);
		} catch (EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@Operation(summary = "Remove uma categoria.")
	public void remover(@PathVariable(value = "id", required = true) String id) {
        	categoriaService.remover(id);
    }	
	
}

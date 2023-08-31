package com.samuel.meme.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
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

import com.samuel.meme.exceptions.EntidadeNaoEncontradaException;
import com.samuel.meme.model.Usuario;
import com.samuel.meme.repository.IUsuarioRepository;
import com.samuel.meme.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    private UsuarioService usuarioService;
    private IUsuarioRepository usuarioRepository;    

    public UsuarioController(UsuarioService usuarioService, IUsuarioRepository usuarioRepository) {
		super();
		this.usuarioService = usuarioService;
		this.usuarioRepository = usuarioRepository;
	}

	@GetMapping
    @Operation(summary = "Busca uma página usuarios.")
    public ResponseEntity<List<Usuario>> getAll(Pageable pageable) {
    	return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuario pelo ID.")
    public ResponseEntity<Usuario> buscaPorId(@PathVariable(value = "id", required = true) String id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        
        if (usuarioOptional.isPresent()) 
        	return ResponseEntity.ok(usuarioOptional.get());
        
        return ResponseEntity.notFound().build();
    }

    @GetMapping("isCadastrado/{id}")
    @Operation(summary = "Verifica se o usuario está cadastrado na base de dados.")
    public ResponseEntity<Boolean> isCadastrado(@PathVariable(value = "id", required = true) String id) {
    	return ResponseEntity.status(HttpStatus.OK).body(usuarioService.estaCadastrado(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra um usuario.")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
        		.body(usuarioService.cadastrarUsuario(usuario));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuario.")
    public ResponseEntity<Object> atualizar(@PathVariable String id,  
    		@RequestBody Usuario usuario) {
    	
    	Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
    	
    	if (!usuarioOptional.isPresent()) 
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
    	
        BeanUtils.copyProperties(usuario, usuarioOptional.get(), "id");
                
    	return ResponseEntity.status(HttpStatus.OK)
    			.body(usuarioService.atualizarUsuario(usuarioOptional.get()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um usuario.")
    public ResponseEntity<Object> remover(@PathVariable(value = "id", required = true) String id) {
        try {
        	usuarioService.removerUsuario(id);
        	return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}    	
    }
}

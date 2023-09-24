package com.samuel.meme.controller;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.samuel.meme.dto.UsuarioDto;
import com.samuel.meme.exceptions.EntidadeEmUsoException;
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
    public Usuario buscaPorId(@PathVariable(value = "id", required = true) String id) {
        return usuarioService.obterUsuario(id);        
    }

    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid UsuarioDto usuarioDto) {
    	if (usuarioService.existsByEmail(usuarioDto.getEmail()))
    		throw new EntidadeEmUsoException("Email já cadastrado.");
    	
    	var usuario = new Usuario();
    	
    	BeanUtils.copyProperties(usuarioDto, usuario);
    	usuario.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
    	
    	return ResponseEntity.status(HttpStatus.CREATED)
    			.body(usuarioService.cadastrarUsuario(usuario));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuario.")
    public ResponseEntity<Object> atualizar(@PathVariable String id,  
    		@RequestBody @Valid UsuarioDto usuarioDto) {    	
    	var usuarioOptional = usuarioService.obterUsuario(id);
    	
    	var usuario = new Usuario();    	
    	
        BeanUtils.copyProperties(usuarioDto, usuario);
        usuario.setId(id);
        usuario.setDataCadastro(usuarioOptional.getDataCadastro());
        
    	return ResponseEntity.status(HttpStatus.OK)
    			.body(usuarioService.atualizarUsuario(usuario));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove um usuario.")
    public void remover(@PathVariable(value = "id", required = true) String id) {
    	usuarioService.removerUsuario(id);    	
    }
    
}

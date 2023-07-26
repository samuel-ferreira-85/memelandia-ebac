package com.samuel.meme.controller;


import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samuel.meme.model.Usuario;
import com.samuel.meme.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Busca uma página usuarios.")
    public ResponseEntity<Page<Usuario>> getAll(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.listarUsuarios(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuario pelo ID.")
    public ResponseEntity<Usuario> buscaPorId(@PathVariable(value = "id", required = true) String id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @GetMapping("isCadastrado/{id}")
    @Operation(summary = "Verifica se o usuario está cadastrado na base de dados.")
    public ResponseEntity<Boolean> isCadastrado(@PathVariable(value = "id", required = true) String id) {
        return ResponseEntity.ok(usuarioService.estaCadastrado(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra um usuario.")
    public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid Usuario usuario) {
        return ResponseEntity.ok(usuarioService.cadastrarUsuario(usuario));
    }

    @PutMapping
    @Operation(summary = "Atualiza um usuario.")
    public ResponseEntity<Usuario> atualizar(@RequestBody @Valid Usuario usuario) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um usuario.")
    public ResponseEntity<String> remover(@PathVariable(value = "id", required = true) String id) {
        usuarioService.removerUsuario(id);
        return ResponseEntity.ok("Usuario removido com sucesso.");
    }
}

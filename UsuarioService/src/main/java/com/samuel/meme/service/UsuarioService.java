package com.samuel.meme.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.samuel.meme.model.Usuario;
import com.samuel.meme.repository.IUsuarioRepository;

@Service
public class UsuarioService {
    
    private IUsuarioRepository usuarioRepository;
    

    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrarUsuario(@Valid Usuario usuario) {
        return usuarioRepository.insert(usuario);
    }

    public Usuario atualizarUsuario(@Valid Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(String id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Recurso não encontrado para o id informado"));        
    }

    public Page<Usuario> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Boolean estaCadastrado(String id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        return usuarioOptional.isPresent() ? true : false;
    }

    public void removerUsuario(String id) {
        usuarioRepository.deleteById(id);
    }
}
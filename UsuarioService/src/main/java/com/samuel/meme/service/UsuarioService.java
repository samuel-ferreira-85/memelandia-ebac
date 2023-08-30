package com.samuel.meme.service;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.samuel.meme.exceptions.EntidadeNaoEncontradaException;
import com.samuel.meme.model.Usuario;
import com.samuel.meme.repository.IUsuarioRepository;

@Service
@EnableMongoRepositories(basePackageClasses = IUsuarioRepository.class)
public class UsuarioService {
    
    private IUsuarioRepository usuarioRepository;    

    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        return usuarioRepository.insert(usuario);
    } 
    
    public Usuario atualizarUsuario(Usuario usuario) {
    	return usuarioRepository.save(usuario);
    }

    public Boolean estaCadastrado(String id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        return usuarioOptional.isPresent() ? true : false;
    }

    public void removerUsuario(String id) {
    	try {
    		usuarioRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(
                    "Não existe um cadastro de usuario para o ID: %d", id));
		} 
        
    }
}
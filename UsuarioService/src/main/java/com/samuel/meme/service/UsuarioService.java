package com.samuel.meme.service;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.samuel.meme.exceptions.EntidadeNaoEncontradaException;
import com.samuel.meme.model.Usuario;
import com.samuel.meme.repository.IUsuarioRepository;

@EnableMongoRepositories(basePackageClasses = IUsuarioRepository.class)
@Service
public class UsuarioService {
    
    private static final String MSG_USUARIO_NOT_FOUND = "Não existe um cadastro de usuario para o ID: %s";
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

    public void removerUsuario(String id) {    	
    		Usuario usuario = obterUsuario(id);
			usuarioRepository.delete(usuario);	
    }

	public boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}
	
	public Usuario obterUsuario(String id) {
		return usuarioRepository.findById(id)
		        .orElseThrow(() -> new EntidadeNaoEncontradaException(
		        		String.format(MSG_USUARIO_NOT_FOUND, id)));
	}
	
}
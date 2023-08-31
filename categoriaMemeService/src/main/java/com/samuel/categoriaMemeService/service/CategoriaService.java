package com.samuel.categoriaMemeService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.samuel.categoriaMemeService.exceptions.EntidadeNaoEncontradaException;
import com.samuel.categoriaMemeService.feign.UsuarioFeignClient;
import com.samuel.categoriaMemeService.model.CategoriaMeme;
import com.samuel.categoriaMemeService.model.Usuario;
import com.samuel.categoriaMemeService.repository.ICategoriaRepository;

@Service
@EnableMongoRepositories(basePackageClasses = ICategoriaRepository.class)
public class CategoriaService {

	@Autowired
    private ICategoriaRepository categoriaRepository;
	
	@Autowired
    private UsuarioFeignClient usuarioFeignClient;	    

	public CategoriaMeme cadastrar(CategoriaMeme categoriaMeme) {
    	Usuario usuario = usuarioFeignClient.buscaPorId(categoriaMeme.getUsuario().getId()).getBody();    	
    	
    	if (usuario != null) {
    		categoriaMeme.setUsuario(usuario);
    		return categoriaRepository.insert(categoriaMeme);
    	} else {
    		throw new EntidadeNaoEncontradaException(String.format(
    				"Não existe um cadastro de Usuario para o id: %d", 
    				categoriaMeme.getUsuario().getId()));
    	} 	
    	
    }
    
    public CategoriaMeme atualizar(CategoriaMeme categoriaMeme) {
    	return categoriaRepository.save(categoriaMeme);
    }
    
    public void remover(String id) {
    	try {
			categoriaRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(
					"Não existe um cadastro de Categoria para o id: %d", id));
		}
    }
    
}

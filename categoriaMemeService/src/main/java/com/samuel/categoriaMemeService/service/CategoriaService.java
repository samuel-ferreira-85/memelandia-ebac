package com.samuel.categoriaMemeService.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.samuel.categoriaMemeService.dto.CategoriaDto;
import com.samuel.categoriaMemeService.exceptions.EntidadeNaoEncontradaException;
import com.samuel.categoriaMemeService.feign.UsuarioFeignClient;
import com.samuel.categoriaMemeService.model.CategoriaMeme;
import com.samuel.categoriaMemeService.model.Usuario;
import com.samuel.categoriaMemeService.repository.ICategoriaRepository;

import feign.FeignException;

@Service
@EnableMongoRepositories(basePackageClasses = ICategoriaRepository.class)
public class CategoriaService {

	@Autowired
    private ICategoriaRepository categoriaRepository;
	
	@Autowired
    private UsuarioFeignClient usuarioFeignClient;	    

	public CategoriaMeme cadastrar(CategoriaDto categoriaDto) {
		try {
			Usuario usuario = usuarioFeignClient.buscaPorId(categoriaDto.getUsuario().getId()).getBody();    	
	    	
	    	if (usuario != null) {
	    		var categoria = new CategoriaMeme();
	    		
	    		BeanUtils.copyProperties(categoriaDto, categoria);
	    		categoria.setUsuario(usuario);
	    		categoria.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
	    		
	    		return categoriaRepository.insert(categoria);    		
	    	}
	    	throw new EntidadeNaoEncontradaException(String.format(
                    "Não existe um cadastro de Usuário para o id: %s",
                    categoriaDto.getUsuario().getId()));
	    	
			} catch (FeignException.NotFound e) {
				throw new EntidadeNaoEncontradaException(String.format(
						"Não foi possível encontrar o Usuário com o id: %s na chamada à API externa", 
	    				categoriaDto.getUsuario().getId()));
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

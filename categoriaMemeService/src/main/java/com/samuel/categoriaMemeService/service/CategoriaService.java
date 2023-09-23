package com.samuel.categoriaMemeService.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.samuel.categoriaMemeService.dto.CategoriaDto;
import com.samuel.categoriaMemeService.exceptions.EntidadeNaoEncontradaException;
import com.samuel.categoriaMemeService.feign.UsuarioFeignClient;
import com.samuel.categoriaMemeService.model.CategoriaMeme;
import com.samuel.categoriaMemeService.model.Usuario;
import com.samuel.categoriaMemeService.repository.ICategoriaRepository;
import com.samuel.categoriaMemeService.exceptions.FeignException;


@Service
@EnableMongoRepositories(basePackageClasses = ICategoriaRepository.class)
public class CategoriaService {
	private static final String MSG_CATEGORIA_NOT_FOUND = "Não existe um cadastro de categoria para o ID: %s";

	@Autowired
    private ICategoriaRepository categoriaRepository;
	
	@Autowired
    private UsuarioFeignClient usuarioFeignClient;	    

	public CategoriaMeme cadastrar(CategoriaDto categoriaDto) {		
		try {
			Usuario usuario = usuarioFeignClient.buscaPorId(categoriaDto.getUsuario().getId()).getBody();    	
	    	
	    	var categoria = new CategoriaMeme();
    		
    		BeanUtils.copyProperties(categoriaDto, categoria);
    		categoria.setUsuario(usuario);
    		categoria.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
    		
    		return categoriaRepository.insert(categoria); 
		} catch (Exception e) {
			throw new FeignException(String.format(
					"Não foi possível encontrar o usuário com o id: %s na chamada à API externa", 
    				categoriaDto.getUsuario().getId()));
		}    	 	
    	
    }
    
    public CategoriaMeme atualizar(CategoriaMeme categoriaMeme) {
    	return categoriaRepository.save(categoriaMeme);
    }
    
    public void remover(String id) {
    	CategoriaMeme categoria = obterCategoria(id);
    	
		categoriaRepository.delete(categoria);
    }

    public CategoriaMeme obterCategoria(String id) {
		return categoriaRepository.findById(id)
		        .orElseThrow(() -> new EntidadeNaoEncontradaException(
		        		String.format(MSG_CATEGORIA_NOT_FOUND, id)));
	}
}

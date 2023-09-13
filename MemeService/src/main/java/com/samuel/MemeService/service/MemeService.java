package com.samuel.MemeService.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.samuel.MemeService.dto.MemeDto;
import com.samuel.MemeService.exceptions.EntidadeNaoEncontradaException;
import com.samuel.MemeService.feign.CategoriaFeignClient;
import com.samuel.MemeService.feign.UsuarioFeignClient;
import com.samuel.MemeService.model.CategoriaMeme;
import com.samuel.MemeService.model.Meme;
import com.samuel.MemeService.model.Usuario;
import com.samuel.MemeService.repository.IMemeRepository;

import feign.FeignException;

@Service
@EnableMongoRepositories(basePackageClasses = IMemeRepository.class)
public class MemeService {
	
	@Autowired
	private IMemeRepository memeRepository;
	
	@Autowired
	private CategoriaFeignClient categoriaFeign;
	
	@Autowired
	private UsuarioFeignClient usuarioFeign;
	
	public Meme criar(Meme meme) {
		Usuario usuario = buscarUsuarioPorId(meme.getUsuario().getId());
	    CategoriaMeme categoria = buscarCategoriaPorId(meme.getCategoria().getId());
	    
	    Meme novoMeme = new Meme();
	    BeanUtils.copyProperties(meme, novoMeme);
	    meme.setUsuario(usuario);
	    meme.setCategoria(categoria);
	    meme.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));

	    return memeRepository.insert(novoMeme);
	}
	
	public Meme cadastrar(MemeDto memeDto) {
	    Usuario usuario = buscarUsuarioPorId(memeDto.getUsuario().getId());
	    CategoriaMeme categoria = buscarCategoriaPorId(memeDto.getCategoria().getId());
	    
	    Meme meme = new Meme();
	    BeanUtils.copyProperties(memeDto, meme);
	    meme.setUsuario(usuario);
	    meme.setCategoria(categoria);
	    meme.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));

	    return memeRepository.insert(meme);
	}

	private Usuario buscarUsuarioPorId(String usuarioId) {
	    try {
	        ResponseEntity<Usuario> response = usuarioFeign.buscaPorId(usuarioId);
	        if (response != null && response.getBody() != null) {
	            return response.getBody();
	        }
	    } catch (FeignException.NotFound e) {
	        throw new EntidadeNaoEncontradaException(String.format(
	                "Não foi possível encontrar o Usuário com o id: %s na chamada à API externa",
	                usuarioId));
	    }

	    throw new EntidadeNaoEncontradaException(String.format(
	            "Não existe um cadastro de Usuário para o id: %s", usuarioId));
	}

	private CategoriaMeme buscarCategoriaPorId(String categoriaId) {
	    try {
	        ResponseEntity<CategoriaMeme> response = categoriaFeign.buscarPorId(categoriaId);
	        if (response != null && response.getBody() != null) {
	            return response.getBody();
	        }
	    } catch (FeignException.NotFound e) {
	        throw new EntidadeNaoEncontradaException(String.format(
	                "Não foi possível encontrar a Categoria de Meme com o id: %s na chamada à API externa",
	                categoriaId));
	    }

	    throw new EntidadeNaoEncontradaException(String.format(
	            "Não existe um cadastro de Categoria de Meme para o id: %s", categoriaId));
	}

	
//	public Meme cadastrar(MemeDto memeDto) {
//		try {
//			Usuario usuario = usuarioFeign.buscaPorId(memeDto.getUsuario().getId()).getBody();
//			CategoriaMeme categoria = categoriaFeign.buscarPorId(memeDto.getCategoria().getId()).getBody();
//	    	                  
//	    	if (usuario != null && categoria != null) {
//	    		Meme meme = new Meme();
//	    		
//	    		BeanUtils.copyProperties(memeDto, meme);
//	    		meme.setUsuario(usuario);
//	    		meme.setCategoria(categoria);
//	    		meme.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
//	    		
//	    		return memeRepository.insert(meme); 
//	    	} 
//	    	throw new EntidadeNaoEncontradaException(String.format(
//                    "Não existe um cadastro de Usuário para o id: %s"
//                    + " E nem de Categoria de Meme para o id: %s",
//                    memeDto.getUsuario().getId(), memeDto.getCategoria().getId()));
//	    	
//			} catch (FeignException.NotFound e) {
//				throw new EntidadeNaoEncontradaException(String.format(
//						"Não foi possível encontrar o Usuário com o id: %s"
//						+ " ou Categoria com id: %s na chamada às APIs externas", 
//	    				memeDto.getUsuario().getId(), memeDto.getCategoria().getId()));
//			} 
//	}
	
}

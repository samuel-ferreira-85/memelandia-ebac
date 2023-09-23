package com.samuel.memems.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.samuel.memems.dto.MemeDto;
import com.samuel.memems.exceptions.EntidadeNaoEncontradaException;
import com.samuel.memems.exceptions.FeignException;
import com.samuel.memems.feign.CategoriaFeignClient;
import com.samuel.memems.feign.UsuarioFeignClient;
import com.samuel.memems.model.CategoriaMeme;
import com.samuel.memems.model.Meme;
import com.samuel.memems.model.Usuario;
import com.samuel.memems.repository.IMemeRepository;

@Service
@EnableMongoRepositories(basePackageClasses = IMemeRepository.class)
public class MemeService {

	private static final String MSG_MEME_NOT_FOUND = "Não existe um cadastro de meme para o ID: %s";
	private static final String MSG_CATEGORIAFEIGN_NOT_FOUND = "Não foi possível encontrar a categoria com o id: %s na chamada à API externa";
	private static final String MSG_USUARIOFEIGN_NOT_FOUND = "Não foi possível encontrar o usuario com o id: %s na chamada à API externa";
	
	@Autowired
	private IMemeRepository memeRepository;	
	@Autowired
	private UsuarioFeignClient usuarioFeign;
	@Autowired
	private CategoriaFeignClient categoriaFeign;	
	
	public Meme cadastrar(MemeDto memeDto) {
		
			Usuario usuario = getUsuarioFeign(memeDto);
			CategoriaMeme categoria = getCategoriaFeign(memeDto);
			
			var meme = new Meme();

			BeanUtils.copyProperties(memeDto, meme);
			meme.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
			meme.setUsuario(usuario);
			meme.setCategoria(categoria);

			return memeRepository.insert(meme);
	}
	
	public Meme atualizar(Meme meme) {
		return memeRepository.save(meme);
	}
	
	public void remover(String id) {
		Meme meme = obterMeme(id);		
		memeRepository.delete(meme);
	}
	
	public Meme aleatorio() {
    	List<Meme> memes = memeRepository.findAll();
    	
    	if (memes.isEmpty()) {
            throw new RuntimeException("Não há memes cadastrados");
        }

        return memes.get(new Random().nextInt(memes.size()));
    }

	private CategoriaMeme getCategoriaFeign(MemeDto memeDto) {
		String id = memeDto.getCategoria().getId();
		try {
			CategoriaMeme categoria = categoriaFeign.buscarPorId(id);
			return categoria;
		} catch (Exception e) {
			throw new FeignException(
	        		String.format(MSG_CATEGORIAFEIGN_NOT_FOUND, id));
		}
		
	}

	private Usuario getUsuarioFeign(MemeDto memeDto) {
		String id = memeDto.getUsuario().getId();
		try {
			Usuario usuario = usuarioFeign.buscaPorId(id).getBody();
			return usuario;
		} catch (Exception e) {
			throw new FeignException(String.format(MSG_USUARIOFEIGN_NOT_FOUND, id));
		}
		
	}

	public Meme obterMeme(String id) {
		return memeRepository.findById(id)
		        .orElseThrow(() -> new EntidadeNaoEncontradaException(
		        		String.format(MSG_MEME_NOT_FOUND, id)));
	}
}

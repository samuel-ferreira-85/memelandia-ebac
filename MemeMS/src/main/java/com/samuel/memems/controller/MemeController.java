package com.samuel.memems.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samuel.memems.feign.CategoriaFeignClient;
import com.samuel.memems.feign.UsuarioFeignClient;
import com.samuel.memems.model.CategoriaMeme;
import com.samuel.memems.model.Meme;
import com.samuel.memems.model.Usuario;
import com.samuel.memems.repository.IMemeRepository;

@RestController
@RequestMapping("memes")
public class MemeController {

	@Autowired
	private IMemeRepository memeRepository;
	@Autowired
	private UsuarioFeignClient usuarioFeign;
	@Autowired
	private CategoriaFeignClient categoriaFeign;
	

	@GetMapping
	public ResponseEntity<List<Meme>> listar() {
		return ResponseEntity.status(OK).body(memeRepository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestBody Meme meme) {
		Usuario usuario = usuarioFeign.buscaPorId(meme.getUsuario().getId()).getBody();
		CategoriaMeme categoria = categoriaFeign.buscarPorId(meme.getCategoria().getId()).getBody();
		
		if (usuario == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("Usuario não encontrado para o ID inofrmado");
		
		if (categoria == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("Categoria não encontrada para o ID informado");
		
		meme.setUsuario(usuario);
		meme.setCategoria(categoria);
		
		return ResponseEntity.status(CREATED).body(memeRepository.insert(meme));
	}
	
	
}
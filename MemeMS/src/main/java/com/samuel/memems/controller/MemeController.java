package com.samuel.memems.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samuel.memems.dto.MemeDto;
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

	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarPorId(@PathVariable String id) {
		Optional<Meme> memeOptional = memeRepository.findById(id);

		if (!memeOptional.isPresent())
			return ResponseEntity.status(NOT_FOUND).body("Meme não foi encontrado.");

		return ResponseEntity.status(OK).body(memeOptional.get());
	}

	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestBody MemeDto memeDto) {
		try {
			Usuario usuario = usuarioFeign.buscaPorId(memeDto.getUsuario().getId()).getBody();
			CategoriaMeme categoria = categoriaFeign.buscarPorId(memeDto.getCategoria().getId()).getBody();

			if (usuario == null)
				return ResponseEntity.status(NOT_FOUND).body("Usuario não encontrado para o ID informado.");

			if (categoria == null)
				return ResponseEntity.status(NOT_FOUND).body("Categoria não encontrada para o ID informado.");

			Meme meme = new Meme();

			BeanUtils.copyProperties(memeDto, meme);
			meme.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
			meme.setUsuario(usuario);
			meme.setCategoria(categoria);

			return ResponseEntity.status(CREATED).body(memeRepository.insert(meme));
			
		} catch (Exception e) {
			return ResponseEntity.status(NOT_FOUND).body("Usuario ou categoria não encontrado."); 
		}
		
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody MemeDto memeDto) {

		Optional<Meme> memeOptional = memeRepository.findById(id);

		if (!memeOptional.isPresent())
			return ResponseEntity.status(NOT_FOUND).body("Meme não encontrado");

		Meme meme = memeOptional.get();

		BeanUtils.copyProperties(memeDto, meme, "id", "usuario", "categoria");
		meme.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));

		return ResponseEntity.status(OK).body(memeRepository.save(meme));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> deletar(@PathVariable String id) {
		Optional<Meme> memeOptional = memeRepository.findById(id);

		if (!memeOptional.isPresent())
			return ResponseEntity.status(NOT_FOUND).body("Meme não encontrado.");
		
		memeRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}

package com.samuel.memems.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.samuel.memems.dto.MemeDto;
import com.samuel.memems.model.Meme;
import com.samuel.memems.repository.IMemeRepository;
import com.samuel.memems.service.MemeService;

@RestController
@RequestMapping("memes")
public class MemeController {

	@Autowired
	private MemeService memeService;
	@Autowired
	private IMemeRepository memeRepository;

	@GetMapping
	public ResponseEntity<List<Meme>> listar() {
		return ResponseEntity.status(OK).body(memeRepository.findAll());
	}

	@GetMapping("/{id}")
	public Meme buscarPorId(@PathVariable String id) {
		return memeService.obterMeme(id);
	}
	
	@GetMapping("/aleatorio")
	public Meme buscarAleatoria() {		
		return memeService.aleatorio();
	}

	@PostMapping
	public ResponseEntity<Object> cadastrar(@RequestBody MemeDto memeDto) {
		return ResponseEntity.status(CREATED)
				.body(memeService.cadastrar(memeDto));		
	}

	@PutMapping("/{id}")
	public Meme atualizar(@PathVariable String id, @RequestBody MemeDto memeDto) {

			Meme memeAtual = memeService.obterMeme(id);
			
			BeanUtils.copyProperties(memeDto, memeAtual, "id", "usuario", "categoria");
			memeAtual.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));

			return memeService.atualizar(memeAtual);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable String id) {				
		memeService.remover(id);
	}
}

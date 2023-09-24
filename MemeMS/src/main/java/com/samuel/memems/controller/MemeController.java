package com.samuel.memems.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.samuel.memems.config.Groups;
import com.samuel.memems.dto.MemeDto;
import com.samuel.memems.model.Meme;
import com.samuel.memems.repository.IMemeRepository;
import com.samuel.memems.service.MemeService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("memes")
public class MemeController {

	@Autowired
	private MemeService memeService;
	@Autowired
	private IMemeRepository memeRepository;

	@GetMapping
	@Operation(summary = "Busca uma página memes")
	public ResponseEntity<List<Meme>> listar() {
		return ResponseEntity.status(OK).body(memeRepository.findAll());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Busca um meme por ID")
	public Meme buscarPorId(@PathVariable String id) {
		return memeService.obterMeme(id);
	}
	
	@GetMapping("/meme-do-dia")
	@Operation(summary = "Busca o meme do dia de forma aleatória")
	public Meme buscarAleatoria() {		
		return memeService.aleatorio();
	}

	@PostMapping
	@Operation(summary = "Cadastra um meme")
	public ResponseEntity<Object> cadastrar(
			@RequestBody @Validated(value = Groups.CadastroMeme.class) MemeDto memeDto) {
		return ResponseEntity.status(CREATED)
				.body(memeService.cadastrar(memeDto));		
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualiza um meme")
	public Meme atualizar(@PathVariable String id, 
			@RequestBody @Valid MemeDto memeDto) {

			Meme memeAtual = memeService.obterMeme(id);
			
			BeanUtils.copyProperties(memeDto, memeAtual, "id", "usuario", "categoria");
			memeAtual.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));

			return memeService.atualizar(memeAtual);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@Operation(summary = "Remove um meme")
	public void deletar(@PathVariable String id) {				
		memeService.remover(id);
	}
}

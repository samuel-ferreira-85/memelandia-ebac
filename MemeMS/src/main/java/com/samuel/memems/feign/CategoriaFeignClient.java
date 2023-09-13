package com.samuel.memems.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.samuel.memems.model.CategoriaMeme;

@Component
@FeignClient(name = "categoria-meme-service", url = "localhost:8200")
public interface CategoriaFeignClient {

	@GetMapping("categorias/{id}")
	ResponseEntity<CategoriaMeme> buscarPorId(@PathVariable String id);
	
}

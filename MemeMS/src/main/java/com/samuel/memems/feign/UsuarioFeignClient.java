package com.samuel.memems.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.samuel.memems.model.Usuario;

@Component
@FeignClient(name = "usuario-service", url = "localhost:8000")
public interface UsuarioFeignClient {
	
	@GetMapping("usuarios/{id}")
	ResponseEntity<Usuario> buscaPorId(@PathVariable String id);
	
}
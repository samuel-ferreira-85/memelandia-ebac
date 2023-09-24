package com.samuel.meme.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UsuarioDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotBlank()
	private String nome;	
	
	@NotBlank
	@Email(message = "Email inválido.")
	private String email;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

}

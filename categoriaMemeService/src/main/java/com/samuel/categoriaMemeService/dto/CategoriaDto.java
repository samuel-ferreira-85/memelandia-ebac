package com.samuel.categoriaMemeService.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.samuel.categoriaMemeService.model.Usuario;

public class CategoriaDto {

	@NotBlank
	private String nome;
	@NotBlank
	private String descricao;
	@NotNull
	private Usuario usuario;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}

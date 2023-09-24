package com.samuel.categoriaMemeService.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.samuel.categoriaMemeService.config.Groups;
import com.samuel.categoriaMemeService.model.Usuario;

public class CategoriaDto {

	@NotBlank(groups = Groups.CadastroCategoria.class)
	@NotBlank
	private String nome;
	@NotBlank(groups = Groups.CadastroCategoria.class)
	@NotBlank
	private String descricao;
	
	@Valid
	@NotNull(groups = Groups.CadastroCategoria.class)
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

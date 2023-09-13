package com.samuel.MemeService.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.samuel.MemeService.model.CategoriaMeme;
import com.samuel.MemeService.model.Usuario;

public class MemeDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "O campo nome não pode está em branco")
	private String nome;
	@NotBlank(message = "O campo Descricao não pode está em branco")
	private String descricao;
	@NotBlank(message = "O campo URL não pode está em branco")
	private String url;
	@NotNull(message = "O campo Usuario não pode está em branco")
	private Usuario usuario;
	@NotNull(message = "O campo Categoria não pode está em branco")
	private CategoriaMeme categoria;
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public CategoriaMeme getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoriaMeme categoria) {
		this.categoria = categoria;
	}		
}

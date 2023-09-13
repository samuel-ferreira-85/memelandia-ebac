package com.samuel.memems.dto;

import com.samuel.memems.model.CategoriaMeme;
import com.samuel.memems.model.Usuario;

public class MemeDto {

	private String nome;
	private String descricao;
	private String url;
	private CategoriaMeme categoria;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public CategoriaMeme getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaMeme categoria) {
		this.categoria = categoria;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}

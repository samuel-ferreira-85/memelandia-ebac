package com.samuel.memems.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "meme")
public class Meme implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String nome;
	private String descricao;
	private String url;
	private LocalDateTime dataCadastro;
	private CategoriaMeme categoria;
	private Usuario usuario;

	public Meme() {
	}

	public Meme(String nome, String descricao, String url, LocalDateTime dataCadastro,
			CategoriaMeme categoria, Usuario usuario) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.url = url;
		this.dataCadastro = dataCadastro;
		this.categoria = categoria;
		this.usuario = usuario;
	}	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
	
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meme other = (Meme) obj;
		return Objects.equals(id, other.id);
	}

}

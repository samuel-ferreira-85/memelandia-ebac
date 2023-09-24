package com.samuel.memems.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Document(collection = "meme")
@Schema(name = "Meme", description = "Meme")
public class Meme implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Schema(description = "Identificador único")
	private String id;
	
	@NotBlank
	@Schema(description="Nome", nullable = false)
	private String nome;
	
	@NotBlank
	@Schema(description="Descrição", nullable = false)
	private String descricao;
	
	@NotBlank
	@Schema(description="URL", nullable = false)
	private String url;
	
	@Schema(description="Data do cadastro", nullable = false)
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	@Valid
	@NotNull
	private CategoriaMeme categoria;
	
	@JsonIgnore
	@Valid
	@NotNull
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
	
	@JsonProperty("usuarioId")
	public String getUsuarioId() {
        return usuario != null ? usuario.getId() : null;
    }
	
	@JsonProperty("categoriaId")
	public String getCategoriaId() {
        return categoria != null ? categoria.getId() : null;
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

package com.samuel.MemeService.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;

@Document(collection = "meme")
@Schema(name = "Meme", description = "Meme")
public class Meme implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Identificador Único")
	private String id;
	
	@NotBlank
	@Size(min = 1, max = 50)
	@Schema(description="Nome", nullable = false)
	private String nome;
	
	@NotBlank
	@Size(min = 1, max = 255)
	@Schema(description="Descricao", nullable = false)
	private String descricao;
	
	@NotBlank
	@Schema(description="URL do meme", nullable = false)
	private String url;
	
	private LocalDateTime dataCadastro;
	
	@NotNull
	private CategoriaMeme categoria;
	
	@NotNull
	private Usuario usuario;
	
	public Meme() {}	

	public Meme(String nome, String descricao, String url, LocalDateTime dataCadastro, 
			CategoriaMeme categoria, Usuario usuario) {
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

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Meme [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", descricao=");
		builder.append(descricao);
		builder.append(", url=");
		builder.append(url);
		builder.append(", dataCadastro=");
		builder.append(dataCadastro);
		builder.append("]");
		return builder.toString();
	}
	
}

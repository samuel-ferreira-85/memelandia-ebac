package com.samuel.categoriaMemeService.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;

@Document(collection = "categoriaMeme")
@Schema(name = "CategoriaMeme", description = "Categoria Meme")
public class CategoriaMeme {

    @Id
    @Schema(description = "Identificador único")
    private String id;

    @NotBlank
	@Size(min = 1, max = 50)
	@Schema(description="Nome", nullable = false)
    private String nome;

    @NotBlank
	@Size(min = 1, max = 100)
	@Schema(description="Descricao", nullable = false)
    private String descricao;

    @NotBlank
    private LocalDateTime dataCadastro;

    
    private Usuario usuario;  
    
    public CategoriaMeme() {
	}

	public CategoriaMeme(String id, String nome, String descricao, LocalDateTime dataCadastro, 
			Usuario usuario) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.dataCadastro = dataCadastro;
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

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
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
		CategoriaMeme other = (CategoriaMeme) obj;
		return Objects.equals(id, other.id);
	}   
    
}

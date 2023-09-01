package com.samuel.meme.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;

@Document(collection = "usuario")
@Schema(name = "Usuario", description = "Usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Schema(description = "Identificador único")
	private String id;

	@NotNull
	@Size(min = 1, max = 50)
	@Schema(description = "Nome", minLength = 1, maxLength = 50, nullable = false)
	private String nome;

	@Email(message = "Email inválido.")
	@Indexed(unique = true, background = true)
	@Schema(description = "E-mail", minLength = 1, maxLength = 50, nullable = false)
	private String email;

	@NotNull
	private LocalDateTime dataCadastro;
	
	public Usuario() { }
	  
	public Usuario(String nome, String email, LocalDateTime dataCadastro) { 
		this.nome = nome; this.email = email; this.dataCadastro = dataCadastro; 
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", dataCadastro=" + dataCadastro + "]";
	}	 

}

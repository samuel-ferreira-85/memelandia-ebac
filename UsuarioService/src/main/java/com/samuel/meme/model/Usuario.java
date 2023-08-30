package com.samuel.meme.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;

@Document(collection = "usuario")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
@Schema(name = "Usuario", description = "Usuario")
public class Usuario implements Serializable {
    
	private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador único")
    private String id;

	@Schema(description = "Nome", minLength = 1, maxLength = 50, nullable = false)
    private String nome;

	@Indexed(unique = true, background = true)
	@Schema(description = "E-mail", minLength = 1, maxLength = 50, nullable = false)
    private String email;

    private Date dataCadastro;
    
    public Usuario() {
	}

	public Usuario(String nome, String email, Date dataCadastro) {
		this.nome = nome;
		this.email = email;
		this.dataCadastro = dataCadastro;
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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
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

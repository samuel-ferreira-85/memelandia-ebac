package com.samuel.categoriaMemeService.model;

import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Usuario {
    
    private String id;

    @NotNull
	@Size(min = 1, max = 50)
    private String nome;

    @NotNull
	@Size(min = 1, max = 50)
	@Email(message = "Email inválido.")
    private String email;

    @NotNull
    private Date dataCadastro;
    
    public Usuario() {
	}

	public Usuario(String id, @NotNull @Size(min = 1, max = 50) String nome,
			@NotNull @Size(min = 1, max = 50) @Email(message = "Email inválido.") String email,
			@NotNull Date dataCadastro) {
		super();
		this.id = id;
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
    
    
}

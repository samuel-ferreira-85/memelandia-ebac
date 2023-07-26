package com.samuel.meme.model;

import java.time.Instant;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Document(collection = "usuario")
@Data
@AllArgsConstructor
@Builder
@Schema(name = "Usuario", description = "Usuario")
public class Usuario {
    
    @Id
    @Schema(description = "Identificador único")
    private String id;

    @NotNull
	@Size(min = 1, max = 50)
	@Schema(description = "Nome", minLength = 1, maxLength = 50, nullable = false)
    private String nome;

    @NotNull
	@Size(min = 1, max = 50)
	@Indexed(unique = true, background = true)
	@Email(message = "Email inválido.")
	@Schema(description = "E-mail", minLength = 1, maxLength = 50, nullable = false)
    private String email;

    @NotNull
    private Instant dataCadastro;

}

package com.samuel.categoriaMemeService.model;

import java.sql.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "categoriaMeme")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "CategoriaMeme", description = "Categoria Meme")
public class CategoriaMeme {

    @Id
    @Schema(description = "Identificador único")
    private String id;

    @NotNull
	@Size(min = 1, max = 50)
	@Schema(description="Nome", nullable = false)
    private String nome;

    @NotNull
	@Size(min = 1, max = 100)
	@Schema(description="Descricao", nullable = false)
    private String descricao;

    @NotNull
    private Date dataCadastro;

    @NotNull
    private Usuario usuario;    
}

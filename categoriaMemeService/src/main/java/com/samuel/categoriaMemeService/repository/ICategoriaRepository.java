package com.samuel.categoriaMemeService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.samuel.categoriaMemeService.model.CategoriaMeme;

public interface ICategoriaRepository extends MongoRepository<CategoriaMeme, String> {
    
}

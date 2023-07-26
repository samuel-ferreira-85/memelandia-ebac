package com.samuel.meme.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.samuel.meme.model.Usuario;

public interface IUsuarioRepository extends MongoRepository<Usuario, String> {
    
}

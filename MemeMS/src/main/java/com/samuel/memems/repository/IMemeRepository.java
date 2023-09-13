package com.samuel.memems.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.samuel.memems.model.Meme;

public interface IMemeRepository extends MongoRepository<Meme, String> {

}

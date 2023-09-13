package com.samuel.MemeService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.samuel.MemeService.model.Meme;

public interface IMemeRepository extends MongoRepository<Meme, String> {
	
}

package com.crio.starter.repository;

import java.util.Optional;
import com.crio.starter.data.Meme;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MemeRepository extends MongoRepository<Meme, String> {
    @Query("{ 'name': ?0, 'url': ?1, 'caption': ?2 }")
    Optional<Meme> findByNameAndUrlAndCaption(String name, String url, String caption);    
}

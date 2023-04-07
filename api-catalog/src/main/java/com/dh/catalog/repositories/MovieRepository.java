package com.dh.catalog.repositories;

import com.dh.catalog.models.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {


    List<Movie> findAllByGenre(String genre);

}

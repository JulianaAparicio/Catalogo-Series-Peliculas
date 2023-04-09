package com.dh.catalog.service;

import com.dh.catalog.clients.MovieServiceClient;
import com.dh.catalog.clients.SerieServiceClient;
import com.dh.catalog.repositories.MovieRepository;
import com.dh.catalog.repositories.SerieRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {
    private final MovieRepository movieRepository;
    private final SerieRepository serieRepository;
    private final SerieServiceClient serieServiceClient;
    private final MovieServiceClient movieServiceClient;

    public CatalogService(MovieRepository movieRepository, SerieRepository serieRepository, SerieServiceClient serieServiceClient, MovieServiceClient movieServiceClient) {
        this.movieRepository = movieRepository;
        this.serieRepository = serieRepository;
        this.serieServiceClient = serieServiceClient;
        this.movieServiceClient = movieServiceClient;
    }


    @Retry(name = "retryFindByGenre")
    @CircuitBreaker(name = "findByGenre", fallbackMethod = "findByGenreFallBack")
    public List<Object> findByGenre(String genre) {
        List<Object> catalogList = new ArrayList<>();
        catalogList.add(serieServiceClient.getSeriesByGenre(genre));
        catalogList.add(movieServiceClient.getMoviesByGenre(genre));
        return catalogList;
    }

    // OFFLINE LOGIC:

    public List<Object> findByGenreOffline(String genre) {
        List<Object> catalogList = new ArrayList<>();
        catalogList.add(movieRepository.findAllByGenre(genre));
        catalogList.add(serieRepository.findAllByGenre(genre));
        return catalogList;
    }

    // This Fallback method run if any of the microservices fail (its returns the MongoDB data stored):
    public List<Object> findByGenreFallBack(String genre, Throwable t) {
        List<Object> catalogList = new ArrayList<>();
        catalogList.add(movieRepository.findAllByGenre(genre));
        catalogList.add(serieRepository.findAllByGenre(genre));
        return catalogList;
    }

}

package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.repository.CatalogRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;
    private final SerieServiceClient serieServiceClient;
    private final MovieServiceClient movieServiceClient;

    public CatalogService(CatalogRepository catalogRepository, SerieServiceClient serieServiceClient, MovieServiceClient movieServiceClient) {
        this.catalogRepository = catalogRepository;
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

    // This Fallback method run if any of the microservices fail:

    public List<Object> findByGenreFallBack(String genre){
        return catalogRepository.findAllByGenre(genre);
    }



}

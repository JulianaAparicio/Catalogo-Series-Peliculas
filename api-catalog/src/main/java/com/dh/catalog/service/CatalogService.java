package com.dh.catalog.service;

import com.dh.catalog.clients.MovieServiceClient;
import com.dh.catalog.clients.SerieServiceClient;
import com.dh.catalog.events.NewMovieEventConsumer;
import com.dh.catalog.events.NewSerieEventConsumer;
import com.dh.catalog.models.Movie;
import com.dh.catalog.models.Serie;
import com.dh.catalog.repositories.MovieRepository;
import com.dh.catalog.repositories.SerieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {
    private final MovieRepository movieRepository;
    private final SerieRepository serieRepository;
    private final SerieServiceClient serieServiceClient;
    private final MovieServiceClient movieServiceClient;
    private final NewMovieEventConsumer newMovieEventConsumer;
    private final NewSerieEventConsumer newSerieEventConsumer;
    @Autowired
    private ObjectMapper mapper;

    public CatalogService(MovieRepository movieRepository, SerieRepository serieRepository, SerieServiceClient serieServiceClient, MovieServiceClient movieServiceClient, NewMovieEventConsumer newMovieEventConsumer, NewSerieEventConsumer newSerieEventConsumer) {
        this.movieRepository = movieRepository;
        this.serieRepository = serieRepository;
        this.serieServiceClient = serieServiceClient;
        this.movieServiceClient = movieServiceClient;
        this.newMovieEventConsumer = newMovieEventConsumer;
        this.newSerieEventConsumer = newSerieEventConsumer;
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

    public void saveMovie(NewMovieEventConsumer.Data data){
        newMovieEventConsumer.listen(data);
        Movie movie = mapper.convertValue(data,Movie.class);
        movieRepository.save(movie);
    }

    public void saveSerie(NewSerieEventConsumer.Data data){
        newSerieEventConsumer.listen(data);
        Serie serie = mapper.convertValue(data,Serie.class);
        serieRepository.save(serie);
    }

    // This Fallback method run if any of the microservices fail (its returns the MongoDB data stored):
    public List<Object> findByGenreFallBack(String genre, Throwable t) {
        List<Object> catalogList = new ArrayList<>();
        catalogList.add(movieRepository.findAllByGenre(genre));
        catalogList.add(serieRepository.findAllByGenre(genre));
        return catalogList;
    }

}

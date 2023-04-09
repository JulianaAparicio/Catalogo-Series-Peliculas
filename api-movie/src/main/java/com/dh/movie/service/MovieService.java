package com.dh.movie.service;


import com.dh.movie.event.NewMovieEventProducer;
import com.dh.movie.model.Movie;
import com.dh.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {


    private final MovieRepository movieRepository;
    private final NewMovieEventProducer newMovieEventProducer;

    public MovieService(MovieRepository movieRepository, NewMovieEventProducer newMovieEventProducer) {
        this.movieRepository = movieRepository;
        this.newMovieEventProducer = newMovieEventProducer;
    }

    public List<Movie> findByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public Movie save(Movie movie) {
        NewMovieEventProducer.Data data = new NewMovieEventProducer.Data();
        data.setMovieName(movie.getName());
        data.setMovieGenre(movie.getGenre());
        data.setMovieUrlStream(movie.getUrlStream());
        newMovieEventProducer.publishNewMovieEvent(data);

        return movieRepository.save(movie);
    }
}

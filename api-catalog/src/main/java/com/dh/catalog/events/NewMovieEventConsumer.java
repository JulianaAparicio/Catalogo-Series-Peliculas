package com.dh.catalog.events;

import com.dh.catalog.configurations.RabbitMQConfiguration;
import com.dh.catalog.models.Movie;
import com.dh.catalog.repositories.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewMovieEventConsumer {

    @Autowired
    MovieRepository movieRepository;

    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_NEW_MOVIE)
    public void listen(Data message) {
        System.out.print("A new Movie was created: "+ message.movieName);
        Movie movie = new Movie();
        movie.setName(message.getMovieName());
        movie.setGenre(message.getMovieGenre());
        movie.setUrlStream(message.getMovieUrlStream());
        movieRepository.save(movie);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data{
        private String movieName;
        private String movieUrlStream;
        private String movieGenre;
    }
}

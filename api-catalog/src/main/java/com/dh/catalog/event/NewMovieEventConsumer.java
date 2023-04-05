package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NewMovieEventConsumer {
    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_NEW_MOVIE)
    public void listen(NewMovieEventConsumer.Data message){
        System.out.print("MOVIE NAME: "+ message.movieName);
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

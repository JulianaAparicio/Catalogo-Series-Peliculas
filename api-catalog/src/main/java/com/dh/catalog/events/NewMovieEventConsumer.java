package com.dh.catalog.events;

import com.dh.catalog.configurations.RabbitMQConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NewMovieEventConsumer {

    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_NEW_MOVIE)
    public Data listen(Data message){
        System.out.print("There is a new Movie created: "+ message.movieName);
        return message;
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

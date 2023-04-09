package com.dh.movie.event;

import com.dh.movie.config.RabbitMQConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewMovieEventProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void publishNewMovieEvent(NewMovieEventProducer.Data message){
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME,
                RabbitMQConfiguration.TOPIC_NEW_MOVIE,message);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data{
        private String movieName;
        private String movieGenre;
        private String movieUrlStream;
    }
}

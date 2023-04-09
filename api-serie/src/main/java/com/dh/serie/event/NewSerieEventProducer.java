package com.dh.serie.event;

import com.dh.serie.config.RabbitMQConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewSerieEventProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishNewSerieEvent(NewSerieEventProducer.Data message){
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME,
                RabbitMQConfiguration.TOPIC_NEW_SERIE,message);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data{
        private String serieName;
        private String serieGenre;
    }
}

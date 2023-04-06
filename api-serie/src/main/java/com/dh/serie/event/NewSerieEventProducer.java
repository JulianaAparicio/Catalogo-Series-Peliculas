package com.dh.serie.event;

import com.dh.serie.config.RabbitMQConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NewSerieEventProducer {
    private final RabbitTemplate rabbitTemplate;

    public NewSerieEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

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

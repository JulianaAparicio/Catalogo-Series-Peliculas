package com.dh.catalog.events;

import com.dh.catalog.configurations.RabbitMQConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NewSerieEventConsumer {


    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_NEW_SERIE)
    public Data listen(NewSerieEventConsumer.Data message){
        System.out.print("There is a new Serie created: "+ message.serieName);
        return message;
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

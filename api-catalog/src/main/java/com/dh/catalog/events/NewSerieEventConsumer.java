package com.dh.catalog.events;

import com.dh.catalog.configurations.RabbitMQConfiguration;
import com.dh.catalog.models.Serie;
import com.dh.catalog.repositories.SerieRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewSerieEventConsumer {
    @Autowired
    SerieRepository serieRepository;


    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_NEW_SERIE)
    public void listen(NewSerieEventConsumer.Data message){
        System.out.print("A new Serie was created: "+ message.serieName);
        Serie serie = new Serie();
        serie.setName(message.getSerieName());
        serie.setGenre(message.getSerieGenre());
        serieRepository.save(serie);
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

package com.dh.catalog.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQSerieConfiguration {
    public static final String EXCHANGE_NAME = "serieExchange";
    public static final String TOPIC_NEW_SERIE = "com.dh.serie.newSerie";
    public static final String QUEUE_NEW_SERIE ="queueNewSerie";


    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queueNewSerie(){
        return new Queue(QUEUE_NEW_SERIE);
    }

    @Bean
    public Binding declareBindingSerie(){
        return BindingBuilder.bind(queueNewSerie()).to(appExchange()).with(TOPIC_NEW_SERIE);
    }


    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

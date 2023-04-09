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
public class RabbitMQConfiguration {
    public static final String EXCHANGE_SERIE_NAME = "serieExchange";
    public static final String TOPIC_NEW_SERIE = "com.dh.serie.newSerie";
    public static final String QUEUE_NEW_SERIE ="queueNewSerie";
    public static final String EXCHANGE_MOVIE_NAME = "movieExchange";
    public static final String TOPIC_NEW_MOVIE = "com.dh.movie.newMovie";
    public static final String QUEUE_NEW_MOVIE ="queueNewMovie";


    @Bean
    public TopicExchange appSerieExchange() {
        return new TopicExchange(EXCHANGE_SERIE_NAME);
    }

    @Bean
    public TopicExchange appMovieExchange() {
        return new TopicExchange(EXCHANGE_MOVIE_NAME);
    }

    @Bean
    public Queue queueNewSerie(){
        return new Queue(QUEUE_NEW_SERIE);
    }

    @Bean
    public Queue queueNewMovie(){
        return new Queue(QUEUE_NEW_MOVIE);
    }

    @Bean
    public Binding declareBindingMovie(){
        return BindingBuilder.bind(queueNewMovie()).to(appMovieExchange()).with(TOPIC_NEW_MOVIE);
    }

    @Bean
    public Binding declareBindingSerie(){
        return BindingBuilder.bind(queueNewSerie()).to(appSerieExchange()).with(TOPIC_NEW_SERIE);
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

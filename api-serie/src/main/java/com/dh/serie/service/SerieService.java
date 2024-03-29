package com.dh.serie.service;

import com.dh.serie.event.NewSerieEventProducer;
import com.dh.serie.model.Serie;
import com.dh.serie.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    private final SerieRepository repository;
    private final NewSerieEventProducer newSerieEventProducer;

    public SerieService(SerieRepository repository, NewSerieEventProducer newSerieEventProducer) {
        this.repository = repository;
        this.newSerieEventProducer = newSerieEventProducer;
    }

    public List<Serie> getAll() {
        return repository.findAll();
    }

    public List<Serie> getSeriesBygGenre(String genre) {
        return repository.findAllByGenre(genre);
    }

    public String create(Serie serie) {
        NewSerieEventProducer.Data data = new NewSerieEventProducer.Data();
        data.setSerieName(serie.getName());
        data.setSerieGenre(serie.getGenre());
        newSerieEventProducer.publishNewSerieEvent(data);
        repository.save(serie);
        return serie.getId();
    }
}

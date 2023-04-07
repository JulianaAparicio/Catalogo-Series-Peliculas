package com.dh.catalog.client;

import com.dh.catalog.config.LoadBalancerConfiguration;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="api-serie")
@LoadBalancerClient(value = "api-serie", configuration = LoadBalancerConfiguration.class)
public interface SerieServiceClient {

	@GetMapping("/api/v1/series/{genre}")
	@Retry(name = "retryGetSerie")
	// Ver si cambio lo de clientInscription (ver config del examen):
	@CircuitBreaker(name = "clientInscription", fallbackMethod = "getSerieFallBack")
	List<SerieDto> getSeriesByGenre(@PathVariable (value = "genre") String genre);


	@Getter
	@Setter
	class SerieDto{
		private String id;
		private String name;
		private String genre;

	}

}

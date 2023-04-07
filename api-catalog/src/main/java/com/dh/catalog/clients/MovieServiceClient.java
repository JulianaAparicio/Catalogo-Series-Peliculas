package com.dh.catalog.clients;

import com.dh.catalog.configurations.LoadBalancerConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="api-movie")
@LoadBalancerClient(value = "api-movie", configuration = LoadBalancerConfiguration.class)
public interface MovieServiceClient {

	@GetMapping("/api/v1/movies/{genre}")
	List<MovieDto> getMoviesByGenre(@PathVariable (value = "genre") String genre);

	@Getter
	@Setter
	class MovieDto{
		private Long id;
		private String name;
		private String genre;
		private String urlStream;
	}

}

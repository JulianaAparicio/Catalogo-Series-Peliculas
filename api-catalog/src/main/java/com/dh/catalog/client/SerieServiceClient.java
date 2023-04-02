package com.dh.catalog.client;

import com.dh.catalog.config.LoadBalancerConfiguration;
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
	List<SerieDto> getSerieByGenre(@PathVariable (value = "genre") String genre);


	@Getter
	@Setter
	class SerieDto{
		private Long id;

		private String name;

		private String genre;

	}

}
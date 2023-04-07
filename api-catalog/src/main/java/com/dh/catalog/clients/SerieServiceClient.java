package com.dh.catalog.clients;

import com.dh.catalog.configurations.LoadBalancerConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="api-serie")
@LoadBalancerClient(value = "api-serie", configuration = LoadBalancerConfiguration.class)
public interface SerieServiceClient {

	@GetMapping("/api/v1/series/{genre}")
	List<SerieDto> getSeriesByGenre(@PathVariable (value = "genre") String genre);


	@Getter
	@Setter
	class SerieDto{
		private String id;
		private String name;
		private String genre;

	}

}

package com.dh.catalog.controller;

import com.dh.catalog.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	private final CatalogService catalogService;

	public CatalogController(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	@GetMapping("/online/{genre}")
	ResponseEntity<List<Object>> getByGenreOnline(@PathVariable String genre) {
		return ResponseEntity.ok(catalogService.findByGenre(genre));
	}

	@GetMapping("/offline/{genre}")
	ResponseEntity<List<Object>> getByGenreOffline(@PathVariable String genre) {
		return ResponseEntity.ok(catalogService.findByGenreOffline(genre));
	}

}

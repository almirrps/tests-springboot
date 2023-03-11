package com.udemy.planets.controllers;

import com.udemy.planets.models.PlanetModel;
import com.udemy.planets.services.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    @Autowired
    PlanetService planetService;

    @PostMapping
    public ResponseEntity<PlanetModel> create(@RequestBody PlanetModel planetModel) {
        PlanetModel planetCreated = planetService.create(planetModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(planetCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanetModel> get(@PathVariable("id") Long id) {
        return planetService.get(id).map(planetModel -> ResponseEntity.ok(planetModel))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}

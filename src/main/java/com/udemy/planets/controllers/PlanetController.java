package com.udemy.planets.controllers;

import com.udemy.planets.models.PlanetModel;
import com.udemy.planets.services.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    @Autowired
    PlanetService planetService;

    @PostMapping
    public ResponseEntity<PlanetModel> create(@RequestBody @Valid PlanetModel planetModel) {
        PlanetModel planetCreated = planetService.create(planetModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(planetCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanetModel> get(@PathVariable("id") Long id) {
        return planetService.get(id).map(planetModel -> ResponseEntity.ok(planetModel))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PlanetModel> getByName(@PathVariable("name") String name) {
        return planetService.getByName(name).map(planetModel -> ResponseEntity.ok(planetModel))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PlanetModel>> list(@RequestParam(required = false) String terrain,
                                                  @RequestParam(required = false) String climate) {
        List<PlanetModel> planets = planetService.list(terrain, climate);
        return ResponseEntity.ok(planets);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        planetService.remove(id);
        return ResponseEntity.noContent().build();
    }

}

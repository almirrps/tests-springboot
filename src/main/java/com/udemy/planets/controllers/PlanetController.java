package com.udemy.planets.controllers;

import com.udemy.planets.models.PlanetModel;
import com.udemy.planets.services.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    @Autowired
    PlanetService planetService;

    @PostMapping
    public ResponseEntity<Object> savingPlanet(@RequestBody PlanetModel planetModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planetService.save(planetModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findPlanetById(@PathVariable(value = "id", required = true) Long id) {
        Optional<PlanetModel> planetModelOptional = planetService.findById(id);
        if (!planetModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Planet not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(planetModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePlanet(@PathVariable(value = "id") Long id) {
        Optional<PlanetModel> planetModelOptional = planetService.findById(id);
        if (!planetModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Planet not found.");
        }

        planetService.delete(planetModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Planet deleted successfully.");
    }

}

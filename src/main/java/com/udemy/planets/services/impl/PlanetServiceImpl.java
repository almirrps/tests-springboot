package com.udemy.planets.services.impl;

import com.udemy.planets.models.PlanetModel;
import com.udemy.planets.repositories.PlanetRepository;
import com.udemy.planets.services.PlanetService;

import java.util.Optional;

public class PlanetServiceImpl implements PlanetService {

    final PlanetRepository planetRepository;

    public PlanetServiceImpl(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public Optional<PlanetModel> findById(Long id) {
        return planetRepository.findById(id);
    }

    public PlanetModel save(PlanetModel planetModel) {
        return planetRepository.save(planetModel);
    }

    public void delete(PlanetModel planetModel) {
        planetRepository.delete(planetModel);
    }

}

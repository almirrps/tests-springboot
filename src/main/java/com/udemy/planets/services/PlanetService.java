package com.udemy.planets.services;

import com.udemy.planets.models.PlanetModel;

import java.util.Optional;

public interface PlanetService {

    Optional<PlanetModel> findById(Long id);
    PlanetModel save(PlanetModel planetModel);
    void delete(PlanetModel planetModel);

}

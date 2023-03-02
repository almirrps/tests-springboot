package com.udemy.planets.services;

import com.udemy.planets.models.PlanetModel;
import com.udemy.planets.repositories.PlanetRepository;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

    private PlanetRepository planetRepository;

    //Realizando a injeção via construtor (ajuda na hora de realizar os testes
    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public PlanetModel create(PlanetModel planetModel) {
        return planetRepository.save(planetModel);

    }

}

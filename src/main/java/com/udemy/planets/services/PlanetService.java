package com.udemy.planets.services;

import com.udemy.planets.commons.QueryBuilder;
import com.udemy.planets.models.PlanetModel;
import com.udemy.planets.repositories.PlanetRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

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

    public Optional<PlanetModel> get(Long id) {
        return planetRepository.findById(id);
    }

    public Optional<PlanetModel> getByName(String name) {
        return planetRepository.findByName(name);
    }

    public List<PlanetModel> list(String terrain, String climate) {
        // Fazendo a busca por meio de query dinâmica
        Example<PlanetModel> query = QueryBuilder.makeQuery(new PlanetModel(terrain, climate));

        return planetRepository.findAll(query);
    }

    public void remove(Long id) {
        planetRepository.deleteById(id);
    }

}

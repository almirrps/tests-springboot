package com.udemy.planets.repositories;

import com.udemy.planets.models.PlanetModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlanetRepository extends CrudRepository<PlanetModel, Long> {

    Optional<PlanetModel> findByName(String name);
}

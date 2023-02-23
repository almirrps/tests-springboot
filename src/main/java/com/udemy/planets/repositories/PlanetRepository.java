package com.udemy.planets.repositories;

import com.udemy.planets.models.PlanetModel;
import org.springframework.data.repository.CrudRepository;

public interface PlanetRepository extends CrudRepository<PlanetModel, Long> {

}

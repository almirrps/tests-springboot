package com.udemy.planets.repositories;

import com.udemy.planets.models.PlanetModel;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;


import java.util.List;
import java.util.Optional;

public interface PlanetRepository extends CrudRepository<PlanetModel, Long>, QueryByExampleExecutor<PlanetModel> {

    Optional<PlanetModel> findByName(String name);

    @Override
    <S extends PlanetModel> List<S> findAll(Example<S> example);
}

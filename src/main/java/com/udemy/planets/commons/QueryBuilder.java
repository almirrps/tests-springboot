package com.udemy.planets.commons;

import com.udemy.planets.models.PlanetModel;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class QueryBuilder {

    public static Example<PlanetModel> makeQuery(PlanetModel planetModel) {

        // Montagem da query dinâmica: o campo que estiver null a montagem ignora
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();

        return Example.of(planetModel, exampleMatcher);
    }

}

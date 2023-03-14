package com.udemy.planets.commons;

import com.udemy.planets.models.PlanetModel;

import java.util.ArrayList;
import java.util.List;

public class PlanetConstants {

    public static final PlanetModel PLANET_MODEL = new PlanetModel("name", "climate", "terrain");
    public static final PlanetModel INVALID_PLANET_MODEL = new PlanetModel("", "", "");

    public static final PlanetModel TATOOINE = new PlanetModel(1L, "Tatooine", "arid", "desert");

    public static final PlanetModel ALDERAAN = new PlanetModel(2L, "Alderaan", "temperate", "grasslands, mountains");

    public static final PlanetModel YAVINIV = new PlanetModel(3L, "Yavin IV", "temperate, tropical", "jungle, rainforests");

    public static final List<PlanetModel> PLANETS = new ArrayList<>() {
        {
            add(TATOOINE);
            add(ALDERAAN);
            add(YAVINIV);
        }
    };

}

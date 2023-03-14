package com.udemy.planets.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)  // Subindo o BD com TomCat em porta aleatória
public class PlanetIT {

    // O método abaixo verifica se o contexto da aplicação foi carregado com sucesso
    @Test
    public void contextLoads() {
    }

}

package com.udemy.planets;

import static com.udemy.planets.commons.PlanetConstants.PLANET_MODEL;
import static org.assertj.core.api.Assertions.assertThat;

import com.udemy.planets.models.PlanetModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)  // Subindo o BD MySQL com TomCat em porta aleatória
@Sql(scripts = "/remove_planets.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) //Executando script depois de cada teste
public class PlanetIT {

    @Autowired
    private TestRestTemplate restTemplate;

    // O método abaixo verifica se o contexto da aplicação foi carregado com sucesso
    //@Test
    //public void contextLoads() {  // Removendo para não ter um teste desnecessário
    //}

    @Test
    public void createPlanet_ReturnsCreated() {
        ResponseEntity<PlanetModel> sut = restTemplate.postForEntity("/planets", PLANET_MODEL, PlanetModel.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(sut.getBody().getId()).isNotNull();
        assertThat(sut.getBody().getName()).isEqualTo(PLANET_MODEL.getName());
        assertThat(sut.getBody().getClimate()).isEqualTo(PLANET_MODEL.getClimate());
        assertThat(sut.getBody().getTerrain()).isEqualTo(PLANET_MODEL.getTerrain());
    }
}

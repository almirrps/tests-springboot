package com.udemy.planets.repositories;

import static com.udemy.planets.commons.PlanetConstants.PLANET_MODEL;
import static com.udemy.planets.commons.PlanetConstants.TATOOINE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.udemy.planets.commons.QueryBuilder;
import com.udemy.planets.models.PlanetModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@DataJpaTest  // Cria automaticamente um banco em memória (H2) para os testes (ver dependência no POM.xml)
public class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private TestEntityManager testEntityManager; // Permite interagir com o BD sem precisar do Repository

    @AfterEach // Chama o método abaixo à cada teste
    public void afterEach() {
        PLANET_MODEL.setId(null);
    }

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        // Arrange
        PlanetModel planetModel = planetRepository.save(PLANET_MODEL);

        // Act
        PlanetModel sut = testEntityManager.find(PlanetModel.class, planetModel.getId());

        System.out.println(planetModel);

        // Assert
        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(PLANET_MODEL.getName());
        assertThat(sut.getClimate()).isEqualTo(PLANET_MODEL.getClimate());
        assertThat(sut.getTerrain()).isEqualTo(PLANET_MODEL.getTerrain());
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        PlanetModel emptyPlanet = new PlanetModel();
        PlanetModel invalidPlanet = new PlanetModel("", "", "");

        assertThatThrownBy(() -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> planetRepository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void createPlanet_WithExistingName_ThrowsException() {
        PlanetModel planetModel = testEntityManager.persistFlushFind(PLANET_MODEL);
        testEntityManager.detach(planetModel);
        planetModel.setId(null);

        assertThatThrownBy(() -> planetRepository.save(planetModel)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        PlanetModel planetModel = testEntityManager.persistFlushFind(PLANET_MODEL);

        Optional<PlanetModel> planetOpt = planetRepository.findById(planetModel.getId());

        assertThat(planetOpt).isNotEmpty();
        assertThat(planetOpt.get()).isEqualTo(planetModel);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsEmpty() {
        Optional<PlanetModel> planetOpt = planetRepository.findById(1L);

        assertThat(planetOpt).isEmpty();
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() {
        PlanetModel planetModel = testEntityManager.persistFlushFind(PLANET_MODEL);

        Optional<PlanetModel> planetOpt = planetRepository.findByName(planetModel.getName());

        assertThat(planetOpt).isNotEmpty();
        assertThat(planetOpt.get()).isEqualTo(planetModel);
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsNotFound() {
        Optional<PlanetModel> planetOpt = planetRepository.findByName("name");

        assertThat(planetOpt).isEmpty();
    }

    @Sql(scripts = "/import_planets.sql")  // Utilizando de um script para importar dados (busca na pasta resources)
    @Test
    public void listPlanets_ReturnsFilteredPlanets() {
        Example<PlanetModel> queryWhithoutFilters = QueryBuilder.makeQuery(new PlanetModel());
        Example<PlanetModel> queryWhithFilters = QueryBuilder.makeQuery(new PlanetModel(TATOOINE.getClimate(), TATOOINE.getTerrain()));

        List<PlanetModel> responseWithoutFilters = planetRepository.findAll(queryWhithoutFilters);
        List<PlanetModel> responseWithFilters = planetRepository.findAll(queryWhithFilters);

        assertThat(responseWithoutFilters).isNotEmpty();
        assertThat(responseWithoutFilters).hasSize(3);
        assertThat(responseWithFilters).isNotEmpty();
        assertThat(responseWithFilters).hasSize(1);
        assertThat(responseWithoutFilters.get(0)).isEqualTo(TATOOINE);
    }

    @Test
    public void listPlanets_ReturnsNoPlanets() {
        Example<PlanetModel> query = QueryBuilder.makeQuery(new PlanetModel());

        List<PlanetModel> response = planetRepository.findAll(query);

        assertThat(response).isEmpty();
    }

}

package com.udemy.planets.repositories;

import static com.udemy.planets.commons.PlanetConstants.PLANET_MODEL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.udemy.planets.models.PlanetModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest  // Cria automaticamente um banco em memória (H2) para os testes (ver dependência no POM.xml)
public class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private TestEntityManager testEntityManager; // Permite interagir com o BD sem precisar do Repository

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

}

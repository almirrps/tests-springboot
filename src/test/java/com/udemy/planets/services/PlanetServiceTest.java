package com.udemy.planets.services;

import static com.udemy.planets.commons.PlanetConstants.PLANET_MODEL;
import static com.udemy.planets.commons.PlanetConstants.INVALID_PLANET_MODEL;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.udemy.planets.commons.QueryBuilder;
import com.udemy.planets.models.PlanetModel;
import com.udemy.planets.repositories.PlanetRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// (1) @SpringBootTest(classes = PlanetService.class)  //Para carregar um bean nesta classe (mais lento)
// ou
@ExtendWith(MockitoExtension.class) //Instanciando o Mockito para o teste unitário (mais rápido)
public class PlanetServiceTest {

    // (1) @Autowired
    @InjectMocks  //Instanciando o PlanetService e todas as suas dependências
    private PlanetService planetService;

    // (1) @MockBean
    @Mock  //Mockando a dependência
    private PlanetRepository planetRepository;

    // Sugestão padrão de nome para método de teste: operacao_estado_retorno

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        // Arrange: arrumando os dados pro teste e coloca os stubs
        when(planetRepository.save(PLANET_MODEL)).thenReturn(PLANET_MODEL);

        // Act: agindo/fazendo a operação de fato
        //sut = system under test
        PlanetModel sut = planetService.create(PLANET_MODEL); //Disparando o método

        // Assert: aferindo se o método sob teste é realmente o esperado
        assertThat(sut).isEqualTo(PLANET_MODEL); //Verificando se o retorno dele corresponde com o esperado
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        // Colocando os stubs para verificar se a exceção acontece
        when(planetRepository.save(INVALID_PLANET_MODEL)).thenThrow(RuntimeException.class);

        // Testando a condição, se a exceção está realmente acontecendo
        assertThatThrownBy(() -> planetService.create(INVALID_PLANET_MODEL)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        // Arrange
        when(planetRepository.findById(1L)).thenReturn(Optional.of(PLANET_MODEL));

        // Act
        Optional<PlanetModel> sut = planetService.get(1L);

        // Assert
        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET_MODEL);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsEmpty() {
        // Arrange
        when(planetRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<PlanetModel> sut = planetService.get(1L);

        // Assert
        assertThat(sut).isEmpty();
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() {
        // Arrange
        when(planetRepository.findByName(PLANET_MODEL.getName())).thenReturn(Optional.of(PLANET_MODEL));

        // Act
        Optional<PlanetModel> sut = planetService.getByName(PLANET_MODEL.getName());

        // Assert
        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET_MODEL);
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsEmpty() {
        final String name = "Unexisting name";
        // Arrange
        when(planetRepository.findByName(name)).thenReturn(Optional.empty());

        // Act
        Optional<PlanetModel> sut = planetService.getByName(name);

        // Assert
        assertThat(sut).isEmpty();
    }

    @Test
    public void listPlanets_ReturnAllPlanets() {
        // Criando o objeto lista
        List<PlanetModel> planets = new ArrayList<>() {
            {
                add(PLANET_MODEL);
            }
        };
        // Montando a query
        Example<PlanetModel> query = QueryBuilder.makeQuery(new PlanetModel(PLANET_MODEL.getTerrain(), PLANET_MODEL.getClimate()));

        // Arrange
        when(planetRepository.findAll(query)).thenReturn(planets);

        // Act
        List<PlanetModel> sut = planetService.list(PLANET_MODEL.getTerrain(), PLANET_MODEL.getClimate());

        // Assert
        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PLANET_MODEL);
    }

    @Test
    public void listPlanets_ReturnNoPlanets() {
        // Arrange
        when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());

        // Act
        List<PlanetModel> sut = planetService.list(PLANET_MODEL.getTerrain(), PLANET_MODEL.getClimate());

        // Assert
        assertThat(sut).isEmpty();
    }

    @Test
    public void removePlanet_WithExistingId_doesNotThrowAnyException() {
        // Não precisa de stub, somente verificar se não está retornando nenhuma exceção (porque o método é void)
        assertThatCode(() -> planetService.remove(1L)).doesNotThrowAnyException();
    }

    @Test
    public void removePlanet_WithUnexistingId_ThrowsException() {
        // Informando a exceção que será lançada e colocando o stub
        doThrow(new RuntimeException()).when(planetRepository).deleteById(99L);

        // Pegando o retorno e verificando se a exceção esperada foi disparada
        assertThatThrownBy(() -> planetService.remove(99L)).isInstanceOf(RuntimeException.class);
    }

}

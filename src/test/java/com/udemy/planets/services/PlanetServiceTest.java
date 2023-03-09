package com.udemy.planets.services;

import static com.udemy.planets.commons.PlanetConstants.PLANET_MODEL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.udemy.planets.models.PlanetModel;
import com.udemy.planets.repositories.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

}

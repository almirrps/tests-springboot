package com.udemy.planets.controllers;

import static com.udemy.planets.commons.PlanetConstants.PLANET_MODEL;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.planets.models.PlanetModel;
import com.udemy.planets.services.PlanetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlanetService planetService;

    @Test
    public void createPlanet_WithValidData_ReturnsCreated() throws Exception {
        when(planetService.create(PLANET_MODEL)).thenReturn(PLANET_MODEL);

        mockMvc.perform(post("/planets").content(objectMapper.writeValueAsString(PLANET_MODEL)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(PLANET_MODEL)); // Validando a propriedade raiz do json
    }

    @Test
    public void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception {
        PlanetModel emptyPlanet = new PlanetModel();
        PlanetModel invalidPlanet = new PlanetModel("", "", "");

        mockMvc.perform(
                    post("/planets").content(objectMapper.writeValueAsString(emptyPlanet))
                            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity()); // Validando a propriedade raiz do json

        mockMvc.perform(
                        post("/planets").content(objectMapper.writeValueAsString(invalidPlanet))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createPlanet_WithExistingName_ReturnsConflict() throws Exception {
        when(planetService.create(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(
                        post("/planets").content(objectMapper.writeValueAsString(PLANET_MODEL))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() throws Exception {
        when(planetService.get(1L)).thenReturn(Optional.of(PLANET_MODEL));

        mockMvc.perform(get("/planets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(PLANET_MODEL)); // Validando a propriedade raiz do json
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/planets/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() throws Exception {
        when(planetService.getByName(PLANET_MODEL.getName())).thenReturn(Optional.of(PLANET_MODEL));

        mockMvc.perform(get("/planets/name/" + PLANET_MODEL.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(PLANET_MODEL)); // Validando a propriedade raiz do json
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/planets/name/"))
                .andExpect(status().isNotFound());
    }

}

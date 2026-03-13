package com.entrainement.ennotechfusion.controller;

import com.entrainement.ennotechfusion.config.GestionnaireDesExceptions;
import com.entrainement.ennotechfusion.dto.MembreDTO;
import com.entrainement.ennotechfusion.service.MembreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({MembreController.class, GestionnaireDesExceptions.class})
class MembreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MembreService membreService;

    private MembreDTO membreDTO;

    @BeforeEach
    void initialiserLesDonneesDeTest() {
        membreDTO = new MembreDTO();
        membreDTO.setIdentifiant(1L);
        membreDTO.setNom("Dupont");
        membreDTO.setPrenom("Jean");
        membreDTO.setDateDeNaissance(LocalDate.of(1985, 3, 15));
        membreDTO.setAVote(false);
    }

    @Test
    void recupererTousLesMembres_devraitRetournerStatut200AvecUneListe() throws Exception {
        when(membreService.recupererTousLesMembres()).thenReturn(List.of(membreDTO));

        mockMvc.perform(get("/membres")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nom").value("Dupont"))
                .andExpect(jsonPath("$[0].prenom").value("Jean"))
                .andExpect(jsonPath("$[0].aVote").value(false));
    }

    @Test
    void recupererTousLesMembres_devraitRetournerUneListeVide() throws Exception {
        when(membreService.recupererTousLesMembres()).thenReturn(List.of());

        mockMvc.perform(get("/membres")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void enregistrerLeVoteDuMembre_devraitRetournerStatut200AvecAVoteTrue() throws Exception {
        membreDTO.setAVote(true);
        when(membreService.enregistrerLeVoteDuMembre(1L)).thenReturn(membreDTO);

        mockMvc.perform(put("/membres/1/voter")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aVote").value(true))
                .andExpect(jsonPath("$.identifiant").value(1));
    }

    @Test
    void enregistrerLeVoteDuMembre_devraitRetournerStatut500SiMembreDejaVote() throws Exception {
        when(membreService.enregistrerLeVoteDuMembre(1L))
                .thenThrow(new RuntimeException("Ce membre a déjà voté."));

        mockMvc.perform(put("/membres/1/voter")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Ce membre a déjà voté."));
    }
}

package com.entrainement.ennotechfusion.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.entrainement.ennotechfusion.dto.MembreDTO;
import com.entrainement.ennotechfusion.entity.Membre;
import com.entrainement.ennotechfusion.mapper.MembreMapper;
import com.entrainement.ennotechfusion.repository.MembreRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MembreServiceTest {

    @Mock
    private MembreRepository membreRepository;

    @Mock
    private MembreMapper membreMapper;

    @InjectMocks
    private MembreService membreService;

    private Membre membreQuiNaPasVote;
    private Membre membreQuiADejaVote;
    private MembreDTO membreDTOAttendu;

    @BeforeEach
    void initialiserLesDonneesDeTest() {
        membreQuiNaPasVote = new Membre();
        membreQuiNaPasVote.setIdentifiant(1L);
        membreQuiNaPasVote.setNom("Dupont");
        membreQuiNaPasVote.setPrenom("Jean");
        membreQuiNaPasVote.setDateDeNaissance(LocalDate.of(1985, 3, 15));
        membreQuiNaPasVote.setAVote(false);

        membreQuiADejaVote = new Membre();
        membreQuiADejaVote.setIdentifiant(2L);
        membreQuiADejaVote.setNom("Martin");
        membreQuiADejaVote.setPrenom("Sophie");
        membreQuiADejaVote.setDateDeNaissance(LocalDate.of(1990, 7, 22));
        membreQuiADejaVote.setAVote(true);

        membreDTOAttendu = new MembreDTO();
        membreDTOAttendu.setIdentifiant(1L);
        membreDTOAttendu.setNom("Dupont");
        membreDTOAttendu.setPrenom("Jean");
        membreDTOAttendu.setDateDeNaissance(LocalDate.of(1985, 3, 15));
        membreDTOAttendu.setAVote(true);
    }

    // -------------------------------------------------------
    // Tests sur recupererTousLesMembres()
    // -------------------------------------------------------

    @Test
    void recupererTousLesMembres_devraitRetournerUneListeDeMembreDTO() {
        when(membreRepository.findAll()).thenReturn(List.of(membreQuiNaPasVote));
        when(membreMapper.convertirEnDto(membreQuiNaPasVote)).thenReturn(membreDTOAttendu);

        List<MembreDTO> resultat = membreService.recupererTousLesMembres();

        assertEquals(1, resultat.size());
        verify(membreRepository, times(1)).findAll();
    }

    @Test
    void recupererTousLesMembres_devraitRetournerUneListeVideSiAucunMembre() {
        when(membreRepository.findAll()).thenReturn(List.of());

        List<MembreDTO> resultat = membreService.recupererTousLesMembres();

        assertTrue(resultat.isEmpty());
    }

    // -------------------------------------------------------
    // Tests sur enregistrerLeVoteDuMembre()
    // -------------------------------------------------------

    @Test
    void enregistrerLeVoteDuMembre_devraitPasserAVoteATrue() {
        when(membreRepository.findById(1L)).thenReturn(Optional.of(membreQuiNaPasVote));
        when(membreRepository.save(membreQuiNaPasVote)).thenReturn(membreQuiNaPasVote);
        when(membreMapper.convertirEnDto(membreQuiNaPasVote)).thenReturn(membreDTOAttendu);

        MembreDTO resultat = membreService.enregistrerLeVoteDuMembre(1L);

        assertTrue(resultat.isAVote());
        verify(membreRepository, times(1)).save(membreQuiNaPasVote);
    }

    @Test
    void enregistrerLeVoteDuMembre_devraitLeverUneExceptionSiMembreDejaVote() {
        when(membreRepository.findById(2L)).thenReturn(Optional.of(membreQuiADejaVote));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            membreService.enregistrerLeVoteDuMembre(2L);
        });

        assertEquals("Ce membre a déjà voté.", exception.getMessage());
        verify(membreRepository, never()).save(any());
    }

    @Test
    void enregistrerLeVoteDuMembre_devraitLeverUneExceptionSiMembreInexistant() {
        when(membreRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            membreService.enregistrerLeVoteDuMembre(999L);
        });

        assertEquals("Membre non trouvé avec l'identifiant : 999", exception.getMessage());
    }
}
package com.entrainement.ennotechfusion.service;

import org.springframework.stereotype.Service;

import com.entrainement.ennotechfusion.dto.MembreDTO;
import com.entrainement.ennotechfusion.entity.Membre;
import com.entrainement.ennotechfusion.mapper.MembreMapper;
import com.entrainement.ennotechfusion.repository.MembreRepository;

import java.util.List;

@Service
public class MembreService {

    private final MembreRepository membreRepository;
    private final MembreMapper membreMapper;

    public MembreService(MembreRepository membreRepository, MembreMapper membreMapper) {
        this.membreRepository = membreRepository;
        this.membreMapper = membreMapper;
    }

    public List<MembreDTO> recupererTousLesMembres() {
        return membreRepository.findAll()
                .stream()
                .map(membreMapper::convertirEnDto)
                .toList();
    }

    public MembreDTO enregistrerLeVoteDuMembre(Long identifiant) {
        Membre membre = membreRepository.findById(identifiant)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé avec l'identifiant : " + identifiant));

        if (membre.isAVote()) {
            throw new RuntimeException("Ce membre a déjà voté.");
        }

        membre.setAVote(true);
        Membre membreSauvegarde = membreRepository.save(membre);
        return membreMapper.convertirEnDto(membreSauvegarde);
    }
}
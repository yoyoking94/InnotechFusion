package com.entrainement.ennotechfusion.mapper;

import org.springframework.stereotype.Component;

import com.entrainement.ennotechfusion.dto.MembreDTO;
import com.entrainement.ennotechfusion.entity.Membre;

@Component
public class MembreMapper {

    public MembreDTO convertirEnDto(Membre membre) {
        MembreDTO membreDTO = new MembreDTO();
        membreDTO.setIdentifiant(membre.getIdentifiant());
        membreDTO.setNom(membre.getNom());
        membreDTO.setPrenom(membre.getPrenom());
        membreDTO.setDateDeNaissance(membre.getDateDeNaissance());
        membreDTO.setAVote(membre.isAVote());
        return membreDTO;
    }
}
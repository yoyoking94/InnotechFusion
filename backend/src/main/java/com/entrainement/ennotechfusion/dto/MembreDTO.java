package com.entrainement.ennotechfusion.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MembreDTO {

    private Long identifiant;
    private String nom;
    private String prenom;
    private LocalDate dateDeNaissance;
    private boolean aVote;
}
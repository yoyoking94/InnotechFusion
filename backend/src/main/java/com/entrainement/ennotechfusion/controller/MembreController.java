package com.entrainement.ennotechfusion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.entrainement.ennotechfusion.dto.MembreDTO;
import com.entrainement.ennotechfusion.service.MembreService;

import java.util.List;

@RestController
@RequestMapping("/membres")
public class MembreController {

    private final MembreService membreService;

    public MembreController(MembreService membreService) {
        this.membreService = membreService;
    }

    @GetMapping
    public ResponseEntity<List<MembreDTO>> recupererTousLesMembres() {
        List<MembreDTO> listeMembres = membreService.recupererTousLesMembres();
        return ResponseEntity.ok(listeMembres);
    }

    @PutMapping("/{identifiant}/voter")
    public ResponseEntity<MembreDTO> enregistrerLeVoteDuMembre(@PathVariable Long identifiant) {
        MembreDTO membreDTO = membreService.enregistrerLeVoteDuMembre(identifiant);
        return ResponseEntity.ok(membreDTO);
    }
}
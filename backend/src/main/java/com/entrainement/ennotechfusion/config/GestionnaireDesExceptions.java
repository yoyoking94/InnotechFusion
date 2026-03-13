package com.entrainement.ennotechfusion.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GestionnaireDesExceptions {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> gererLesExceptions(RuntimeException exception) {
        Map<String, String> reponseErreur = Map.of("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reponseErreur);
    }
}

package com.entrainement.ennotechfusion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entrainement.ennotechfusion.entity.Membre;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {

}
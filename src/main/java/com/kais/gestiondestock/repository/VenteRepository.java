package com.kais.gestiondestock.repository;

import com.kais.gestiondestock.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VenteRepository extends JpaRepository<Ventes, Integer> {
    Optional<Ventes> findByCode(String code);
}

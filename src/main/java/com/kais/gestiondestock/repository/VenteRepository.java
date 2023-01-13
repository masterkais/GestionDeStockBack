package com.kais.gestiondestock.repository;

import com.kais.gestiondestock.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenteRepository extends JpaRepository<Ventes,Integer> {
}

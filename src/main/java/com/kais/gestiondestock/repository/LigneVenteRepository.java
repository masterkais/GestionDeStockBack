package com.kais.gestiondestock.repository;

import com.kais.gestiondestock.model.LigneCommandeFournisseur;
import com.kais.gestiondestock.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneVenteRepository extends JpaRepository<LigneVente,Integer> {
}

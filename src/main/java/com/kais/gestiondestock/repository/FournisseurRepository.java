package com.kais.gestiondestock.repository;

import com.kais.gestiondestock.model.Article;
import com.kais.gestiondestock.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur,Integer> {
}

package com.kais.gestiondestock.repository;

import com.kais.gestiondestock.model.Roles;
import com.kais.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles,Integer> {
}

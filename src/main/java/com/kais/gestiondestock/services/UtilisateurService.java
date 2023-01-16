package com.kais.gestiondestock.services;

import com.kais.gestiondestock.dto.FournisseurDto;
import com.kais.gestiondestock.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService {
    UtilisateurDto save(UtilisateurDto dto);

    UtilisateurDto findById(Integer id);

    List<UtilisateurDto> findAll();

    void delete(Integer id);
}

package com.kais.gestiondestock.services;

import com.kais.gestiondestock.dto.CommandeFournisseurDto;

import java.util.List;

public interface CommandeFournisseurService {
    CommandeFournisseurDto save(CommandeFournisseurDto dto);

    CommandeFournisseurDto findById(Integer id);

    List<CommandeFournisseurDto> findAll();

    CommandeFournisseurDto findByCode(String code);

    void delete(Integer id);
}

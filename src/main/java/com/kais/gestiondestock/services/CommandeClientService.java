package com.kais.gestiondestock.services;

import com.kais.gestiondestock.dto.CategoryDto;
import com.kais.gestiondestock.dto.CommandeClientDto;

import java.util.List;

public interface CommandeClientService {
    CommandeClientDto save(CommandeClientDto dto);

    CommandeClientDto findById(Integer id);

    List<CommandeClientDto> findAll();

    CommandeClientDto findByCode(String code);

    void delete(Integer id);
}

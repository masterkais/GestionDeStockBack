package com.kais.gestiondestock.services;

import com.kais.gestiondestock.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto save(CategoryDto dto);

    CategoryDto findById(Integer id);

    List<CategoryDto> findAll();

    void delete(Integer id);
}

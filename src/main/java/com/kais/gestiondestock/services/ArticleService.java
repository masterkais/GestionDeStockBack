package com.kais.gestiondestock.services;

import com.kais.gestiondestock.dto.ArticleDto;

import java.util.List;

public interface ArticleService {
    ArticleDto save(ArticleDto dto);

    ArticleDto findById(Integer id);

    ArticleDto findByCodeArticle(String code);

    List<ArticleDto> findAll();

    void delete(Integer id);


}

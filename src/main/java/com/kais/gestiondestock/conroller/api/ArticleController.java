package com.kais.gestiondestock.conroller.api;

import com.kais.gestiondestock.conroller.ArticleApi;
import com.kais.gestiondestock.dto.ArticleDto;
import com.kais.gestiondestock.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController implements ArticleApi {

    ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public ArticleDto save(ArticleDto dto) {
        return this.articleService.save(dto);
    }

    @Override
    public ArticleDto findById(Integer id) {
        return this.articleService.findById(id);
    }

    @Override
    public ArticleDto findByCodeArticle(String code) {
        return this.articleService.findByCodeArticle(code);
    }

    @Override
    public List<ArticleDto> findAll() {
        return this.articleService.findAll();
    }

    @Override
    public void delete(Integer id) {
    this.articleService.delete(id);
    }
}

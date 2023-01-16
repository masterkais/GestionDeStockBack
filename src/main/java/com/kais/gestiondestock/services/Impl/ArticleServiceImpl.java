package com.kais.gestiondestock.services.Impl;

import com.kais.gestiondestock.dto.ArticleDto;
import com.kais.gestiondestock.exception.EntityNotFoundException;
import com.kais.gestiondestock.exception.ErrorCodes;
import com.kais.gestiondestock.exception.InvalidEntityException;
import com.kais.gestiondestock.repository.ArticleRepository;
import com.kais.gestiondestock.services.ArticleService;
import com.kais.gestiondestock.validaor.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public ArticleDto save(ArticleDto dto) {
        List<String> errors = ArticleValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_FOUND, errors);
        }
        return ArticleDto.fromEntity(articleRepository.save(ArticleDto.toEntity(dto)));
    }

    @Override
    public ArticleDto findById(Integer id) {
        if (id == null) {
            log.error("Article ID is null");
            return null;
        }
        ArticleDto articleDto = ArticleDto.fromEntity(articleRepository.findById(id).get());
        return Optional.of(articleDto).orElseThrow(() ->
                new EntityNotFoundException("Aucun article avec ce id " + id, ErrorCodes.ARTICLE_NOT_FOUND)
        );
    }

    @Override
    public ArticleDto findByCodeArticle(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Article code is null");
            return null;
        }
        ArticleDto articleDto = ArticleDto.fromEntity(articleRepository.findByCodeArticle(code));
        return Optional.of(articleDto).orElseThrow(() ->
                new EntityNotFoundException("Aucun article avec ce code " + code, ErrorCodes.ARTICLE_NOT_FOUND)
        );
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Article ID is null");
            return;
        }
        articleRepository.deleteById(id);
    }
}

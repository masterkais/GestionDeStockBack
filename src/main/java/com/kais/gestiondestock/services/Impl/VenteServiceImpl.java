package com.kais.gestiondestock.services.Impl;

import com.kais.gestiondestock.dto.LigneVenteDto;
import com.kais.gestiondestock.dto.VentesDto;
import com.kais.gestiondestock.exception.EntityNotFoundException;
import com.kais.gestiondestock.exception.ErrorCodes;
import com.kais.gestiondestock.exception.InvalidEntityException;
import com.kais.gestiondestock.model.Article;
import com.kais.gestiondestock.model.LigneVente;
import com.kais.gestiondestock.model.Ventes;
import com.kais.gestiondestock.repository.ArticleRepository;
import com.kais.gestiondestock.repository.LigneVenteRepository;
import com.kais.gestiondestock.repository.VenteRepository;
import com.kais.gestiondestock.services.VenteService;
import com.kais.gestiondestock.validaor.VentesValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VenteServiceImpl implements VenteService {
    private ArticleRepository articleRepository;
    private VenteRepository venteRepository;
    private LigneVenteRepository ligneVenteRepository;

    @Autowired
    public VenteServiceImpl(ArticleRepository articleRepository, VenteRepository venteRepository, LigneVenteRepository ligneVenteRepository) {
        this.articleRepository = articleRepository;
        this.venteRepository = venteRepository;
        this.ligneVenteRepository = ligneVenteRepository;
    }

    @Override
    public VentesDto save(VentesDto dto) {
        List<String> errors = VentesValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Ventes n'est pas valide");
            throw new InvalidEntityException("", ErrorCodes.VENTE_NOT_VALID, errors);
        }
        List<String> articleErros = new ArrayList<>();
        dto.getLigneVentes().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
            if (article.isEmpty()) {
                articleErros.add("Aucun article avec l'ID " + ligneVenteDto.getArticle().getId());
            }
        });
        if (!articleErros.isEmpty()) {
            log.error("One or more article was not found in the DB, {}", errors);
            throw new InvalidEntityException("un ou plusieurs articles n'ont pas ete trouve dans le BD  ", ErrorCodes.VENTE_NOT_VALID, errors);
        }
        Ventes savedVente = venteRepository.save(VentesDto.toEntity(dto));
        dto.getLigneVentes().forEach(ligneVenteDto -> {
            LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
            ligneVente.setVente(savedVente);
            ligneVenteRepository.save(ligneVente);
        });
        return VentesDto.fromEntity(savedVente);
    }

    @Override
    public VentesDto findById(Integer id) {
        if (id == null) {
            log.error("vente id is null");
        }
        return venteRepository.findById(id).map(VentesDto::fromEntity).orElseThrow(
                () -> new EntityNotFoundException("Aucun vente n'a etait trouver", ErrorCodes.VENTE_NOT_FOUND));
    }

    @Override
    public VentesDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("vente id is null");
        }
        return venteRepository.findByCode(code).map(VentesDto::fromEntity).orElseThrow(
                () -> new EntityNotFoundException("Aucun vente n'a etait trouver", ErrorCodes.VENTE_NOT_FOUND));
    }

    @Override
    public List<VentesDto> findAll() {
        return venteRepository.findAll().stream().map(VentesDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("vente id is null");
        }
        venteRepository.deleteById(id);
    }
}

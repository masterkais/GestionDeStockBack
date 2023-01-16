package com.kais.gestiondestock.services.Impl;

import com.kais.gestiondestock.dto.CommandeFournisseurDto;
import com.kais.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.kais.gestiondestock.exception.EntityNotFoundException;
import com.kais.gestiondestock.exception.ErrorCodes;
import com.kais.gestiondestock.exception.InvalidEntityException;
import com.kais.gestiondestock.model.Article;
import com.kais.gestiondestock.model.CommandeFournisseur;
import com.kais.gestiondestock.model.Fournisseur;
import com.kais.gestiondestock.model.LigneCommandeFournisseur;
import com.kais.gestiondestock.repository.ArticleRepository;
import com.kais.gestiondestock.repository.CommandeFournisseurRepository;
import com.kais.gestiondestock.repository.FournisseurRepository;
import com.kais.gestiondestock.repository.LigneCommandeFournisseurRepository;
import com.kais.gestiondestock.services.CommandeFournisseurService;
import com.kais.gestiondestock.validaor.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {
    CommandeFournisseurRepository commandeFournisseurRepository;
    FournisseurRepository fournisseurRepository;
    ArticleRepository articleRepository;
    LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;

    public CommandeFournisseurServiceImpl(LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository, ArticleRepository articleRepository, FournisseurRepository fournisseurRepository, CommandeFournisseurRepository commandeFournisseurRepository) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
    }


    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        List<String> errors = CommandeFournisseurValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("commande client n'est pas valide");
            throw new InvalidEntityException("Commande client n'est pas valide", ErrorCodes.COMMANDE_CLIENT_NOT_VALID, errors);
        }
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId());
        if (!fournisseur.isPresent()) {
            log.error("client with id {} was not found in the DB", dto.getFournisseur().getId());
            throw new EntityNotFoundException("Aucun client avec ce ID", ErrorCodes.CLIENT_NOT_FOUND);
        }
        List<String> articleError = new ArrayList<>();
        if (dto.getLigneCommandeFournisseurs() != null) {
            dto.getLigneCommandeFournisseurs().forEach(ligneCommande -> {
                if (ligneCommande.getArticle() != null) {
                    Optional<Article> article = articleRepository.findById(ligneCommande.getArticle().getId());
                    if (article.isEmpty()) {
                        articleError.add("L'article avec l' ID " + ligneCommande.getArticle().getId() + " n'existe pas");
                    }
                }
            });
        } else {
            articleError.add("Impossible d'enregistrer commande commande avec un article null");
        }
        if (!articleError.isEmpty()) {
            log.error("");
            throw new InvalidEntityException("Article n'existe pas dans le bd", ErrorCodes.ARTICLE_NOT_VALID, articleError);
        }
        CommandeFournisseur commandeFournisseursaved = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(dto));
        if (dto.getLigneCommandeFournisseurs() != null) {
            dto.getLigneCommandeFournisseurs().forEach(ligneCommandeFournisseur -> {
                LigneCommandeFournisseur lnCommdFournisseur = LigneCommandeFournisseurDto.toEntity(ligneCommandeFournisseur);
                lnCommdFournisseur.setCommandeFournisseur(commandeFournisseursaved);
                ligneCommandeFournisseurRepository.save(lnCommdFournisseur);
            });
        }
        return CommandeFournisseurDto.fromEntity(commandeFournisseursaved);
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if (id == null) {
            log.error("Commande client id is Null");
        }
        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException("Aucun commande client n'a été trouve avec l'ID" + id, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if (code == null) {
            log.error("Commande client id is Null");
        }
        return commandeFournisseurRepository.findCommandeFournisseurByCode(code)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException("Aucun commande client n'a été trouve avec ce CODE" + code, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Commande client id is Null");
        }
        commandeFournisseurRepository.deleteById(id);

    }
}

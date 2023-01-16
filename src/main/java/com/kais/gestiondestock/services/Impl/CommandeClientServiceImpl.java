package com.kais.gestiondestock.services.Impl;

import com.kais.gestiondestock.dto.CommandeClientDto;
import com.kais.gestiondestock.dto.LigneCommandeClientDto;
import com.kais.gestiondestock.exception.EntityNotFoundException;
import com.kais.gestiondestock.exception.ErrorCodes;
import com.kais.gestiondestock.exception.InvalidEntityException;
import com.kais.gestiondestock.model.Article;
import com.kais.gestiondestock.model.Client;
import com.kais.gestiondestock.model.CommandeClient;
import com.kais.gestiondestock.model.LigneCommandeClient;
import com.kais.gestiondestock.repository.ArticleRepository;
import com.kais.gestiondestock.repository.ClientRepository;
import com.kais.gestiondestock.repository.CommandeClientRepository;
import com.kais.gestiondestock.repository.LigneCommandeClientRepository;
import com.kais.gestiondestock.services.CommandeClientService;
import com.kais.gestiondestock.validaor.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeClientServiceImpl implements CommandeClientService {
    CommandeClientRepository commandeClientRepository;
    ClientRepository clientRepository;
    ArticleRepository articleRepository;
    LigneCommandeClientRepository ligneCommandeClientRepository;

    public CommandeClientServiceImpl(LigneCommandeClientRepository ligneCommandeClientRepository, ArticleRepository articleRepository, ClientRepository clientRepository, CommandeClientRepository commandeClientRepository) {
        this.commandeClientRepository = commandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
    }


    @Override
    public CommandeClientDto save(CommandeClientDto dto) {
        List<String> errors = CommandeClientValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("commande client n'est pas valide");
            throw new InvalidEntityException("Commande client n'est pas valide", ErrorCodes.COMMANDE_CLIENT_NOT_VALID, errors);
        }
        Optional<Client> client = clientRepository.findById(dto.getClient().getId());
        if (!client.isPresent()) {
            log.error("client with id {} was not found in the DB", dto.getClient().getId());
            throw new EntityNotFoundException("Aucun client avec ce ID", ErrorCodes.CLIENT_NOT_FOUND);
        }
        List<String> articleError = new ArrayList<>();
        if (dto.getLigneCommandeClients() != null) {
            dto.getLigneCommandeClients().forEach(ligneCommande -> {
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
        CommandeClient commandeClientsaved = commandeClientRepository.save(CommandeClientDto.toEntity(dto));
        if (dto.getLigneCommandeClients() != null) {
            dto.getLigneCommandeClients().forEach(ligneCommandeClient -> {
                LigneCommandeClient lnCommdClient = LigneCommandeClientDto.toEntity(ligneCommandeClient);
                lnCommdClient.setCommandeClient(commandeClientsaved);
                ligneCommandeClientRepository.save(lnCommdClient);
            });
        }
        return CommandeClientDto.fromEntity(commandeClientsaved);
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        if (id == null) {
            log.error("Commande client id is Null");
        }
        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException("Aucun commande client n'a été trouve avec l'ID" + id, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if (code == null) {
            log.error("Commande client id is Null");
        }
        return commandeClientRepository.findCommandeClientByCode(code)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException("Aucun commande client n'a été trouve avec ce CODE" + code, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Commande client id is Null");
        }
        commandeClientRepository.deleteById(id);

    }
}

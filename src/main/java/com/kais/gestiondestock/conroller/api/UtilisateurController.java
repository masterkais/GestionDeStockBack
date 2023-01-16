package com.kais.gestiondestock.conroller.api;

import com.kais.gestiondestock.conroller.UtilisateurApi;
import com.kais.gestiondestock.dto.UtilisateurDto;
import com.kais.gestiondestock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UtilisateurController implements UtilisateurApi {

    UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        return this.utilisateurService.save(dto);
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        return this.utilisateurService.findById(id);
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return this.utilisateurService.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.utilisateurService.delete(id);
    }
}

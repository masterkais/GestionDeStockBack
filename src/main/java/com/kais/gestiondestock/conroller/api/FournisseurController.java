package com.kais.gestiondestock.conroller.api;

import com.kais.gestiondestock.conroller.FournisseurApi;
import com.kais.gestiondestock.dto.FournisseurDto;
import com.kais.gestiondestock.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FournisseurController implements FournisseurApi {

    FournisseurService fournisseurService;

    @Autowired
    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    @Override
    public FournisseurDto save(FournisseurDto dto) {
        return this.fournisseurService.save(dto);
    }

    @Override
    public FournisseurDto findById(Integer id) {
        return this.fournisseurService.findById(id);
    }

    @Override
    public List<FournisseurDto> findAll() {
        return this.fournisseurService.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.fournisseurService.delete(id);
    }
}

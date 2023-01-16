package com.kais.gestiondestock.conroller.api;

import com.kais.gestiondestock.conroller.EntrepriseApi;
import com.kais.gestiondestock.dto.EntrepriseDto;
import com.kais.gestiondestock.services.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EntrepriseController implements EntrepriseApi {

    EntrepriseService entrepriseService;

    @Autowired
    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        return this.entrepriseService.save(dto);
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        return this.entrepriseService.findById(id);
    }

    @Override
    public List<EntrepriseDto> findAll() {
        return this.entrepriseService.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.entrepriseService.delete(id);
    }
}

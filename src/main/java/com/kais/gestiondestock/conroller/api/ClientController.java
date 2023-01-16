package com.kais.gestiondestock.conroller.api;

import com.kais.gestiondestock.conroller.ArticleApi;
import com.kais.gestiondestock.conroller.ClientApi;
import com.kais.gestiondestock.dto.ArticleDto;
import com.kais.gestiondestock.dto.ClientDto;
import com.kais.gestiondestock.services.ArticleService;
import com.kais.gestiondestock.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController implements ClientApi {

    ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        return this.clientService.save(dto);
    }

    @Override
    public ClientDto findById(Integer id) {
        return this.clientService.findById(id);
    }

    @Override
    public List<ClientDto> findAll() {
        return this.clientService.findAll();
    }

    @Override
    public void delete(Integer id) {
    this.clientService.delete(id);
    }
}

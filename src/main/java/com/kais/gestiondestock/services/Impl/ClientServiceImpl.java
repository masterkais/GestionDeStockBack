package com.kais.gestiondestock.services.Impl;

import com.kais.gestiondestock.dto.ClientDto;
import com.kais.gestiondestock.exception.EntityNotFoundException;
import com.kais.gestiondestock.exception.ErrorCodes;
import com.kais.gestiondestock.exception.InvalidEntityException;
import com.kais.gestiondestock.repository.ClientRepository;
import com.kais.gestiondestock.services.ClientService;
import com.kais.gestiondestock.validaor.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {
    ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        List<String> errors = ClientValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Client is not valid {}", dto);
            throw new InvalidEntityException("L'e client n'est pas valide", ErrorCodes.CLIENT_NOT_FOUND, errors);
        }
        return ClientDto.fromEntity(this.clientRepository.save(ClientDto.toEntity(dto)));
    }

    @Override
    public ClientDto findById(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return null;
        }
        ClientDto clientDto = ClientDto.fromEntity(this.clientRepository.findById(id).get());
        return Optional.of(clientDto).orElseThrow(() ->
                new EntityNotFoundException("Aucun client avec ce id " + id, ErrorCodes.CLIENT_NOT_FOUND)
        );
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return;
        }
        clientRepository.deleteById(id);
    }
}

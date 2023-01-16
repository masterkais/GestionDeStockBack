package com.kais.gestiondestock.services.Impl;

import com.kais.gestiondestock.dto.FournisseurDto;
import com.kais.gestiondestock.exception.EntityNotFoundException;
import com.kais.gestiondestock.exception.ErrorCodes;
import com.kais.gestiondestock.exception.InvalidEntityException;
import com.kais.gestiondestock.repository.FournisseurRepository;
import com.kais.gestiondestock.services.FournisseurService;
import com.kais.gestiondestock.validaor.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {
    FournisseurRepository fournisseurRepository;

    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    @Override
    public FournisseurDto save(FournisseurDto dto) {
        List<String> errors = FournisseurValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Client is not valid {}", dto);
            throw new InvalidEntityException("L'e client n'est pas valide", ErrorCodes.CLIENT_NOT_FOUND, errors);
        }
        return FournisseurDto.fromEntity(this.fournisseurRepository.save(FournisseurDto.toEntity(dto)));
    }

    @Override
    public FournisseurDto findById(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return null;
        }
        FournisseurDto fournisseurDto = FournisseurDto.fromEntity(this.fournisseurRepository.findById(id).get());
        return Optional.of(fournisseurDto).orElseThrow(() ->
                new EntityNotFoundException("Aucun fournisseur avec ce id " + id, ErrorCodes.FOURNISSEUR_NOT_FOUND)
        );
    }

    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(FournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return;
        }
        fournisseurRepository.deleteById(id);
    }
}

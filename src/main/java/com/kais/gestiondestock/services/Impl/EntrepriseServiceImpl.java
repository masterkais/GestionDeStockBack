package com.kais.gestiondestock.services.Impl;

import com.kais.gestiondestock.dto.EntrepriseDto;
import com.kais.gestiondestock.exception.EntityNotFoundException;
import com.kais.gestiondestock.exception.ErrorCodes;
import com.kais.gestiondestock.exception.InvalidEntityException;
import com.kais.gestiondestock.repository.EntrepriseRepository;
import com.kais.gestiondestock.services.EntrepriseService;
import com.kais.gestiondestock.validaor.EntrepriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {
    EntrepriseRepository entrepriseRepository;

    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        List<String> errors = EntrepriseValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Entreprise is not valid {}", dto);
            throw new InvalidEntityException("L'entreprise n'est pas valide", ErrorCodes.ENTREPRISE_NOT_FOUND, errors);
        }
        return EntrepriseDto.fromEntity(this.entrepriseRepository.save(EntrepriseDto.toEntity(dto)));
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        if (id == null) {
            log.error("Entreprise ID is null");
            return null;
        }
        EntrepriseDto entrepriseDto = EntrepriseDto.fromEntity(this.entrepriseRepository.findById(id).get());
        return Optional.of(entrepriseDto).orElseThrow(() ->
                new EntityNotFoundException("Aucun entreprise avec ce id " + id, ErrorCodes.ENTREPRISE_NOT_FOUND)
        );
    }

    @Override
    public List<EntrepriseDto> findAll() {
        return this.entrepriseRepository.findAll().stream()
                .map(EntrepriseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Entreprise ID is null");
            return;
        }
        entrepriseRepository.deleteById(id);
    }
}

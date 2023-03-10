package com.kais.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kais.gestiondestock.model.Ventes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VentesDto {

    private Integer id;

    private String code;

    private Instant dateVente;

    private String commentaire;

    private Integer idEntreprise;

    @JsonIgnore
    private List<LigneVenteDto> ligneVentes;

    public static VentesDto fromEntity(Ventes ventes) {

        if (ventes == null) {
            return null;
        }
        return VentesDto.builder()
                .id(ventes.getId())
                .code(ventes.getCode())
                .dateVente(ventes.getDateVente())
                .commentaire(ventes.getCommentaire())
                .idEntreprise(ventes.getIdEntreprise())
                .build();
    }

    public static Ventes toEntity(VentesDto ventesDto) {
        if (ventesDto == null) {
            return null;
        }
        Ventes ventes = new Ventes();
        ventes.setId(ventesDto.getId());
        ventes.setCode(ventesDto.getCode());
        ventes.setDateVente(ventesDto.getDateVente());
        ventes.setCommentaire(ventesDto.getCommentaire());
        ventes.setIdEntreprise(ventesDto.getIdEntreprise());
        return ventes;

    }
}

package com.kais.gestiondestock.modal;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Adress  {
    @Column(name = "adress1")
    private String adress1;
    @Column(name = "adress2")
    private String adress2;
    @Column(name = "ville")
    private String ville;
    @Column(name = "codepostale")
    private String codePostale;
    @Column(name = "pays")
    private String pays;
}

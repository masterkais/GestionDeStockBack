package com.kais.gestiondestock.modal;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "article")
public class Article extends AbstractEntity {
    @Column(name = "codeArticle")
    private String codeArticle;
    @Column(name = "designation")
    private String designation;
    @Column(name = "prixunitaireht")
    private BigDecimal prixUnitaireHt;
    @Column(name = "prixunitairettc")
    private BigDecimal prixUnitaireTtc;
    @Column(name = "tauxtva")
    private BigDecimal tauxTva;
    @Column(name = "photo")
    private String photo;
    @ManyToOne
    @JoinColumn(name = "idcategory")
    Category category;

}

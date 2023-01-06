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
@Table(name = "lignevente")
public class LigneVente extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "idvente")
    private Vente vente;
    @Column(name = "quantite")
    private BigDecimal quantite;
}

package com.kais.gestiondestock.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
public class Category extends AbstractEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "designation")
    private String designation;

    @OneToMany(mappedBy = "category")
    private List<Article> articles;

    @Column(name = "identreprise")
    private Integer idEntreprise;

}

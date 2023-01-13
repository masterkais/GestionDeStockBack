package com.kais.gestiondestock.repository;

import com.kais.gestiondestock.model.MvtStk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MvtStkRepository extends JpaRepository<MvtStk,Integer> {
}

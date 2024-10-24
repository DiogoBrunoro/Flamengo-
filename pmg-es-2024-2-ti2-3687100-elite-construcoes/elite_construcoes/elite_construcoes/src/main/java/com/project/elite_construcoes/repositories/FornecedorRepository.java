package com.project.elite_construcoes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.elite_construcoes.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    Fornecedor findByEmailCorporativo(String emailCorpotativo);
}
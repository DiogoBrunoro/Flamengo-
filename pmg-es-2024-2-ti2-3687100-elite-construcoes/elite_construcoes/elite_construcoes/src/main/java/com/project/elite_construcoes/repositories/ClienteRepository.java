package com.project.elite_construcoes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.elite_construcoes.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByEmail(String email);
}
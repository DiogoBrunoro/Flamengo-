package com.project.elite_construcoes.model.DTOs;

import java.time.LocalDate;

public record RegisterDTO(
    String email,
    String senha,
    String nomeCompleto,
    String nomeUsuario,
    LocalDate dataNascimento
) {
}

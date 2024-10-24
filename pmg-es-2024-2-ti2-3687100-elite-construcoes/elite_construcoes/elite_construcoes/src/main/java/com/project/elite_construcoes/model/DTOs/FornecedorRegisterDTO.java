package com.project.elite_construcoes.model.DTOs;

public record FornecedorRegisterDTO(
    String emailCorporativo,
    String senhaFornecedor,
    String nomeFornecedor,
    String cnpj,
    String endereco
) {
}

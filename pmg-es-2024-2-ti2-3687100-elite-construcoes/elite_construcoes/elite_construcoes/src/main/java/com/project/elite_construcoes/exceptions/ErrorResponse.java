package com.project.elite_construcoes.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ErrorResponse {
    private int status;
    private String message;
    private String stackTrace;
    private List<ValidationError> erros;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class ValidationError {
        private final String field;
        private final String message;
    }

    public void addValidationError(String field, String message) {
        if (Objects.isNull(erros)) {
            this.erros = new ArrayList<>();
            
        }
        this.erros.add(new ValidationError(field, message));
    }
}
package com.project.elite_construcoes.exceptions;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "GlobalExceptionHandler")
@RestControllerAdvice
public class FornecedorGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${server.error.include-exception}")
    private boolean printStackTrace;
    
    
    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException methodArgumentNotValidException,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request){
        FornecedorErrorResponse errorResponse = new FornecedorErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error. Check error field for details.");
        for(FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()){
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(errorResponse);


    }
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleUsernameNotFoundException(
        AuthenticationException AuthenticationException,
        WebRequest request){
            final String errorMessage = "Usuário ou senha inválidos. Por favor, tente novamente.";
            log.error(errorMessage, AuthenticationException);
            return buildErrorResponse(
                AuthenticationException,
                errorMessage,
                HttpStatus.NOT_FOUND,
                request);
        }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request){
        log.error("Erro Desconhecido ocorreu", exception);
        final String errorMessage = "Erro desconhecido ocorreu. Por favor, tente novamente.";
        FornecedorErrorResponse errorResponse = new FornecedorErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage);
        if(printStackTrace){
            errorResponse.setStackTrace(exception.getMessage());
        }
        return buildErrorResponse(
            exception,
            errorMessage,
            HttpStatus.INTERNAL_SERVER_ERROR,
            request);
    }


        @ExceptionHandler(DataIntegrityViolationException.class)
        @ResponseStatus(HttpStatus.CONFLICT)
        public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException dataIntegrityViolationException, WebRequest request){
            final String errorMessage = "Erro de integridade de dados ocorreu. Por favor, tente novamente.";
            log.error("Falha de integridade de dados ocorreu: " + errorMessage, dataIntegrityViolationException);
            return buildErrorResponse(
                dataIntegrityViolationException,
                errorMessage,
                HttpStatus.CONFLICT,
                request);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException,
            WebRequest request){
                final String errorMessage = "Erro de violação de restrição ocorreu. Por favor, tente novamente.";
                log.error("Falha de violação de restrição ocorreu: " + errorMessage, constraintViolationException);
                return buildErrorResponse(
                    constraintViolationException,
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    request);
            }

        @ExceptionHandler(ObjectNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ResponseEntity<Object> handleObjectNotFoundException(
            ObjectNotFoundException objectNotFoundException,
            WebRequest request){
                final String errorMessage = "Objeto não encontrado.";
                log.error("Objeto não encontrado: " + errorMessage, objectNotFoundException);
                return buildErrorResponse(
                    objectNotFoundException,
                    errorMessage,
                    HttpStatus.NOT_FOUND,
                    request);
            }


        private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            String errorMessage,
            HttpStatus status,
            WebRequest request){
                FornecedorErrorResponse errorResponse = new FornecedorErrorResponse(status.value(), errorMessage);
                if(this.printStackTrace){
                    errorResponse.setStackTrace(exception.getMessage());
                }
                return ResponseEntity.status(status).body(errorResponse);
            }

        private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            HttpStatus status,
            WebRequest request){
                return buildErrorResponse(exception, exception.getMessage(), status, request);
            }
        
    





}
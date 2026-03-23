package com.escola.sistemaescolar.dto;

import org.springframework.validation.FieldError;

public record ErroDeValidacaoDTO(String campo, String mensagem) {
    // Esse construtor facilita a nossa vida na hora de converter o erro do Spring para o nosso DTO
    public ErroDeValidacaoDTO(FieldError erro) {
        this(erro.getField(), erro.getDefaultMessage());
    }
}
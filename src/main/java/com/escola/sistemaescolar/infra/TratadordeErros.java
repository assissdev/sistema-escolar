package com.escola.sistemaescolar.infra;

import com.escola.sistemaescolar.dto.ErroDeValidacaoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadordeErros {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroDeValidacaoDTO>> tratarErro400(MethodArgumentNotValidException exception) {

        // Pega todos os erros que aconteceram na requisição
        var erros = exception.getFieldErrors();

        // Transforma a lista de erros feios do Spring na nossa lista de DTOs bonitinhos e devolve com status 400
        return ResponseEntity.badRequest().body(erros.stream().map(ErroDeValidacaoDTO::new).toList());
    }
}
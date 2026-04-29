package com.escola.sistemaescolar.dto;

import java.util.List;

public record BoletimResponseDTO(
        Long alunoId,
        List<NotaResponseDTO> notas,
        Double media,
        String status // Vai ser "APROVADO" ou "REPROVADO"
) { }

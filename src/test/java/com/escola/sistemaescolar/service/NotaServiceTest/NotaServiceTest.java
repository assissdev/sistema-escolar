package com.escola.sistemaescolar.service.NotaServiceTest;

import com.escola.sistemaescolar.model.Nota;
import com.escola.sistemaescolar.repository.AlunoRepository;
import com.escola.sistemaescolar.repository.NotaRepository;
import com.escola.sistemaescolar.service.NotaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotaServiceTest {

    @Mock
    private NotaRepository notaRepository; // Banco de dados "de mentira"

    @Mock
    private AlunoRepository alunoRepository; // Banco de dados "de mentira"

    @InjectMocks
    private NotaService notaService; // A classe real que vamos testar

    @Test
    @DisplayName("Deve retornar o DTO da nota quando buscar por um ID que existe")
    void deveBuscarNotaPorIdComSucesso() {
        // 1. Cenário (Arrange)
        Long idBuscado = 1L;
        Nota notaSimulada = new Nota();
        notaSimulada.setId(idBuscado);
        notaSimulada.setValor(9.5);
        // ... (você pode setar disciplina e aluno simulados aqui se quiser)

        // Ensinando o Mockito: "Quando procurarem o ID 1, devolva a notaSimulada"
        when(notaRepository.findById(idBuscado)).thenReturn(Optional.of(notaSimulada));

        // 2. Ação (Act)
        var resultadoDTO = notaService.buscarPorId(idBuscado);

        // 3. Verificação (Assert)
        assertNotNull(resultadoDTO);
        assertEquals(9.5, resultadoDTO.valor());
    }

    @Test
    @DisplayName("Deve lançar exceção quando buscar por um ID que não existe")
    void deveLancarExceptionQuandoNotaNaoExistir() {
        // 1. Cenário
        Long idBuscado = 99L;

        // Ensinando o Mockito: "Quando procurarem o ID 99, devolva Vazio"
        when(notaRepository.findById(idBuscado)).thenReturn(Optional.empty());

        // 2. Ação e 3. Verificação (Tudo junto para testar erros)
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            notaService.buscarPorId(idBuscado);
        });

        assertEquals("Nota não encontrada!", exception.getMessage());
    }
}
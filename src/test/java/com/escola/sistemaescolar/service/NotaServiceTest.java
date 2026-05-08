package com.escola.sistemaescolar.service;

import com.escola.sistemaescolar.dto.BoletimResponseDTO;
import com.escola.sistemaescolar.model.Nota;
import com.escola.sistemaescolar.repository.AlunoRepository;
import com.escola.sistemaescolar.repository.NotaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotaServiceTest {

    @Mock // Cria um repositório "de mentirinha" para não sujar o banco real
    private NotaRepository notaRepository;

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks // Injeta os repositórios falsos dentro do nosso service verdadeiro
    private NotaService notaService;

    @Test
    @DisplayName("Deve gerar boletim com status APROVADO e média exata quando as notas forem boas")
    void deveGerarBoletimAprovado() {
        // 1. Arrange (Preparar o cenário)
        Long alunoId = 1L;
        when(alunoRepository.existsById(alunoId)).thenReturn(true); // Finge que o aluno existe

        Nota nota1 = new Nota(); nota1.setValor(8.0);
        Nota nota2 = new Nota(); nota2.setValor(7.0);

        // Quando o service procurar as notas, devolve as notas falsas que criamos acima
        when(notaRepository.findByAlunoId(alunoId)).thenReturn(List.of(nota1, nota2));

        // 2. Act (Agir - chamar o seu método de verdade)
        BoletimResponseDTO boletim = notaService.gerarBoletim(alunoId);

        // 3. Assert (Afirmar - verificar se o seu código acertou)
        assertEquals(7.5, boletim.media(), "A média deveria ser exatamente 7.5");
        assertEquals("APROVADO", boletim.status(), "O aluno deveria estar APROVADO");
    }
}
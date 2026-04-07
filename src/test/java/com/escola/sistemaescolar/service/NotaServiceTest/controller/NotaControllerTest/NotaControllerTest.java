package com.escola.sistemaescolar.service.NotaServiceTest.controller.NotaControllerTest;

import com.escola.sistemaescolar.controller.NotaController;
import com.escola.sistemaescolar.service.NotaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotaController.class)
@AutoConfigureMockMvc(addFilters = false) // Desliga o JWT só para esse teste focar na validação
class NotaControllerTest {

    @Autowired
    private MockMvc mockMvc; // O nosso "Postman" de mentira

    @MockBean
    private NotaService notaService; // O Service de mentira (não queremos salvar nada no banco de verdade aqui)

    @Test
    @DisplayName("Deve devolver erro 400 (Bad Request) se a nota enviada for maior que 10")
    void deveDevolverErro400QuandoNotaInvalida() throws Exception {
        // Criamos um JSON simulando um professor digitando "15.0"
        String jsonComNotaInvalida = """
                {
                    "valor": 15.0,
                    "alunoId": 1,
                    "disciplina": "Matemática"
                }
                """;

        // Simulamos o envio desse JSON para a rota POST /notas
        mockMvc.perform(post("/notas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonComNotaInvalida))
                .andExpect(status().isBadRequest()); // A grande verificação: O Spring TEM que barrar e dar 400!
    }
}
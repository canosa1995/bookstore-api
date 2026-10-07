package br.com.bookstore.bookstore_api.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;



import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import br.com.bookstore.bookstore_api.model.Livro;
import br.com.bookstore.bookstore_api.service.LivroService;
import br.com.bookstore.bookstore_api.exception.GlobalExceptionHandler;
import br.com.bookstore.bookstore_api.exception.LivroNaoEncontradoException;


import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;

public class LivroControllerTest {

    private MockMvc mockMvc;

    private LivroService livroService;

    @BeforeEach 
    void configurar(){

        livroService = mock(LivroService.class);

        LivroController livroController =
                new LivroController(livroService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(livroController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test 
    void deveListarTodosOsLivros() throws Exception {

        Livro livro = new Livro();

        livro.setId(1L);
        livro.setTitulo("Clean Code");
        livro.setAutor("Robert C. Martin");
        livro.setIsbn("9780132350884");
        livro.setPreco(new BigDecimal("89.90"));

        when(livroService.listarTodos())
                .thenReturn(List.of(livro));

        mockMvc.perform(get("/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Clean Code"))
                .andExpect(jsonPath("$[0].autor").value("Robert C. Martin"));

        verify(livroService).listarTodos();
    }

    @Test
    void deveBuscarLivroPorIdQuandoExistir() throws Exception{
        
        Livro livro = new Livro();

        livro.setId(1L);
        livro.setTitulo("Clean Code");
        livro.setAutor("Robert C. Martin");
        livro.setIsbn("9780132350884");
        livro.setPreco(new BigDecimal("89.90"));

        when(livroService.buscarPorId(1L))
                .thenReturn(livro);

        mockMvc.perform(get("/livros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Clean Code"))
                .andExpect(jsonPath("$.autor").value("Robert C. Martin"))
                .andExpect(jsonPath("$.isbn").value("9780132350884"))
                .andExpect(jsonPath("$.preco").value(89.90));

        verify(livroService).buscarPorId(1L);
    }

    @Test 
    void deveRetornar404QuandoLivroNaoExistir() throws Exception{

        when(livroService.buscarPorId(9999L))
                .thenThrow(new LivroNaoEncontradoException(9999L));

        mockMvc.perform(get("/livros/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.erro").value("Not Found"))
                .andExpect(jsonPath("$.mensagem")
                .value("Livro com ID 9999 não encontrado"));
        
        verify(livroService).buscarPorId(9999L);
    }

    @Test 
    void deveCriarLivroQuandoDadosForemValidos() throws Exception {

        Livro livroSalvo = new Livro();

        livroSalvo.setId(6L);
        livroSalvo.setTitulo("Java Efetivo");
        livroSalvo.setAutor("Autor Teste");
        livroSalvo.setIsbn("1234567890");
        livroSalvo.setPreco(new BigDecimal("49.90"));

        when(livroService.salvar(org.mockito.ArgumentMatchers.any(Livro.class)))
                .thenReturn(livroSalvo);

        String json = """
                {
                "titulo": "Java Efetivo",
                "autor": "Autor Teste",
                "isbn": "1234567890",
                "preco": 49.00
                }
                        """;

        mockMvc.perform(
                post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.titulo").value("Java Efetivo"))
                .andExpect(jsonPath("$.autor").value("Autor Teste"))
                .andExpect(jsonPath("$.isbn").value("1234567890"))
                .andExpect(jsonPath("$.preco").value(49.90));

        verify(livroService)
                .salvar(org.mockito.ArgumentMatchers.any(Livro.class));
        
    }

    @Test 
    void deveRetornar400QuandoDadosForemInvalidos() throws Exception {

        String json = """
                {
                "titulo": "",
                "autor": "",
                "isbn": "1234567890123456789012345",
                "preco": -10.00
                }
                """;

        mockMvc.perform(
                post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.titulo")
                .value("O título do livro é obrigatório"))
        .andExpect(jsonPath("$.autor")
                .value("O autor do livro é obrigatório"))
        .andExpect(jsonPath("$.isbn")
                .value("O ISBN deve ter no máximo 20 caracteres"))
        .andExpect(jsonPath("$.preco")
                .value("O preço do livro deve ser um valor positivo"));
                
        verify(livroService, never())
                .salvar(any(Livro.class));
    }

    @Test 
    void deveAtualizarLivroQuandoDadosForemValidos() throws Exception {

        Livro livroAtualizado = new Livro();

        livroAtualizado.setId(1L);
        livroAtualizado.setTitulo("Clean Code - Atualizado");
        livroAtualizado.setAutor("Robert C. Martin");
        livroAtualizado.setIsbn("9780132350884");
        livroAtualizado.setPreco(new BigDecimal("99.90"));

        when(
             livroService.atualizar(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any(Livro.class)
             )   
        ).thenReturn(livroAtualizado);

        String json = """
                        {
                        "titulo": "Clean Code - Atualizado",
                        "autor": "Robert C. Martin",
                        "isbn": "9780132350884",
                        "preco": 99.90
                        }
                        """;

        mockMvc.perform(
                put("/livros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.titulo").value("Clean Code - Atualizado"))
        .andExpect(jsonPath("$.autor").value("Robert C. Martin"))
        .andExpect(jsonPath("$.isbn").value("9780132350884"))
        .andExpect(jsonPath("$.preco").value(99.90));

        verify(livroService).atualizar(
               org.mockito.ArgumentMatchers.eq(1L),
               org.mockito.ArgumentMatchers.any(Livro.class) 
        );
    }

    @Test
    void deveRetornar404AoAtualizarLivroQueNaoExiste() throws Exception{

        when(
             livroService.atualizar(
                org.mockito.ArgumentMatchers.eq(9999L),
                org.mockito.ArgumentMatchers.any(Livro.class)
             )   
        ).thenThrow(new LivroNaoEncontradoException(9999L));

        String json = """
                        {
                        "titulo": "Livro Teste",
                        "autor": "Autor Teste",
                        "isbn": "1234567890",
                        "preco": 10.00
                        }
                        """;

        mockMvc.perform(
                put("/livros/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.erro").value("Not Found"))
        .andExpect(jsonPath("$.mensagem")
                .value("Livro com ID 9999 não encontrado"));

        verify(livroService).atualizar(
                org.mockito.ArgumentMatchers.eq(9999L),
                org.mockito.ArgumentMatchers.any(Livro.class)
        );
    }

    @Test
    void deveExcluirLivroQuandoExistir() throws Exception{

        mockMvc.perform(delete("/livros/1"))
                .andExpect(status().isNoContent());

        verify(livroService).excluirPorId(1L);
    }

    @Test 
    void deveRetornar404AoExcluirLivroQueNaoExiste() throws Exception{

        org.mockito.Mockito.doThrow(
            new LivroNaoEncontradoException(9999L)    
        )
        .when(livroService)
        .excluirPorId(9999L);

        mockMvc.perform(delete("/livros/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.erro").value("Not Found"))
                .andExpect(jsonPath("$.mensagem").value("Livro com ID 9999 não encontrado"));

        verify(livroService).excluirPorId(9999L);
    }

}

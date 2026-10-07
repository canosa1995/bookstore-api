package br.com.bookstore.bookstore_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.bookstore.bookstore_api.model.Livro;
import br.com.bookstore.bookstore_api.repository.LivroRepository;
import br.com.bookstore.bookstore_api.exception.LivroNaoEncontradoException;

@ExtendWith (MockitoExtension.class)
public class LivroServiceTest {

    @Mock 
    private LivroRepository livroRepository;

    @InjectMocks 
    private LivroService livroService;

    @Test
    void deveListarTodosOsLivros(){

        Livro livro = new Livro();

        livro.setId(1L);
        livro.setTitulo("Clean Code");
        livro.setAutor("Robert C. Martin");
        livro.setIsbn("9780132350884");
        livro.setPreco(new BigDecimal("89.90"));

        when(livroRepository.findAll()).thenReturn(List.of(livro));

        List<Livro> resultado = livroService.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("Clean Code", resultado.get(0).getTitulo());

        verify(livroRepository).findAll();
    }

    @Test 
    void deveBuscarLivroPorIdQuandoExistir() {
       Livro livro = new Livro();

       livro.setId(1L);
       livro.setTitulo("Clean Code");
       livro.setAutor("Robert C. Martin");
       livro.setIsbn("9780132350884");
       livro.setPreco(new BigDecimal("89.90"));

       when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

         Livro resultado = livroService.buscarPorId(1L);

         assertEquals(1L, resultado.getId());
         assertEquals("Clean Code", resultado.getTitulo());
         verify(livroRepository).findById(1L);

    }

    @Test 
    void deveLancarExcecaoQuandoLivroNaoExistir(){
        
        when(livroRepository.findById(9999L)).thenReturn(Optional.empty());

        LivroNaoEncontradoException exception = assertThrows(
            LivroNaoEncontradoException.class, 
        () -> livroService.buscarPorId(9999L)
        );

        assertEquals(
        "Livro com ID 9999 não encontrado", 
                exception.getMessage()
        );

        verify(livroRepository).findById(9999L);        
    }

    @Test 
    void deveSalvarLivro(){

        Livro livro = new Livro();

        livro.setTitulo("Java Efetivo");
        livro.setAutor("Autor Teste");
        livro.setIsbn("9780134685991");
        livro.setPreco(new BigDecimal("49.90"));

        Livro livroSalvo = new Livro();

        livroSalvo.setId(6L);
        livroSalvo.setTitulo("Java Efetivo");
        livroSalvo.setAutor("Autor Teste");
        livroSalvo.setIsbn("9780134685991");
        livroSalvo.setPreco(new BigDecimal("49.90"));

        when(livroRepository.save(livro)).thenReturn(livroSalvo);

        Livro resultado = livroService.salvar(livro);

        assertEquals(6L, resultado.getId());
        assertEquals("Java Efetivo", resultado.getTitulo());
        assertEquals("Autor Teste", resultado.getAutor());
        verify(livroRepository).save(livro);
    }

    @Test 
    void deveAtualizarLivroQuandoExistir(){
        
        Livro livroExistente = new Livro();

        livroExistente.setId(1L);
        livroExistente.setTitulo("Clean Code");
        livroExistente.setAutor("Robert C. Martin");
        livroExistente.setIsbn("9780132350884");
        livroExistente.setPreco(new BigDecimal("89.90"));

        Livro novoDados = new Livro();

        novoDados.setTitulo("Clean Code - Atualizado");
        novoDados.setAutor("Robert C. Martin");
        novoDados.setIsbn("9780132350884");
        novoDados.setPreco(new BigDecimal("99.90"));

        when(livroRepository.findById(1L)).thenReturn(Optional.of(livroExistente));
        when(livroRepository.save(livroExistente)).thenReturn(livroExistente);

        Livro resultado = livroService.atualizar(1L, novoDados);

        assertEquals(1L, resultado.getId());
        assertEquals("Clean Code - Atualizado", resultado.getTitulo());
        assertEquals(new BigDecimal("99.90"), resultado.getPreco());

        verify(livroRepository).findById(1L);
        verify(livroRepository).save(livroExistente);
    }

    @Test 
    void deveLancarExcecaoAoAtualizarLivroQueNaoExiste(){

        Livro novosDados = new Livro();

        novosDados.setTitulo("Livro Teste");
        novosDados.setAutor("Autor Teste");
        novosDados.setIsbn("1234567890");
        novosDados.setPreco(new BigDecimal("10.00"));

        when(livroRepository.findById(9999L))
        .thenReturn(Optional.empty());
        
        LivroNaoEncontradoException exception = assertThrows(
            LivroNaoEncontradoException.class,
            () -> livroService.atualizar(9999L, novosDados)
        );

        assertEquals(
            "Livro com ID 9999 não encontrado",
            exception.getMessage()
        );

        verify(livroRepository).findById(9999L);

    }

    @Test 
    void deveExcluirLivroQuandoExistir(){

        when(livroRepository.existsById(1L))
        .thenReturn(true);

        livroService.excluirPorId(1L);

        verify(livroRepository).existsById(1L);
        verify(livroRepository).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoExcluirLivroQueNaoExiste(){

        when(livroRepository.existsById(9999L))
        .thenReturn(false);

        LivroNaoEncontradoException exception = assertThrows(
            LivroNaoEncontradoException.class,
            () -> livroService.excluirPorId(9999L)
        );

        assertEquals(
            "Livro com ID 9999 não encontrado",
            exception.getMessage()
        );

        verify(livroRepository).existsById(9999L);
    }

}

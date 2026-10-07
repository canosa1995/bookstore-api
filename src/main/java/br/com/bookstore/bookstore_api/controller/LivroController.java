package br.com.bookstore.bookstore_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.DeleteMapping;

import br.com.bookstore.bookstore_api.model.Livro;
import br.com.bookstore.bookstore_api.service.LivroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService){
        this.livroService = livroService;
    }

    @GetMapping
    public List<Livro> listarTodos(){
        return livroService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {

    Livro livro = livroService.buscarPorId(id);

    return ResponseEntity.ok(livro);
    }

    @PostMapping
    public Livro criar(@Valid @RequestBody Livro livro){
        return livroService.salvar(livro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody Livro livro){

        Livro livroAtualizado = livroService.atualizar(id, livro);

        return ResponseEntity.ok(livroAtualizado);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        livroService.excluirPorId(id);

        return ResponseEntity.noContent().build();
    }

    
}

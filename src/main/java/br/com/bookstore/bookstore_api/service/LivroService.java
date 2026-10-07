package br.com.bookstore.bookstore_api.service;

//import br.com.bookstore.bookstore_api.BookstoreApiApplication;
//import br.com.bookstore.bookstore_api.controller.LivroController;
import java.util.List;
//import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.bookstore.bookstore_api.model.Livro;
import br.com.bookstore.bookstore_api.repository.LivroRepository;

import br.com.bookstore.bookstore_api.exception.LivroNaoEncontradoException;

@Service
public class LivroService {

    //private final LivroController livroController;
    //private final BookstoreApiApplication bookstoreApiApplication;
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository){
        this.livroRepository = livroRepository;
    }

    public List<Livro> listarTodos(){
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id){
        return livroRepository.findById(id).orElseThrow(() -> new LivroNaoEncontradoException(id));
    }

    public Livro salvar(Livro livro){
        return livroRepository.save(livro);
    }

    public Livro atualizar(Long id, Livro dadosLivro){
       Livro livroExistente = livroRepository.findById(id)
            .orElseThrow(() -> new LivroNaoEncontradoException(id));

        livroExistente.setTitulo(dadosLivro.getTitulo());
        livroExistente.setAutor(dadosLivro.getAutor());
        livroExistente.setIsbn(dadosLivro.getIsbn());
        livroExistente.setPreco(dadosLivro.getPreco());

        return livroRepository.save(livroExistente);
    }

    public void excluirPorId(Long id){

       if(!livroRepository.existsById(id)){
            throw new LivroNaoEncontradoException(id);
        }
        
        livroRepository.deleteById(id);

    }
}

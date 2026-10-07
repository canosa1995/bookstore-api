package br.com.bookstore.bookstore_api.exception;

public class LivroNaoEncontradoException extends RuntimeException {
    public LivroNaoEncontradoException(Long id) {
        super("Livro com ID " + id + " não encontrado");
    }
}

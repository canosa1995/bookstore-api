package br.com.bookstore.bookstore_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bookstore.bookstore_api.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    
}

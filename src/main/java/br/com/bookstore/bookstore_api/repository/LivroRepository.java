package br.com.bookstore.bookstore_api.repository;

import br.com.bookstore.bookstore_api.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {

}

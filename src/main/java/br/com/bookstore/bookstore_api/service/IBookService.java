package br.com.bookstore.bookstore_api.service;

import br.com.bookstore.bookstore_api.application.dto.FindBookCommand;
import br.com.bookstore.bookstore_api.domain.Book;

import java.util.List;

public interface IBookService {

  List<Book> findAll();

  Book findById(Long id);

  List<Book> findByFilter(FindBookCommand command);

  Book save(Book book);

  Book update(Long id, Book book);

  void delete(Long id);
}

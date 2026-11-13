package br.com.bookstore.bookstore_api.service;

import br.com.bookstore.bookstore_api.adapter.out.persistence.BookRepository;
import br.com.bookstore.bookstore_api.adapter.out.persistence.BookSpecs;
import br.com.bookstore.bookstore_api.application.dto.FindBookCommand;
import br.com.bookstore.bookstore_api.application.mapper.BookMapper;
import br.com.bookstore.bookstore_api.domain.Book;
import br.com.bookstore.bookstore_api.exception.LivroNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService implements IBookService {

  private final BookRepository bookRepository;

  public List<Book> findAll() {
    log.debug("Finding all books");
    return bookRepository.findAll()
        .stream()
        .map(BookMapper::toModel)
        .toList();
  }

  public Book findById(final Long id) {
    log.debug("Finding by id {}", id);
    return bookRepository.findById(id)
        .map(BookMapper::toModel)
        .orElseThrow(() -> new LivroNaoEncontradoException(id));
  }

  public List<Book> findByFilter(final FindBookCommand command) {
    log.debug("Finding by filter {}", command);
    final var books = Specification.where(BookSpecs.title(command.title()))
        .and(BookSpecs.author(command.author()))
        .and(BookSpecs.isbn(command.isbn()))
        .and(BookSpecs.price(command.priceMin(), command.priceMax()));
    return BookMapper.toModel(bookRepository.findAll(books));
  }

  public Book save(final Book book) {
    log.debug("save book {}", book);
    final var bookEntity = BookMapper.toEntity(book);
    return BookMapper.toModel(bookRepository.save(bookEntity));
  }

  public Book update(final Long id, final Book book) {
    log.debug("update book {}", book);
    final var isExisting = bookRepository.findById(id)
        .map(BookMapper::toModel)
        .orElseThrow(() -> new LivroNaoEncontradoException(id));
    return save(isExisting);
  }

  public void delete(final Long id) {
    log.debug("delete book {}", id);
    if (!bookRepository.existsById(id)) {
      throw new LivroNaoEncontradoException(id);
    }
    bookRepository.deleteById(id);
  }

}

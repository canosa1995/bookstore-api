package br.com.bookstore.bookstore_api.application.mapper;

import br.com.bookstore.bookstore_api.adapter.in.web.v1.request.FindBookRequest;
import br.com.bookstore.bookstore_api.adapter.in.web.v1.response.FindBookResponse;
import br.com.bookstore.bookstore_api.adapter.out.persistence.BookEntity;
import br.com.bookstore.bookstore_api.application.dto.FindBookCommand;
import br.com.bookstore.bookstore_api.domain.Book;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@UtilityClass
public class BookMapper {

  public static FindBookCommand toCommand(final FindBookRequest command) {
    log.debug("Mapping FindBookRequest to FindBookCommand");
    return new FindBookCommand(
        command.id(),
        command.author(),
        command.title(),
        command.isbn(),
        command.priceMin(),
        command.priceMax()
    );
  }

  public static List<FindBookResponse> toResponse(final List<Book> book) {
    log.debug("Mapping List FindBookResponse to List FindBookResponse");
    return book.stream().map(BookMapper::toResponse).toList();
  }

  public static FindBookResponse toResponse(final Book book) {
    log.debug("Mapping FindBookResponse to FindBookResponse");
    return new FindBookResponse(
        book.id(),
        book.author(),
        book.title(),
        book.isbn(),
        book.price()
    );
  }

  public static List<Book> toModel(final List<BookEntity> entity) {
    log.debug("Mapping List BookEntity to Book");
    return entity.stream().map(BookMapper::toModel).toList();
  }

  public static Book toModel(final BookEntity entity) {
    log.debug("Mapping Book to BookEntity");
    return new Book(
        entity.getId(),
        entity.getAuthor(),
        entity.getTitle(),
        entity.getIsbn(),
        entity.getPrice()
    );
  }

  public static BookEntity toEntity(final Book book) {
    log.debug("Mapping BookEntity to Book");
    return BookEntity.builder()
        .id(book.id())
        .author(book.author())
        .title(book.title())
        .isbn(book.isbn())
        .price(book.price())
        .build();
  }
}

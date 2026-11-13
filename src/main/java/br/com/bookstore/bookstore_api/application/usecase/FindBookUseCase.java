package br.com.bookstore.bookstore_api.application.usecase;

import br.com.bookstore.bookstore_api.application.dto.FindBookCommand;
import br.com.bookstore.bookstore_api.application.port.in.IFindBookUseCase;
import br.com.bookstore.bookstore_api.domain.Book;
import br.com.bookstore.bookstore_api.service.IBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindBookUseCase implements IFindBookUseCase {

  private final IBookService bookService;

  @Override
  public List<Book> execute(final FindBookCommand command) {
    log.debug("Finding all books");
    if (command.id() != null) {
      log.debug("Finding by id");
      return List.of(bookService.findById(command.id()));
    }
    log.debug("Finding by filter");
    return bookService.findByFilter(command);
  }
}

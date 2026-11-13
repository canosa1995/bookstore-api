package br.com.bookstore.bookstore_api.application.port.in;

import br.com.bookstore.bookstore_api.application.dto.FindBookCommand;
import br.com.bookstore.bookstore_api.application.usecase.IUseCase;
import br.com.bookstore.bookstore_api.domain.Book;

import java.util.List;

public interface IFindBookUseCase extends IUseCase<FindBookCommand, List<Book>> {

  List<Book> execute(final FindBookCommand command);
}

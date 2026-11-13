package br.com.bookstore.bookstore_api.adapter.in.web.v1;

import br.com.bookstore.bookstore_api.adapter.in.web.v1.request.FindBookRequest;
import br.com.bookstore.bookstore_api.adapter.in.web.v1.response.FindBookResponse;
import br.com.bookstore.bookstore_api.application.mapper.BookMapper;
import br.com.bookstore.bookstore_api.application.usecase.FindBookUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/book/v1")
@RequiredArgsConstructor
public class BookController {

  private final FindBookUseCase findBookUseCase;

  @GetMapping("/find")
  public ResponseEntity<List<FindBookResponse>> findByFilter(
      final FindBookRequest request
  ) {
    log.debug("Finding by filter");
    final var command = BookMapper.toCommand(request);
    return ResponseEntity.ok(BookMapper.toResponse(findBookUseCase.execute(command)));
  }

}

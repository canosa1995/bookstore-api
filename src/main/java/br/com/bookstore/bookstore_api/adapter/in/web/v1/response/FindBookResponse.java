package br.com.bookstore.bookstore_api.adapter.in.web.v1.response;

import java.math.BigDecimal;

public record FindBookResponse(
    Long id,
    String author,
    String title,
    String isbn,
    BigDecimal price
) {
}

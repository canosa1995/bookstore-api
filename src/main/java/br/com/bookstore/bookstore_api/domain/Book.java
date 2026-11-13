package br.com.bookstore.bookstore_api.domain;

import java.math.BigDecimal;

public record Book(
    Long id,
    String author,
    String title,
    String isbn,
    BigDecimal price) {
}

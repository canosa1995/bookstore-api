package br.com.bookstore.bookstore_api.application.dto;

import java.math.BigDecimal;

public record FindBookCommand(
    Long id,
    String author,
    String title,
    String isbn,
    BigDecimal priceMin,
    BigDecimal priceMax) {
}

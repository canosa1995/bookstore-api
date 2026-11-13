package br.com.bookstore.bookstore_api.adapter.in.web.v1.request;

import java.math.BigDecimal;

public record FindBookRequest(
    Long id,
    String author,
    String title,
    String isbn,
    BigDecimal priceMin,
    BigDecimal priceMax) {
}

package br.com.bookstore.bookstore_api.adapter.out.persistence;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class BookSpecs {

  public static Specification<BookEntity> title(final String title) {
    return (root, query, cb) ->
        title == null ? null : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
  }

  public static Specification<BookEntity> author(final String author) {
    return (root, query, cb) ->
        author == null ? null : cb.greaterThan(root.get("author"), author);
  }

  public static Specification<BookEntity> isbn(final String isbn) {
    return (root, query, cb) ->
        isbn == null ? null : cb.like(cb.lower(root.get("isbn")), "%" + isbn.toLowerCase() + "%");
  }

  public static Specification<BookEntity> price(BigDecimal priceMin, BigDecimal priceMax) {
    return (root, query, cb) -> {
      // Caso 1: Ambos os parâmetros foram informados (BETWEEN)
      if (priceMin != null && priceMax != null) {
        return cb.between(root.get("preco"), priceMin, priceMax);
      }

      // Caso 2: Apenas o preço mínimo foi informado (>= priceMin)
      if (priceMin != null) {
        return cb.greaterThanOrEqualTo(root.get("preco"), priceMin);
      }

      // Caso 3: Apenas o preço máximo foi informado (<= priceMax)
      if (priceMax != null) {
        return cb.lessThanOrEqualTo(root.get("preco"), priceMax);
      }

      // Caso 4: Nenhum foi informado (ignora o filtro)
      return null;
    };
  }
}
package br.com.bookstore.bookstore_api.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK")
public class BookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @NotBlank(message = "O título do livro é obrigatório")
  @Size(max = 200, message = "O título deve ter no máximo 200 caracteres")
  @Column(name = "TITLE", nullable = false, length = 200)
  private String title;

  @NotBlank(message = "O autor do livro é obrigatório")
  @Size(max = 150, message = "O autor deve ter no máximo 150 caracteres")
  @Column(name = "AUTHOR", nullable = false, length = 150)
  private String author;

  @Size(max = 20, message = "O ISBN deve ter no máximo 20 caracteres")
  @Column(name = "ISBN", length = 20)
  private String isbn;

  @PositiveOrZero(message = "O preço do livro deve ser um valor positivo")
  @Column(name = "PRICE", precision = 10, scale = 2)
  private BigDecimal price;

}

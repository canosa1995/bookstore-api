package br.com.bookstore.bookstore_api.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "LIVRO")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "O título do livro é obrigatório")
    @Size(max = 200, message = "O título deve ter no máximo 200 caracteres")
    @Column(name = "TITULO", nullable = false, length = 200)
    private String titulo;

    @NotBlank(message = "O autor do livro é obrigatório")
    @Size(max = 150, message = "O autor deve ter no máximo 150 caracteres")
    @Column(name = "AUTOR", nullable = false, length = 150)
    private String autor;

    @Size (max = 20, message = "O ISBN deve ter no máximo 20 caracteres")
    @Column(name = "ISBN", length = 20)
    private String isbn;

    @PositiveOrZero(message = "O preço do livro deve ser um valor positivo")
    @Column(name = "PRECO", precision = 10, scale = 2)
    private BigDecimal preco;

    public Livro() {

    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getAutor(){
        return autor;
    }

    public void setAutor(String autor){
        this.autor = autor;
    }

    public String getIsbn(){
        return isbn;
    }

    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    public BigDecimal getPreco(){
        return preco;
    }

    public void setPreco(BigDecimal preco){
        this.preco = preco;
    }
}

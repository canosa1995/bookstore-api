package br.com.bookstore.bookstore_api.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends
    JpaRepository<BookEntity, Long>,
    JpaSpecificationExecutor<BookEntity> {

}

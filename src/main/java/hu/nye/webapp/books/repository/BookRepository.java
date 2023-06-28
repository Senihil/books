package hu.nye.webapp.books.repository;

import hu.nye.webapp.books.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}

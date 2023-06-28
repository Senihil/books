package hu.nye.webapp.books.service;

import hu.nye.webapp.books.dto.BookDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookDTO> findAll();

    Optional<BookDTO> findById(Long id);

    BookDTO save(BookDTO bookDTO);

    BookDTO update(BookDTO bookDTO);

    void delete(Long id);

}

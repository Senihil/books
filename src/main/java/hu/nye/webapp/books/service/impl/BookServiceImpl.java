package hu.nye.webapp.books.service.impl;

import hu.nye.webapp.books.dto.BookDTO;
import hu.nye.webapp.books.entity.BookEntity;
import hu.nye.webapp.books.exception.BookNotFoundException;
import hu.nye.webapp.books.repository.BookRepository;
import hu.nye.webapp.books.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;

        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle("Lord of The Rings");
        bookRepository.save(bookEntity);
    }

    @Override
    public List<BookDTO> findAll() {
        List<BookEntity> books = bookRepository.findAll();

        List<BookDTO> result = new ArrayList<>();

        for (BookEntity bookEntity : books) {
            BookDTO bookDTO = modelMapper.map(bookEntity, BookDTO.class);
            result.add(bookDTO);
        }

        return result;
    }

    @Override
    public Optional<BookDTO> findById(Long id) {
        Optional<BookEntity> bookEntityOptional = bookRepository.findById(id);

        Optional<BookDTO> bookDTO = bookEntityOptional.map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class));

        return bookDTO;
    }

    @Override
    public BookDTO save(BookDTO bookDTO) {
        BookEntity bookEntity = modelMapper.map(bookDTO, BookEntity.class);
        bookEntity.setId(null);

        BookEntity savedBook = bookRepository.save(bookEntity);

        return modelMapper.map(savedBook, BookDTO.class);
    }

    @Override
    public BookDTO update(BookDTO bookDTO) {
        Long id = bookDTO.getId();

        boolean existsById = bookRepository.existsById(id);

        if (existsById) {
            BookEntity bookToSave = modelMapper.map(bookDTO, BookEntity.class);
            BookEntity savedBook = bookRepository.save(bookToSave);
            return modelMapper.map(savedBook, BookDTO.class);
        } else {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
    }

    @Override
    public void delete(Long id) {
        Optional<BookEntity> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            bookRepository.delete(optionalBook.get());
        } else {
            throw new BookNotFoundException("Book not found with id: " + id);
        }

    }
}

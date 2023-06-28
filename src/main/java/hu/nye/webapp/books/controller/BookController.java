package hu.nye.webapp.books.controller;

import hu.nye.webapp.books.dto.BookDTO;
import hu.nye.webapp.books.exception.BookValidationException;
import hu.nye.webapp.books.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public List<BookDTO> findAll() {
        List<BookDTO> books = bookService.findAll();
        return books;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<BookDTO> findById(@PathVariable Long id) {
        Optional<BookDTO> bookDTO = bookService.findById(id);

        ResponseEntity<BookDTO> response;

        if (bookDTO.isPresent()) {
            // response = ResponseEntity.status(200).body(bookDTO.get());
            response = ResponseEntity.ok(bookDTO.get());
        } else {
            //response = ResponseEntity.status(404).build();
            response = ResponseEntity.notFound().build();
        }

        return response;
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<BookDTO> save(@RequestBody BookDTO bookDTO) {
        BookDTO savedBook = bookService.save(bookDTO);

        return ResponseEntity.status(201).body(savedBook);
    }

    @RequestMapping(path = "/", method = RequestMethod.PUT)
    public ResponseEntity<BookDTO> update(@Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            List<String> errorList = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errorList.add(fieldError.getDefaultMessage());
            }
            throw new BookValidationException(errorList);
        }

        BookDTO updatedBook = bookService.update(bookDTO);

        return ResponseEntity.status(200).body(updatedBook);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.status(204).build();
    }

}

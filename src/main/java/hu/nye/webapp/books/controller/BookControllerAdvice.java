package hu.nye.webapp.books.controller;

import hu.nye.webapp.books.exception.BookNotFoundException;
import hu.nye.webapp.books.exception.BookValidationException;
import hu.nye.webapp.books.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = BookController.class)
public class BookControllerAdvice {

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<Void> handleBookNotFoundException() {
        return ResponseEntity.status(404).build();
    }

    @ExceptionHandler(value = BookValidationException.class)
    public ResponseEntity<ErrorResponse> handleBookValidationException(BookValidationException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorList());
        return ResponseEntity.status(400).body(errorResponse);
    }

}

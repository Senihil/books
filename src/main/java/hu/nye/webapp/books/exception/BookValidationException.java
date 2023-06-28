package hu.nye.webapp.books.exception;

import java.util.List;

public class BookValidationException extends RuntimeException {

    private List<String> errorList;

    public BookValidationException(List<String> errorList) {
        this.errorList = errorList;
    }

    public List<String> getErrorList() {
        return errorList;
    }
}

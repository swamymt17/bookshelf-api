package com.example.bookapi.service;

import com.example.bookapi.dto.BookDTO;
import java.util.List;

public interface BookService {
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    BookDTO getBookByIsbn(String isbn);
    BookDTO createBook(BookDTO bookDTO);
    BookDTO updateBook(Long id, BookDTO bookDTO);
    void deleteBook(Long id);
    List<BookDTO> searchBooks(String keyword);
    List<BookDTO> getBooksByAuthor(String author);
    List<BookDTO> getBooksByYear(Integer year);
}

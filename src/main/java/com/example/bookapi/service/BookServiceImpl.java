package com.example.bookapi.service;

import com.example.bookapi.dto.BookDTO;
import com.example.bookapi.exception.BookNotFoundException;
import com.example.bookapi.exception.DuplicateIsbnException;
import com.example.bookapi.model.Book;
import com.example.bookapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return toDTO(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        return toDTO(book);
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new DuplicateIsbnException(bookDTO.getIsbn());
        }
        Book book = toEntity(bookDTO);
        Book saved = bookRepository.save(book);
        return toDTO(saved);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        // Only check ISBN conflict if ISBN is being changed
        if (!existing.getIsbn().equals(bookDTO.getIsbn())
                && bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new DuplicateIsbnException(bookDTO.getIsbn());
        }

        existing.setTitle(bookDTO.getTitle());
        existing.setAuthor(bookDTO.getAuthor());
        existing.setIsbn(bookDTO.getIsbn());
        existing.setPrice(bookDTO.getPrice());
        existing.setPublishedYear(bookDTO.getPublishedYear());
        existing.setDescription(bookDTO.getDescription());

        return toDTO(bookRepository.save(existing));
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> searchBooks(String keyword) {
        return bookRepository.searchByKeyword(keyword)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getBooksByYear(Integer year) {
        return bookRepository.findByPublishedYear(year)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // --- Mapper helpers ---

    private BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .publishedYear(book.getPublishedYear())
                .description(book.getDescription())
                .build();
    }

    private Book toEntity(BookDTO dto) {
        return Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .isbn(dto.getIsbn())
                .price(dto.getPrice())
                .publishedYear(dto.getPublishedYear())
                .description(dto.getDescription())
                .build();
    }
}

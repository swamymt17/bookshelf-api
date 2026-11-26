package com.example.bookapi;

import com.example.bookapi.dto.BookDTO;
import com.example.bookapi.exception.BookNotFoundException;
import com.example.bookapi.exception.DuplicateIsbnException;
import com.example.bookapi.model.Book;
import com.example.bookapi.repository.BookRepository;
import com.example.bookapi.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book sampleBook;
    private BookDTO sampleDTO;

    @BeforeEach
    void setUp() {
        sampleBook = Book.builder()
                .id(1L)
                .title("Effective Java")
                .author("Joshua Bloch")
                .isbn("978-0134685991")
                .price(45.99)
                .publishedYear(2018)
                .build();

        sampleDTO = BookDTO.builder()
                .title("Effective Java")
                .author("Joshua Bloch")
                .isbn("978-0134685991")
                .price(45.99)
                .publishedYear(2018)
                .build();
    }

    @Test
    void getAllBooks_returnsAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(sampleBook));
        List<BookDTO> result = bookService.getAllBooks();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Effective Java");
    }

    @Test
    void getBookById_existingId_returnsBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));
        BookDTO result = bookService.getBookById(1L);
        assertThat(result.getTitle()).isEqualTo("Effective Java");
    }

    @Test
    void getBookById_notFound_throwsException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.getBookById(99L))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void createBook_newIsbn_createsSuccessfully() {
        when(bookRepository.existsByIsbn(sampleDTO.getIsbn())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(sampleBook);
        BookDTO result = bookService.createBook(sampleDTO);
        assertThat(result.getIsbn()).isEqualTo("978-0134685991");
    }

    @Test
    void createBook_duplicateIsbn_throwsException() {
        when(bookRepository.existsByIsbn(sampleDTO.getIsbn())).thenReturn(true);
        assertThatThrownBy(() -> bookService.createBook(sampleDTO))
                .isInstanceOf(DuplicateIsbnException.class);
    }

    @Test
    void deleteBook_existingId_deletesSuccessfully() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        assertThatCode(() -> bookService.deleteBook(1L)).doesNotThrowAnyException();
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBook_notFound_throwsException() {
        when(bookRepository.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> bookService.deleteBook(99L))
                .isInstanceOf(BookNotFoundException.class);
    }
}

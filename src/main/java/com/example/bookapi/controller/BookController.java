package com.example.bookapi.controller;

import com.example.bookapi.dto.ApiResponse;
import com.example.bookapi.dto.BookDTO;
import com.example.bookapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // GET /api/v1/books — List all books
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDTO>>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(
                ApiResponse.success(books, "Books retrieved successfully"));
    }

    // GET /api/v1/books/{id} — Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(
                ApiResponse.success(book, "Book retrieved successfully"));
    }

    // GET /api/v1/books/isbn/{isbn} — Get book by ISBN
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<ApiResponse<BookDTO>> getBookByIsbn(@PathVariable String isbn) {
        BookDTO book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(
                ApiResponse.success(book, "Book retrieved successfully"));
    }

    // GET /api/v1/books/search?keyword=java — Search books
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookDTO>>> searchBooks(
            @RequestParam String keyword) {
        List<BookDTO> books = bookService.searchBooks(keyword);
        return ResponseEntity.ok(
                ApiResponse.success(books, "Search results for: " + keyword));
    }

    // GET /api/v1/books/author?name=Bloch — Filter by author
    @GetMapping("/author")
    public ResponseEntity<ApiResponse<List<BookDTO>>> getBooksByAuthor(
            @RequestParam String name) {
        List<BookDTO> books = bookService.getBooksByAuthor(name);
        return ResponseEntity.ok(
                ApiResponse.success(books, "Books by author: " + name));
    }

    // GET /api/v1/books/year/{year} — Filter by publication year
    @GetMapping("/year/{year}")
    public ResponseEntity<ApiResponse<List<BookDTO>>> getBooksByYear(
            @PathVariable Integer year) {
        List<BookDTO> books = bookService.getBooksByYear(year);
        return ResponseEntity.ok(
                ApiResponse.success(books, "Books published in: " + year));
    }

    // POST /api/v1/books — Create a new book
    @PostMapping
    public ResponseEntity<ApiResponse<BookDTO>> createBook(
            @Valid @RequestBody BookDTO bookDTO) {
        BookDTO created = bookService.createBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Book created successfully"));
    }

    // PUT /api/v1/books/{id} — Update book
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO bookDTO) {
        BookDTO updated = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(
                ApiResponse.success(updated, "Book updated successfully"));
    }

    // DELETE /api/v1/books/{id} — Delete book
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(
                ApiResponse.success(null, "Book deleted successfully"));
    }
}

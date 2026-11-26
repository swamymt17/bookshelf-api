package com.example.bookapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 100, message = "Author must not exceed 100 characters")
    @Column(nullable = false)
    private String author;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^[0-9-]{10,17}$", message = "Invalid ISBN format")
    @Column(unique = true, nullable = false)
    private String isbn;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @Min(value = 1000, message = "Invalid publication year")
    @Max(value = 2100, message = "Invalid publication year")
    private Integer publishedYear;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

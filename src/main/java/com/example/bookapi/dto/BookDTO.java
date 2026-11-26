package com.example.bookapi.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 200)
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 100)
    private String author;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^[0-9-]{10,17}$", message = "Invalid ISBN format")
    private String isbn;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;

    private Integer publishedYear;

    @Size(max = 1000)
    private String description;
}

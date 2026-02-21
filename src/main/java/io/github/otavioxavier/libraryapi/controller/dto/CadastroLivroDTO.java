package io.github.otavioxavier.libraryapi.controller.dto;

import io.github.otavioxavier.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @ISBN
        @NotBlank(message = "ISBN é obrigatório")
        String isbn,
        @NotBlank(message = "Título é obrigatório")
        String titulo,
        @NotNull(message = "Data de publicação é obrigatório")
        @Past(message = "Data de publicação não pode ser uma data futura")
        LocalDate dataPublicacao,
        GeneroLivro generoLivro,
        BigDecimal preco,
        @NotNull(message = "Autor é obrigatório")
        UUID idAutor
) {
}

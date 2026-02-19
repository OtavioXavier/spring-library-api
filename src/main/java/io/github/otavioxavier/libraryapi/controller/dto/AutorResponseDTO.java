package io.github.otavioxavier.libraryapi.controller.dto;

import io.github.otavioxavier.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

public record AutorResponseDTO(UUID id, String nome, LocalDate dataNascimento, String nacionalidade) {
}

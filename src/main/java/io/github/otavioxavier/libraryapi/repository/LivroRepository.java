package io.github.otavioxavier.libraryapi.repository;

import io.github.otavioxavier.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
}

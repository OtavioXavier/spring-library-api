package io.github.otavioxavier.libraryapi.repository;

import io.github.otavioxavier.libraryapi.model.Autor;
import io.github.otavioxavier.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    boolean existsByAutor(Autor autor);
}

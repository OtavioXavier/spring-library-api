package io.github.otavioxavier.libraryapi.service;

import io.github.otavioxavier.libraryapi.model.GeneroLivro;
import io.github.otavioxavier.libraryapi.model.Livro;
import io.github.otavioxavier.libraryapi.repository.LivroRepository;
import io.github.otavioxavier.libraryapi.repository.specs.LivroSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.otavioxavier.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    public Livro salvar(Livro livro) {
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Livro livro) {
        repository.delete(livro);
    }

    public List<Livro> pesquisar(
            String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro genero,
            Integer anoPublicacao) {
        Specification<Livro> specs = Specification
                .where((root, query, cb) -> cb.conjunction());

        if(isbn != null && !isbn.isEmpty()) {
            specs = specs.and(isbnEqual(isbn));
        }
        if(genero != null) {
            specs = specs.and(generoEqual(genero));
        }
        if(titulo != null && !titulo.isEmpty()) {
            specs = specs.and(tituloLike(titulo));
        }
        if(anoPublicacao != null) {
            specs = specs.and(anoPublicacapEqual(anoPublicacao));
        }
        if(nomeAutor != null) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }
        return repository.findAll(specs);
    }
}

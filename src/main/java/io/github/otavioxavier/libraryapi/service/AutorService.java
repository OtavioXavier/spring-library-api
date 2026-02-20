package io.github.otavioxavier.libraryapi.service;

import io.github.otavioxavier.libraryapi.model.Autor;
import io.github.otavioxavier.libraryapi.repository.AutorRepository;
import io.github.otavioxavier.libraryapi.validator.AutorTemLivrosValidator;
import io.github.otavioxavier.libraryapi.validator.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final AutorTemLivrosValidator temLivrosValidator;

    public AutorService(AutorRepository repository, AutorValidator validator, AutorTemLivrosValidator temLivrosValidator) {
        this.repository = repository;
        this.validator = validator;
        this.temLivrosValidator = temLivrosValidator;
    }


    public Autor saveAutor(Autor autor) {
        validator.validar(autor);
        return repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Autor autor) {
        temLivrosValidator.validar(autor);
        repository.delete(autor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if (nome != null) {
            return repository.findByNome(nome);
        }

        if (nacionalidade != null) {
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();
    }

    public void atualizar(Autor autor) {
        validator.validar(autor);
        if (autor.getId() == null)
            throw new IllegalArgumentException("Para atualizar é necessário que o autor já esteja salvo na base de dados.");

        repository.save(autor);
    }

}

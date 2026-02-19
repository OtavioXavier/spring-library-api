package io.github.otavioxavier.libraryapi.service;

import io.github.otavioxavier.libraryapi.model.Autor;
import io.github.otavioxavier.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }


    public Autor saveAutor(Autor autor) {
        return repository.save(autor);
    }

}

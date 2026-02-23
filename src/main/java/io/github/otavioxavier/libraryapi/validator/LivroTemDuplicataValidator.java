package io.github.otavioxavier.libraryapi.validator;

import io.github.otavioxavier.libraryapi.exception.RegistroDuplicadoException;
import io.github.otavioxavier.libraryapi.model.Livro;
import io.github.otavioxavier.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LivroTemDuplicataValidator {

    @Autowired
    private LivroRepository repository;

    public void validar(Livro livro) {
        if(existeLivroDuplicado(livro)) throw new RegistroDuplicadoException("Já existe livro com mesmo ISBN");
    }

    private boolean existeLivroDuplicado(Livro livro) {
        return repository.existsByIsbn(livro.getIsbn());

    }

}

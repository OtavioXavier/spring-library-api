package io.github.otavioxavier.libraryapi.validator;

import io.github.otavioxavier.libraryapi.exception.OperacaoNaoPermitidaException;
import io.github.otavioxavier.libraryapi.model.Autor;
import io.github.otavioxavier.libraryapi.repository.AutorRepository;
import io.github.otavioxavier.libraryapi.repository.LivroRepository;
import org.springframework.stereotype.Component;

@Component
public class AutorTemLivrosValidator {

    private final LivroRepository livroRepository;

    public AutorTemLivrosValidator(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void validar(Autor autor) {
        if(possuiLivros(autor)) {
            throw new OperacaoNaoPermitidaException("Não é permitido excluir um autor possui livros cadastrados!");
        }
    }

    private boolean possuiLivros(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}

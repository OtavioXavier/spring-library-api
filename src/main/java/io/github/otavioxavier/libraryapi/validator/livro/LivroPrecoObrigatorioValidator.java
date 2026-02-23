package io.github.otavioxavier.libraryapi.validator.livro;

import io.github.otavioxavier.libraryapi.exception.CampoInvalidoException;
import io.github.otavioxavier.libraryapi.model.Livro;
import io.github.otavioxavier.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LivroPrecoObrigatorioValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    @Autowired
    private LivroRepository repository;

    public void validar(Livro livro) {
        if(precoObrigatorio(livro)) throw new CampoInvalidoException("preco","Já existe livro com mesmo ISBN");
    }

    private boolean precoObrigatorio(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }
}

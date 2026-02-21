package io.github.otavioxavier.libraryapi.service;

import io.github.otavioxavier.libraryapi.model.Livro;
import io.github.otavioxavier.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }
}

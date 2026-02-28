package io.github.otavioxavier.libraryapi.service;

import io.github.otavioxavier.libraryapi.model.Autor;
import io.github.otavioxavier.libraryapi.model.Usuario;
import io.github.otavioxavier.libraryapi.repository.AutorRepository;
import io.github.otavioxavier.libraryapi.security.SecurityService;
import io.github.otavioxavier.libraryapi.validator.autor.AutorTemLivrosValidator;
import io.github.otavioxavier.libraryapi.validator.autor.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final AutorTemLivrosValidator temLivrosValidator;
    private final SecurityService securityService;


    public Autor saveAutor(Autor autor) {
        validator.validar(autor);
        Usuario usuarioLogado = securityService.obterUsuarioLogado();
        autor.setIdUsuario(usuarioLogado.getId());
        return repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Autor autor) {
        temLivrosValidator.validar(autor);
        repository.delete(autor);
    }

    public List<Autor> pesquisarPorExemplo(String nome, String nacionalidade) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);

        return repository.findAll(autorExample);
    }

    public void atualizar(Autor autor) {
        validator.validar(autor);
        if (autor.getId() == null)
            throw new IllegalArgumentException("Para atualizar é necessário que o autor já esteja salvo na base de dados.");

        repository.save(autor);
    }

}

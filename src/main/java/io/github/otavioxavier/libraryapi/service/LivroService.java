package io.github.otavioxavier.libraryapi.service;

import io.github.otavioxavier.libraryapi.model.GeneroLivro;
import io.github.otavioxavier.libraryapi.model.Livro;
import io.github.otavioxavier.libraryapi.model.Usuario;
import io.github.otavioxavier.libraryapi.repository.LivroRepository;
import io.github.otavioxavier.libraryapi.security.SecurityService;
import io.github.otavioxavier.libraryapi.validator.livro.LivroPrecoObrigatorioValidator;
import io.github.otavioxavier.libraryapi.validator.livro.LivroTemDuplicataValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static io.github.otavioxavier.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroTemDuplicataValidator validator;
    private final LivroPrecoObrigatorioValidator precoObrigatorioValidator;
    private final SecurityService securityService;

    public Livro salvar(Livro livro) {
        validator.validar(livro);
       Usuario usuarioLogado = securityService.obterUsuarioLogado();
       livro.setUsuario(usuarioLogado);
        precoObrigatorioValidator.validar(livro);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Livro livro) {
        repository.delete(livro);
    }

    public Page<Livro> pesquisar(
            String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro genero,
            Integer anoPublicacao,
            Integer pagina,
            Integer tamanhoPagina
    ) {
        Specification<Livro> specs = Specification
                .where((root, query, cb) -> cb.conjunction());

        if (isbn != null && !isbn.isEmpty()) {
            specs = specs.and(isbnEqual(isbn));
        }
        if (genero != null) {
            specs = specs.and(generoEqual(genero));
        }
        if (titulo != null && !titulo.isEmpty()) {
            specs = specs.and(tituloLike(titulo));
        }
        if (anoPublicacao != null) {
            specs = specs.and(anoPublicacapEqual(anoPublicacao));
        }
        if (nomeAutor != null) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, pageRequest);
    }

    public void atualizar(Livro livro) {
        validator.validar(livro);
        precoObrigatorioValidator.validar(livro);
        if (livro.getId() == null)
            throw new IllegalArgumentException("Para atualizar é necessário que o livro já esteja salvo na base de dados.");

        repository.save(livro);
    }
}

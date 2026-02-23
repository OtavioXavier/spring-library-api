package io.github.otavioxavier.libraryapi.repository.specs;

import io.github.otavioxavier.libraryapi.model.GeneroLivro;
import io.github.otavioxavier.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(final String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(final String titulo) {
        return (root, query, cb) -> cb.like( cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%");
    }

    public static Specification<Livro> generoEqual(final GeneroLivro genero) {
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacapEqual(final Integer anoPublicacap) {
        return (root, query, cb) ->
                cb.equal(
                        cb.function("to_char", String.class,
                                root.get("dataPublicacao"), cb.literal("YYYY")), anoPublicacap.toString());
    }

    public static Specification<Livro> nomeAutorLike(final String nomeAutor) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("autor").get("nome")), "%" + nomeAutor.toLowerCase() + "%");
    }
}

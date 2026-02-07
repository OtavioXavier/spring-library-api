package io.github.otavioxavier.libraryapi.repository;

import io.github.otavioxavier.libraryapi.model.Autor;
import io.github.otavioxavier.libraryapi.model.GeneroLivro;
import io.github.otavioxavier.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    Autor makeAutor() {
        Autor autor = new Autor();
        autor.setNome("Otavio");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2005, 5, 13));
        autorRepository.save(autor);
        return autor;
    }

    Livro makeLivro() {
        Livro livro = new Livro();
        Autor autor = new Autor();
        autor.setNome("Otavio");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2005, 5, 13));

        livro.setAutor(autor);
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(35));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Percy Jackson: O mar de monstros");
        livro.setDataPublicacao(LocalDate.now());
        return livro;
    }

    @Test
    void deveSalvarLivro() {
        Livro livro = makeLivro();

        var livroSalvo = repository.save(livro);

        assertNotNull(livroSalvo);
        assertEquals(livro.getIsbn(), livroSalvo.getIsbn());
    }

    @Test
    void deveAtualizarLivro() {
        Livro livro = makeLivro();

        var livroSalvo = repository.save(livro);

        livroSalvo.setTitulo("Titulo atualizado");
        var livroAtualizado = repository.save(livroSalvo);

        assertNotNull(livroAtualizado);
        assertEquals(livroAtualizado.getId(), livroSalvo.getId());
        assertEquals("Titulo atualizado", livroAtualizado.getTitulo());
    }

    @Test
    void deveBuscarPorId() {
        Livro livro = makeLivro();

        var livroSalvo = repository.save(livro);

        var livroEncontrado = repository.findById(livroSalvo.getId()).orElse(null);

        assertNotNull(livroEncontrado);
        assertEquals(livroEncontrado, livroSalvo);
    }

    @Test
    void deveListarTodosLivros() {
        Livro livro1 = makeLivro();
        livro1.setAutor(makeAutor());

        Livro livro2 = makeLivro();
        livro2.setIsbn("90887-0000");
        livro2.setAutor(makeAutor());

        repository.save(livro1);
        repository.save(livro2);

        List<Livro> livros = repository.findAll();

        assertNotNull(livros);
        assertEquals(2, livros.size());

        List<UUID> ids = livros.stream()
                .map(Livro::getId)
                .toList();

        assertTrue(ids.contains(livro1.getId()));
        assertTrue(ids.contains(livro2.getId()));
    }

    @Test
    void deveDeletarLivroPorId() {
        Livro livro = makeLivro();
        var livroSalvo = repository.save(livro);
        repository.deleteById(livroSalvo.getId());
        var livroEncontrado = repository.findById(livroSalvo.getId()).orElse(null);
        assertNull(livroEncontrado);
    }


}
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;
    
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


}
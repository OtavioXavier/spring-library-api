package io.github.otavioxavier.libraryapi.repository;

import io.github.otavioxavier.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void deveSalvar() {
        Autor autor = new Autor();
        autor.setNome("Otavio");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2005, 05, 13));

        var autorSalvo = repository.save(autor);
        assertNotNull(autorSalvo);
    }

    @Test
    public void deveAtualizar() {
        Autor autor = new Autor();
        autor.setNome("Otavio");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2005, 05, 13));
        var autorSalvo = repository.save(autor);

        autorSalvo.setNome("nome atualizado");
        var autorAtualizado = repository.save(autorSalvo);

        assertEquals("nome atualizado", autorAtualizado.getNome());
    }

    @Test
    public void deveListar() {
        List<Autor> lista;
        lista = repository.findAll();
        assertNotNull(lista);
    }

    @Test
    public void deveApagarPorId() {
        Autor autor = new Autor();
        autor.setNome("Otavio");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2005, 05, 13));

        var autorSalvo = repository.save(autor);

        repository.deleteById(autorSalvo.getId());

        var deletedUser = repository.findById(autor.getId()).orElse(null);

        assertNull(deletedUser);
    }

}

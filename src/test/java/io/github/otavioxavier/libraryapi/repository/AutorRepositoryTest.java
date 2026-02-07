package io.github.otavioxavier.libraryapi.repository;

import io.github.otavioxavier.libraryapi.model.Autor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        System.out.println("Salvo com sucesso: " + autorSalvo);
    }
}

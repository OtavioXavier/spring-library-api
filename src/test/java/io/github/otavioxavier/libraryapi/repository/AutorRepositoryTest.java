package io.github.otavioxavier.libraryapi.repository;

import io.github.otavioxavier.libraryapi.model.Autor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    public void deveAtualizar() {
        var id = UUID.fromString("c4e16b16-32dd-44aa-a783-4b251fe30210");

        Optional<Autor> optional = repository.findById(id);

        if(optional.isPresent()) {
            Autor autorEncontrado = optional.get();
            System.out.println("Autor encontrado: " + autorEncontrado.getNome());

            autorEncontrado.setNome("Atualiza de verdade");

            repository.save(autorEncontrado);
        }

    }

    @Test
    public void deveListar() {
        List<Autor> lista;
        lista = repository.findAll();
        assertNotNull(lista);
    }

}

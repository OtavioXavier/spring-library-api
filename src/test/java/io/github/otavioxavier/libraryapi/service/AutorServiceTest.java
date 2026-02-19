package io.github.otavioxavier.libraryapi.service;

import io.github.otavioxavier.libraryapi.model.Autor;
import io.github.otavioxavier.libraryapi.repository.AutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {

    @Mock
    private AutorRepository repository;

    @InjectMocks
    private AutorService service;

    @Test
    public void DeveSalvar() {
        Autor autor = new Autor();
        autor.setNome("Jhon V.");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1980, 1, 1));

        when(repository.save(autor)).thenReturn(autor);
        Autor autorSalvo = service.saveAutor(autor);

        assertNotNull(autorSalvo);

    }

    @Test
    public void DeveRetornarAutorSeExistir() {
        Autor autor = new Autor();
        autor.setNome("Jhon V.");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1980, 1, 1));

        service.saveAutor(autor);
        when(repository.findById(autor.getId())).thenReturn(Optional.of(autor));

        Autor autorEncontrado = service.obterPorId(autor.getId()).orElse(null);
        assertNotNull(autorEncontrado);
    }

    @Test
    public void DeveRetornarNullSeNaoExistir() {
        UUID idInexistente = UUID.randomUUID();
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        Autor autorEncontrado = service.obterPorId(idInexistente).orElse(null);
        assertNull(autorEncontrado);
    }
}

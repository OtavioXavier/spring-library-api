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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    public void DeveDeletarAutor() {
        UUID idExistente = UUID.randomUUID();

        service.deletar(idExistente);

        verify(repository).deleteById(idExistente);
    }

    @Test
    public void devePesquisarPorNomeENacionalidade() {
        String nome = "João";
        String nacionalidade = "Brasileiro";

        List<Autor> listaMock = List.of(new Autor());

        when(repository.findByNomeAndNacionalidade(nome, nacionalidade))
                .thenReturn(listaMock);

        List<Autor> resultado = service.pesquisar(nome, nacionalidade);

        assertEquals(1, resultado.size());
        verify(repository).findByNomeAndNacionalidade(nome, nacionalidade);
    }

    @Test
    public void devePesquisarApenasPorNome() {
        String nome = "João";

        List<Autor> listaMock = List.of(new Autor());

        when(repository.findByNome(nome))
                .thenReturn(listaMock);

        List<Autor> resultado = service.pesquisar(nome, null);

        assertEquals(1, resultado.size());
        verify(repository).findByNome(nome);
    }

    @Test
    public void devePesquisarApenasPorNacionalidade() {
        String nacionalidade = "Brasileiro";

        List<Autor> listaMock = List.of(new Autor());

        when(repository.findByNacionalidade(nacionalidade))
                .thenReturn(listaMock);

        List<Autor> resultado = service.pesquisar(null, nacionalidade);

        assertEquals(1, resultado.size());
        verify(repository).findByNacionalidade(nacionalidade);
    }

    @Test
    public void deveRetornarTodosQuandoFiltrosForemNulos() {
        List<Autor> listaMock = List.of(new Autor(), new Autor());

        when(repository.findAll()).thenReturn(listaMock);

        List<Autor> resultado = service.pesquisar(null, null);

        assertEquals(2, resultado.size());
        verify(repository).findAll();
    }
}

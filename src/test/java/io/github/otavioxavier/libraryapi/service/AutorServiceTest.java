package io.github.otavioxavier.libraryapi.service;

import io.github.otavioxavier.libraryapi.exception.OperacaoNaoPermitidaException;
import io.github.otavioxavier.libraryapi.model.Autor;
import io.github.otavioxavier.libraryapi.repository.AutorRepository;
import io.github.otavioxavier.libraryapi.validator.autor.AutorTemLivrosValidator;
import io.github.otavioxavier.libraryapi.validator.autor.AutorValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {

    @Mock
    private AutorRepository repository;

    @Mock
    private AutorValidator validator;

    @Mock
    private AutorTemLivrosValidator temLivrosValidator;

    @InjectMocks
    private AutorService service;

    @Test
    void deveSalvarAutorQuandoValido() {
        Autor autor = new Autor();
        autor.setNome("Jhon V.");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1980, 1, 1));

        when(repository.save(autor)).thenReturn(autor);

        Autor resultado = service.saveAutor(autor);

        verify(validator).validar(autor);
        verify(repository).save(autor);
        assertEquals(autor, resultado);
    }

    @Test
    void deveRetornarAutorQuandoExistir() {
        UUID id = UUID.randomUUID();
        Autor autor = new Autor();
        autor.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(autor));

        Optional<Autor> resultado = service.obterPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(autor, resultado.get());
    }


    @Test
    void deveDeletarAutorQuandoNaoPossuirLivros() {
        Autor autor = new Autor();
        autor.setId(UUID.randomUUID());

        doNothing().when(temLivrosValidator).validar(autor);

        service.deletar(autor);

        verify(temLivrosValidator).validar(autor);
        verify(repository).delete(autor);
    }

    @Test
    void naoDeveDeletarQuandoAutorPossuirLivros() {
        Autor autor = new Autor();
        autor.setId(UUID.randomUUID());

        doThrow(new OperacaoNaoPermitidaException("Erro"))
                .when(temLivrosValidator)
                .validar(autor);

        assertThrows(
                OperacaoNaoPermitidaException.class,
                () -> service.deletar(autor)
        );

        verify(repository, never()).delete(any());
    }

    @Test
    void devePesquisarPorNomeENacionalidade() {
        service.pesquisarPorExemplo("Jhon", "Brasileiro");

        verify(repository).findByNomeAndNacionalidade("Jhon", "Brasileiro");
    }

    @Test
    void devePesquisarApenasPorNome() {
        service.pesquisarPorExemplo("Jhon", null);

        verify(repository).findByNome("Jhon");
    }

    @Test
    void devePesquisarApenasPorNacionalidade() {
        service.pesquisarPorExemplo(null, "Brasileiro");

        verify(repository).findByNacionalidade("Brasileiro");
    }

    @Test
    void devePesquisarTodosQuandoFiltrosNulos() {
        service.pesquisarPorExemplo(null, null);

        verify(repository).findAll();
    }

    @Test
    void deveAtualizarAutorQuandoIdValido() {
        Autor autor = new Autor();
        autor.setId(UUID.randomUUID());

        service.atualizar(autor);

        verify(validator).validar(autor);
        verify(repository).save(autor);
    }

    @Test
    void deveLancarErroQuandoAtualizarSemId() {
        Autor autor = new Autor();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.atualizar(autor)
        );

        verify(repository, never()).save(any());
    }
}

package io.github.otavioxavier.libraryapi.validator;

import io.github.otavioxavier.libraryapi.exception.RegistroDuplicadoException;
import io.github.otavioxavier.libraryapi.model.Autor;
import io.github.otavioxavier.libraryapi.repository.AutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AutorValidatorTest {
    @Mock
    private AutorRepository repository;

    @InjectMocks
    private AutorValidator validator;

    @Test
    void devePermitirNovoAutorQuandoNaoExistirDuplicado() {
        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1990, 1, 1));

        when(repository.findByNomeAndNacionalidadeAndDataNascimento(
                autor.getNome(),
                autor.getNacionalidade(),
                autor.getDataNascimento()
        )).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> validator.validar(autor));
    }

    @Test
    void deveLancarExcecaoQuandoNovoAutorJaExistir() {
        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1990, 1, 1));

        when(repository.findByNomeAndNacionalidadeAndDataNascimento(
                autor.getNome(),
                autor.getNacionalidade(),
                autor.getDataNascimento()
        )).thenReturn(Optional.of(new Autor()));

        assertThrows(
                RegistroDuplicadoException.class,
                () -> validator.validar(autor)
        );
    }

    @Test
    void devePermitirAtualizacaoQuandoForMesmoId() {
        UUID id = UUID.randomUUID();

        Autor autor = new Autor();
        autor.setId(id);
        autor.setNome("João");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1990, 1, 1));

        Autor autorBanco = new Autor();
        autorBanco.setId(id);

        when(repository.findByNomeAndNacionalidadeAndDataNascimento(
                autor.getNome(),
                autor.getNacionalidade(),
                autor.getDataNascimento()
        )).thenReturn(Optional.of(autorBanco));

        assertDoesNotThrow(() -> validator.validar(autor));
    }
}

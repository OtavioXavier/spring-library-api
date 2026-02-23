package io.github.otavioxavier.libraryapi.validator;

import io.github.otavioxavier.libraryapi.exception.OperacaoNaoPermitidaException;
import io.github.otavioxavier.libraryapi.model.Autor;
import io.github.otavioxavier.libraryapi.repository.LivroRepository;
import io.github.otavioxavier.libraryapi.validator.autor.AutorTemLivrosValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AutorTemLivrosValidatorTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private AutorTemLivrosValidator validator;

    @Test
    void devePermitirExclusaoQuandoNaoPossuirLivros() {
        Autor autor = new Autor();
        autor.setId(UUID.randomUUID());

        when(livroRepository.existsByAutor(autor)).thenReturn(false);

        assertDoesNotThrow(() -> validator.validar(autor));
    }

    @Test
    void deveLancarExcecaoQuandoPossuirLivros() {
        Autor autor = new Autor();
        autor.setId(UUID.randomUUID());

        when(livroRepository.existsByAutor(autor)).thenReturn(true);

        assertThrows(
                OperacaoNaoPermitidaException.class,
                () -> validator.validar(autor)
        );

        verify(livroRepository).existsByAutor(autor);
    }
}

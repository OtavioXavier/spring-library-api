package io.github.otavioxavier.libraryapi.validator;

import io.github.otavioxavier.libraryapi.exception.RegistroDuplicadoException;
import io.github.otavioxavier.libraryapi.model.Autor;
import io.github.otavioxavier.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private final AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor) {
        if(existe(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }

    private boolean existe(Autor autor) {
        Optional<Autor> autorEncontrado = autorRepository.findByNomeAndNacionalidadeAndDataNascimento(autor.getNome(), autor.getNacionalidade(), autor.getDataNascimento());
        if(autor.getId() == null) {
            return autorEncontrado.isPresent();
        }

        return autorEncontrado.isPresent() && !autor.getId().equals(autorEncontrado.get().getId());
    }
}

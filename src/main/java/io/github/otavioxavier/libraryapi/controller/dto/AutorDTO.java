package io.github.otavioxavier.libraryapi.controller.dto;

import io.github.otavioxavier.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AutorDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 100, message = "Tamanho válido de nome: 2 ˜ 100")
        String nome,
        @NotNull(message = "Data de nascimento é obrigatório")
        @Past(message = "Deve ser uma data do passado")
        LocalDate dataNascimento,
        @NotBlank(message = "Nacionalidade é obrigatório")
        @Size(min = 2, max = 50, message = "Tamanho válido da nacionalidade: 2 ˜ 50")
        String nacionalidade) {

    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}

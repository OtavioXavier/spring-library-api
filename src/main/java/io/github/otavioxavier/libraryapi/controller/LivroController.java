package io.github.otavioxavier.libraryapi.controller;

import io.github.otavioxavier.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.otavioxavier.libraryapi.controller.error.ErroResposta;
import io.github.otavioxavier.libraryapi.controller.mapper.LivroMapper;
import io.github.otavioxavier.libraryapi.exception.RegistroDuplicadoException;
import io.github.otavioxavier.libraryapi.model.Livro;
import io.github.otavioxavier.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {
            Livro livro = livroMapper.toEntity(dto);
            service.salvar(livro);
            URI location = generateHeaderLocation(livro.getId());
            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
           var erroDTO =  ErroResposta.conflito(e.getMessage());
           return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

}

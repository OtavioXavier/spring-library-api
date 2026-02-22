package io.github.otavioxavier.libraryapi.controller;

import io.github.otavioxavier.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.otavioxavier.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.otavioxavier.libraryapi.controller.mapper.LivroMapper;
import io.github.otavioxavier.libraryapi.model.Livro;
import io.github.otavioxavier.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = livroMapper.toEntity(dto);
        service.salvar(livro);
        URI location = generateHeaderLocation(livro.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    ResultadoPesquisaLivroDTO dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

}

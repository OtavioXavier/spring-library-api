package io.github.otavioxavier.libraryapi.controller;

import io.github.otavioxavier.libraryapi.controller.dto.AutorDTO;
import io.github.otavioxavier.libraryapi.controller.dto.AutorResponseDTO;
import io.github.otavioxavier.libraryapi.model.Autor;
import io.github.otavioxavier.libraryapi.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService service;

    @PostMapping
    public ResponseEntity<Void> createAutor(@RequestBody AutorDTO dto) {
        Autor autor = service.saveAutor(dto.mapearParaAutor());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autor.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorResponseDTO> obterDetalhes(@PathVariable String id) {
        UUID autorId = UUID.fromString(id);
        Autor autor = service.obterPorId(autorId).orElse(null);

        if(autor != null) {
            AutorResponseDTO dto = new AutorResponseDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        UUID autorId = UUID.fromString(id);
        Autor autor = service.obterPorId(autorId).orElse(null);

        if(autor != null) {
            service.deletar(autorId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> listar(
            @PathVariable(value = "nome", required = false) String nome,
            @PathVariable(value = "nacionalidade", required = false) String nacionalidade
    ) {
        List<Autor> list = service.pesquisar(nome, nacionalidade);
        List<AutorResponseDTO> listDTO = list
                .stream()
                .map(autor -> new AutorResponseDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listDTO);
    }
}

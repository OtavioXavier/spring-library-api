package io.github.otavioxavier.libraryapi.controller;

import io.github.otavioxavier.libraryapi.controller.dto.AutorDTO;
import io.github.otavioxavier.libraryapi.model.Autor;
import io.github.otavioxavier.libraryapi.repository.AutorRepository;
import io.github.otavioxavier.libraryapi.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
}

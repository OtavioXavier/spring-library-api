package io.github.otavioxavier.libraryapi.controller;

import io.github.otavioxavier.libraryapi.controller.dto.AutorDTO;
import io.github.otavioxavier.libraryapi.controller.dto.AutorResponseDTO;
import io.github.otavioxavier.libraryapi.controller.mapper.AutorMapper;
import io.github.otavioxavier.libraryapi.model.Autor;
import io.github.otavioxavier.libraryapi.model.Usuario;
import io.github.otavioxavier.libraryapi.security.SecurityService;
import io.github.otavioxavier.libraryapi.service.AutorService;
import io.github.otavioxavier.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE')")
    public ResponseEntity<Void> createAutor(@RequestBody @Valid AutorDTO dto, Authentication auth) {
        Autor autor = mapper.toEntity(dto);
        service.saveAutor(autor);
        URI location = generateHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<AutorResponseDTO> obterDetalhes(@PathVariable String id) {
        UUID autorId = UUID.fromString(id);
        return service.obterPorId(autorId)
                .map(autor -> {
                    AutorResponseDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        UUID autorId = UUID.fromString(id);
        Autor autor = service.obterPorId(autorId).orElse(null);
        if (autor != null) {
            service.deletar(autor);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<AutorResponseDTO>> listar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ) {
        List<Autor> list = service.pesquisarPorExemplo(nome, nacionalidade);
        List<AutorResponseDTO> listDTO = list
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listDTO);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('GERENTE')")
    public ResponseEntity<Void> editar(@PathVariable String id, @RequestBody @Valid AutorDTO dto) {
        UUID autorId = UUID.fromString(id);
        Autor autor = service.obterPorId(autorId).orElse(null);
        if (autor == null)
            return ResponseEntity.notFound().build();
        Autor autorAtualizado = mapper.toEntity(dto);
        service.atualizar(autorAtualizado);
        return ResponseEntity.noContent().build();
    }
}

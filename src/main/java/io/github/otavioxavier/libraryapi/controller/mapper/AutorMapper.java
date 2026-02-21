package io.github.otavioxavier.libraryapi.controller.mapper;

import io.github.otavioxavier.libraryapi.controller.dto.AutorDTO;
import io.github.otavioxavier.libraryapi.controller.dto.AutorResponseDTO;
import io.github.otavioxavier.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {
    Autor toEntity(AutorDTO dto);

    AutorResponseDTO toDTO(Autor autor);
}
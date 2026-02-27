package io.github.otavioxavier.libraryapi.controller.mapper;

import io.github.otavioxavier.libraryapi.controller.dto.UsuarioDTO;
import io.github.otavioxavier.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}

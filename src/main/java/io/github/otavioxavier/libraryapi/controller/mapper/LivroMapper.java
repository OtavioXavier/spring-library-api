package io.github.otavioxavier.libraryapi.controller.mapper;

import io.github.otavioxavier.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.otavioxavier.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.otavioxavier.libraryapi.model.Livro;
import io.github.otavioxavier.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "autor", expression = "java( autorRepository.findById( dto.idAutor() ).orElse( null ) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}

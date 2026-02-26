package io.github.otavioxavier.libraryapi.service;

import io.github.otavioxavier.libraryapi.model.Usuario;
import io.github.otavioxavier.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void salvar(Usuario usuario) {
        var senha = usuario.getSenha();
        usuario.setSenha(passwordEncoder.encode(senha));
        repository.save(usuario);
    }


}

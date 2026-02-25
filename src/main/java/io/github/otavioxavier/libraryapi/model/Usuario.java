package io.github.otavioxavier.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private int id;

    private String login;
    private String password;

    @Column(name = "roles")
    private List<String> roles;
}

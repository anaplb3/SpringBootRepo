package com.example.roteiro1.model;

public class UsuarioDTO {

    private String email;
    private String nome;

    public UsuarioDTO(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }

    public static UsuarioDTO fromUsuario(Usuario usuario) {
        return new UsuarioDTO(usuario.getEmail(), usuario.getNome());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

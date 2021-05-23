package com.example.roteiro1.service;

import com.example.roteiro1.model.Usuario;
import com.example.roteiro1.model.UsuarioDTO;
import com.example.roteiro1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthService authService;

    public Usuario getUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException();
        }
        return usuario;
    }

    public UsuarioDTO addUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()) == null) {
            usuarioRepository.save(usuario);
            return UsuarioDTO.fromUsuario(usuario);
        } else {
            return null;
        }
    }

    public boolean validaUsuario(Usuario usuario) {
        Usuario usuarioBusca = usuarioRepository.findByEmail(usuario.getEmail());
        return usuarioBusca != null && usuarioBusca.getSenha().equals(usuario.getSenha());
    }

    public boolean validaPermissao(String authHeader, String email) {
        String subject = authService.getToken(authHeader);
        Usuario usuario = getUsuario(subject);
        return usuario != null && usuario.getEmail().equals(email);
    }

    public Usuario removeUsuario(String email, String authHeader) throws ServletException {
        Usuario usuario = getUsuario(email);
        if (validaPermissao(authHeader, email)) {
            usuarioRepository.delete(usuario);
            return usuario;
        }

        throw new ServletException("Usuário não tem permissão!");
    }
}

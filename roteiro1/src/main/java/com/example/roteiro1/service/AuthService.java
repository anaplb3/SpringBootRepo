package com.example.roteiro1.service;

import com.example.roteiro1.filter.Filter;
import com.example.roteiro1.model.Login;
import com.example.roteiro1.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {
    public final static String SECRET_KEY_TOKEN = "nickiminaj";
    @Autowired
    private UsuarioService usuarioService;

    public Login autentica(Usuario usuario) {
        return usuarioService.validaUsuario(usuario) ? new Login(usuario.getNome(), usuario.getEmail(), geraToken(usuario.getEmail())) : null;
    }

    private String geraToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY_TOKEN)
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)).compact();
    }

    public String getToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new SecurityException("Token não é válido!");
        }
        String token = authHeader.substring(Filter.TOKEN_INDEX);
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY_TOKEN)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (SignatureException e) {
            throw new SecurityException("Token não é válido!");
        }
    }
}

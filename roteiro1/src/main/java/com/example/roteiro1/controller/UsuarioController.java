package com.example.roteiro1.controller;

import com.example.roteiro1.model.Usuario;
import com.example.roteiro1.model.UsuarioDTO;
import com.example.roteiro1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/v1/api/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping()
    public ResponseEntity<?> addUsuario(@RequestBody Usuario usuario) {

        UsuarioDTO usuarioDTO = usuarioService.addUsuario(usuario);
        return usuarioDTO == null ? new ResponseEntity<>("Usuário já existe!", HttpStatus.ALREADY_REPORTED) : new ResponseEntity<>(usuarioDTO, HttpStatus.CREATED);

    }

    @DeleteMapping("/auth/{email}")
    public ResponseEntity<?> removeUsuario(@PathVariable String email, @RequestHeader("Authorization") String header) {
        try {
            return new ResponseEntity<Usuario>(usuarioService.removeUsuario(email, header), HttpStatus.OK);
        } catch (ServletException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}

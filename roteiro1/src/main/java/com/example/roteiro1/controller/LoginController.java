package com.example.roteiro1.controller;

import com.example.roteiro1.model.Login;
import com.example.roteiro1.model.Usuario;
import com.example.roteiro1.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth")
public class LoginController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> autentica(@RequestBody Usuario usuario) {
        Login login = authService.autentica(usuario);

        return login == null
                ? new ResponseEntity<>("Email ou senha inválidos, login não realizado.", HttpStatus.UNAUTHORIZED)
                : new ResponseEntity<>(login, HttpStatus.OK);
    }
}

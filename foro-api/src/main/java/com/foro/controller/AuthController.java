package com.foro.controller;

import com.foro.dto.JwtResponse;
import com.foro.dto.LoginRequest;
import com.foro.dto.RegisterRequest;
import com.foro.model.Usuario;
import com.foro.security.JwtUtil;
import com.foro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        Usuario u = usuarioService.register(req.getNombre(), req.getEmail(), req.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(u);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return usuarioService.findByEmail(req.getEmail())
                .filter(u -> usuarioService.checkPassword(u, req.getPassword()))
                .map(u -> ResponseEntity.ok(new JwtResponse(jwtUtil.generateToken(u.getEmail()))))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}

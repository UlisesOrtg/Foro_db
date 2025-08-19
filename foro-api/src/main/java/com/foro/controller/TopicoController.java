package com.foro.controller;

import com.foro.dto.CrearTopicoRequest;
import com.foro.model.Topico;
import com.foro.model.Usuario;
import com.foro.repository.TopicoRepository;
import com.foro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public Page<Topico> listar(Pageable pageable) {
        return topicoRepository.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CrearTopicoRequest req) {
        Usuario u = usuarioRepository.findById(req.getUsuarioId())
                .orElse(null);
        if (u == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado");
        }
        Topico t = new Topico(req.getTitulo(), req.getMensaje(), req.getCurso(), u);
        Topico saved = topicoRepository.save(t);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody CrearTopicoRequest req) {
        return topicoRepository.findById(id).map(t -> {
            if (req.getTitulo() != null) t.setTitulo(req.getTitulo());
            if (req.getMensaje() != null) t.setMensaje(req.getMensaje());
            if (req.getCurso() != null) t.setCurso(req.getCurso());
            if (req.getUsuarioId() != null) {
                Usuario u = usuarioRepository.findById(req.getUsuarioId()).orElse(null);
                if (u == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado");
                t.setUsuario(u);
            }
            Topico updated = topicoRepository.save(t);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }
    

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        topicoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

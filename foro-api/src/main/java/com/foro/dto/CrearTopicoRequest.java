package com.foro.dto;

public class CrearTopicoRequest {
    private String titulo;
    private String mensaje;
    private String curso;
    private Long usuarioId;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}

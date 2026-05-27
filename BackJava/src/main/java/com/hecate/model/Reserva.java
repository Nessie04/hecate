package com.hecate.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHora;
    private String mesaSolicitada;
    private String experienciaEsoterica;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    public Reserva() {}

    public Reserva(LocalDateTime fechaHora, String mesaSolicitada, String experienciaEsoterica, Usuario usuario) {
        this.fechaHora = fechaHora;
        this.mesaSolicitada = mesaSolicitada;
        this.experienciaEsoterica = experienciaEsoterica;
        this.usuario = usuario;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getMesaSolicitada() { return mesaSolicitada; }
    public void setMesaSolicitada(String mesaSolicitada) { this.mesaSolicitada = mesaSolicitada; }
    public String getExperienciaEsoterica() { return experienciaEsoterica; }
    public void setExperienciaEsoterica(String experienciaEsoterica) { this.experienciaEsoterica = experienciaEsoterica; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
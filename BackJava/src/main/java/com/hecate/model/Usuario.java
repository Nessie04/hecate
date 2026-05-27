<<<<<<< HEAD
package com.hecate.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    
    @Column(unique = true, nullable = false) // Evita que se repitan emails en el aquelarre
    private String email;
    
    private String password; // <-- Nuevo campo para el Login
    private String signoZodiacal;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Reserva> reservas;

    // Constructores
    public Usuario() {}

    public Usuario(String nombre, String email, String password, String signoZodiacal) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.signoZodiacal = signoZodiacal;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getSignoZodiacal() { return signoZodiacal; }
    public void setSignoZodiacal(String signoZodiacal) { this.signoZodiacal = signoZodiacal; }
    public List<Reserva> getReservas() { return reservas; }
    public void setReservas(List<Reserva> reservas) { this.reservas = reservas; }
=======
package com.hecate.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String signoZodiacal;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Reserva> reservas;

    public Usuario() {}

    public Usuario(String nombre, String email, String signoZodiacal) {
        this.nombre = nombre;
        this.email = email;
        this.signoZodiacal = signoZodiacal;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSignoZodiacal() { return signoZodiacal; }
    public void setSignoZodiacal(String signoZodiacal) { this.signoZodiacal = signoZodiacal; }
    public List<Reserva> getReservas() { return reservas; }
    public void setReservas(List<Reserva> reservas) { this.reservas = reservas; }
>>>>>>> 8689bf185cc0758aed9cd71df4667622986fdfbd
}
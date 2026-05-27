package com.hecate.controller;

import com.hecate.model.Usuario;
import com.hecate.model.Reserva;
import com.hecate.repository.UsuarioRepository;
import com.hecate.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Clave para conectar con Angular sin problemas de CORS
public class HecateController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    // 1. LISTAR todos los usuarios
    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // 2. VER DETALLE de un usuario
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREAR una nueva reserva asignada a un usuario
    @PostMapping("/reservas")
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva nuevaReserva) {
        if (nuevaReserva.getUsuario() == null || nuevaReserva.getUsuario().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Validar que el usuario que intenta reservar existe en la BBDD
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(nuevaReserva.getUsuario().getId());
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        nuevaReserva.setUsuario(usuarioOptional.get());
        Reserva reservaGuardada = reservaRepository.save(nuevaReserva);
        return ResponseEntity.ok(reservaGuardada);
    }

    // 4. CONSULTAR RELACIÓN: Obtener todas las reservas de un usuario concreto
    @GetMapping("/usuarios/{usuarioId}/reservas")
    public ResponseEntity<List<Reserva>> obtenerReservasPorUsuario(@PathVariable Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            return ResponseEntity.notFound().build();
        }
        List<Reserva> reservas = reservaRepository.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(reservas);
    }
}
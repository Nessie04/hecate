<<<<<<< HEAD
package com.hecate.controller;

import com.hecate.model.Usuario;
import com.hecate.model.Reserva;
import com.hecate.repository.UsuarioRepository;
import com.hecate.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Permite la comunicación fluida con Angular
public class HecateController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    // ==========================================
    // SISTEMA DE AUTENTICACIÓN (REGISTRO Y LOGIN)
    // ==========================================

    // 1. REGISTRAR UN NUEVO USUARIO
    @PostMapping("/auth/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario nuevoUsuario) {
        // Verificar si el email ya está cogido en el sistema
        if (usuarioRepository.findByEmail(nuevoUsuario.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El email ya está registrado en La Guarida.");
        }
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
    }

    // 2. INICIAR SESIÓN (LOGIN)
    @PostMapping("/auth/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Usuario credenciales) {
        // Buscar al usuario por el email enviado
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(credenciales.getEmail());
        
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas (Email no encontrado).");
        }

        Usuario usuario = usuarioOpt.get();
        
        // Comprobar si la contraseña coincide (en un entorno real usaríamos hashing/BCrypt)
        if (!usuario.getPassword().equals(credenciales.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas (Contraseña errónea).");
        }

        // Si todo va bien, devolvemos el usuario completo (incluyendo su ID)
        return ResponseEntity.ok(usuario);
    }

    // ==========================================
    // GESTIÓN DE RESERVAS Y RELACIÓN 1:M
    // ==========================================

    // 3. CREAR RESERVA ASOCIADA A UN USUARIO
    @PostMapping("/reservas")
    public ResponseEntity<?> crearReserva(@RequestBody Reserva nuevaReserva) {
        if (nuevaReserva.getUsuario() == null || nuevaReserva.getUsuario().getId() == null) {
            return ResponseEntity.badRequest().body("La reserva debe estar vinculada a un usuario existente.");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(nuevaReserva.getUsuario().getId());
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        nuevaReserva.setUsuario(usuarioOptional.get());
        Reserva reservaGuardada = reservaRepository.save(nuevaReserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaGuardada);
    }

    // 4. CONSULTAR RESERVAS DE UN USUARIO CONCRETO
    @GetMapping("/usuarios/{usuarioId}/reservas")
    public ResponseEntity<List<Reserva>> obtenerReservasPorUsuario(@PathVariable Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            return ResponseEntity.notFound().build();
        }
        List<Reserva> reservas = reservaRepository.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(reservas);
    }
=======
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
>>>>>>> 8689bf185cc0758aed9cd71df4667622986fdfbd
}
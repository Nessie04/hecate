<<<<<<< HEAD
package com.hecate.repository;

import com.hecate.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Busca un usuario por su email para comprobar si existe al iniciar sesión
    Optional<Usuario> findByEmail(String email);
}
=======
package com.hecate.repository;

import com.hecate.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
>>>>>>> 8689bf185cc0758aed9cd71df4667622986fdfbd

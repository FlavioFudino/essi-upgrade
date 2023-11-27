package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
    UsuarioModel findFirstByUsername(String username);
    UsuarioModel findFirstByIdUsuario(Integer idUsuario);
}

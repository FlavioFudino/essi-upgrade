package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.UsuarioRolModel;
import gob.pe.essalud.trx.jpa.model.UsuarioRolPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRolModel, UsuarioRolPk> {
    UsuarioRolModel findByIdUsuarioAndIdRol(Integer idUsuario, Integer idRol);

    boolean existsByIdUsuarioAndIdRol(Integer idUsuario, Integer idRol);
}

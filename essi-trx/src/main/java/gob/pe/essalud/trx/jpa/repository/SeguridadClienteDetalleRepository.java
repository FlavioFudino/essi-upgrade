package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.SeguridadClienteDetalleModel;
import gob.pe.essalud.trx.jpa.model.key.SeguridadClienteDetalleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguridadClienteDetalleRepository extends JpaRepository<SeguridadClienteDetalleModel, SeguridadClienteDetalleKey> {

}

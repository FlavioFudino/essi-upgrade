package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.SeguridadClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguridadClienteRepository extends JpaRepository<SeguridadClienteModel, Long> {
    SeguridadClienteModel findByIpCliente(String ipCliente);
}

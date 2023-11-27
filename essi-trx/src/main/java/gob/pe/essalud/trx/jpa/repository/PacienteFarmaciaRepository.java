package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.PacienteFarmaciaModel;
import gob.pe.essalud.trx.jpa.model.PacienteFarmaciaPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteFarmaciaRepository extends JpaRepository<PacienteFarmaciaModel, PacienteFarmaciaPk> {
    PacienteFarmaciaModel findTopByIdPaciente(Integer idPaciente);
}

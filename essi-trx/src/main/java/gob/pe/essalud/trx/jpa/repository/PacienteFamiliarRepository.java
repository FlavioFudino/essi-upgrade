package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.PacienteFamiliarModel;
import gob.pe.essalud.trx.jpa.model.PacienteFamiliarPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteFamiliarRepository extends JpaRepository<PacienteFamiliarModel, PacienteFamiliarPk> {
    PacienteFamiliarModel findTopByIdPaciente(Integer idPaciente);

    PacienteFamiliarModel findTopByIdPacienteAndEstado(Integer idPaciente, String estado);
}

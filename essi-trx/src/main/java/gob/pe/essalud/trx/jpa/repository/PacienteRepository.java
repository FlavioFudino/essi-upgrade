package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteModel, Integer> {

    Optional<PacienteModel> findFirstByIdPaciente(Integer idPaciente);

}

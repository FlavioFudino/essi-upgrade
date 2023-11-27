package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.DireccionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionRepository extends JpaRepository<DireccionModel, Integer> {
    DireccionModel findTopByIdPersona(Integer idPersona);
}

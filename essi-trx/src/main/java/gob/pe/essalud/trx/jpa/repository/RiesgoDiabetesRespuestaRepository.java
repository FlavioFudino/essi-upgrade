package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.RiesgoDiabetesRespuestaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiesgoDiabetesRespuestaRepository extends JpaRepository<RiesgoDiabetesRespuestaModel, Long> {
}

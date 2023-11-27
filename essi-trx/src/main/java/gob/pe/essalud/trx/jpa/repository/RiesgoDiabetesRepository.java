package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.RiesgoDiabetesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiesgoDiabetesRepository extends JpaRepository<RiesgoDiabetesModel, Long> {

}

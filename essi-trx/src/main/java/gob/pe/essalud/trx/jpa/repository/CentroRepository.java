package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.CentroModel;
import gob.pe.essalud.trx.jpa.model.key.CentroKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroRepository extends JpaRepository<CentroModel, CentroKey> {
    CentroModel findFirstByCenAsiCod(String cenAsiCod);
}

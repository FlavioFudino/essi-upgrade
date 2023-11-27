package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.CitaModel;
import gob.pe.essalud.trx.jpa.model.key.CitaKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<CitaModel, CitaKey> {
    List<CitaModel> findAllByTipoDocIdentAndNumeroDocIdent(String tipoDocIdent, String numeroDocIdent);
}

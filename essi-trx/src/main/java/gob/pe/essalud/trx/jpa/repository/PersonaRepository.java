package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.PersonaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaModel, Integer> {
    Optional<PersonaModel> findByNumeroDocIdent(String numDocumento);

    Optional<PersonaModel> findByNumeroDocIdentAndTipo(String numDocumento, Integer tipo);

    PersonaModel getByTipoDocIdentAndNumeroDocIdentAndTipo(String tipoDoc, String numeroDocIdent, Integer tipo);

    PersonaModel getByTipoDocIdentAndNumeroDocIdentAndTipoAndIdUsuarioRegistra(String tipoDoc, String numeroDocIdent, Integer tipo, Integer idUsuarioRegistra);

    PersonaModel getByNumeroDocIdentAndTipo(String numerdoDocIdent, Integer tipo);
}

package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.FamiliarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamiliarRepository extends JpaRepository<FamiliarModel, Integer> {
    FamiliarModel findByIdFamiliar(Integer idFamiliar);
}

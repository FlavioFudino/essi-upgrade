package gob.pe.essalud.trx.jpa.repository;

import gob.pe.essalud.trx.jpa.model.FarmaciaModel;
import gob.pe.essalud.trx.jpa.model.FarmaciaModelPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmaciaRepository extends JpaRepository<FarmaciaModel, FarmaciaModelPk> {
    FarmaciaModel findTopByIdFarmacia(String idFarmacia);
}

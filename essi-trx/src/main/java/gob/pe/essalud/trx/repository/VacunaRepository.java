package gob.pe.essalud.trx.repository;

import gob.pe.essalud.trx.dto.VacunaDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VacunaRepository {
    List<VacunaDto> findAllByCriteria(Map criteria);

    List<VacunaDto> findParamsByCriteria(Map criteria);
}

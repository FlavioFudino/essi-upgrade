package gob.pe.essalud.trx.repository;


import gob.pe.essalud.trx.dto.FamiliarPacienteRequestDto;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface FamiliarMyRepository {
    FamiliarPacienteRequestDto getDireccionFamiliar(Map params);
}
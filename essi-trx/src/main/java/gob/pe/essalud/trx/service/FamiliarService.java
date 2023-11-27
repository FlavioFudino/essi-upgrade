package gob.pe.essalud.trx.service;

import gob.pe.essalud.trx.dto.FamiliarPacienteRequestDto;

public interface FamiliarService {
    FamiliarPacienteRequestDto save(FamiliarPacienteRequestDto model);

    FamiliarPacienteRequestDto get(String tipoDocumento, String numeroDocumento);
}

package gob.pe.essalud.client.service;

import gob.pe.essalud.client.dto.familiar.FamiliarPacienteRequestDto;

import java.util.Map;

public interface FamiliarService {
    Map get(String tipoDocumento, String numeroDocumento);

    FamiliarPacienteRequestDto save(FamiliarPacienteRequestDto familiar);
}

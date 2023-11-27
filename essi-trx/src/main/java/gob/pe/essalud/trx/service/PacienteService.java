package gob.pe.essalud.trx.service;

import gob.pe.essalud.trx.dto.*;
import gob.pe.essalud.trx.dto.paciente.GetRiesgoDiabetesResponseDto;
import gob.pe.essalud.trx.dto.paciente.RiesgoDiabetesEvaluarRequestDto;
import gob.pe.essalud.trx.dto.paciente.RiesgoDiabetesEvaluarResponseDto;

public interface PacienteService {
    PacienteRequestDto save(PacienteRequestDto pacienteRequestDto);

    PacienteRequestDto getDireccionFarmacia(String tipoDocumento, String numeroDocumento);

    PacienteDto getPacienteByNumero(String numeroDocumento);

    PacienteAseguradoDto getPacienteAsegurado(PacienteAseguradoRequestDto input);

    boolean updateCentroPaciente(UpdateCentroPacienteRequestDto input);

    GetRiesgoDiabetesResponseDto getRiesgoDiabetes(RequestGenericDto input);

    RiesgoDiabetesEvaluarResponseDto riesgoDiabetesEvaluar(RiesgoDiabetesEvaluarRequestDto input);
}

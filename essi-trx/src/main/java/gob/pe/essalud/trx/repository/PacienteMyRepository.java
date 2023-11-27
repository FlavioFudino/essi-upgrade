package gob.pe.essalud.trx.repository;


import gob.pe.essalud.trx.dto.PacienteAseguradoDto;
import gob.pe.essalud.trx.dto.PacienteDto;
import gob.pe.essalud.trx.dto.PacienteRequestDto;
import gob.pe.essalud.trx.dto.paciente.GetRiesgoDiabetesDto;
import gob.pe.essalud.trx.dto.paciente.GetRiesgoDiabetesRespuestaDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PacienteMyRepository {
    PacienteRequestDto getDireccionFarmacia(Map params);

    PacienteDto getPacienteByNumero(Map params);

    PacienteAseguradoDto getPacienteAsegurado(Map params);

    GetRiesgoDiabetesDto getRiesgoDiabetes(int idPersona);

    List<GetRiesgoDiabetesRespuestaDto> getRiesgoDiabetesRespuestas(Long idRiesgoDiabetes);
}
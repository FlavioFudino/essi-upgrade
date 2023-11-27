package gob.pe.essalud.client.service;

import gob.pe.essalud.client.dto.ConsultasDto;
import gob.pe.essalud.client.dto.RequestGenericDto;
import gob.pe.essalud.client.dto.consulta.ConsultaEmergenciaDto;
import gob.pe.essalud.client.dto.essi.EssiResponseDto;
import gob.pe.essalud.client.dto.essi_consulta.*;

import java.util.List;
import java.util.Map;

public interface ConsultasService {
    Map consultaExterna(ConsultasDto paramInput);

    List<ConsultaEmergenciaDto> consultaEmergencia(ConsultasDto paramInput);

    Map consultaHospitalizacion(ConsultasDto paramInput);

    Map consultaCentroQuirurgico(ConsultasDto paramInput);

    Map consultasRecetas(RequestGenericDto paramInput);

    EssiResponseDto<List<EssiConsultaAtencionMedicaDto>> consultaAtencionesMedicas(RequestGenericDto paramInput);

    ListaSolicitudCittRespDto listaSolicitudCitt(ListaSolicitudCittReqDto paramInput);

    DetalleSolicitudCittRespDto detalleSolicitudCitt(DetalleSolicitudCittReqDto paramInput);
}

package gob.pe.essalud.client.service;

import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.dto.RequestCancelaSolicitudDto;
import gob.pe.essalud.client.dto.RequestConsultaSolicitudDto;
import gob.pe.essalud.client.dto.RequestGenericDto;
import gob.pe.essalud.client.dto.RequestParametroDto;
import gob.pe.essalud.client.dto.essi.*;
import gob.pe.essalud.client.dto.gestion_atencion.GaRegistrarColaRequestDto;
import gob.pe.essalud.client.dto.gestion_atencion.GaRegistrarColaResponseDto;
import gob.pe.essalud.client.dto.paciente.GetRiesgoDiabetesResponseDto;
import gob.pe.essalud.client.dto.paciente.RiesgoDiabetesEvaluarRequestDto;
import gob.pe.essalud.client.dto.paciente.RiesgoDiabetesEvaluarResponseDto;
import gob.pe.essalud.client.dto.trx.CancelarCitaRequestDto;
import gob.pe.essalud.client.dto.trx.CitasEmitidasResponseDto;
import gob.pe.essalud.client.dto.trx.UsuarioDataDto;

import java.util.List;
import java.util.Map;

public interface PacienteService {

    EssiResponseDto<List<EssiListaSolicitudDto>> getListaSolicitud(RequestConsultaSolicitudDto paramInput);

    Map getCancelaSolicitud(RequestCancelaSolicitudDto paramInput);

    Map getCrearSolicitud(Map paramInput);

    Map getListaReferencia(Map paramInput);

    EssiResponseDto<ParametroSolicitudResponseDto> parametroSolicitud(RequestParametroDto paramInput);

    Map loginMovil(EssiPacienteRequestDto paramInput);

    EssiResponseDto<List<CitasEmitidasResponseDto>> citasEmitidas(UsuarioDataDto paramInput);

    Map programacionDisponible(Map paramInput);

    Map generarCita(Map paramInput);

    Map generarCitaSolicitud(Map paramInput);

    EssiResponseDto<Void> eliminarCita(CancelarCitaRequestDto paramInput);

    Map getProgramacionSolicitud(Map paramInput);

    RegistrarSolCittRespDto registrarSolCitt(RegistrarSolCittReqDto input);

    EssiResponseDto<List<EssiListaSolicitudOperacionDto>> getListaSolicitudOperaciones(EssiListaSolicitudOperacionRequestDto input);

    EssiResponseDto<?> confirmarSolicitudOperacion(EssiConfirmarSolicitudOperacionRequestDto input);

    GetRiesgoDiabetesResponseDto getRiesgoDiabetes(RequestGenericDto input);

    RiesgoDiabetesEvaluarResponseDto riesgoDiabetesEvaluar(RiesgoDiabetesEvaluarRequestDto input);

    ResponseDto<GaRegistrarColaResponseDto> registrarTeleUrgencia(GaRegistrarColaRequestDto input);
}

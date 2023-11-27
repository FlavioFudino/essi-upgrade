package gob.pe.essalud.client.client.essi;

import gob.pe.essalud.client.client.essi.model.DataContactoPac;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.dto.RequestConsultaSolicitudDto;
import gob.pe.essalud.client.dto.RequestParametroDto;
import gob.pe.essalud.client.dto.essi.*;
import gob.pe.essalud.client.dto.trx.CitasEmitidasResponseDto;
import gob.pe.essalud.client.dto.trx.UsuarioDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "essiservice", url = "${" + Constantes.URL_ENDPOINT_CITA_ESSI + "}")
public interface EssiClient {

    @PostMapping(Constantes.URL_LOGIN_MOVIL)
    EssiResponseDto<PacienteEssiDto[]> loginMovil(@RequestBody EssiPacienteRequestDto paramInput);

    @PostMapping(Constantes.URL_ACT_DAT_CONTACT_PAC)
    EssiResponseDto<Void> actualizarDatosContactoPac(@RequestBody DataContactoPac paramInput);

    @PostMapping(Constantes.URL_ELIMINAR_CITA)
    EssiResponseDto<Void> eliminarCita(@RequestBody EssiCancelarCitaRequestDto paramInput);

    @PostMapping(Constantes.URL_CITAS_EMITIDAS)
    //@Cacheable(value = "citasEmitidas", key = "#paramInput.numDoc + '-' + #paramInput.codTipDoc")
    EssiResponseDto<List<CitasEmitidasResponseDto>> getCitas(@RequestBody UsuarioDataDto paramInput);

    @PostMapping(Constantes.URL_ACTUALIZAR_DATOS_PERSONA)
    EssiResponseDto<Void> actualizarDatosPersona(@RequestBody ActualizarDatosPersonaRequest paramInput);

    @PostMapping(Constantes.URL_PARAM_SOLICITUD)
    EssiResponseDto<ParametroSolicitudResponseDto> parametroSolicitud(@RequestBody RequestParametroDto paramInput);

    @PostMapping(Constantes.URL_REGISTRAR_SOLICITUD_CITT)
    RegistrarSolCittRespDto registrarSolCitt(@RequestBody RegistrarSolCittReqDto input);

    @PostMapping(Constantes.URL_LISTA_SOLICITUD)
    EssiResponseDto<List<EssiListaSolicitudDto>> listaSolicitud(@RequestBody RequestConsultaSolicitudDto input);

    @PostMapping("pBusSolOpePenRWs")
    EssiResponseDto<List<EssiListaSolicitudOperacionDto>> listaSolicitudOperaciones(@RequestBody EssiListaSolicitudOperacionRequestDto input);

    @PostMapping("pConfEstSolOpeRWs")
    EssiResponseDto<?> confirmarSolicitudOperacion(@RequestBody EssiConfirmarSolicitudOperacionRequestDto input);

}

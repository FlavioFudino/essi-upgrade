package gob.pe.essalud.client.client.essi;

import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.dto.ConsultasDto;
import gob.pe.essalud.client.dto.RequestGenericDto;
import gob.pe.essalud.client.dto.consulta.ConsultaEmergenciaRespDto;
import gob.pe.essalud.client.dto.essi.EssiResponseDto;
import gob.pe.essalud.client.dto.essi_consulta.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "essiconsultaservice", url = "${" + Constantes.URL_ENDPOINT_CONSULTAS_ESSI + "}")
public interface EssiConsulta {

    @PostMapping(Constantes.URL_CONSULTA_ATENCIONES_MEDICAS_CITT)
    //@Cacheable(value = "consultaAtencionesMedicas", key = "#paramInput.tipDoc + '-' + #paramInput.numDoc")
    EssiResponseDto<List<EssiConsultaAtencionMedicaDto>> consultaAtencionesMedicas(@RequestBody RequestGenericDto paramInput);

    @PostMapping(Constantes.URL_CONSULTA_LISTA_SOLICITUD_CITT)
    //@Cacheable(value = "listaSolicitudCitt", key = "#paramInput.lisNumDoc + '-' + #paramInput.lisTipDoc")
    ListaSolicitudCittRespDto listaSolicitudCitt(@RequestBody ListaSolicitudCittReqDto paramInput);

    @PostMapping(Constantes.URL_CONSULTA_DETALLE_SOLICITUD_CITT)
    DetalleSolicitudCittRespDto detalleSolicitudCitt(@RequestBody DetalleSolicitudCittReqDto paramInput);

    @PostMapping(Constantes.URL_CONSULTA_EMERGENCIA)
    EssiResponseDto<List<ConsultaEmergenciaRespDto>> consultaEmergencia(@RequestBody ConsultasDto paramInput);

}

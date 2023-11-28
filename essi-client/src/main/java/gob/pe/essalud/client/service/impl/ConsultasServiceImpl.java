package gob.pe.essalud.client.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.client.essi.EssiConsulta;
import gob.pe.essalud.client.client.trx.TrxClient;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.dto.CentroDto;
import gob.pe.essalud.client.dto.ConsultasDto;
import gob.pe.essalud.client.dto.RequestGenericDto;
import gob.pe.essalud.client.dto.consulta.ConsultaEmergenciaDto;
import gob.pe.essalud.client.dto.consulta.ConsultaEmergenciaRespDto;
import gob.pe.essalud.client.dto.essi.EssiResponseDto;
import gob.pe.essalud.client.dto.essi_consulta.DetalleSolicitudCittReqDto;
import gob.pe.essalud.client.dto.essi_consulta.DetalleSolicitudCittRespDto;
import gob.pe.essalud.client.dto.essi_consulta.EssiConsultaAtencionMedicaDto;
import gob.pe.essalud.client.dto.essi_consulta.ListaSolicitudCittReqDto;
import gob.pe.essalud.client.dto.essi_consulta.ListaSolicitudCittRespDto;
import gob.pe.essalud.client.service.ConsultasService;

@Service
public class ConsultasServiceImpl extends BaseService implements ConsultasService {

    @Autowired
    private final RestTemplate restTemplate;

    private final EssiConsulta _essiConsulta;
    private final TrxClient _trxClient;

    @Autowired
    public ConsultasServiceImpl(RestTemplate restTemplate,
                                EssiConsulta essiConsulta,
                                TrxClient trxClient) {

        this.restTemplate = restTemplate;
        _essiConsulta = essiConsulta;
        _trxClient = trxClient;
    }  

    @Override
    //@Cacheable(value = "consultaExterna", key = "#paramInput.numDoc + '-' + #paramInput.tipDoc + '-' + #paramInput.anio")
    public Map consultaExterna(ConsultasDto paramInput) {
        this.loggerDebug("Inicio consultaExterna", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_CONSULTAS_ESSI))
                .path(Constantes.URL_CONSULTA_EXTERNA)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerDebug(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerDebug("Fin consultaExterna", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    //@Cacheable(value = "consultaEmergencia", key = "#paramInput.numDoc + '-' + #paramInput.tipDoc + '-' + #paramInput.anio")
    public List<ConsultaEmergenciaDto> consultaEmergencia(ConsultasDto paramInput) {
        this.loggerDebug("Inicio consultaEmergencia", formatterHour.format(new Date()));

        List<ConsultaEmergenciaDto> response = new ArrayList<>();

        EssiResponseDto<List<ConsultaEmergenciaRespDto>> consultaEmergenciaRespDto = _essiConsulta.consultaEmergencia(paramInput);
        if (consultaEmergenciaRespDto.getCodError().equals("0")) {

            ConsultaEmergenciaDto obj = null;
            for (ConsultaEmergenciaRespDto item : consultaEmergenciaRespDto.getvDataItem()) {
                CentroDto centroDto = _trxClient.getCentro(item.getCodCenAsi());
                String cenAsi = "";
                if (centroDto != null) {
                    cenAsi = centroDto.getDescripcion();
                }

                obj = new ConsultaEmergenciaDto();
                obj.setCenAsi(cenAsi); //trae del postgres
                obj.setFechaAdmision(item.getFechaAdmision());
                obj.setDesPriAten(item.getDesPriAten());
                obj.setObserAdmision(item.getObserAdmision());
                response.add(obj);
            }
        }

        return response;
    }

    @Override
    //@Cacheable(value = "consultaHospitalizacion", key = "#paramInput.numDoc + '-' + #paramInput.tipDoc + '-' + #paramInput.anio")
    public Map consultaHospitalizacion(ConsultasDto paramInput) {
        this.loggerDebug("Inicio consultaHospitalizacion", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_CONSULTAS_ESSI))
                .path(Constantes.URL_CONSULTA_HOSPITALIZACION)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerDebug(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerDebug("Fin consultaHospitalizacion", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    //@Cacheable(value = "consultaCentroQuirurgico", key = "#paramInput.numDoc+ '-' + #paramInput.tipDoc + '-' + #paramInput.anio")
    public Map consultaCentroQuirurgico(ConsultasDto paramInput) {
        this.loggerDebug("Inicio consultaCentroQuirurgico", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_CONSULTAS_ESSI))
                .path(Constantes.URL_CONSULTA_CENTRO_QUIRURGICO)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerDebug(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerDebug("Fin consultaCentroQuirurgico", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    //@Cacheable(value = "consultasRecetas", key = "#paramInput.numDoc + '-' + #paramInput.tipDoc")
    public Map consultasRecetas(RequestGenericDto paramInput) {
        this.loggerDebug("Inicio consultasRecetas", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_CONSULTAS_ESSI))
                .path(Constantes.URL_CONSULTA_RECETAS)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerDebug(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerDebug("Fin consultasRecetas", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public EssiResponseDto<List<EssiConsultaAtencionMedicaDto>> consultaAtencionesMedicas(RequestGenericDto paramInput) {
        this.loggerDebug("Inicio consultaAtencionesMedicas", formatterHour.format(new Date()));
        EssiResponseDto<List<EssiConsultaAtencionMedicaDto>> response = _essiConsulta.consultaAtencionesMedicas(paramInput);
        this.loggerDebug("Fin consultaAtencionesMedicas", formatterHour.format(new Date()));
        return response;
    }

    @Override
    public ListaSolicitudCittRespDto listaSolicitudCitt(ListaSolicitudCittReqDto paramInput) {
        this.loggerDebug("Inicio listaSolicitudCitt", formatterHour.format(new Date()));
        ListaSolicitudCittRespDto response = _essiConsulta.listaSolicitudCitt(paramInput);
        this.loggerDebug("Fin listaSolicitudCitt", formatterHour.format(new Date()));
        return response;
    }

    @Override
    public DetalleSolicitudCittRespDto detalleSolicitudCitt(DetalleSolicitudCittReqDto paramInput) {
        this.loggerDebug("Inicio detalleSolicitudCitt", formatterHour.format(new Date()));
        DetalleSolicitudCittRespDto response = _essiConsulta.detalleSolicitudCitt(paramInput);
        this.loggerDebug("Fin detalleSolicitudCitt", formatterHour.format(new Date()));
        return response;
    }
}

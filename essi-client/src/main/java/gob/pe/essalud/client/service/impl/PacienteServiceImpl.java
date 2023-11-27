package gob.pe.essalud.client.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.client.GestionAtencionClient;
import gob.pe.essalud.client.client.essi.EssiClient;
import gob.pe.essalud.client.client.trx.TrxClient;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.common.constants.DateFormat;
import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.common.http.ResponseType;
import gob.pe.essalud.client.common.util.DateUtil;
import gob.pe.essalud.client.common.util.StringUtil;
import gob.pe.essalud.client.common.util.Util;
import gob.pe.essalud.client.dto.CentroDto;
import gob.pe.essalud.client.dto.RequestCancelaSolicitudDto;
import gob.pe.essalud.client.dto.RequestConsultaSolicitudDto;
import gob.pe.essalud.client.dto.RequestGenericDto;
import gob.pe.essalud.client.dto.RequestParametroDto;
import gob.pe.essalud.client.dto.essi.EssiCancelarCitaRequestDto;
import gob.pe.essalud.client.dto.essi.EssiConfirmarSolicitudOperacionRequestDto;
import gob.pe.essalud.client.dto.essi.EssiListaSolicitudDto;
import gob.pe.essalud.client.dto.essi.EssiListaSolicitudOperacionDto;
import gob.pe.essalud.client.dto.essi.EssiListaSolicitudOperacionRequestDto;
import gob.pe.essalud.client.dto.essi.EssiPacienteRequestDto;
import gob.pe.essalud.client.dto.essi.EssiResponseDto;
import gob.pe.essalud.client.dto.essi.ParametroSolicitudResponseDto;
import gob.pe.essalud.client.dto.essi.RegistrarSolCittReqDto;
import gob.pe.essalud.client.dto.essi.RegistrarSolCittRespDto;
import gob.pe.essalud.client.dto.gestion_atencion.GaRegistrarColaRequestDto;
import gob.pe.essalud.client.dto.gestion_atencion.GaRegistrarColaResponseDto;
import gob.pe.essalud.client.dto.paciente.GetRiesgoDiabetesResponseDto;
import gob.pe.essalud.client.dto.paciente.RiesgoDiabetesEvaluarRequestDto;
import gob.pe.essalud.client.dto.paciente.RiesgoDiabetesEvaluarResponseDto;
import gob.pe.essalud.client.dto.trx.CancelarCitaRequestDto;
import gob.pe.essalud.client.dto.trx.CitaDto;
import gob.pe.essalud.client.dto.trx.CitasEmitidasResponseDto;
import gob.pe.essalud.client.dto.trx.UsuarioDataDto;
import gob.pe.essalud.client.service.CentroService;
import gob.pe.essalud.client.service.PacienteService;

@Service
public class PacienteServiceImpl extends BaseService implements PacienteService {

    @Autowired
    private final RestTemplate restTemplate;

    private final EssiClient essiClient;
    private final TrxClient trxClient;
    private final CentroService centroService;
    private final GestionAtencionClient gestionAtencionClient;

    @Value("${citasEmitidas.botonConfirmar.diaMin}")
    private int citaEmitidaDiaMin;

    @Value("${citasEmitidas.botonConfirmar.diaMax}")
    private int citaEmitidaDiaMax;

    @Value("${pedir-cita-cod-centros-habilitados}")
    private String pedirCitaCodCentrosHabilitados;

    @Autowired
    public PacienteServiceImpl(RestTemplate restTemplate,
                               EssiClient essiClient,
                               TrxClient trxClient,
                               CentroService centroService,
                               GestionAtencionClient gestionAtencionClient) {

        this.restTemplate = restTemplate;
        this.essiClient = essiClient;
        this.trxClient = trxClient;
        this.centroService = centroService;
        this.gestionAtencionClient = gestionAtencionClient;
    }

    @Override
    public EssiResponseDto<List<EssiListaSolicitudDto>> getListaSolicitud(RequestConsultaSolicitudDto paramInput) {
        this.loggerInfo("Inicio getListaSolicitud", formatterHour.format(new Date()));

        EssiResponseDto<List<EssiListaSolicitudDto>> responseDto = essiClient.listaSolicitud(paramInput);

        if (responseDto.getvDataItem() != null) {
            for (EssiListaSolicitudDto item : responseDto.getvDataItem()) {
                boolean puedePedirCita = StringUtil.isStringInMatcher(item.getCodCentroSolCita(), pedirCitaCodCentrosHabilitados);
                item.setPuedePedirCita(puedePedirCita);
            }
        }

        this.loggerInfo("Fin getListaSolicitud", formatterHour.format(new Date()));

        return responseDto;
    }

    @Override
    public EssiResponseDto<ParametroSolicitudResponseDto> parametroSolicitud(RequestParametroDto paramInput) {
        this.loggerInfo("Inicio parametroSolicitud", formatterHour.format(new Date()));

        EssiResponseDto<ParametroSolicitudResponseDto> response = essiClient.parametroSolicitud(paramInput);

        this.loggerInfo("Fin parametroSolicitud", formatterHour.format(new Date()));

        return response;
    }

    @Override
    public Map getCancelaSolicitud(RequestCancelaSolicitudDto paramInput) {
        this.loggerInfo("Inicio getCancelaSolicitud", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_CITA_ESSI))
                .path(Constantes.URL_CANCELA_SOLICITUD)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerInfo("Fin getCancelaSolicitud", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public Map getCrearSolicitud(Map paramInput) {
        this.loggerInfo("Inicio getCrearSolicitud", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_CITA_ESSI))
                .path(Constantes.URL_CREAR_SOLICITUD)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerInfo("Fin getCrearSolicitud", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public Map loginMovil(EssiPacienteRequestDto paramInput) {
        this.loggerInfo("Inicio loginMovil", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_CITA_ESSI))
                .path(Constantes.URL_LOGIN_MOVIL)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerInfo("Fin loginMovil", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public EssiResponseDto<List<CitasEmitidasResponseDto>> citasEmitidas(UsuarioDataDto paramInput) {
        this.loggerInfo("Inicio citasEmitidas", formatterHour.format(new Date()));

        EssiResponseDto<List<CitasEmitidasResponseDto>> citasEssi = this.essiClient.getCitas(paramInput);
        List<CitaDto> citas = trxClient.buscarCitas(paramInput);

        for (CitasEmitidasResponseDto cita : citasEssi.getvDataItem()) {

            boolean isInDays = false;
            try {
                Date fechaCita = DateUtil.stringToDate(cita.getCitFecha(), DateFormat.DD_MM_YYYY);
                Date currentDate = trxClient.getCurrentDate();
                currentDate.setHours(0);
                currentDate.setMinutes(0);
                currentDate.setSeconds(0);

                long daysDiff = DateUtil.dateDiffInDays(currentDate,fechaCita);
                isInDays = (daysDiff >= citaEmitidaDiaMin && daysDiff <= citaEmitidaDiaMax);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            List<CitaDto> respuestasEncontradas = citas.stream()
                    .filter(c ->
                            c.getActMedNum().equals(cita.getCitActMedNum()) &&
                            c.getCenAsiCod().equals(cita.getCitCenAsiCod()) &&
                            c.getOriCenAsi().equals(cita.getCitOriCenAsiCod()))
                    .collect(Collectors.toList());

            CitaDto citaConfirmadaRespondida = null;
            CitaDto citaCanceladaRespondida = null;

            if (respuestasEncontradas.size() > 0) {
                citaConfirmadaRespondida = respuestasEncontradas.stream()
                        .filter(c -> c.isIndConfirmado()).findAny().orElse(null);

                citaCanceladaRespondida = respuestasEncontradas.stream()
                        .filter(c -> !c.isIndConfirmado()).findAny().orElse(null);

                cita.setTieneRespuesta(true);
            }

            boolean isCancelada = (citaCanceladaRespondida != null || cita.isCitaAnulada());
            boolean isConfirmada = (citaConfirmadaRespondida != null);

            if (isInDays && cita.isCitaPendiente() && (!isCancelada && !isConfirmada)) {
                cita.setPuedeConfirmar(true);
            }

            cita.setIndConfirmado(isConfirmada);
            cita.setPuedeCancelar(cita.isCitaPendiente());
        }

        this.loggerInfo("Fin citasEmitidas", formatterHour.format(new Date()));

        return citasEssi;
    }

    @Override
    public Map programacionDisponible(Map paramInput) {
        this.loggerInfo("Inicio programacionDisponible", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_CITA_ESSI))
                .path(Constantes.URL_PROGRAMACION_DISPONIBLE)
                .build().encode().toUriString();

        this.setProgDate(paramInput);

        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerInfo("Fin programacionDisponible", formatterHour.format(new Date()));
        return response.getBody();
    }

    private void setProgDate(Map paramInput) {
        String dateKey = "fechaCitaSol";
        if (paramInput.get(dateKey) == null || StringUtil.isNullOrEmpty(paramInput.get(dateKey).toString())) {
            paramInput.put(dateKey, DateUtil.format(new Date(), DateFormat.DD_MM_YYYY));
        }
    }

    public Map generarCita(Map paramInput) {
        this.loggerInfo("Inicio generarCita", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_CITA_ESSI))
                .path(Constantes.URL_GENERAR_CITA)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);

        /*
        if (response.getBody().get("codError").toString().equals(Constantes.RETORNO_EXITO)) {

        }*/

        this.loggerInfo("Fin generarCita", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public Map generarCitaSolicitud(Map paramInput) {
        this.loggerInfo("Inicio generarCitaSolicitud", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_CITA_ESSI))
                .path(Constantes.URL_GENERAR_CITA_SOLICITUD)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);

        this.loggerInfo("Fin generarCitaSolicitud", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public EssiResponseDto<Void> eliminarCita(CancelarCitaRequestDto paramInput) {
        this.loggerInfo("Inicio eliminarCita", formatterHour.format(new Date()));

        /*String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_CITA_ESSI))
                .path(Constantes.URL_ELIMINAR_CITA)
                .build().encode().toUriString();

        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());

        this.loggerInfo(Constantes.INFO_URL, url);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);*/

        EssiCancelarCitaRequestDto essiCancelarCitaRequestDto = Util.objectToObject(EssiCancelarCitaRequestDto.class, paramInput);
        essiCancelarCitaRequestDto.setMotEli("05"); //05 = DISCONFORMIDAD_PACIENTE

        EssiResponseDto<Void> response = essiClient.eliminarCita(essiCancelarCitaRequestDto);

        this.loggerInfo("Fin eliminarCita", formatterHour.format(new Date()));
        return response;
    }

    @Override
    //@Cacheable(value = "getListaReferencia", key = "#paramInput['numDoc'] + '-' + #paramInput['codTipDoc']")
    public Map getListaReferencia(Map paramInput) {
        this.loggerInfo("Inicio getListaReferencia", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_CITA_ESSI))
                .path(Constantes.URL_LISTA_REFERENCIA)
                .build().encode().toUriString();

        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerInfo("Fin getListaReferencia", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public Map getProgramacionSolicitud(Map paramInput) {
        this.loggerInfo("Inicio getProgramacionSolicitud", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_CITA_ESSI))
                .path(Constantes.URL_PROGRAMACION_SOLICITUD)
                .build().encode().toUriString();

        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerInfo("Fin getProgramacionSolicitud", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public RegistrarSolCittRespDto registrarSolCitt(RegistrarSolCittReqDto input) {
        return essiClient.registrarSolCitt(input);
    }

    @Override
    public EssiResponseDto<List<EssiListaSolicitudOperacionDto>> getListaSolicitudOperaciones(EssiListaSolicitudOperacionRequestDto input) {
        return essiClient.listaSolicitudOperaciones(input);
    }

    @Override
    public EssiResponseDto<?> confirmarSolicitudOperacion(EssiConfirmarSolicitudOperacionRequestDto input) {
        return essiClient.confirmarSolicitudOperacion(input);
    }

    @Override
    public GetRiesgoDiabetesResponseDto getRiesgoDiabetes(RequestGenericDto input) {
        return trxClient.getRiesgoDiabetes(input);
    }

    @Override
    public RiesgoDiabetesEvaluarResponseDto riesgoDiabetesEvaluar(RiesgoDiabetesEvaluarRequestDto input) {
        return trxClient.riesgoDiabetesEvaluar(input);
    }

    @Override
    public ResponseDto<GaRegistrarColaResponseDto> registrarTeleUrgencia(GaRegistrarColaRequestDto input) {
        CentroDto centro = centroService.getCentro(input.getCodCentro());
        if (centro == null) {
            ResponseDto<GaRegistrarColaResponseDto> responseDto = new ResponseDto<>();
            responseDto.setCodResult(ResponseType.ERROR);
            responseDto.setMessage(String.format("Su centro no tiene habilitado el registro de casos de urgencia (%s)", input.getCodCentro()));
            return responseDto;
        }

        input.setCodRed(centro.getCodRed());
        return gestionAtencionClient.registrarCola(input);
    }

}

package gob.pe.essalud.client.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.common.dto.ResponseSalogDto;
import gob.pe.essalud.client.common.dto.ResponseSalogFarmaciaDto;
import gob.pe.essalud.client.common.util.Util;
import gob.pe.essalud.client.dto.PacienteDto;
import gob.pe.essalud.client.dto.essi.PacienteEssiDto;
import gob.pe.essalud.client.dto.farmacia.PacienteRequestDto;
import gob.pe.essalud.client.service.AuthService;
import gob.pe.essalud.client.service.FarmaciaService;
import gob.pe.essalud.client.service.ServiceException;

@Service
public class FarmaciaServiceImpl extends BaseService implements FarmaciaService {

    @Autowired
    private final RestTemplate restTemplate;
    private final AuthService authService;

    @Autowired
    public FarmaciaServiceImpl(RestTemplate restTemplate, AuthService authService) {
        this.restTemplate = restTemplate;
        this.authService = authService;
    }

    @Override
    public Map getListFarmacias(Map paramInput) {
        this.loggerDebug("Inicio getListFarmacias", formatterHour.format(new Date()));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        if (paramInput != null) {
            String filter = paramInput.get("filter").toString();
            if (!filter.equals(Constantes.FILTER_TODOS)) {
                String value = paramInput.get("value").toString();
                body.add(filter, value);
            }
        }
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_FARMACIAS_LISTA))
                .path(Constantes.URL_FARMACIAS_LISTAR)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(body, this.getHttpHeader(MediaType.APPLICATION_FORM_URLENCODED));
        this.loggerDebug(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerDebug("Fin getListFarmacias", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public PacienteRequestDto getFarmaciaPaciente(PacienteDto param) {
        this.loggerDebug("Inicio getFarmaciaPaciente", formatterHour.format(new Date()));
        PacienteRequestDto pacienteRequestDto = this.getPacienteDireccionSalog(param.getTipoDocIdent(),
                param.getNumeroDocIdent());
        this.loggerDebug("Fin getFarmaciaPaciente", formatterHour.format(new Date()));
        return pacienteRequestDto;
    }

    @Override
    public PacienteRequestDto saveFarmaciaPaciente(PacienteRequestDto paciente) {
        this.loggerDebug("Inicio saveFarmaciaPaciente", formatterHour.format(new Date()));
        savePacienteSalog(paciente);
        PacienteRequestDto retorno = savePacienteEssi(paciente);
        this.loggerDebug("Fin saveFarmaciaPaciente", formatterHour.format(new Date()));
        return retorno;
    }


    private void savePacienteSalog(PacienteRequestDto paciente) {
        this.loggerDebug("Inicio saveFarmaciaPaciente", formatterHour.format(new Date()));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        PacienteRequestDto usuarioFarmacia = getPacienteDireccionSalog(paciente
                        .getPaciente().getTipoDocIdent(),
                paciente.getPaciente().getNumeroDocIdent());
        boolean existUserSalog = (usuarioFarmacia == null) ? false : true;
        PacienteDto pacienteDto = authService.getUsuario(paciente.getPaciente().getNumeroDocIdent());
        PacienteEssiDto pacienteEssiDto = authService.getPacienteEssi(pacienteDto);
        //Body
        body.add("doc_paciente", paciente.getPaciente().getNumeroDocIdent());
        body.add("tipo_doc", paciente.getPaciente().getTipoDocIdent());
        body.add("cd_hospital", pacienteEssiDto.getCodCentro());
        body.add("id_service", Constantes.URL_FARMACIAS_SERVICE);
        body.add("first_name", pacienteEssiDto.getPriNombre());
        body.add("last_name", pacienteEssiDto.getApeMaterno());
        body.add("gender", (pacienteEssiDto.getCodGenero() == "0") ? "F" : "M");
        body.add("status", Constantes.URL_FARMACIAS_STATUS);
        body.add("address", paciente.getDireccionPaciente().getDescripcion());
        body.add("id_ubigeo_pat", paciente.getDireccionPaciente().getUbigeo());
        body.add("id_to", paciente.getDireccionFarmacia().getIdFarmacia().toString());
        body.add("id_ubigeo", paciente.getDireccionFarmacia().getUbigeo());
        body.add("phone", paciente.getPaciente().getNroTelefonoFijo());
        body.add("cellphone", paciente.getPaciente().getNroCelular());
        body.add("phone_aux", pacienteEssiDto.getNumTelefono());
        body.add("cellphone_aux", pacienteEssiDto.getNumCelular());
        body.add("email", paciente.getPaciente().getEmail());
        body.add("birth_date", pacienteDto.getFechaNacimiento());
        body.add("latitude", paciente.getDireccionPaciente().getNroLatitud());
        body.add("longitude", paciente.getDireccionPaciente().getNroLongitud());
        body.add("id_user", Constantes.URL_FARMACIAS_USUARIO);
        if (!existUserSalog)
            body.add("mode", Constantes.URL_FARMACIAS_INSERT);
        else
            body.add("mode", Constantes.URL_FARMACIAS_UPDATE);

        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_FARMACIAS_BASE))
                .path(Constantes.URL_FARMACIAS_REG_PACIENTE)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(body, this.getHttpHeader(MediaType.APPLICATION_FORM_URLENCODED));
        this.loggerDebug(Constantes.INFO_URL, url);
        ResponseEntity<ResponseSalogDto> responseSalog = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                ResponseSalogDto.class);
        if (responseSalog.getBody().isError())
            throw new ServiceException(responseSalog.getBody().getMessage());
        return;
    }

    private PacienteRequestDto savePacienteEssi(PacienteRequestDto paciente) {
        this.loggerDebug("Inicio savePacienteEssi", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX))
                .path(Constantes.URL_PACIENTE_SAVE)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(paciente, this.getHttpHeader());
        this.loggerDebug(Constantes.INFO_URL, url);
        ResponseEntity<ResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                ResponseDto.class);
        this.loggerDebug("Fin savePacienteEssi", formatterHour.format(new Date()));
        return Util.objectToObject(PacienteRequestDto.class, response.getBody().getData());
    }

    private PacienteRequestDto getPacienteDireccionSalog(String tipoDocumento, String numeroDocumento) {
        PacienteRequestDto pacienteRequestDto = null;
        try {
            this.loggerDebug("Inicio getPacienteDireccionSalog", formatterHour.format(new Date()));
            //consulta en SALOG
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("tipoDocIdent", tipoDocumento);
            body.add("numeroDocIdent", numeroDocumento);
            String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_FARMACIAS_BASE))
                    .path(Constantes.URL_FARMACIAS_CON_PACIENTE)
                    .build().encode().toUriString();
            HttpEntity<?> httpEntity = new HttpEntity<>(body, this.getHttpHeader(MediaType.APPLICATION_FORM_URLENCODED));
            this.loggerDebug(Constantes.INFO_URL, url);
            ResponseEntity<ResponseDto> responsePaciente = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                    ResponseDto.class);
            pacienteRequestDto = Util.objectToObject(PacienteRequestDto.class, responsePaciente.getBody().getData());
            this.loggerDebug("Fin getPacienteDireccionSalog", formatterHour.format(new Date()));
        } catch (Exception e) {
            pacienteRequestDto = null;
        }
        return pacienteRequestDto;
    }


    private PacienteRequestDto getPacienteDireccionEssi(PacienteDto paramInput) {
        this.loggerDebug("Inicio getPacienteDireccionEssi", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX))
                .path(Constantes.URL_PACIENTE_DIRECCION_GET)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerDebug(Constantes.INFO_URL, url);
        ResponseEntity<ResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                ResponseDto.class);
        this.loggerDebug("Fin getPacienteDireccionEssi", formatterHour.format(new Date()));
        PacienteRequestDto pacienteRequestDto = Util.objectToObject(PacienteRequestDto.class, response.getBody().getData());
        return pacienteRequestDto;
    }

    @Override
    public ResponseSalogFarmaciaDto getTrackingRecetas(Map paramInput) {
        this.loggerDebug("Inicio getTrackingRecetas", formatterHour.format(new Date()));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        if (paramInput != null) {
            //String tipo = paramInput.get("NRO_DOC").toString();
            body.add("OPT", "1");
            body.add("TIPO_DOC", paramInput.get("TIPO_DOC").toString());
            body.add("NRO_DOC", paramInput.get("NRO_DOC").toString());
        }
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_FARMACIAS_TRACKING))
                .path(Constantes.URL_FARMACIA_VECINA)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(body, this.getHttpHeader(MediaType.APPLICATION_FORM_URLENCODED));
        this.loggerDebug(Constantes.INFO_URL, url);
        ResponseEntity<ResponseSalogFarmaciaDto> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                ResponseSalogFarmaciaDto.class);
        this.loggerDebug("Fin getTrackingRecetas", formatterHour.format(new Date()));
        return response.getBody();
    }

}

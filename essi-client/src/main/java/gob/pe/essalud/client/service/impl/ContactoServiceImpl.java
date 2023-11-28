package gob.pe.essalud.client.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.common.util.Util;
import gob.pe.essalud.client.dto.contacto.ContactoCercanoRequestDto;
import gob.pe.essalud.client.service.ContactoService;

@Service
public class ContactoServiceImpl extends BaseService implements ContactoService {

    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    public ContactoServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Map findAll(String tipoDocumento, String numeroDocumento) {
        this.loggerDebug("Inicio findAll", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX))
                .path(Constantes.URL_OTROS_CONTACTOS)
                .queryParam(Constantes.URL_TIPO_DOCUMENTO, tipoDocumento)
                .queryParam(Constantes.URL_NRO_DOCUMENTO, numeroDocumento)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(this.getHttpHeader());
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Map.class).getBody();
    }

    @Override
    public ContactoCercanoRequestDto save(ContactoCercanoRequestDto contacto) {
        this.loggerDebug("Inicio save", formatterHour.format(new Date()));

        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX))
                .path(Constantes.URL_OTROS_CONTACTOS)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(contacto, this.getHttpHeader());
        this.loggerDebug(Constantes.INFO_URL, url);
        ResponseEntity<ResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                ResponseDto.class);
        this.loggerDebug("Fin save", formatterHour.format(new Date()));
        return Util.objectToObject(ContactoCercanoRequestDto.class, response.getBody().getData());
    }

    @Override
    public ContactoCercanoRequestDto delete(ContactoCercanoRequestDto contacto) {
        this.loggerDebug("Inicio save", formatterHour.format(new Date()));

        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX))
                .path(Constantes.URL_OTROS_CONTACTOS)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(contacto, this.getHttpHeader());
        this.loggerDebug(Constantes.INFO_URL, url);
        ResponseEntity<ResponseDto> response = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity,
                ResponseDto.class);
        this.loggerDebug("Fin save", formatterHour.format(new Date()));
        return Util.objectToObject(ContactoCercanoRequestDto.class, response.getBody().getData());
    }

}

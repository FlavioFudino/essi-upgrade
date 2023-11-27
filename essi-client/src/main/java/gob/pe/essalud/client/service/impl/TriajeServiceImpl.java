package gob.pe.essalud.client.service.impl;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.service.TriajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class TriajeServiceImpl extends BaseService implements TriajeService {

    @Autowired
    private final RestTemplate restTemplate;

    private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    public TriajeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Map autoEvaluacion(Map paramInput) {
        this.loggerInfo("Inicio autoEvaluacion", formatter.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRIAJE))
                .path(Constantes.URL_TRIAJE_AUTOEVALUACION)
                .queryParam(Constantes.URL_USERNAME, this.getProperty(Constantes.URL_TRIAJE_USERNAME))
                .queryParam(Constantes.URL_ACCESS, this.getProperty(Constantes.URL_TRIAJE_ACCESS))
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerInfo("Fin autoEvaluacion", formatter.format(new Date()));
        return response.getBody();

    }
}

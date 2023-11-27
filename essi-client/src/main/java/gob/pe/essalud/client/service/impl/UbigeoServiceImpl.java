package gob.pe.essalud.client.service.impl;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.service.UbigeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class UbigeoServiceImpl extends BaseService implements UbigeoService {

    private final RestTemplate restTemplate;
    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    public UbigeoServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Cacheable(value = "getDepartamentos", key = "'all'")
    public Map[] getDepartaments() {
        this.loggerInfo("Inicio getDepartamentos", formatter.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX) + "ubigeo/")
                .path(Constantes.URL_DEPARTAMENTOS)
                .build().encode().toUriString();
        ResponseEntity<Map[]> response = restTemplate.exchange(url, HttpMethod.GET, null,
                Map[].class);
        this.loggerInfo("Inicio getDepartamentos", formatter.format(new Date()));
        return response.getBody();
    }

    @Override
    @Cacheable(value = "getProvincias", key = "#codigoDepartamento")
    public Map[] getProvinces(String codigoDepartamento) {
        this.loggerInfo("Inicio getProvincias", formatter.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX) + "ubigeo/")
                .path(Constantes.URL_PROVINCIAS)
                .queryParam("codigoDepartamento", codigoDepartamento)
                .build().encode().toUriString();
        ResponseEntity<Map[]> response = restTemplate.exchange(url, HttpMethod.GET, null,
                Map[].class);
        this.loggerInfo("Inicio getProvincias", formatter.format(new Date()));
        return response.getBody();
    }

    @Override
    @Cacheable(value = "getDistritos", key = "#codigoDepartamento + '-' + #codigoProvincia")
    public Map[] getDistricts(String codigoDepartamento, String codigoProvincia) {
        this.loggerInfo("Inicio getDistritos", formatter.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX) + "ubigeo/")
                .path(Constantes.URL_DISTRITOS)
                .queryParam("codigoDepartamento", codigoDepartamento)
                .queryParam("codigoProvincia", codigoProvincia)
                .build().encode().toUriString();
        ResponseEntity<Map[]> response = restTemplate.exchange(url, HttpMethod.GET, null,
                Map[].class);
        this.loggerInfo("Inicio getDistritos", formatter.format(new Date()));
        return response.getBody();
    }

    @Override
    @Cacheable(value = "getUbigeo", key = "#codigo")
    public Map getUbigeo(String codigo) {
        this.loggerInfo("Inicio getUbigeo", formatter.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX) + "ubigeo/")
                .path(codigo)
                .build().encode().toUriString();
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, null,
                Map.class);
        this.loggerInfo("Inicio getUbigeo", formatter.format(new Date()));
        return response.getBody();
    }

}

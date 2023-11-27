package gob.pe.essalud.client.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.service.AppsVersionService;

@Service
public class AppsVersionServiceImpl extends BaseService implements AppsVersionService {

    @Autowired
    private final RestTemplate restTemplate; 

    @Autowired
    public AppsVersionServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Map version(String siglas) {
        Map map = new HashMap();
        map.put("version", this.getProperty(Constantes.APP_VERSION_NUMBER));
        return map;
    }

    /*
    @Override
    public Map version(String siglas) {
        this.loggerInfo("Inicio version", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_VERSION_APPS))
                .path(Constantes.URL_RECURSO_SIGLAS)
                .queryParam(Constantes.URL_PARAM_SIGLAS, siglas)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(this.getHttpHeader());
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Map.class).getBody();
    }*/

}

package gob.pe.essalud.client.service.impl;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.service.ParametrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class ParametrosServiceImpl extends BaseService implements ParametrosService {

    private static final String KEY_CONDICION_SALUD = "condicionSalud";

    private final RestTemplate restTemplate;
    //private final BusqActivaClient busqActivaClient;

    @Autowired
    public ParametrosServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Map get(String filter) {

        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX))
                .path(Constantes.URL_PARAMETROS)
                .queryParam(Constantes.URL_PARAMETROS_FILTERS, filter)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(this.getHttpHeader());
        Map data = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Map.class).getBody();
        /*if (filter.contains(KEY_CONDICION_SALUD)) {
            Map busqData = busqActivaClient.parametros(KEY_CONDICION_SALUD);
            data.put(KEY_CONDICION_SALUD, busqData.get(KEY_CONDICION_SALUD));
        }*/
        return data;
    }

}

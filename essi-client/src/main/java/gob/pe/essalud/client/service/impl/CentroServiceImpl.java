package gob.pe.essalud.client.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.client.trx.TrxClient;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.dto.CentroDto;
import gob.pe.essalud.client.service.CentroService;

@Service
public class CentroServiceImpl extends BaseService implements CentroService {

    private final RestTemplate restTemplate;
    private final TrxClient trxClient;

    @Autowired
    public CentroServiceImpl(RestTemplate restTemplate,
                             TrxClient trxClient) {
        this.restTemplate = restTemplate;
        this.trxClient = trxClient;
    }

    @Override
    public boolean checkApplyCita(String cenAsiCod) {
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX))
                .path(Constantes.URL_CENTROS_APPLY_CITA)
                .queryParam("cenAsiCod", cenAsiCod)
                .build().encode().toUriString();

        this.loggerDebug("checkApplyCita:", url);
        ResponseEntity<Map> responsePaciente = restTemplate.exchange(url, HttpMethod.GET, null,
                Map.class);
        Map map = responsePaciente.getBody();

    if (map != null) {
        boolean applyCita = Boolean.parseBoolean(map.get("applyCita").toString());
        return applyCita;
    } else {
        // Manejo de la situación cuando map es nulo
        return false;
    }
    }

    @Override
    public CentroDto getCentro(String cenAsiCod) {
        return trxClient.getCentro(cenAsiCod);
    }
}

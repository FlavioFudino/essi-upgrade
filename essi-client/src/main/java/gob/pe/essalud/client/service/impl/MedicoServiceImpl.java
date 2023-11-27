package gob.pe.essalud.client.service.impl;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.client.medico.EssiMedico;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.dto.medico.*;
import gob.pe.essalud.client.dto.medico.listaSolicitudExamen.SolicitudExamenDto;
import gob.pe.essalud.client.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MedicoServiceImpl extends BaseService implements MedicoService {

    @Autowired
    private final RestTemplate restTemplate;

    private final EssiMedico essiMedico;

    private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    public MedicoServiceImpl(RestTemplate restTemplate, EssiMedico essiMedico) {
        this.restTemplate = restTemplate;
        this.essiMedico = essiMedico;
    }

    @Override
    public Map login(MedicoLoginDto paramInput) {
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_MEDICO_ESSI))
                .path(Constantes.URL_LOGIN_MEDICO)
                .build().encode().toUriString();
        return postExchange(url, paramInput);
    }

    @Override
    public Map programacion(ProgramacionRequestDto paramInput) {
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_MEDICO_ESSI))
                .path(Constantes.URL_PROGRAMACION_MEDICO)
                .build().encode().toUriString();
        return postExchange(url, paramInput);
    }

    @Override
    public Map pacientesCitados(PacienteCitadosRequestDto paramInput) {
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_MEDICO_ESSI))
                .path(Constantes.URL_PACIENTE_MEDICO)
                .build().encode().toUriString();
        return postExchange(url, paramInput);
    }

    private Map postExchange(String url, Object paramInput) {
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        return response.getBody();
    }

    @Override
    public ListaSolicitudExamenResponseDto listaSolicitudExamen(ListaSolicitudExamenRequestDto paramInput) {
        this.loggerInfo("Inicio listaSolicitudExamen", formatter.format(new Date()));

        ListaSolicitudExamenResponseDto data = essiMedico.listaSolicitudExamen(paramInput);

        Map<String, List<SolicitudExamenDto>> groupedData = new HashMap<>();
        for (SolicitudExamenDto item: data.getListaSolExa()) {
            String key = item.getCodCen().concat("|").concat(item.getNumSol());

            if (groupedData.get(key) == null) {
                groupedData.put(key, new ArrayList<>());
            }

            groupedData.get(key).add(item);
        }

        List<SolicitudExamenDto> result = new ArrayList<>();

        for (String key: groupedData.keySet()) {
            List<SolicitudExamenDto> items = groupedData.get(key);
            result.add(items.get(0));
        }

        data.setListaSolExa(result);

        this.loggerInfo("Fin listaSolicitudExamen", formatter.format(new Date()));
        return data;
    }

}

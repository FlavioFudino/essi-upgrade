package gob.pe.essalud.client.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
import gob.pe.essalud.client.common.util.PropertiesUtil;
import gob.pe.essalud.client.dto.TokenRegistroRequestDto;
import gob.pe.essalud.client.service.TokenService;

@Service
public class TokenServiceImpl extends BaseService implements TokenService {

    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    public TokenServiceImpl(RestTemplate restTemplate, PropertiesUtil propertiesUtil) {
        this.restTemplate = restTemplate;
    }


    public Map token(String autorization) {
        this.loggerInfo("Inicio token", formatterHour.format(new Date()));
        String base64Credentials = autorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        //
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", Constantes.URL_TOKEN_USER_SECURITY);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(Constantes.URL_GRANT_TYPE, Constantes.URL_ACCESS);
        body.add(Constantes.URL_USERNAME, values[0]);
        body.add(Constantes.URL_ACCESS, values[1]);

        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_OAUTH))
                .path(Constantes.URL_TOKEN).build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerInfo("Fin token", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public Map retoken(Map token) {
        this.loggerInfo("Inicio retoken", formatterHour.format(new Date()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", Constantes.URL_TOKEN_USER_SECURITY);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(Constantes.URL_GRANT_TYPE, Constantes.URL_RETOKEN);
        body.add(Constantes.URL_RETOKEN, token.get(Constantes.URL_RETOKEN).toString());

        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_OAUTH))
                .path(Constantes.URL_TOKEN).build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerInfo("Fin retoken", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public Map tokenRegistro(TokenRegistroRequestDto token) {
        this.loggerInfo("Inicio tokenRegistro", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX) + "token-registro/")
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(token, this.getHttpHeader());
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerInfo("Fin tokenRegistro", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public Map tokenActivar(TokenRegistroRequestDto token) {
        this.loggerInfo("Inicio tokenActivar", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX) + "token-registro/")
                .path(Constantes.URL_TOKEN_ACTIVAR).build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(token, this.getHttpHeader());
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerInfo("Fin tokenActivar", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public Map validarToken(TokenRegistroRequestDto token) {
        this.loggerInfo("Inicio validarToken", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX) + "token-registro/")
                .path(Constantes.URL_TOKEN_VALIDAR).build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(token, this.getHttpHeader());
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerInfo("Fin validarToken", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public Map existeTokenActivo(TokenRegistroRequestDto token) {
        this.loggerInfo("Inicio existeTokenActivo", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX) + "token-registro/")
                .path(Constantes.URL_TOKEN_EXISTE_ACTIVO).build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(token, this.getHttpHeader());
        this.loggerInfo(Constantes.INFO_URL, url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerInfo("Fin existeTokenActivo", formatterHour.format(new Date()));
        return response.getBody();
    }

    @Override
    public Map sendToken(Map paramInput) {
        this.loggerInfo("Inicio sendToken", formatterHour.format(new Date()));
        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX) + "token/")
                .path(Constantes.URL_SEND_TOKEN)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);
        this.loggerInfo("Fin sendToken", formatterHour.format(new Date()));
        return response.getBody();
    }
}

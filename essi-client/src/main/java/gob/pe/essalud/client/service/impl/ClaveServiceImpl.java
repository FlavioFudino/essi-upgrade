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
import gob.pe.essalud.client.common.constants.CaptchaAction;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.common.util.MaskUtil;
import gob.pe.essalud.client.common.util.PropertiesUtil;
import gob.pe.essalud.client.common.util.SecurityUtil;
import gob.pe.essalud.client.dto.ClaveChangeRequestDto;
import gob.pe.essalud.client.dto.ClaveRecoveryRequestDto;
import gob.pe.essalud.client.dto.ClaveRecoveryResponseDto;
import gob.pe.essalud.client.service.CaptchaService;
import gob.pe.essalud.client.service.ClaveService;
import gob.pe.essalud.client.service.SeguridadClienteService;
import gob.pe.essalud.client.service.ServiceException;

@Service
public class ClaveServiceImpl extends BaseService implements ClaveService {

    @Autowired
    private final RestTemplate restTemplate;
    private final CaptchaService captchaService;
    private final SeguridadClienteService seguridadClienteService;

    private final int INTENTOS_RESTANTES_INDEFINIDO = -1;

    @Autowired
    public ClaveServiceImpl(RestTemplate restTemplate,
                            PropertiesUtil propertiesUtil,
                            CaptchaService captchaService,
                            SeguridadClienteService seguridadClienteService) {

        this.restTemplate = restTemplate;
        this.captchaService = captchaService;
        this.seguridadClienteService = seguridadClienteService;
    }


    @Override
    public ResponseDto<ClaveRecoveryResponseDto> recovery(ClaveRecoveryRequestDto paramInput, String captchaToken,boolean validarCaptcha) {
        this.loggerDebug("Inicio recovery", formatterHour.format(new Date()));

        if (validarCaptcha)
            captchaService.process(captchaToken, CaptchaAction.GENERATE_TOKEN_RESET);

        seguridadClienteService.verificarAcceso();

        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX))
                .path(Constantes.URL_PATH_RECOVERY)
                .build().encode().toUriString();
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        this.loggerDebug("URL recovery", url);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);

        if (!response.getBody().get("codResult").toString().equals(Constantes.RETORNO_EXITO_TRX)) {
            if (validarCaptcha)
                captchaService.incrementClientAttempts();

            seguridadClienteService.incrementarIntento("Recuperacion de Clave","Usuario",paramInput.getNumeroDocIden(),
                    "Origen", paramInput.getOrigin(), "Error en el proceso de Recuperacion de Clave, El Usuario puede que no exista");

            this.validarIntentosRestantesMsg("Usted no se encuentra registrado en esta aplicación, por favor proceda con el registro.");
        }

        Map data = (Map)response.getBody().get("data");
        String correo = data.get("correo").toString();
        correo = MaskUtil.MaskEmail(correo);

        ResponseDto<ClaveRecoveryResponseDto> responseDto = new ResponseDto<>();
        responseDto.setCodResult(Integer.parseInt(String.valueOf(response.getBody().get("codResult"))));
        responseDto.setMessage(String.valueOf(response.getBody().get("message")));
        responseDto.setData(new ClaveRecoveryResponseDto(correo));

        this.loggerDebug("Fin recovery", formatterHour.format(new Date()));
        return responseDto;
    }

    @Override
    public Map save(ClaveChangeRequestDto paramInput, String captchaToken,boolean validarCaptcha) {
        this.loggerDebug("Inicio save", formatterHour.format(new Date()));

        if (validarCaptcha)
            captchaService.process(captchaToken, CaptchaAction.RESET);

        seguridadClienteService.verificarAcceso();

        this.validarCaracteresCambiarClave(paramInput.getNuevaClave());

        String url = UriComponentsBuilder.fromUriString(this.getProperty(Constantes.URL_ENDPOINT_BASE_TRX))
                .path(Constantes.URL_PATH_CLAVE)
                .build().encode().toUriString();
        this.loggerDebug("URL save", url);
        HttpEntity<?> httpEntity = new HttpEntity<>(paramInput, this.getHttpHeader());
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                Map.class);

        if (!response.getBody().get("codResult").toString().equals(Constantes.RETORNO_EXITO_TRX)) {
            if (validarCaptcha)
                captchaService.incrementClientAttempts();

            seguridadClienteService.incrementarIntento("Cambiar Clave","Token",paramInput.getToken(),
                    "NuevaClave",paramInput.getNuevaClave(),"Error al cambiar la contraseña, El enlace puede que no sea valido o ha expirado.");

            this.validarIntentosRestantesMsg("No se pudo cambiar su contraseña, contacte al Administrador.");
        }

        if (validarCaptcha)
            captchaService.success();

        this.loggerDebug("Fin save", formatterHour.format(new Date()));
        return response.getBody();
    }

    private void validarCaracteresCambiarClave(String password) {
        String sVerifyPasswordResult = SecurityUtil.verifyPassword(password);
        if (sVerifyPasswordResult != null)
            throw new ServiceException(sVerifyPasswordResult);
    }

    private void validarIntentosRestantesMsg(String message) {
        int intentosRestantes = seguridadClienteService.obtenerIntentosRestantes();

        if (intentosRestantes == INTENTOS_RESTANTES_INDEFINIDO) {
            throw new ServiceException(message);
        }
        else {
            throw new ServiceException(String.format("%s (%s intento(s) restante(s))",message,intentosRestantes));
        }
    }
}

package gob.pe.essalud.client.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.common.util.PropertiesUtil;
import gob.pe.essalud.client.common.util.StringUtil;
import gob.pe.essalud.client.config.CaptchaConfig;
import gob.pe.essalud.client.dto.CaptchaResponseDto;
import gob.pe.essalud.client.service.CaptchaService;
import gob.pe.essalud.client.service.ServiceException;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CaptchaServiceImpl implements CaptchaService {
    private final CaptchaConfig captchaConfig;
    private final RestOperations restTemplate;
    private final CaptchaAttemptServiceImpl captchaAttemptServiceImpl;

    private final HttpServletRequest httpServletRequest;
    private final PropertiesUtil propertiesUtil;

    @Autowired
    public CaptchaServiceImpl(HttpServletRequest httpServletRequest,
                              CaptchaConfig captchaConfig,
                              RestOperations restTemplate,
                              CaptchaAttemptServiceImpl captchaAttemptServiceImpl,
                              PropertiesUtil propertiesUtil) {
        this.httpServletRequest = httpServletRequest;
        this.captchaConfig = captchaConfig;
        this.restTemplate = restTemplate;
        this.captchaAttemptServiceImpl = captchaAttemptServiceImpl;
        this.propertiesUtil = propertiesUtil;
    }

    @Override
    public void process(String captchaToken, String action) {

        if (!captchaConfig.isEnabled())
            return;

        String urlBase = propertiesUtil.getPropertiesString(Constantes.URL_ENDPOINT_CAPTCHA);
        URI verifyUri = URI.create(String.format("%s%s?secret=%s&response=%s&remoteip=%s",
                urlBase, Constantes.URL_SITE_VERIFY, captchaConfig.getSecret(), captchaToken, getClientIP()));

        String sClientIP = getClientIP();

        if (StringUtil.isNullOrEmpty(captchaToken)) {
            incrementClientAttempts();
            throw new ServiceException("Captcha no valido, Vuelva a intentar");
        }

        if (captchaAttemptServiceImpl.isBlocked(sClientIP)) {
            throw new ServiceException("Se excedió el limite de solicitudes máxima");
        }

        CaptchaResponseDto captchaResponse = restTemplate.getForObject(verifyUri, CaptchaResponseDto.class);

        if (captchaResponse == null || !captchaResponse.isSuccess()){
            log.error("RECAPTCHA: is null or not success");
            throw new ServiceException("Captcha no validado correctamente. Vuelva a intentar");
        }

        boolean isSuccessCaptcha =
                captchaResponse.getAction().equals(action) &&
                captchaResponse.getScore() >= captchaConfig.getThreshold();

        if (!isSuccessCaptcha) {
            if (captchaResponse.hasClientError()) {
                incrementClientAttempts();
            }
            log.error("RECAPTCHA: " + captchaResponse.toString());
            throw new ServiceException("Captcha no validado correctamente. Vuelva a intentar");
        }
    }

    @Override
    public void incrementClientAttempts() {
        if (!captchaConfig.isEnabled())
            return;

        String sClientIP = getClientIP();
        captchaAttemptServiceImpl.reCaptchaFailed(sClientIP);
    }

    @Override
    public void success() {
        if (!captchaConfig.isEnabled())
            return;

        String sClientIP = getClientIP();
        captchaAttemptServiceImpl.reCaptchaSucceeded(sClientIP);
    }

    private static final String IP_REGEX = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$";

    private String getClientIP() {
        String ipAddress = httpServletRequest.getHeader("X-Real-IP");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = httpServletRequest.getHeader("X-Forwarded-For");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = httpServletRequest.getRemoteAddr();
        }
        if (ipAddress != null) {
            String[] ipAddressList = ipAddress.split(",");
            ipAddress = ipAddressList[0].trim();

            if (!ipAddress.matches(IP_REGEX) || ipAddress.length() > 15) {
                log.info("[getClientIP]: Dirección IP No Valida" + ":" + ipAddress);
                //throw new RuntimeException("Dirección IP no valida");
                return ipAddress;
            }
        }
        return ipAddress;
    }
}

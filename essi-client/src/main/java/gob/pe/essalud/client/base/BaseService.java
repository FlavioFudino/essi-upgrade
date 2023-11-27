package gob.pe.essalud.client.base;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Data
public abstract class BaseService {
    private static final Logger logger = LogManager.getLogger(BaseService.class);

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private Environment env;

    @PostConstruct
    void init() {
    }

    @Value("${spring.logging.info}")
    private String logginInfo;

    @Value("${essiPacienteKey}")
    private String EssiPacienteKey;

    public String getProperty(String key) {
        return env.getProperty(key);
    }

    public Integer getStatusOk() {
        return Integer.valueOf(200);
    }

    public HttpHeaders getHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            headers.add("ESSIPACIENTE", EssiPacienteKey);
        } catch (Exception e) {
            loggerException("error al obtener headers...", e);
        }
        return headers;
    }

    public HttpHeaders getHttpHeader(MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.add("Content-Type", String.valueOf(mediaType));
        } catch (Exception e) {
            loggerException("error al obtener headers...", e);
        }
        return headers;
    }

    public void loggerException(String title, Exception e) {
        logger.error(title, e);
    }

    public void loggerInfo(String title, String info) {
        if (logginInfo.contains("show"))
            logger.info(title + ":" + info);
    }

    public void loggerError(String title, String info) {
        if (logginInfo.contains("show"))
            logger.error(title + ":" + info);
    }

}
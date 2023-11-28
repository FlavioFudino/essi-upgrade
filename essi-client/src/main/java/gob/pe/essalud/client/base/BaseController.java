package gob.pe.essalud.client.base;

import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.common.http.Response;
import gob.pe.essalud.client.common.http.ResponseType;
import gob.pe.essalud.client.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class BaseController {

    private static final Logger logger = LogManager.getLogger(BaseService.class);

    @Value("${spring.logging.info}")
    private String logginInfo;

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> Response<T> unKnownException(Exception ex) {
        String errMsg = ex.getMessage();
        this.loggerException(errMsg, ex);
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), errMsg, errMsg);
    }

    @ExceptionHandler(value = {HttpClientErrorException.class})
    public HttpEntity<?> clientException(HttpClientErrorException ex) {
        String errMsg = ex.getMessage();
        if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        } else {
            this.loggerException(errMsg, ex);
        }
        return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ServiceException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto serviceException(ServiceException ex) {
        return buildValidationResponse(ex.getMessage(), ex);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto modelValidations(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        return buildValidationResponse(message, ex);
    }

    private ResponseDto buildValidationResponse(String message, Exception ex) {
        ResponseDto response = new ResponseDto<>();
        response.setMessage(message);
        response.setCodResult(ResponseType.VALIDATION);
        return response;
    }

    public void loggerException(String title, Exception e) {
        logger.error(title, e);
    }

    public void loggerInfo(String title, String info) {
        if (logginInfo.contains("show"))
            logger.info(title + ":" + info);
    }

    public void loggerDebug(String title, String info) {
        logger.debug(title + ":" + info);
    }

}
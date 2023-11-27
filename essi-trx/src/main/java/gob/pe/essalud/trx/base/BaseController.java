package gob.pe.essalud.trx.base;

import gob.pe.essalud.trx.common.http.ResponseType;
import gob.pe.essalud.trx.dto.ResponseDto;
import gob.pe.essalud.trx.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseController {

    private static final Logger logger = LogManager.getLogger(BaseService.class);

    @ExceptionHandler(value = {ServiceException.class})
    @ResponseStatus(HttpStatus.OK)
    public <T> ResponseDto<T> serviceException(Exception ex) {
        ResponseDto<T> response = new ResponseDto<>();
        response.setMessage(ex.getMessage());
        response.setCodResult(ResponseType.VALIDATION);
        return response;
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    public <T> ResponseDto<T> modelValidations(MethodArgumentNotValidException ex) {
        ResponseDto<T> response = new ResponseDto<>();
        BindingResult bindingResult = ex.getBindingResult();
        String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        response.setMessage(message);
        response.setCodResult(ResponseType.VALIDATION);
        return response;
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> ResponseDto<T> unKnownException(Exception ex) {
        ResponseDto<T> response = new ResponseDto<>();
        this.loggerException(ex.getMessage(), ex);
        response.setMessage(ex.getMessage());
        response.setCodResult(ResponseType.ERROR);
        return response;
    }

    public void loggerException(String title, Exception e) {
        logger.error(title, e);
    }

    public void loggerInfo(String title, String info) {
        logger.info(title + ":" + info);
    }

}
package gob.pe.essalud.emailservice.dto;

import gob.pe.essalud.emailservice.common.http.ResponseType;
import lombok.Data;

@Data
public class ResponseDto<T> {
    private static final long serialVersionUID = 1L;
    private String message;
    private T Data;
    private int CodResult = ResponseType.SUCCESS;

}
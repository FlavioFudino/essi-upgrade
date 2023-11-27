package gob.pe.essalud.trx.dto;

import gob.pe.essalud.trx.common.http.ResponseType;
import lombok.Data;

@Data
public class ResponseDto<T> {
    private static final long serialVersionUID = 1L;
    private String message;
    private T Data;
    private int CodResult = ResponseType.SUCCESS;

}
package gob.pe.essalud.client.common.dto;

import gob.pe.essalud.client.common.http.ResponseType;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseDto<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private T data;
    private int codResult = ResponseType.SUCCESS;

}
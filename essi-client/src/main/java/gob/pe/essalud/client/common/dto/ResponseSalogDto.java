package gob.pe.essalud.client.common.dto;

import lombok.Data;

@Data
public class ResponseSalogDto<T> {
    private static final long serialVersionUID = 1L;
    private boolean error;
    private String message;
    private int result;
    private int code;
    private T params;

}
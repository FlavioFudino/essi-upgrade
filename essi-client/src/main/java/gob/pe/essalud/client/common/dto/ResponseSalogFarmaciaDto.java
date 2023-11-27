package gob.pe.essalud.client.common.dto;

import lombok.Data;

@Data
public class ResponseSalogFarmaciaDto<T> {
    private static final long serialVersionUID = 1L;
    private boolean error;
    private int code;
    private int rowNum;
    private String message;
    private T result;

}
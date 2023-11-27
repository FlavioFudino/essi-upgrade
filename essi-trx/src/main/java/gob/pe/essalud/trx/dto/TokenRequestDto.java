package gob.pe.essalud.trx.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TokenRequestDto {
    private String token;
    private Date dateCreate;
    private Date dateExpiration;
    private Boolean indConfirmado;
    private String userName;
}

package gob.pe.essalud.trx.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TokenRegistroDto {
    private Long idTokenRegistro;
    private String token;
    private Date dateCreate;
    private Date dateExpiration;
    private Boolean indConfirmado;
    private Long idUsuario;
}

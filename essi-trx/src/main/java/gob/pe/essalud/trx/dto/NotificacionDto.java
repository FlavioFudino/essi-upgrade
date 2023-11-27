package gob.pe.essalud.trx.dto;

import gob.pe.essalud.trx.base.BaseDto;
import lombok.Data;

import java.util.Date;

@Data
public class NotificacionDto extends BaseDto {
    private Date fechaAviso;
    private String tipoDocIdent;
    private String numeroDocIdent;
    private String title;
    private String body;
    private String token;

}

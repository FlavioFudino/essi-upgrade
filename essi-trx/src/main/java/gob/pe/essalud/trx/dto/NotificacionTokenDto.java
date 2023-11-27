package gob.pe.essalud.trx.dto;

import gob.pe.essalud.trx.base.BaseDto;
import lombok.Data;

@Data
public class NotificacionTokenDto extends BaseDto {

    private String title;
    private String body;
    private String token;

}

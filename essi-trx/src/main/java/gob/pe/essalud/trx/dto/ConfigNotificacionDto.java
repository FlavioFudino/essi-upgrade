package gob.pe.essalud.trx.dto;

import gob.pe.essalud.trx.base.BaseDto;
import lombok.Data;

@Data
public class ConfigNotificacionDto extends BaseDto {
    private String oriCenAsiCod;
    private String cenAsiCod;
    private int diasAviso1;
    private int diasAviso2;
    private int diasAviso3;
    private String title;
    private String body;
}

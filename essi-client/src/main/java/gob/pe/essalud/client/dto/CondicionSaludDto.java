package gob.pe.essalud.client.dto;

import gob.pe.essalud.client.base.BaseDto;
import lombok.Data;

@Data
public class CondicionSaludDto extends BaseDto {
    private Integer idPersona;
    private String idCondicionSalud;
}

package gob.pe.essalud.trx.dto;

import gob.pe.essalud.trx.base.BaseDto;
import lombok.Data;

@Data
public class UbigeoRequestDto extends BaseDto {
    private String ubigeo;
    private String descripcion;
}

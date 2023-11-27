package gob.pe.essalud.client.dto.asegurado;

import gob.pe.essalud.client.base.BaseDto;
import lombok.Data;

@Data
public class CondicionHogarDto extends BaseDto {

    private Integer idPersona;
    private int cantAmbientes;
    private Boolean indAgua;
    private Boolean indSshh;

}

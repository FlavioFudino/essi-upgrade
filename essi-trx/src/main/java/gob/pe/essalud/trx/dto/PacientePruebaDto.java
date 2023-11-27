package gob.pe.essalud.trx.dto;

import gob.pe.essalud.trx.base.BaseDto;
import lombok.Data;

@Data
public class PacientePruebaDto extends BaseDto {
    private String tipoDocIdent;
    private String numeroDocIdent;
    private String correo;
    private Boolean existe = false;
}

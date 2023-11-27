package gob.pe.essalud.client.dto.asegurado;

import gob.pe.essalud.client.base.BaseDto;
import lombok.Data;

@Data
public class CondicionHogarRegisterDto extends BaseDto {
    private String tipoDocIdent;
    private String numeroDocIdent;
    private int cantAmbientes;
    private boolean indAgua;
    private boolean indSshh;

}

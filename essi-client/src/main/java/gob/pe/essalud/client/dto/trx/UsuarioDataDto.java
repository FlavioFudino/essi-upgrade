package gob.pe.essalud.client.dto.trx;

import lombok.Data;

@Data
public class UsuarioDataDto extends gob.pe.essalud.client.base.BaseDto {
    private String codTipDoc;
    private String numDoc;
}

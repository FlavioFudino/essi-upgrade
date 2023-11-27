package gob.pe.essalud.trx.dto;

import gob.pe.essalud.trx.base.BaseDto;
import lombok.Data;

@Data
public class ContactoCercanoRequestDto extends BaseDto {
    private String tipoDocIdent;
    private String numeroDocIdent;
    private PersonaCercanaDto personaCercano;
}
